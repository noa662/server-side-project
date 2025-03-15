package Data;

import java.time.LocalDateTime;

import static java.lang.Thread.sleep;

public class Complaint extends Inquiry{

    String assignedBranch;
    public void fillDataByUser(String description, LocalDateTime creationDate,String assignedBranch){
        super.fillDataByUser(description,creationDate);
        this.assignedBranch=assignedBranch;
    }

    @Override
    public void handling() {
        System.out.println("...handling complain inquiry code "+code);
    }
}
