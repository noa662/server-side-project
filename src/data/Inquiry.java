package data;

import HandleStoreFiles.IForSaving;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Inquiry implements IForSaving, Serializable {

    private static final long serialVersionUID = 1L;
    protected Integer code;
    protected String description;
    protected LocalDateTime creationDate;
    protected String className;
    protected InquiryStatus status;
    protected int codeRepresentative;

    public void setCodeRepresentative(int codeRepresentative) {
        this.codeRepresentative = codeRepresentative;
    }

    public int getCodeRepresentative() {
        return codeRepresentative;
    }

    public void setStatus(InquiryStatus status) {
        this.status = status;
    }

    public InquiryStatus getStatus() {
        return status;
    }

    public void fillDataByUser(String description, LocalDateTime creationDate) {
        this.description = description;
        this.creationDate = creationDate;
    }

    public Inquiry(String description) {

        fillDataByUser(description, LocalDateTime.now());
        this.className = this.getClass().getName();
    }

    public Inquiry() {
    }

    ;

    public abstract void handling();

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
