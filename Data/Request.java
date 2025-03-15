package Data;

import static java.lang.Thread.sleep;

public class Request extends Inquiry{
    @Override
    public void handling() {
        System.out.println("...handling request inquiry code "+code);
    }
}
