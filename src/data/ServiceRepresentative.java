package data;

import HandleStoreFiles.IForSaving;

import java.util.List;

public class ServiceRepresentative implements IForSaving {

    public ServiceRepresentative(String name, int code) {
        this.name = name;
        this.code = code;
        this.className = this.getClass().getName();
    }
    public ServiceRepresentative() {
        this.className = this.getClass().getName();
    }

    private String name;
    private int code;
    private String className;

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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String getFolderName() {
        return "Representatives";
    }

    @Override
    public String getFileName() {
        String code2 = String.valueOf(code);
        return code2;
    }

    @Override
    public String getData() {
        return name + "," +className+","+  code;
    }

    @Override
    public void parseFromFile(List<String> values) {
        name = values.get(0);
        className=values.get(1);
        code = Integer.parseInt(values.get(2));
    }
}
