import business.InquiryManager;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
 //    public static void main(String[] args) throws Exception {
//
//        InquiryHandling handling1 = new InquiryHandling();
//        Thread handling2 = new InquiryHandling();
//        InquiryHandling handling3 = new InquiryHandling();
//        InquiryHandling handling4 = new InquiryHandling();
//        handling1. createInquiry(1);
//        ((InquiryHandling) handling2).createInquiry(2);
//        handling3.createInquiry(3);
//        handling4.createInquiry(1);
//
//
//        handling1.start();
//		handling2.start();
//		handling3.start();
//		handling4.start();
//       // second running

    /// /        handling1.run();
    /// /        handling2.run();
    /// /        handling3.run();
    /// /        handling4.run();
//
//
//    }
    public static void main(String[] args) {
//        List<Thread> threads = new ArrayList<>();
//        for (int i = 0; i < 15; i++) {
//            InquiryHandling handling = new InquiryHandling();
//            handling.createInquiry(i%3+1);
//            Thread thread = handling;
//            threads.add(thread);
//            thread.start();
//        }
//
//
//        for (Thread thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }



        InquiryManager inquiryManager =InquiryManager.getInstance();
        inquiryManager.inquiryCreation();
        inquiryManager.stop();


//        InquiryHandling handling1 = new InquiryHandling();
//        Thread handling2 = new InquiryHandling();
//        InquiryHandling handling3 = new InquiryHandling();
//        InquiryHandling handling4 = new InquiryHandling();
//        handling1. createInquiry(1);
//        ((InquiryHandling) handling2).createInquiry(2);
//        handling3.createInquiry(3);
//        handling4.createInquiry(1);
//
//
//        handling1.start();
//		handling2.start();
//		handling3.start();
//		handling4.start();

    }
}