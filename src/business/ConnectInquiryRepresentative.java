package business;

import HandleStoreFiles.HandleFiles;
import data.Inquiry;
import data.InquiryStatus;
import data.ServiceRepresentative;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class ConnectInquiryRepresentative extends Thread{

    //לקבל את תור הפניות ותור הנציגים, וכן את המפה
    //אם יש נציג בתור ופנייה בתור- נכניס אותם כזוג למפה
    //נעדכן את סטטוס הפנייה וקוד הנציג המטפל- גם בקובץ הפנייה
    //נשלח את הזוג לטיפול
    @Override
    public void run() {
        InquiryManager inquiryManager=InquiryManager.getInstance();
        BlockingQueue<ServiceRepresentative> representativeQ=inquiryManager.getRepresentativeQ();
        BlockingQueue<Inquiry> inquiryQ=inquiryManager.getAllInquiries();
        Map<Inquiry, ServiceRepresentative> map=inquiryManager.getRepresentativeInquiryMap();
        ServiceRepresentative serviceRepresentative;
        Inquiry inquiry;
        HandleFiles handleFiles=new HandleFiles();

        while (true){
            try {
                //מחכה עד שיש פנייה ונציג בתור
                serviceRepresentative=representativeQ.take();
                inquiry=inquiryQ.take();

                //עידכון נתוני הפנייה
                inquiry.setStatus(InquiryStatus.OPENED);
                inquiry.setCodeRepresentative(serviceRepresentative.getCode());

                //עידכון קובץ הפנייה
                handleFiles.updateFile("Inquiries",inquiry);

                map.put(inquiry,serviceRepresentative);

                //שליחת הפנייה לטיפול
                InquiryHandling inquiryHandling=new InquiryHandling(inquiry);
                inquiryHandling.start();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }




    }
}
