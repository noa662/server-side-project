package testSaveFile;

import data.Inquiry;
import data.Question;
import HandleStoreFiles.IForSaving;

import java.util.List;

public class PersonForTestSaving implements IForSaving {

    String id;
    String name;
    Inquiry inquiry;

    public PersonForTestSaving(String id, String name) {
        this.id = id;
        this.name = name;
        inquiry=new Question("dddd");
    }

    public PersonForTestSaving(){

    }

    @Override
    public String getFolderName() {
        return getClass().getPackage().getName();
    }

    @Override
    public String getFileName() {
        return getClass().getSimpleName()+id;
    }

    @Override
    public String getData() {
        return id+","+name;
    }

    @Override
    public void parseFromFile(List<String> values) {

    }

    @Override
    public String toString() {
        return "PersonForTestSaving{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", inquiry=" + inquiry +
                '}';
    }
}
