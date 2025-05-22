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
        File file=new File("Representatives");
        if(!file.exists()){
            System.out.println("There is no representative..:(");
            return;
        }

        for(File f: file.listFiles()){
            System.out.println(f.getName()+"\n");// שם הקובץ הוא מספר הנציג
        }

        String ans="";
        Set<String> chooses=new HashSet<>();
        System.out.println("Insert please the available representatives:)\n" +
                "for exit, insert 'exit' ");
        while (true){
            ans=scanner.next();
            if(ans.equals("exit"))
                break;
            chooses.add(ans);
        }

        for(String i:chooses){
            File chosenFile= Arrays.stream(file.listFiles())
                    .filter(x->x.getName().equals(i+".txt"))
                    .findFirst().orElse(null);
            if(chosenFile==null){
                System.out.println("You inserted invalid representative code");
                continue;
            }

                HandleFiles handleFiles=new HandleFiles();
                ServiceRepresentative serviceRepresentative= (ServiceRepresentative) handleFiles.readFile(chosenFile);
                inquiryManager.getRepresentativeQ().add(serviceRepresentative);
        }
    }

    public static void AddRepresentative(ServiceRepresentative representative){
        //שמירת הנציג בקובץ
        HandleFiles handleFiles=new HandleFiles();
        handleFiles.saveFile("Representatives",representative);
    }

    public static void RemoveRepresentative(ServiceRepresentative representative){
        //חיפוש הקובץ המתאים ומחיקת הנציג
        HandleFiles handleFiles=new HandleFiles();
        File file=new File("Representatives");
        if(!file.exists()){
            System.out.println("There is no representative..:(");
            return;
        }
        handleFiles.deleteFile("Representatives",representative);
    }
}
