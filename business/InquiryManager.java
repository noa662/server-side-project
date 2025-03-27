package business;
import Data.Complaint;
import Data.Inquiry;
import Data.Question;
import Data.Request;
import HandleStoreFiles.HandleFiles;
import HandleStoreFiles.IForSaving;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

public class InquiryManager {

    private static InquiryManager instance;
    private static final BlockingQueue<InquiryHandling> q;
    private ExecutorService executer;
    private boolean running=true;

    static {
       q=new LinkedBlockingQueue<>();
        loadInquiries();
    }

    private static void loadInquiries() {
        File directory=new File("Inquiries");
        for(File dir:directory.listFiles()){
            File[] files=dir.listFiles();
            if(files==null)
                return;
            HandleFiles handleFiles=new HandleFiles();
            for(File f:files){
                IForSaving newInquiry=handleFiles.readFile(f);
                if(newInquiry instanceof Inquiry){
                    InquiryHandling inquiryHandling=new InquiryHandling((Inquiry) newInquiry);
                    q.add(inquiryHandling);
                }
            }
        }
    }

    public static InquiryManager getInstance(){
        if(instance==null)
            instance=new InquiryManager();
        return instance;
    }
    private InquiryManager() {
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
                handleFiles.saveFile((Request)newInquiry.getCurrentInquiry());
                break;
            }
            case "3": {
                System.out.println("Add a short description");
                description = scanner.next();
                System.out.println("Insert the assigned branch");
                String assignedBranch = scanner.next();
                newInquiry=new InquiryHandling(new Complaint(description,assignedBranch));
                handleFiles.saveFile((Complaint)newInquiry.getCurrentInquiry());
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
        executer.shutdownNow();
    }

    public void start() {
        Thread processingThread = new Thread(this::processInquiryManager);
        processingThread.start();
    }

    public void processInquiryManager() {
        while (running) {
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