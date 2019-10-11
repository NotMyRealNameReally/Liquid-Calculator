package gui;

import javax.swing.*;
import java.awt.*;

public class SummaryPanel extends JPanel {
    private JTextField strengthField;
    private JTextField ratioField;
    private JTextField concentrateTotalField;

    public SummaryPanel() {
        setupComponents();
        setLayout(new GridLayout(3, 2));
        layoutComponents();

    }

    private void setupComponents() {
        strengthField = new JTextField(5);
        ratioField = new JTextField(5);
        concentrateTotalField = new JTextField(5);

        strengthField.setEditable(false);
        ratioField.setEditable(false);
        concentrateTotalField.setEditable(false);
    }

    private void layoutComponents() {
        add(new JLabel("                  Moc:"));
        add(strengthField);

        add(new JLabel("               PG/VG"));
        add(ratioField);

        add(new JLabel("Ilość aromatów:"));
        add(concentrateTotalField);
    }

    void setSummaryValues(String strength, String ratio, String concentrate) {
        strengthField.setText(strength);
        ratioField.setText(ratio);
        concentrateTotalField.setText(concentrate);
    }
}
