package business;

import data.*;
import HandleStoreFiles.HandleFiles;
import HandleStoreFiles.IForSaving;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
            return;
        for (File dir : directory.listFiles()) {
            File[] files = dir.listFiles();
            if (files == null)
                continue;
            HandleFiles handleFiles = new HandleFiles();
            for (File f : files) {
                IForSaving newInquiry = handleFiles.readFile(f);
                nextCodeVal = Math.max(nextCodeVal, ((Inquiry) newInquiry).getCode() + 1);
                addInquiryToQueue((Inquiry) newInquiry,false);
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
            addInquiryToQueue(newInquiry,true);
        }
    }


    public static void addInquiryToQueue(Inquiry newInquiry,boolean isNew) {
        if(isNew){
            newInquiry.setCode(nextCodeVal);
            nextCodeVal++;
        }
        
        HandleFiles handleFiles = new HandleFiles();
        handleFiles.saveFile("Inquiries", newInquiry);
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

    public static Map<Inquiry, ServiceRepresentative> getRepresentativeInquiryMap() {
        return representativeInquiryMap;
    }

    public BlockingQueue<ServiceRepresentative> getRepresentativeQ() {
        return representativeQ;
    }

    public static void moveToHistory(int id) throws Exception {
        String path = "Inquiries";
        String fileName = String.valueOf(id);
        String fileName2 = id + ".csv";
        File folder = new File(path);
        File[] files = folder.listFiles();
        File history = new File("History");
        if (!history.exists()) {
            history.mkdir();
        }
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File[] files1 = file.listFiles();
                    if (files1 != null) {
                        for (File f : files1) {
                            if (f.isFile() && (f.getName().equals(fileName)) || (f.getName().equals(fileName2))) {
                                Path path1 = f.toPath();
                                Path path2 = new File(history, f.getName()).toPath();
                                try {
                                    Files.move(path1, path2);
                                } catch (IOException e) {
                                    throw new Exception("Error move file to history");
                                }
                                return;
                            }
                        }
                    }
                }
            }
            throw new Exception("Inquiry not found");
        }
    }

    public static void removeInquiry(int id) throws Exception {
        Map<Inquiry, ServiceRepresentative> map = getRepresentativeInquiryMap();
        Inquiry inq = null;
        boolean found = false;
        for (var entry : map.entrySet()) {
            if (entry.getValue().getCode() == id) {
                entry.getKey().setStatus(InquiryStatus.CANCELED);
                inq = entry.getKey();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new Exception("Inquiry num. " + id + " not found");
        }
        map.remove(inq);
        moveToHistory(id);
    }

}