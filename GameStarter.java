/**
This is the GameStarter class. It handles client-side connection to the game server and player synchronization.
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

public class GameStarter {
    private Scanner playerInput;
    private Socket pSocket;
    private GameFrame gameStart;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    /**
     * This Constructor creates a new GameStarter object
     */
    public GameStarter(){
        playerInput = new Scanner(System.in);
    }

    /**
     * connects player to the game server using user-provided IP and port
     */
    public void ConnectToGameServer(){
        try{
            System.out.print("Please input the server's IP Address: ");
            String ipAdress = playerInput.nextLine();
            // System.out.print("Please input the port number: ");
            // int portNum = Integer.parseInt(playerInput.nextLine());
            System.out.println("ATTEMPTING TO CONNECT TO SERVER...");
            pSocket = new Socket(ipAdress, 9145);
            System.out.println("CONNECTION SUCCESSFUL!");
            dataIn = new DataInputStream(pSocket.getInputStream());
            dataOut = new DataOutputStream(pSocket.getOutputStream());
        } catch(IOException e){
            System.out.println("IOException from ConnectToServer() method.");
        }
    }
	
    /**
     * waits for the other player to connect
     */
    public void waitForOtherPlayer(){
        try{
			System.out.println("WAITING FOR OTHER PLAYER...");
			System.out.println(dataIn.readUTF()+" ");
			String playerNumber = dataIn.readUTF();
			gameStart = new GameFrame(playerNumber);
        }catch(IOException e){
            System.out.println("IOException from chat() method.");
        }
    }

	 /**
	 * notifies the server when the player is ready
	 * handles the server's response to begin gameplay
	 */
	public void notifyPlayerReady() {
		try {
			if (gameStart.isPlayerReady()) { // check if this GameCanvas' 'playerReady' is true through the GameFrame
				dataOut.writeUTF("PLAYER_READY"); // send "PLAYER_READY" to server
				dataOut.flush();
				
				String response = dataIn.readUTF(); // wait for server's START_GAME response
				if (response.equals("START_GAME")) {
					gameStart.startGameFromServer(); // start game
					dataOut.writeInt(gameStart.updateGameLevel()); // send initial level to server to begin level synchronization
					startSync(); // start the syncing of the player's elements
				}
			}
		} catch (IOException e) {
			System.out.println("IOException from notifyPlayerReady() method.");
		}
	}

    /**
     * thread that constantly checks the GameFrame's playerReady() method
	 * If true, then it calls the notifyPlayerReady()
     */
	public void waitForPlayerReady() {
		new Thread(() -> {
			try {
				while (true) {
					if (gameStart.isPlayerReady()) {
						notifyPlayerReady();
						break;
					}
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

    /**
     * starts synchronization of game state between players
	 * if level is less than 3 then the only syncing it does is the syncing of levels
	 * if the level becomes 3 (final level), it stops syncing the level and focuses on syncing the player's positions, boss HPs, and game over states
	 * only gets called when players are both ready (notifyPlayerReady())
     */
	public void startSync() {
		new Thread(() -> {
			try {
				int syncedLevel = 0;
				
				while (true) {
					if (syncedLevel < 3) {
						int currentLevel = gameStart.updateGameLevel();
						dataOut.writeInt(currentLevel);
						syncedLevel = dataIn.readInt();
						gameStart.setLevel(syncedLevel);
					}

					if (syncedLevel == 3) {
						dataOut.writeUTF("POSITION");
						dataOut.writeInt(gameStart.getPlayerX());
						dataOut.writeInt(gameStart.getPlayerY());
						dataOut.flush();

						dataOut.writeUTF("BOSSHP");
						dataOut.writeInt(gameStart.getBossHp());
						dataOut.flush();

						dataOut.writeUTF("GAMEOVER");
						dataOut.writeBoolean(gameStart.isGameOver());
						dataOut.flush();

						// Source: Vivien Barousse' answer to "What does InputStream.available() do in Java?" in StackOverflow
						// URL: https://stackoverflow.com/questions/3695372/what-does-inputstream-available-do-in-java
						// Accessed: May 10, 2025
						// Note: Used to know how dataIn.available() works.
						while (dataIn.available() > 0) {
							String msgType = dataIn.readUTF();
							if (msgType.equals("POSITION")) {
								int x = dataIn.readInt();
								int y = dataIn.readInt();
								gameStart.updateOtherPlayerPosition(x, y);
							} 
							else if (msgType.equals("BOSSHP")) {
								int otherPlayerHp = dataIn.readInt();
								int currentHp = gameStart.getBossHp();
								if (otherPlayerHp < currentHp) {
									gameStart.updateBossHp(otherPlayerHp);
								}
							}
							else if (msgType.equals("GAMEOVER")) {
								boolean otherGameOver = dataIn.readBoolean();
								if (otherGameOver) {
									gameStart.updateGameOver(true);
								}
							}
						}
					}
					Thread.sleep(100);
				}
			} catch (Exception e) {
				System.out.println("Sync error: " + e.getMessage());
			}
		}).start();
	}

    /**
     * ensures sockets are closed when the program shuts down
     */
    public void closeSocketOnShutdown(){
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            try{
                pSocket.close();
            }catch(IOException e){
                System.out.println("IOException from closeSocketOnShutdown() method.");
            }
        }));
    }

    /**
     * main method to start the client
     * @param args command line arguments
     */
    public static void main(String[] args) {
        GameStarter cc = new GameStarter();
        cc.closeSocketOnShutdown();
        cc.ConnectToGameServer();
	    cc.waitForOtherPlayer();
		cc.waitForPlayerReady();
    }
}