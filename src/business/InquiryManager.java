package business;

import data.*;
import HandleStoreFiles.HandleFiles;
import HandleStoreFiles.IForSaving;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.*;

public class InquiryManager {

    static Integer nextCodeVal = 0;
    private static InquiryManager instance;
    private static final BlockingQueue<Inquiry> q;
    private static final BlockingQueue<ServiceRepresentative> representativeQ;
    private static final Map<Inquiry, ServiceRepresentative> representativeInquiryMap;
    private ExecutorService executer;
    private boolean running = true;

    static {
        q = new LinkedBlockingQueue<>();
        representativeQ = new LinkedBlockingQueue<>();
        representativeInquiryMap = new HashMap<>();
        try {
            loadInquiries();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void loadInquiries() throws FileNotFoundException {
        File directory = new File("Inquiries");
        if (!directory.exists())
            directory.mkdir();
        for (File dir : directory.listFiles()) {
            File[] files = dir.listFiles();
            if (files == null)
                return;
            HandleFiles handleFiles = new HandleFiles();
            for (File f : files) {
                IForSaving newInquiry = handleFiles.readFile(f);
                nextCodeVal = Math.max(nextCodeVal, ((Inquiry) newInquiry).getCode() + 1);
                addInquiryToQueue((Inquiry) newInquiry);
            }
        }
    }

    public static InquiryManager getInstance() {
        if (instance == null)
            instance = new InquiryManager();
        return instance;
    }

    private InquiryManager() {
        executer = Executors.newCachedThreadPool();
        start();//הפעלה של הפונקציה לשליפה מהתור בסרד נפרד ע"י הפונקציה start
    }

    public void inquiryCreation() {
        Scanner scanner = new Scanner(System.in);
        String choose;
        String description;
        Inquiry newInquiry = null;
        while (true) {
            System.out.println("enter your choose, 1->Question 2->Request 3->Complaint");
            choose = scanner.next();
            switch (choose) {
                case "1": {
                    System.out.println("Add a short description");
                    description = scanner.next();
                    newInquiry = new Question(description);
                    break;
                }
                case "2": {
                    System.out.println("Add a short description");
                    description = scanner.next();
                    newInquiry = new Request(description);
                    break;
                }
                case "3": {
                    System.out.println("Add a short description");
                    description = scanner.next();
                    System.out.println("Insert the assigned branch");
                    String assignedBranch = scanner.next();
                    newInquiry = new Complaint(description, assignedBranch);
                    break;
                }
                default: {
                    if (!choose.equals("exit")) {
                        System.out.println("Unvalid input, try again");
                        continue;
                    }
                    break;
                }
            }
            if (choose.equals("exit")) {
                stop();
                break;
            }
            addInquiryToQueue(newInquiry);
        }
    }



    public static void addInquiryToQueue(Inquiry newInquiry) {
        newInquiry.setCode(nextCodeVal);
        HandleFiles handleFiles = new HandleFiles();
        handleFiles.saveFile("Inquiries",newInquiry);
            q.add(newInquiry);
    }

    public static BlockingQueue<Inquiry> getAllInquiries() {
        System.out.println(q);
        return q;
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
                Inquiry inquiry = q.poll(1, TimeUnit.SECONDS);
                InquiryHandling inquiryHandling = new InquiryHandling(inquiry);
                if (inquiry != null)//אם היתה פנייה זמינה בתור
                     executer.submit(inquiryHandling);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                break;
            }
        }
    }

    public Map<Inquiry, ServiceRepresentative> getRepresentativeInquiryMap() {
        return representativeInquiryMap;
    }

    public BlockingQueue<ServiceRepresentative> getRepresentativeQ() {
        return representativeQ;
    }

    public ServiceRepresentative getRepresentativeByInquiry(Inquiry inquiry) {
        return representativeInquiryMap.get(inquiry);
    }

    public Inquiry getInquiryByRepresentative(ServiceRepresentative representative) {
        for (Map.Entry<Inquiry, ServiceRepresentative> entry : representativeInquiryMap.entrySet()) {
            if (entry.getValue().equals(representative))

                return entry.getKey();
        }
        return null;
    }
//    public void removeInquiry(int id) {
//        Map<Inquiry, ServiceRepresentative> map = getRepresentativeInquiryMap();
//        for (var entry : map.entrySet()) {
//            if(entry.getValue().getCode()==id){
//                entry.getKey().setStatus(InquiryStatus.CANCELED);
//            }
//        }
//        map.remove(id);
//        //MoveToHistory();
//    }
}