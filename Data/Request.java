package Data;

public class Request extends Inquiry{
    @Override
    public void handling() {
        System.out.println("...handling request inquiry code "+code);
    }

    public Request(String description) {
        super(description);
    }
}
