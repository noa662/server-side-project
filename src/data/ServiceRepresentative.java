package data;

import HandleStoreFiles.IForSaving;

import java.util.List;

public class ServiceRepresentative implements IForSaving {

    public ServiceRepresentative(String name, int code) {
        this.name = name;
        this.code = code;
    }

    private String name;
    private int code;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getFolderName() {
        String code2 = String.valueOf(code);
        return code2;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public String getData() {
        return name + "," + code;
    }

    @Override
    public void parseFromFile(List<String> values) {
        name = values.get(0);
        code = Integer.parseInt(values.get(1));
    }
}
