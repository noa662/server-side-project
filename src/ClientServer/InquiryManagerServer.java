package ClientServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class InquiryManagerServer {
    ServerSocket myServer;
    public InquiryManagerServer(){
        try {
            myServer=new ServerSocket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
   public void Start(){
       try {
          Socket clientSocket= myServer.accept();
          HandleClient handleClient=new HandleClient(clientSocket);
          Thread thread=new Thread(handleClient);
          thread.start();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

    public void Stop(){
        try {
            myServer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
