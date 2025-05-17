/**
This is the GameServer class. It manages game sessions and player connections.
@author Edward Francis S. Ibera (242267) & Erin Clare G. Maranan (242916)
@version 19 May 2025
We have not discussed the Java language code in our program
with anyone other than our instructor or the teaching assistants
assigned to this course.
We have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.
If any Java language code or documentation used in our program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of our program.
**/
import java.io.*;
import java.net.*;
import java.util.*;

public class GameServer {
    private ServerSocket ss;
    private ArrayList<Socket> sockets;
    private int playerCount = 0;
    private Socket p1Socket, p2Socket;

    /**
     * This Constructor creates a new GameServer object
     */
    public GameServer(){
        sockets = new ArrayList<>();
        try {
            ss = new ServerSocket(9145);
        } catch (IOException ex) {
            System.out.println("IOException from GameServer Constructor.");
        }

        System.out.println("GAME SERVER HAS BEEN CONNECTED.");
    }

    /**
     * waits for and accepts player connections
     */
    public void waitForPlayerConnection(){
        try{
            System.out.println("NOW ACCEPTING PLAYER CONNECTIONS...");
            while(true){
                Socket pSocket = ss.accept();
                sockets.add(pSocket);
                playerCount++;
                if (sockets.size() >= 2){
                    p1Socket = sockets.remove(0);
                    p2Socket = sockets.remove(0);
                    GameSession gSession = new GameSession(p1Socket, p2Socket);
                    gSession.startThread();
                }
            }            
        } catch (IOException ex){
            System.out.println("IOException from waitForPlayerConnection method.");
        }
    }

    /**
     * inner class representing a game session between the two players
     */
    private class GameSession implements Runnable{
		private Socket p1Socket, p2Socket;
		private DataInputStream dataIn1, dataIn2;
		private DataOutputStream dataOut1, dataOut2;
		private boolean gameStarted = false;
		private boolean p1Ready = false;
		private boolean p2Ready = false;

        /**
         * This Constructor creates a new GameSession for the two players
         * @param p1 first player's socket
         * @param p2 second player's socket
         */
		public GameSession (Socket p1, Socket p2){
			p1Socket = p1;
			p2Socket = p2;
            gameWin = false;
        
			try{
				dataIn1 = new DataInputStream(p1Socket.getInputStream());
				dataOut1 = new DataOutputStream(p1Socket.getOutputStream());
				dataIn2 = new DataInputStream(p2Socket.getInputStream());
				dataOut2 = new DataOutputStream(p2Socket.getOutputStream());
			} catch (IOException ex){
				System.out.println("IOException from GameSession constructor.");
			}
		}

        /**
         * starts the game session thread
         */
		public void startThread(){
			Thread t = new Thread(this);
			t.	start();
		}
		
        /**
         * runs the main game session logic
         */
		public void run() {
			try {
				dataOut1.writeUTF("HELLO! WELCOME TO [GAME NAME]! You are Player 1");
				dataOut2.writeUTF("HELLO! WELCOME TO [GAME NAME]! You are Player 2");
				dataOut1.writeUTF("Player 1");
				dataOut2.writeUTF("Player 2");
				System.out.println("2 players are in this game session.");

				// wait for both players to send "PLAYER_READY" to start the gam
				while (!p1Ready || !p2Ready) { 
					if (!p1Ready) {
						String msg = dataIn1.readUTF();
						if (msg.equals("PLAYER_READY")) {
							p1Ready = true;
						}
					}

					if (!p2Ready) {
						String msg = dataIn2.readUTF();
						if (msg.equals("PLAYER_READY")) {
							p2Ready = true;
						}
					}
				}

				// notify both players that the game is starting
				dataOut1.writeUTF("START_GAME");
				dataOut2.writeUTF("START_GAME");
				dataOut1.flush();
				dataOut2.flush();

				// Initial level sync - get starting levels from both players
				int level1 = dataIn1.readInt();
				int level2 = dataIn2.readInt();

				// Track the highest level reached by either player
				int syncedLevel = 0;

				while (true) {
					// keep both players at the same level progression (the one with the higher level is the one used to sync the players' levels)
					// stops at level 3 since level 3 is the final level
					if (syncedLevel < 3) {
						level1 = dataIn1.readInt();
						level2 = dataIn2.readInt();
						syncedLevel = Math.max(level1, level2);
						dataOut1.writeInt(syncedLevel);
						dataOut2.writeInt(syncedLevel);
					}
				
					// syncing for the boss battle / level 3
					// only starts at level 3 since position sync, boss hp sync, game over state sync, is only needed in level 3
					if (syncedLevel == 3) {
						// Source: Vivien Barousse' answer to "What does InputStream.available() do in Java?" in StackOverflow
						// URL: https://stackoverflow.com/questions/3695372/what-does-inputstream-available-do-in-java
						// Accessed: May 10, 2025
						// Note: Used to know how dataIn.available() works.
						while (dataIn1.available() > 0) { // Player 1's data to Player 2
							String msgType = dataIn1.readUTF();
							if (msgType.equals("POSITION")) { // position sync
								int x = dataIn1.readInt();
								int y = dataIn1.readInt();
								dataOut2.writeUTF("POSITION");
								dataOut2.writeInt(x);
								dataOut2.writeInt(y);
								dataOut2.flush();
							} 
							else if (msgType.equals("BOSSHP")) { // boss HP sync
								int hp1 = dataIn1.readInt();
								dataOut2.writeUTF("BOSSHP");
								dataOut2.writeInt(hp1);
								dataOut2.flush();
							}
							else if (msgType.equals("GAMEOVER")) { // game over state sync
								boolean gameOver1 = dataIn1.readBoolean();
								dataOut2.writeUTF("GAMEOVER");
								dataOut2.writeBoolean(gameOver1);
								dataOut2.flush();
							}
						}

						while (dataIn2.available() > 0) { // Player 2's data to Player 1
							String msgType = dataIn2.readUTF();
							if (msgType.equals("POSITION")) { // position sync
								int x = dataIn2.readInt();
								int y = dataIn2.readInt();
								dataOut1.writeUTF("POSITION");
								dataOut1.writeInt(x);
								dataOut1.writeInt(y);
								dataOut1.flush();
							}
							else if (msgType.equals("BOSSHP")) { // boss HP sync
								int hp2 = dataIn2.readInt();
								dataOut1.writeUTF("BOSSHP");
								dataOut1.writeInt(hp2);
								dataOut1.flush();
							}
							else if (msgType.equals("GAMEOVER")) { // game over state sync
								boolean gameOver2 = dataIn2.readBoolean();
								dataOut1.writeUTF("GAMEOVER");
								dataOut1.writeBoolean(gameOver2);
								dataOut1.flush();
							}
						}
					}
					Thread.sleep(10);
				}
			} catch (Exception ex) {
				System.out.println("Exception in GameSession run()");
			}
		}	
	}

    /**
     * ensures sockets are closed when the program shuts down
     */
    public void closeSocketsOnShutdown(){
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            try{
                for (Socket s : sockets){
                    s.close();
                }

            } catch (IOException e){
                System.out.println("IOException from closeSocketOnShutdown method.");
            }
        }));
    }


    /**
     * main method to start the server
     * @param args command line arguments
     */
    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.closeSocketsOnShutdown();
        gs.waitForPlayerConnection();
    }
}