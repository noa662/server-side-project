package clientServer;


import java.io.Serializable;

public class RequestData implements Serializable {

    private InquiryManagerActions action;
    private Object[] parameters;

    public RequestData() {
    }

    public RequestData(InquiryManagerActions action, Object[] parameters) {
        this.action = action;
        this.parameters = parameters;
    }

    public InquiryManagerActions getAction() {
        return action;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setAction(InquiryManagerActions action) {
        this.action = action;
    }

    public void setParameters(Object... parameters) {
        this.parameters = parameters;
    }
}
