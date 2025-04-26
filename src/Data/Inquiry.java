package Data;

import java.time.LocalDateTime;

public abstract class Inquiry {

    static Integer nextCodeVal = 0;
    protected Integer code;
    protected String description;
    protected LocalDateTime creationDate;
    protected  String className;

    public void fillDataByUser(String description,LocalDateTime creationDate){
        this.code=nextCodeVal++;
       this.description=description;
       this.creationDate=creationDate;
    }
    public Inquiry(String description){

        fillDataByUser(description,LocalDateTime.now());
        this.className=this.getClass().getName();
    }
    public Inquiry(){};

    public abstract void handling();

    public static Integer getNextCodeVal() {
        return nextCodeVal;
    }

    public static void setNextCodeVal(Integer nextCodeVal) {
        Inquiry.nextCodeVal = nextCodeVal;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
