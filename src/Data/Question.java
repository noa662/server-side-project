package Data;

import HandleStoreFiles.IForSaving;

import java.util.List;

public class Question extends Inquiry implements IForSaving {
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
        return className+","+code+","+description;
    }

    @Override
    public void parseFromFile(List<String> values) {
        className=values.get(0);
        code=Integer.parseInt(values.get(1));
        description=values.get(2);
    }

}
