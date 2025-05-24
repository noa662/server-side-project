package clientServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class InquiryManagerServer {
    ServerSocket myServer;

    public InquiryManagerServer(){
        try {
            myServer=new ServerSocket(3030);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   public void start(){
       try {
           while (true) {
               Socket clientSocket = myServer.accept();
               HandleClient handleClient = new HandleClient(clientSocket);
               System.out.println("client connected" + clientSocket.getInetAddress());
               Thread thread = new Thread(handleClient);
               thread.start();
           }
       } catch (IOException e) {
           e.printStackTrace();
           stop();
       }
   }

    public void stop(){
        try {
            myServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        InquiryManagerServer server=new InquiryManagerServer();
//        server.start();
    }
}
