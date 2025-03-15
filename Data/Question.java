package Data;

import static java.lang.Thread.sleep;

public class Question extends Inquiry {
    @Override
    public void handling() {
        System.out.println("...handling question inquiry code "+code);
    }

}
