package ClientServer;

import Data.Inquiry;
import business.InquiryManager;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;

public class HandleClient extends Thread{
    Socket clientSocket;

    public HandleClient(Socket socket){
        clientSocket=socket;
    }

    public void HandleClientRequest(){
        RequestData requestData=new RequestData();
        ResponseData responseData=new ResponseData();
        try {
            ObjectInputStream in= (ObjectInputStream) clientSocket.getInputStream();
            ObjectOutputStream out= (ObjectOutputStream) clientSocket.getOutputStream();
            RequestData request= (RequestData) in.readObject();

            switch (request.getAction()){
                case ADD_INQUIRY:{
                    try{
                        InquiryManager.addInquiryToQueue((Inquiry) request.getParameters());
                        out.writeObject(new ResponseData(ResponseStatus.SCCESS,"Your request has been successfully received.",null));
                    }
                    catch (Exception e){
                        out.writeObject(new ResponseData(ResponseStatus.FAIL,e.getMessage(),null));
                    }
                }

                case ALL_INQUIRY:{
                    try{
                        BlockingDeque<Inquiry>q= (BlockingDeque<Inquiry>) InquiryManager.getAllInquiries();
                        out.writeObject(new ResponseData(ResponseStatus.SCCESS,"Your request has been successfully received.",q));
                    }
                    catch (Exception e){
                        out.writeObject(new ResponseData(ResponseStatus.FAIL,e.getMessage(),null));
                    }
                }

                default:{
                    out.writeObject(new ResponseData(ResponseStatus.FAIL,"no such action",null));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run(){
        HandleClientRequest();
    }

}
