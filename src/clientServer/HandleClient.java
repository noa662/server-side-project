package clientServer;

import Data.Inquiry;
import business.InquiryManager;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class HandleClient extends Thread {
    Socket clientSocket;
    ObjectInputStream in;
    ObjectOutputStream out;

    public HandleClient(Socket socket) {
        clientSocket = socket;
    }

    public void HandleClientRequest() {

        try {

            in =new ObjectInputStream(clientSocket.getInputStream());
            RequestData request = (RequestData) in.readObject();
            out=new ObjectOutputStream(clientSocket.getOutputStream());
            switch (request.getAction()) {
                case ADD_INQUIRY: {
                    try {
                        InquiryManager.addInquiryToQueue((Inquiry) request.getParameters()[0]);
                        out.writeObject(new ResponseData(ResponseStatus.SCCESS, "Your request has been successfully received.", null));
                        out.flush();
                    } catch (Exception e) {
                        out.writeObject(new ResponseData(ResponseStatus.FAIL, e.getMessage(), null));
                    }
                }

                case ALL_INQUIRY: {
                    try {
                        LinkedBlockingQueue<Inquiry> q = (LinkedBlockingQueue<Inquiry>) InquiryManager.getAllInquiries();
                        out.writeObject(new ResponseData(ResponseStatus.SCCESS, "Your request has been successfully received.", q));
                        out.flush();
                    } catch (Exception e) {
                        out.writeObject(new ResponseData(ResponseStatus.FAIL, e.getMessage(), null));
                    }
                }

                default: {
                    out =new ObjectOutputStream(clientSocket.getOutputStream());
                    out.writeObject(new ResponseData(ResponseStatus.FAIL, "no such action", null));
                    in.close();
                   out.close();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        HandleClientRequest();
    }

}
