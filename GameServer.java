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

				int level1 = dataIn1.readInt();
				int level2 = dataIn2.readInt();

				while (true) {
					level1 = dataIn1.readInt();
					level2 = dataIn2.readInt();

					if (level1 > level2) {
						dataOut2.writeInt(level1);
					} else if (level2 > level1) {
						dataOut1.writeInt(level2);
					} else {
						dataOut1.writeInt(level1);
						dataOut2.writeInt(level2);
					}
				}

			} catch (IOException ex) {
				System.out.println("IOException from GameSession run() method.");
			}
		}

        public boolean gameIsWon(){
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