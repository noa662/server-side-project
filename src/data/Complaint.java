package data;

import java.time.LocalDateTime;
import java.util.List;

public class Complaint extends Inquiry {

    String assignedBranch;

    public void fillDataByUser(String description, LocalDateTime creationDate,String assignedBranch){
        super.fillDataByUser(description,creationDate);
        this.assignedBranch=assignedBranch;
    }

    public Complaint(String description,String assignedBranch) {
        super(description);
        this.assignedBranch=assignedBranch;
    }

    public Complaint() {
        super("description");
        this.assignedBranch="assignedBranch";
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
        return className+","+code+","+description+","+assignedBranch;
    }

    @Override
    public void parseFromFile(List<String> values) {
        className=values.get(0);
        code=Integer.parseInt(values.get(1));
        description=values.get(2);
        assignedBranch=values.get(3);
    }

}
