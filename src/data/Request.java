package data;

import HandleStoreFiles.IForSaving;

import java.time.LocalDateTime;
import java.util.List;

public class Request extends Inquiry {
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
        return creationDate+","+ className+","+status+","+codeRepresentative+","+code+","+description;
    }

    @Override
    public void parseFromFile(List<String> values) {
        creationDate= LocalDateTime.parse(values.get(0));
        className=values.get(1);
        status= InquiryStatus.valueOf(values.get(2));
        codeRepresentative= Integer.parseInt(values.get(3));
        code=Integer.parseInt(values.get(4));
        description=values.get(5);
    }

}
