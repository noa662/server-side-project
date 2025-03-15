package business;
import Data.Complaint;
import Data.Question;
import Data.Request;
import java.util.*;

public class InquiryManager {

    private Queue<InquiryHandling> q;

    public InquiryManager() {
        q = new LinkedList<>();
    }

    public void inquiryCreation() {
        Scanner scanner = new Scanner(System.in);
        String choose;
        while (true) {
            choose = scanner.next();
            switch (choose) {
                case "1": {
                    q.add(new InquiryHandling(new Question()));
                    break;
                }
                case "2": {
                    q.add(new InquiryHandling(new Request()));
                    break;
                }
                case "3": {
                    q.add(new InquiryHandling(new Complaint()));
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
            if (choose.equals("exit"))
                break;
        }
    }

//מנהלת את תור הפניות
public void processInquiryManager() {
    while (!q.isEmpty())
        q.poll().start();
}
}

