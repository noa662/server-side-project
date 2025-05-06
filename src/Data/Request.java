package Data;

import HandleStoreFiles.IForSaving;

import java.util.List;

public class Request extends Inquiry implements IForSaving {
    @Override
    public void handling() {
        System.out.println("...handling request inquiry code "+code);
    }

    public Request(String description) { super(description);}

    public Request() { super("description");}

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
        return className+","+code+","+description;
    }

    @Override
    public void parseFromFile(List<String> values) {
        className=values.get(0);
        code=Integer.parseInt(values.get(1));
        description=values.get(2);
    }

}
