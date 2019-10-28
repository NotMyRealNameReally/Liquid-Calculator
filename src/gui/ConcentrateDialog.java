package gui;

import model.Concentrate;

import javax.swing.*;
import java.awt.*;

public class ConcentrateDialog extends JDialog {
   private JList<Concentrate> concentrates;
   private JButton addBtn;
   private JButton newConcentrateBtn;

   public ConcentrateDialog(Frame parent){
       super(parent, "Dodaj aromat", false);
   }
}
