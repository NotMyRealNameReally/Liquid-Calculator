package gui;

import com.bulenkov.darcula.DarculaLaf;
import gui.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new MainFrame();
    }
}
