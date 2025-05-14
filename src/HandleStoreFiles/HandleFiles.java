package HandleStoreFiles;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class HandleFiles {
    //public static String FOLDER= "inquirymanagement_rs/Inquiries";

    public void saveFile(String mainFolder,IForSaving forSaving) {
        try {
            File folder = new File(mainFolder,forSaving.getFolderName());
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(folder, forSaving.getFileName() + ".txt");

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
                writer.write(forSaving.getData());
            }

            System.out.println("קובץ נשמר בהצלחה: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("שגיאה בשמירת הקובץ: " + e.getMessage(), e);
        }
    }

    public void deleteFile(String mainFolder,IForSaving forSaving) {
        File f = new File(mainFolder,forSaving.getFolderName());
        File file=new File(f,forSaving.getFileName() + ".txt");
        System.out.println(file.exists());
        if (file.exists() && file.delete()) {
            System.out.println("הקובץ נמחק בהצלחה: " + file.getAbsolutePath());
        } else {
            System.out.println("שגיאה במחיקת הקובץ: " + file.getAbsolutePath());
        }
    }

    public void updateFile(String mainFolder,IForSaving forSaving) {
        try {
            File f = new File(mainFolder,forSaving.getFolderName());
            File file=new File(f,forSaving.getFileName() + ".txt");

            if (!file.exists()) {
                System.out.println("הקובץ לא קיים, יבוצע שמירה במקום עדכון.");
                saveFile(mainFolder,forSaving);
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
                writer.newLine();
                writer.write(forSaving.getData());
            }

            System.out.println("הקובץ עודכן בהצלחה: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("שגיאה בעדכון הקובץ: " + e.getMessage(), e);
        }
    }

    public void saveFiles(String mainFolder,List<IForSaving> forSavingList) {
        for (IForSaving i : forSavingList) {
            saveFile(mainFolder,i);
        }
    }

    public IForSaving readFile(File f){
        try {
            BufferedReader br=new BufferedReader(new FileReader(f));
            String line= br.readLine();
            List<String> values= Arrays.asList(line.split(","));
            String  className=values.get(0);
            IForSaving inquiry=null;
            if(!className.substring(5).equals("Complaint"))
                 inquiry=(IForSaving) Class.forName(values.get(0)).getConstructor().newInstance();
             else
                 inquiry=(IForSaving) Class.forName(values.get(0)).getConstructor().newInstance();
            inquiry.parseFromFile(values);
            br.close();
            return inquiry;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    //בלי GPT(!)
    public StringBuilder getCSVDataRecursive(Object obj){
        StringBuilder result= new StringBuilder();
        try {
            Class clazz=obj.getClass();
            result.append(clazz.getName());
            while (!clazz.equals(Object.class)) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(true); //to ignore the access permission
                    if(Modifier.isStatic(f.getModifiers()))
                        continue;

                    if(f.getType().isPrimitive()||f.getType().equals(String.class)||f.getType().equals(Integer.class)||f.getType().equals(LocalDateTime.class)){
                        Object value = f.get(obj);
                        result.append(",").append(value.toString());
                    }

                    else
                        result.append(","+getCSVDataRecursive(f.get(obj)));
                }
                clazz=clazz.getSuperclass();
            }
            return result;
        }
       catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //בלי GPT(!)
    public boolean saveCSV(Object obj , String filePath){
        try {
            //create the file
            File file=new File(filePath+".csv");
            if(!file.exists())
                file.createNewFile();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                writer.write(""+getCSVDataRecursive(obj));
            System.out.println("קובץ נשמר בהצלחה: " + file.getAbsolutePath());
            writer.close();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    //בלי GPT(!)
    public Object createInstance(String[]values,int index){
        try {
            Class clazz=Class.forName(values[index++]);
            Object instance=clazz.getConstructor().newInstance();
            while (!clazz.equals(Object.class)) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(true);//to ignore the access permission
                    if(Modifier.isStatic(f.getModifiers())){
                        continue;
                    }
                    if(f.getType().isPrimitive()||f.getType().equals(String.class)||f.getType().equals(Integer.class)||f.getType().equals(Boolean.class)||f.getType().equals(LocalDateTime.class))
                    {
                        if(f.getType().equals(Integer.class)){
                            f.set(instance,Integer.parseInt(values[index++]));
                        }

                        else if(f.getType().equals(LocalDateTime.class))
                            f.set(instance,LocalDateTime.parse(values[index++]));

                        else
                            f.set(instance,values[index++]);
                    }
                    else{//complex object
                        Object o=createInstance(values,index);
                        f.set(instance,o);
                    }
                }
                clazz=clazz.getSuperclass();
            }
            return instance;

        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //בלי GPT
    public Object readCsv(String filePath) throws FileNotFoundException {
        File file=new File(filePath);
        if(!file.exists())
            return null;

        try{
            BufferedReader reader=new BufferedReader(new FileReader(filePath));
            String line= reader.readLine();
            String[]values=line.split(",");
            return createInstance(values,0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCSV(String mainFolder,IForSaving forSaving) {
        File f = new File(mainFolder,forSaving.getFolderName());
        File file=new File(f,forSaving.getFileName() + ".csv");
        System.out.println(file.exists());
        if (file.exists() && file.delete()) {
            System.out.println("הקובץ נמחק בהצלחה: " + file.getAbsolutePath());
        } else {
            System.out.println("שגיאה במחיקת הקובץ: " + file.getAbsolutePath());
        }
    }
}

