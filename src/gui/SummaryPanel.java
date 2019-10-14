package gui;

import javax.swing.*;
import java.awt.*;

public class SummaryPanel extends JPanel {
    private JTextField strengthField;
    private JTextField ratioField;
    private JTextField concentrateTotalField;
    private JTextField nicVolume;
    private JTextField glycolVolume;
    private JTextField glycerineVolume;

    public SummaryPanel() {
        setupComponents();
        setLayout(new GridLayout(3, 4));
        layoutComponents();

    }

    private void setupComponents() {
        strengthField = new JTextField(5);
        ratioField = new JTextField(5);
        concentrateTotalField = new JTextField(5);

        nicVolume = new JTextField(5);
        glycolVolume = new JTextField(5);
        glycerineVolume = new JTextField(5);

        strengthField.setEditable(false);
        ratioField.setEditable(false);
        concentrateTotalField.setEditable(false);

        nicVolume.setEditable(false);
        glycolVolume.setEditable(false);
        glycerineVolume.setEditable(false);
    }

    private void layoutComponents() {
        add(new JLabel("                           Moc:"));
        add(strengthField);

        add(new JLabel("                 Ilość bazy: "));
        add(nicVolume);

        add(new JLabel("                       PG/VG:"));
        add(ratioField);

        add(new JLabel("                   Ilość PG:"));
        add(glycolVolume);

        add(new JLabel("                   Objętość:"));
        add(concentrateTotalField);

        add(new JLabel("                   Ilość VG:"));
        add(glycerineVolume);
    }

    void setSummaryValues(String strength, String ratio, String concentrate, String nicVolume, String glycolVolume, String glycerineVolume) {
        strengthField.setText(strength);
        ratioField.setText(ratio);
        concentrateTotalField.setText(concentrate);

        this.nicVolume.setText(nicVolume);
        this.glycolVolume.setText(glycolVolume);
        this.glycerineVolume.setText(glycerineVolume);
    }
}
