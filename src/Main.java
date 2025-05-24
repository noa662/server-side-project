import business.ConnectInquiryRepresentative;
import business.InquiryManager;
import business.RepresentativeManager;
import clientServer.InquiryManagerServer;
import data.ServiceRepresentative;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        ConnectInquiryRepresentative connectInquiryRepresentative=new ConnectInquiryRepresentative();
        connectInquiryRepresentative.start();

        ServiceRepresentative serviceRepresentative=new ServiceRepresentative("rep1",0);
        RepresentativeManager.AddRepresentative(serviceRepresentative);
        ServiceRepresentative serviceRepresentative2=new ServiceRepresentative("rep2",1);
        ServiceRepresentative serviceRepresentative3=new ServiceRepresentative("rep3",2);
        RepresentativeManager.AddRepresentative(serviceRepresentative2);
        RepresentativeManager.AddRepresentative(serviceRepresentative3);

        InquiryManagerServer inquiryManagerServer=new InquiryManagerServer();
        inquiryManagerServer.start();

        System.out.println("end main....");
    }
}