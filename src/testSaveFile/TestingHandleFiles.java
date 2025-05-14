package testSaveFile;

import HandleStoreFiles.HandleFiles;

import java.util.Arrays;

public class TestingHandleFiles {

    public static void main(String[] args) {
        PersonForTestSaving p1 = new PersonForTestSaving("1234","aaa");
        PersonForTestSaving p2 = new PersonForTestSaving("5432","bbb");
        PersonForTestSaving p3 = new PersonForTestSaving("9999","ccc");
        PersonForTestSaving p4 = new PersonForTestSaving("0090","ccdc");

        HandleFiles handleFiles = new HandleFiles();
        handleFiles.saveFile("inquirymanagement_rs/Inquiries",p3);
        handleFiles.saveFiles("inquirymanagement_rs/Inquiries",Arrays.asList(p1,p2,p3,p4));

        handleFiles.deleteFile("inquirymanagement_rs/Inquiries",p2);

    }
}
