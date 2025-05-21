package data;

import HandleStoreFiles.IForSaving;

import java.time.LocalDateTime;
import java.util.List;

public class Question extends Inquiry{
    @Override
    public void handling() {
        System.out.println("...handling question inquiry code "+code);
    }

    public Question(String description) {
        super(description);
    }

    public Question() {
        super("description");
    }

    @Override
    public String getFolderName() {
        return "Questions";
    }

    @Override
    public String getFileName() {
        return code.toString();
    }

    @Override
    public String getData() {
        return creationDate+","+ className+","+code+","+description;
    }

    @Override
    public void parseFromFile(List<String> values) {
        creationDate= LocalDateTime.parse(values.get(0));
        className=values.get(1);
        code=Integer.parseInt(values.get(2));
        description=values.get(3);
    }

}
