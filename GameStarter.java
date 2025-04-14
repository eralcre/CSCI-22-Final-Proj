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
            System.out.println("ATTEMPTING TO CONNECTE TO SERVER...");
            pSocket = new Socket(ipAdress, portNum);
            System.out.println("CONNECTION SUCCESSFUL!");
            dataIn = new DataInputStream(pSocket.getInputStream());
            dataOut = new DataOutputStream(pSocket.getOutputStream());
            // gameStart = new GameFrame();
        } catch(IOException e){
            System.out.println("IOException from ConnectToServer() method.");
        }
    }

    public void waitForOtherPlayer(){
        try{
            System.out.println(dataIn.readUTF()+" ");
            System.out.println("WAITING FOR OTHER PLAYER...");
            while(true){
                
            }
        }catch(IOException e){
            System.out.println("IOException from chat() method.");
        }
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
    }
}