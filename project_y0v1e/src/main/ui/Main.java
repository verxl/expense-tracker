package ui;

import java.io.FileNotFoundException;

// For running the app
public class Main {
    public static void main(String[] args) {
//        try {
//            new Tracker();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run application: file not found");
//        }
        new TrackerGUI();
    }
}
