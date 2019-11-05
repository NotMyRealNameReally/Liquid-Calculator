package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class SummaryPanel extends JPanel {
    private JTextField strengthField;
    private JTextField ratioField;
    private JTextField concentrateTotalField;
    private JTextField nicVolumeField;
    private JTextField glycolVolumeField;
    private JTextField glycerineVolumeField;

    SummaryPanel() {
        setupComponents();
        layoutComponents();
        Dimension dim = new Dimension(370, 105);
        setPreferredSize(dim);
        setMinimumSize(dim);

        Border innerBorder = BorderFactory.createEmptyBorder(5, 0, 5, 0);
        Border outerBorder = BorderFactory.createTitledBorder("Podsumowanie");
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

    }

    private void setupComponents() {
        strengthField = new JTextField(8);
        ratioField = new JTextField(8);
        concentrateTotalField = new JTextField(8);

        nicVolumeField = new JTextField(8);
        glycolVolumeField = new JTextField(8);
        glycerineVolumeField = new JTextField(8);

        strengthField.setEditable(false);
        ratioField.setEditable(false);
        concentrateTotalField.setEditable(false);

        nicVolumeField.setEditable(false);
        glycolVolumeField.setEditable(false);
        glycerineVolumeField.setEditable(false);
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //first column
        gc.weightx = 0.2;
        gc.weighty = 0.2;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;

        add(new JLabel("Moc: "), gc);

        gc.gridy++;
        add(new JLabel("PG/VG: "), gc);

        gc.gridy++;
        add(new JLabel("Ilość aromatu: "), gc);

        //second column
        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;

        add(strengthField, gc);

        gc.gridy++;
        add(ratioField, gc);

        gc.gridy++;
        add(concentrateTotalField, gc);

        // third column
        gc.weightx = 0.2;
        gc.weighty = 0.2;

        gc.gridx = 2;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_END;

        add(new JLabel("Ilość bazy: "), gc);

        gc.gridy++;
        add(new JLabel("Ilość PG: "), gc);

        gc.gridy++;
        add(new JLabel("Ilość VG: "), gc);

        //fourth column
        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 3;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;

        add(nicVolumeField, gc);

        gc.gridy++;
        add(glycolVolumeField, gc);

        gc.gridy++;
        add(glycerineVolumeField, gc);
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
