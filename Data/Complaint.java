package Data;
import HandleStoreFiles.IForSaving;

import java.time.LocalDateTime;

public class Complaint extends Inquiry implements IForSaving {

    String assignedBranch;

    public void fillDataByUser(String description, LocalDateTime creationDate,String assignedBranch){
        super.fillDataByUser(description,creationDate);
        this.assignedBranch=assignedBranch;
    }

    public Complaint(String description,String assignedBranch) {
        super(description);
        this.assignedBranch=assignedBranch;
    }

    @Override
    public void handling() {
        System.out.println("...handling complain inquiry code "+code);
    }

    @Override
    public String getFolderName() {
        return "Complaints";
    }

    @Override
    public String getFileName() {
        return code.toString();
    }

    @Override
    public String getData() {
        return "Complaint require, code "+code+", description: "+description+" assingmentBranch: "+assignedBranch;
    }
}
