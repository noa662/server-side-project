package Data;

public class Question extends Inquiry {
    @Override
    public void handling() {
        System.out.println("...handling question inquiry code "+code);
    }

    public Question(String description) {
        super(description);
    }
}
