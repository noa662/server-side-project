package clientServer;

import data.Inquiry;
import business.InquiryManager;

import java.io.*;
import java.net.Socket;
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
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                out.flush();
                in = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            RequestData request = (RequestData) in.readObject();
            switch (request.getAction()) {
                case ADD_INQUIRY: {
                    try {
                        InquiryManager.addInquiryToQueue((Inquiry) request.getParameters()[0],true);
                        out.writeObject(new ResponseData(ResponseStatus.SUCCESS, "Your request has been successfully received.", null));
                        out.flush();
                    } catch (Exception e) {
                        out.writeObject(new ResponseData(ResponseStatus.FAIL, e.getMessage(), null));
                    }
                    break;
                }

                case ALL_INQUIRY: {
                    try {
                        LinkedBlockingQueue<Inquiry> q = (LinkedBlockingQueue<Inquiry>) InquiryManager.getAllInquiries();
                        out.writeObject(new ResponseData(ResponseStatus.SUCCESS, "Your request has been successfully received.", q));
                    } catch (Exception e) {
                        out.writeObject(new ResponseData(ResponseStatus.FAIL, e.getMessage(), null));
                    }
                    break;
                }

                default: {
                    out = new ObjectOutputStream(clientSocket.getOutputStream());
                    out.writeObject(new ResponseData(ResponseStatus.FAIL, "no such action", null));
                    closeConnection();
                }
                closeConnection();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            System.out.println("connection to the server was closed.");
        } catch (IOException e) {
            System.out.println("error close the connection " + e.getMessage());
        }
    }

    @Override
    public void run() {
        HandleClientRequest();
    }

}
