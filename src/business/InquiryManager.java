package business;

import data.*;
import HandleStoreFiles.HandleFiles;
import HandleStoreFiles.IForSaving;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        File directory2 = new File("History");
        HandleFiles handleFiles = new HandleFiles();


        if (!directory.exists()&&!directory2.exists())
            return;

        //טעינת הפניות מתיקיית Inquiries
        for (File dir : directory.listFiles()) {
            File[] files = dir.listFiles();
            if (files == null)
                continue;
            for (File f : files) {
                IForSaving newInquiry = handleFiles.readFile(f);
                nextCodeVal = Math.max(nextCodeVal, ((Inquiry) newInquiry).getCode() + 1);
                addInquiryToQueue((Inquiry) newInquiry, false);
            }
        }

        //טעינת הפניות מקבצי History
        for (File f : directory2.listFiles()) {
            String name=f.getName();
            String nameWithoutTxt=name.substring(0, name.lastIndexOf('.'));
            nextCodeVal = Math.max(nextCodeVal, Integer.parseInt(nameWithoutTxt + 1));
        }
    }

    public static int GetMonthlyFileStats(int month) {
        int count = 0;
        File history = new File("History");
        if (!history.exists()) {
            return count;
        }
        File[] files = history.listFiles();
        if (files == null)
            return count;
        for (File f : files) {
            if (f.isFile()) {
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length > 0) {
                            String date = parts[0];
                            LocalDateTime localDateTime = LocalDateTime.parse(date);
                            if (localDateTime.getMonth() == LocalDate.now().getMonth()) {
                                count++;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    public static int getRepresentativeInquiries(int codeRepresentative) {
        int count = 0;
        File history = new File("History");
        if (!history.exists()) {
            return count;
        }
        File[] files = history.listFiles();
        if (files == null)
            return count;
        for (File f : files) {
            if (f.isFile()) {
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length > 3) {
                            String stringCode = parts[3];
                            int code = Integer.parseInt(stringCode);
                            if (code == codeRepresentative)
                                count++;
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return count;
    }


    public static InquiryManager getInstance() {
        if (instance == null)
            instance = new InquiryManager();
        return instance;
    }

    private InquiryManager() {
        executer = Executors.newCachedThreadPool();
        //start();//הפעלה של הפונקציה לשליפה מהתור בסרד נפרד ע"י הפונקציה start
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
            addInquiryToQueue(newInquiry, true);
        }
    }


    public static void addInquiryToQueue(Inquiry newInquiry, boolean isNew) {
        if (isNew) {
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

    @Deprecated
    public void start() {
        Thread processingThread = new Thread(this::processInquiryManager);
        processingThread.start();
    }

    @Deprecated
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
        String fileName2 = id + ".txt";
        File folder = new File(path);
        File[] files = folder.listFiles();
        File history = new File("History");
        HandleFiles handleFiles = new HandleFiles();
        if (!history.exists()) {
            history.mkdir();
        }
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File[] files1 = file.listFiles();
                    if (files1 == null) {
                        System.out.println("Could not read contents of: " + file.getAbsolutePath());
                        continue;
                    }
                    if (files1 != null) {
                        for (File f : files1) {
                            if (f.isFile() && (f.getName().equals(fileName) || f.getName().equals(fileName2))) {
                                IForSaving inquiry= handleFiles.readFile(f);
                                Inquiry inquiry1=(Inquiry)inquiry;
                                inquiry1.setStatus(InquiryStatus.MOVEDTOHISTORY);
                                inquiry=(IForSaving)inquiry1;
                                handleFiles.updateFile("Inquiries", inquiry);
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
        Inquiry inq = null;
        boolean found = false;
        for (var entry : representativeInquiryMap.entrySet()) {
            if (entry.getKey().getCode() == id) {
                entry.getKey().setStatus(InquiryStatus.CANCELED);
                inq = entry.getKey();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new Exception("Inquiry num. " + id + " not found");
        }
        ServiceRepresentative sr = representativeInquiryMap.remove(inq);
        moveToHistory(id);

        InquiryManager.getInstance().getRepresentativeQ().add(sr);
    }

    public static Inquiry getInquiryFromAllFiles(int inquiryId){
        HandleFiles hf = new HandleFiles();
        String path = "Inquiries/";
        File inquiriesFolder = new File(path);
        File inquiryFile;
        if(inquiriesFolder.exists())
            for(File file :inquiriesFolder.listFiles()){
                inquiryFile = new File(file.getPath()+"/inquiryId");
                if(inquiryFile.exists()){
                    return ((Inquiry) hf.readFile(inquiryFile));
                }
            }
        path = "History/"+inquiryId+".txt";
        inquiryFile = new File(path);
        if(!inquiryFile.exists())
            throw new RuntimeException("inquiry not found");
        return ((Inquiry) hf.readFile(inquiryFile));
    }

    public static InquiryStatus getStatusForInquiry(int inquiryId) {
        return getInquiryFromAllFiles(inquiryId).getStatus();
    }


    public static int getRepresentativeForInquiry(int inquiryId) {
        return getInquiryFromAllFiles(inquiryId).getCodeRepresentative();
    }


}