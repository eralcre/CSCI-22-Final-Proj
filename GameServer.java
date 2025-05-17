import java.io.*;
import java.net.*;
import java.util.*;

public class GameServer {
    private ServerSocket ss;
    private ArrayList<Socket> sockets;
    private int playerCount = 0;
    private Socket p1Socket, p2Socket;

    public GameServer(){
        sockets = new ArrayList<>();
        try {
            ss = new ServerSocket(9145);
        } catch (IOException ex) {
            System.out.println("IOException from GameServer Constructor.");
        }

        System.out.println("GAME SERVER HAS BEEN CONNECTED.");
    }

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

    private class GameSession implements Runnable{
		private Socket p1Socket, p2Socket;
		private DataInputStream dataIn1, dataIn2;
		private DataOutputStream dataOut1, dataOut2;
        private boolean gameWin;
        public int gameLevel;
		private boolean gameStarted = false;
		private boolean p1Ready = false;
		private boolean p2Ready = false;

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

		public void startThread(){
			Thread t = new Thread(this);
			t.start();
		}

		public void run() {
			try {
				dataOut1.writeUTF("HELLO! WELCOME TO [GAME NAME]! You are Player 1");
				dataOut2.writeUTF("HELLO! WELCOME TO [GAME NAME]! You are Player 2");
				dataOut1.writeUTF("Player 1");
				dataOut2.writeUTF("Player 2");
				System.out.println("2 players are in this game session.");

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

				dataOut1.writeUTF("START_GAME");
				dataOut2.writeUTF("START_GAME");
				dataOut1.flush();
				dataOut2.flush();

				int level1 = dataIn1.readInt();
				int level2 = dataIn2.readInt();

				int syncedLevel = 0;

				while (true) {
					if (syncedLevel < 3) {
						level1 = dataIn1.readInt();
						level2 = dataIn2.readInt();
						syncedLevel = Math.max(level1, level2);
						dataOut1.writeInt(syncedLevel);
						dataOut2.writeInt(syncedLevel);
					}

					if (syncedLevel == 3) {
						while (dataIn1.available() > 0) {
							String msgType = dataIn1.readUTF();
							if (msgType.equals("POSITION")) {
								int x = dataIn1.readInt();
								int y = dataIn1.readInt();
								dataOut2.writeUTF("POSITION");
								dataOut2.writeInt(x);
								dataOut2.writeInt(y);
								dataOut2.flush();
							} 
							else if (msgType.equals("BOSSHP")) {
								int hp1 = dataIn1.readInt();
								dataOut2.writeUTF("BOSSHP");
								dataOut2.writeInt(hp1);
								dataOut2.flush();
							}
							else if (msgType.equals("GAMEOVER")) {
								boolean gameOver1 = dataIn1.readBoolean();
								dataOut2.writeUTF("GAMEOVER");
								dataOut2.writeBoolean(gameOver1);
								dataOut2.flush();
							}
							else if (msg.equals("ATTACK")) {
								int x = dataIn1.readInt();
								int y = dataIn1.readInt();
								dataOut2.writeUTF("ATTACK");
								dataOut2.writeInt(x);
								dataOut2.writeInt(y);
							}
						}

						while (dataIn2.available() > 0) {
							String msgType = dataIn2.readUTF();
							if (msgType.equals("POSITION")) {
								int x = dataIn2.readInt();
								int y = dataIn2.readInt();
								dataOut1.writeUTF("POSITION");
								dataOut1.writeInt(x);
								dataOut1.writeInt(y);
								dataOut1.flush();
							}
							else if (msgType.equals("BOSSHP")) {
								int hp2 = dataIn2.readInt();
								dataOut1.writeUTF("BOSSHP");
								dataOut1.writeInt(hp2);
								dataOut1.flush();
							}
							else if (msgType.equals("GAMEOVER")) {
								boolean gameOver2 = dataIn2.readBoolean();
								dataOut1.writeUTF("GAMEOVER");
								dataOut1.writeBoolean(gameOver2);
								dataOut1.flush();
							}
							else if (msg.equals("ATTACK")) {
								int x = dataIn2.readInt();
								int y = dataIn2.readInt();
								dataOut1.writeUTF("ATTACK");
								dataOut1.writeInt(x);
								dataOut1.writeInt(y);
							}
						}
					}
					Thread.sleep(10);
				}
			} catch (Exception ex) {
				System.out.println("Exception in GameSession run()");
			}
		}

		public boolean gameIsWon() {
			if(gameLevel>3){
				gameWin = true;
			}
			return gameWin;
		}
	
	}

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

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.closeSocketsOnShutdown();
        gs.waitForPlayerConnection();
    }
}