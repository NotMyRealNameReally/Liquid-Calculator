package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConcentrateCreationDialog extends JDialog {
    private JTextField nameField;
    private JComboBox<String> flavourProfileBox;
    private JComboBox<String> manufacturerBox;
    private JButton createBtn;
    private JLabel flavourProfileLabel;
    private JLabel manufacturerLabel;

    ConcentrateCreationDialog(JDialog parent) {
        super(parent, "Stwórz nowy aromat", true);
        setupComponents();
        setNameFieldFont();
        layoutComponents();

        setLocationRelativeTo(parent);
        setSize(300, 200);
    }

    private void setupComponents() {
        nameField = new JTextField(30);
        flavourProfileBox = new JComboBox<>();
        manufacturerBox = new JComboBox<>();
        createBtn = new JButton("Stwórz");
        flavourProfileLabel = new JLabel("Profil smaku: ");
        manufacturerLabel = new JLabel("Producent: ");

        flavourProfileBox.setEditable(true);
        manufacturerBox.setEditable(true);
    }

    private void setNameFieldFont() {
        Font defaultFont = nameField.getFont();
        Font newFont = defaultFont.deriveFont(Font.BOLD, (float) 14.0);
        nameField.setFont(newFont);
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(0, 8, 0 ,8);

        //First column
        gc.gridwidth = 2;
        gc.ipady = 8;
        add(nameField, gc);

        gc.gridwidth = 1;
        gc.ipady = 0;
        gc.gridy++;
        add(manufacturerLabel, gc);

        gc.gridy++;
        add(flavourProfileLabel, gc);

        gc.gridy++;
        gc.gridwidth = 2;
        add(createBtn, gc);
        gc.gridwidth = 1;

        //second column
        gc.gridx = 1;
        gc.gridy = 0;
        // empty row

        gc.gridy++;
        add(manufacturerBox, gc);

        gc.gridy++;
        add(flavourProfileBox, gc);
    }

    void setBoxItems(List<String> flavourProfileList, List<String> manufacturersList){
        flavourProfileBox.removeAllItems();
        manufacturerBox.removeAllItems();

        for (String flavourProfile: flavourProfileList){
            flavourProfileBox.addItem(flavourProfile);
        }
        for (String manufacturer: manufacturersList){
            manufacturerBox.addItem(manufacturer);
        }
    }

    JButton getCreateBtn(){
        return createBtn;
    }
    String getConcentrateName(){
        String name = nameField.getText();
        nameField.setText("");
        return name;
    }
    String getManufacturer(){
        return (String) manufacturerBox.getSelectedItem();
    }

    String getFlavourProfile(){
        return (String) flavourProfileBox.getSelectedItem();
    }

    void showErrorDialog(){
        JOptionPane.showMessageDialog(this, "Wypełnij wszystkie pola.", "Błąd", JOptionPane.ERROR_MESSAGE);
    }
    void showAlreadyExistsDialog(){
        JOptionPane.showMessageDialog(this, "Aromat już istnieje w bazie danych.", "Błąd", JOptionPane.ERROR_MESSAGE);
    }
}
