package Data;

import HandleStoreFiles.IForSaving;

public class Request extends Inquiry implements IForSaving {
    @Override
    public void handling() {
        System.out.println("...handling request inquiry code "+code);
    }

    public Request(String description) {
        super(description);
    }

    @Override
    public String getFolderName() {
        return "Requests";
    }

    @Override
    public String getFileName() {
        return code.toString();
    }

    @Override
    public String getData() {
        return "Request require, code "+code+", description: "+description;
    }
}
