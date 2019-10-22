package gui;

import com.bulenkov.darcula.DarculaLaf;
import controller.SupervisingController;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SupervisingController superController = new SupervisingController(new MainFrame());
    }
}
