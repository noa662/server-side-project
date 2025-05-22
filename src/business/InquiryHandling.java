package business;

import data.*;
import HandleStoreFiles.HandleFiles;
import HandleStoreFiles.IForSaving;

import java.util.Map;
import java.util.Random;

import static business.InquiryManager.moveToHistory;

public class InquiryHandling extends Thread {
    private Inquiry currentInquiry;

    public Inquiry getCurrentInquiry() {
        return currentInquiry;
    }

    public InquiryHandling(Inquiry inquiry) {
        this.currentInquiry = inquiry;
    }

    @Deprecated
    public void createInquiry(int num) throws Exception {
        switch (num) {
            case 1: {
                currentInquiry = new Question("");
                break;
            }
            case 2: {
                currentInquiry = new Request("");
                break;
            }
            case 3: {
                currentInquiry = new Complaint("", "");
                break;
            }
            default:
                throw new Exception("incorrect input");
        }
    }

    public void completeInquiry() throws Exception {
        currentInquiry.setStatus(InquiryStatus.HANDLED);
        ServiceRepresentative sr = InquiryManager.getInstance().getRepresentativeInquiryMap().remove(currentInquiry);
        InquiryManager.getInstance().getRepresentativeQ().add(sr);
        moveToHistory(currentInquiry.getCode());
        currentInquiry.setStatus(InquiryStatus.MOVEDTOHISTORY);
    }

    @Override
    public void run() {
        Random rand = new Random();
        int estimationTime;
        if (currentInquiry instanceof Question) {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            estimationTime = rand.nextInt(5) + 1; // 1-5 שניות
        } else if (currentInquiry instanceof Request) {
            estimationTime = rand.nextInt(6) + 10; // 10-15 שניות
        } else if (currentInquiry instanceof Complaint) {
            estimationTime = rand.nextInt(21) + 20; // 20-40 שניות
        } else {
            return;
        }

        currentInquiry.handling();

        // שחרור תהליכים נוספים אם העומס גבוה
        if (estimationTime > 5 && Thread.activeCount() > 10) {
            Thread.yield();
        }

        // השהיית זמן הריצה בהתאם להערכת הזמן
        try {
            Thread.sleep(estimationTime * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // הדפסת פרטי הפנייה
        System.out.println(currentInquiry.getClass().getSimpleName() +
                " inquiry code: " + currentInquiry.getCode() +
                ", estimated time: " + estimationTime + "s");
    }

}
