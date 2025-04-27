package ClientServer;

import Data.Inquiry;

import java.util.List;

public class RequestData {

    private InquiryManagerActions action;
    private List<Object> parameters;

    public RequestData() {
    }

    public RequestData(InquiryManagerActions action, List<Object> parameters) {
        this.action = action;
        this.parameters = parameters;
    }

    public InquiryManagerActions getAction() {
        return action;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setAction(InquiryManagerActions action) {
        this.action = action;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }
}
