import java.io.*;
import java.net.*;
import java.util.*;

public class GameStarter {
    private Scanner playerInput;
    private Socket pSocket;
    private GameFrame gameStart;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    public GameStarter(){
        playerInput = new Scanner(System.in);
    }

    public void ConnectToGameServer(){
        try{
            System.out.print("Please input the server's IP Address: ");
            String ipAdress = playerInput.nextLine();
            System.out.print("Please input the port number: ");
            int portNum = Integer.parseInt(playerInput.nextLine());
            System.out.println("ATTEMPTING TO CONNECT TO SERVER...");
            pSocket = new Socket(ipAdress, portNum);
            System.out.println("CONNECTION SUCCESSFUL!");
            dataIn = new DataInputStream(pSocket.getInputStream());
            dataOut = new DataOutputStream(pSocket.getOutputStream());
        } catch(IOException e){
            System.out.println("IOException from ConnectToServer() method.");
        }
    }

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

	public void notifyPlayerReady() {
		try {
			if (gameStart.isPlayerReady()) {
				dataOut.writeUTF("PLAYER_READY");
				dataOut.flush();

				String response = dataIn.readUTF();
				if (response.equals("START_GAME")) {
					gameStart.startGameFromServer();
					dataOut.writeInt(gameStart.updateGameLevel());
					startSync();
				}
			}
		} catch (IOException e) {
			System.out.println("IOException from notifyPlayerReady() method.");
		}
	}
	
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

						if (gameStart.shouldSendAttack()) {
							dataOut.writeUTF("ATTACK");
							dataOut.writeInt(gameStart.getPlayerX());
							dataOut.writeInt(gameStart.getPlayerY());
						}

						if (dataIn.available() > 0) {
							String msg = dataIn.readUTF();
							if (msg.equals("POSITION")) {
								int x = dataIn.readInt();
								int y = dataIn.readInt();
								gameStart.updateOtherPlayerPosition(x, y);
							}
							else if (msg.equals("ATTACK")) {
								int x = dataIn.readInt();
								int y = dataIn.readInt();
								gameStart.spawnOtherPlayerAttack(x, y);
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

    public void closeSocketOnShutdown(){
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            try{
                pSocket.close();
            }catch(IOException e){
                System.out.println("IOException from closeSocketOnShutdown() method.");
            }
        }));
    }

    public static void main(String[] args) {
        GameStarter cc = new GameStarter();
        cc.closeSocketOnShutdown();
        cc.ConnectToGameServer();
	    cc.waitForOtherPlayer();
		cc.waitForPlayerReady();
    }
}