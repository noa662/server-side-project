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
        String description;
        while (true) {
            System.out.println("enter your choose, 1->Question 2->Request 3->Complaint");
            choose = scanner.next();
            switch (choose) {
                case "1": {
                    System.out.println("Add a short description");
                    description = scanner.next();
                    q.add(new InquiryHandling(new Question(description)));
                    break;
                }
                case "2": {
                    System.out.println("Add a short description");
                    description = scanner.next();
                    q.add(new InquiryHandling(new Request(description)));
                    break;
                }
                case "3": {
                    System.out.println("Add a short description");
                    description = scanner.next();
                    System.out.println("Insert the assigned branch");
                    String assignedBranch = scanner.next();
                    q.add(new InquiryHandling(new Complaint(description,assignedBranch)));
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

