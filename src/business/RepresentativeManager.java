package business;

import HandleStoreFiles.HandleFiles;
import data.ServiceRepresentative;

import java.io.*;
import java.util.*;


public  class RepresentativeManager {

    static Scanner scanner=new Scanner(System.in);

    static {
        updateAvailableRepresentative();
    }

    private static void updateAvailableRepresentative(){
    //מעבר על הקבצים והצגת הנציגים
     //קבלת מספרי הנציגים הפנויים והכנסתם לתור
        InquiryManager inquiryManager=InquiryManager.getInstance();
        System.out.println("Here is the representative signed in the system\n" +
                "insert the available representative today!");
        File file=new File("Representative");
        if(!file.exists()){
            System.out.println("There is no representative..:(");
            return;
        }

        for(File f: file.listFiles()){
            System.out.println(f.getName()+"\n");// שם הקובץ הוא מספר הנציג
        }

        String ans="";
        List<String> chooses=new ArrayList<>();
        System.out.println("Insert please the available representatives:)\n" +
                "for exit, insert 'exit' ");
        while (true){
            ans=scanner.next();
            if(ans.equals("exit"))
                break;
            chooses.add(ans);
        }

        for(String i:chooses){
            try {
            File chosenFile= Arrays.stream(file.listFiles())
                    .filter(x->x.getName().equals(i))
                    .findFirst().orElse(null);

                FileReader fr=new FileReader(chosenFile);
                BufferedReader br=new BufferedReader(fr);
                String line=br.readLine();
                ServiceRepresentative serviceRepresentative=new ServiceRepresentative(line,Integer.parseInt(i));
                inquiryManager.getRepresentativeQ().add(serviceRepresentative);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void AddRepresentative(ServiceRepresentative representative){
        //שמירת הנציג בקובץ
        HandleFiles handleFiles=new HandleFiles();
        handleFiles.saveCSV(representative,"C:\\git\\inquirymanagement_rs\\Representative");
    }

    public static void RemoveRepresentative(ServiceRepresentative representative){
        //חיפוש הקובץ המתאים ומחיקת הנציג
        HandleFiles handleFiles=new HandleFiles();
        File file=new File("C:\\git\\inquirymanagement_rs\\Representative");
        if(!file.exists()){
            System.out.println("There is no representative..:(");
            return;
        }
        handleFiles.deleteCSV("C:\\git\\inquirymanagement_rs\\Representative",representative);
    }
}
