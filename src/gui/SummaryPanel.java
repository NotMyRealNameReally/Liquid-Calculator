package gui;

import javax.swing.*;
import java.awt.*;

public class SummaryPanel extends JPanel {
    private JTextField strengthField;
    private JTextField ratioField;
    private JTextField concentrateTotalField;
    private JTextField nicVolumeField;
    private JTextField glycolVolumeField;
    private JTextField glycerineVolumeField;

    public SummaryPanel() {
        setupComponents();
        setLayout(new GridLayout(3, 4));
        layoutComponents();
        Dimension dim = new Dimension(455, 80);
        this.setPreferredSize(dim);
        setBorder(BorderFactory.createEtchedBorder());

    }

    private void setupComponents() {
        strengthField = new JTextField(5);
        ratioField = new JTextField(5);
        concentrateTotalField = new JTextField(5);

        nicVolumeField = new JTextField(5);
        glycolVolumeField = new JTextField(5);
        glycerineVolumeField = new JTextField(5);

        strengthField.setEditable(false);
        ratioField.setEditable(false);
        concentrateTotalField.setEditable(false);

        nicVolumeField.setEditable(false);
        glycolVolumeField.setEditable(false);
        glycerineVolumeField.setEditable(false);
    }

    private void layoutComponents() {
        add(new JLabel("                         Moc:"));
        add(strengthField);

        add(new JLabel("               Ilość bazy: "));
        add(nicVolumeField);

        add(new JLabel("                    PG/VG:"));
        add(ratioField);

        add(new JLabel("                  Ilość PG:"));
        add(glycolVolumeField);

        add(new JLabel("         Ilość aromatu:"));
        add(concentrateTotalField);

        add(new JLabel("                  Ilość VG:"));
        add(glycerineVolumeField);
    }

    void setSummaryValues(String strength, String ratio, String concentrate, String nicVolume, String glycolVolume, String glycerineVolume) {
        strengthField.setText(strength);
        ratioField.setText(ratio);
        concentrateTotalField.setText(concentrate);

        nicVolumeField.setText(nicVolume);
        glycolVolumeField.setText(glycolVolume);
        glycerineVolumeField.setText(glycerineVolume);
    }
}
