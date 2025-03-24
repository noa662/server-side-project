package Data;

import HandleStoreFiles.IForSaving;

public class Question extends Inquiry implements IForSaving {
    @Override
    public void handling() {
        System.out.println("...handling question inquiry code "+code);
    }

    public Question(String description) {
        super(description);
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
        return "Question require, code "+code+", description: "+description;
    }
}
