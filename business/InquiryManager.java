package business;
import Data.Complaint;
import Data.Question;
import Data.Request;
import HandleStoreFiles.HandleFiles;

import java.util.*;
import java.util.concurrent.*;

public class InquiryManager {

    private BlockingQueue<InquiryHandling> q;
    private ExecutorService executer;
    private boolean running=true;

    public InquiryManager() {
       q = new LinkedBlockingQueue<>();
       executer= Executors.newCachedThreadPool();
       start();//הפעלה של הפונקציה לשליפה מהתור בסרד נפרד ע"י הפונקציה start
  }

public void inquiryCreation() {
    Scanner scanner = new Scanner(System.in);
    String choose;
    String description;
    InquiryHandling newInquiry = null;
    HandleFiles handleFiles=new HandleFiles();
    while (true) {
        System.out.println("enter your choose, 1->Question 2->Request 3->Complaint");
        choose = scanner.next();
        switch (choose) {
            case "1": {
                System.out.println("Add a short description");
                description = scanner.next();
                newInquiry=new InquiryHandling(new Question(description));
                handleFiles.saveFile((Question)newInquiry.getCurrentInquiry());
                break;
            }
            case "2": {
                System.out.println("Add a short description");
                description = scanner.next();
                newInquiry=new InquiryHandling(new Request(description));
                handleFiles.saveFile((Question)newInquiry.getCurrentInquiry());
                break;
            }
            case "3": {
                System.out.println("Add a short description");
                description = scanner.next();
                System.out.println("Insert the assigned branch");
                String assignedBranch = scanner.next();
                newInquiry=new InquiryHandling(new Complaint(description,assignedBranch));
                handleFiles.saveFile((Question)newInquiry.getCurrentInquiry());
                break;
            }
            default: {
                if (!choose.equals("exit")){
                    System.out.println("Unvalid input, try again");
                    continue;
                }
                break;
            }
        }
        if (choose.equals("exit")){
            stop();
            break;
        }
        q.add(newInquiry);
    }
}

    public void stop() {
        running = false;
        executer.shutdown();
    }

    public void start() {
        Thread processingThread = new Thread(this::processInquiryManager);
        processingThread.start();
    }

    public void processInquiryManager() {
        while (running||!q.isEmpty()) {
            try {
                InquiryHandling inquiry = q.poll(1, TimeUnit.SECONDS);
                if(inquiry!=null)//אם היתה פנייה זמינה בתור
                    executer.submit(inquiry);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                break;
            }
        }
    }
}



//    private Queue<InquiryHandling> q;
//
//    public InquiryManager() {
//        q = new LinkedList<>();
//    }

//    public void inquiryCreation() {
//        Scanner scanner = new Scanner(System.in);
//        String choose;
//        String description;
//        while (true) {
//            System.out.println("enter your choose, 1->Question 2->Request 3->Complaint");
//            choose = scanner.next();
//            switch (choose) {
//                case "1": {
//                    System.out.println("Add a short description");
//                    description = scanner.next();
//                    q.add(new InquiryHandling(new Question(description)));
//                    break;
//                }
//                case "2": {
//                    System.out.println("Add a short description");
//                    description = scanner.next();
//                    q.add(new InquiryHandling(new Request(description)));
//                    break;
//                }
//                case "3": {
//                    System.out.println("Add a short description");
//                    description = scanner.next();
//                    System.out.println("Insert the assigned branch");
//                    String assignedBranch = scanner.next();
//                    q.add(new InquiryHandling(new Complaint(description,assignedBranch)));
//                    break;
//                }
//                default: {
//                    if (!choose.equals("exit")){
//                        System.out.println("Unvalid input, try again");
//                        continue;
//                    }
//                    break;
//                }
//            }
//            if (choose.equals("exit"))
//                break;
//        }
//    }
//
////מנהלת את תור הפניות
//public void processInquiryManager() {
//    while (!q.isEmpty())
//        q.poll().start();
//}
//}