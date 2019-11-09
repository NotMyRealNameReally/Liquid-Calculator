package gui;

import model.Concentrate;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class ConcentrateDialog extends JDialog {
    private JTable table;
    private ConcentrateCatalogTableModel tableModel;
    private JButton addBtn;
    private JButton newConcentrateBtn;
    private List<String> flavourProfiles;
    private List<String> manufacturers;
    private ConcentrateDialogListener listener;

    private ConcentrateCreationDialog concentrateCreationDialog;

    ConcentrateDialog(Frame parent) {
        super(parent, "Dodaj aromat", true);
        setupComponents();
        layoutComponents();
        setTableFont();

        setConcentrateCreationDialogListener();
        setupButtonsListeners();

        setSize(400, 400);
        setLocationRelativeTo(parent);
    }

    public void showConcentrateCreationError() {
        concentrateCreationDialog.showErrorDialog();
    }

    public void hideConcentrateCreationDialog() {
        concentrateCreationDialog.setVisible(false);
    }

    public void showConcentrateAlreadyExistsMessage() {
        concentrateCreationDialog.showAlreadyExistsDialog();
    }

    public void refresh() {
        tableModel.fireTableDataChanged();
    }

    private void setupComponents() {
        tableModel = new ConcentrateCatalogTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(30);
        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(10);
        tcm.getColumn(1).setPreferredWidth(170);

        addBtn = new JButton("Dodaj");
        newConcentrateBtn = new JButton("Nowy aromat");

        concentrateCreationDialog = new ConcentrateCreationDialog(this);
    }

    private void layoutComponents() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        add(new JScrollPane(table));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addBtn);
        buttonPanel.add(newConcentrateBtn);
        add(buttonPanel);
    }

    private void setupButtonsListeners() {
        newConcentrateBtn.addActionListener(e -> {
            concentrateCreationDialog.setBoxItems(flavourProfiles, manufacturers);
            concentrateCreationDialog.setVisible(true);
        });
        addBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            listener.concentrateChosen(row);
        });
    }

    private void setTableFont() {
        Font oldFont = table.getFont();
        Font newFont = oldFont.deriveFont(Font.BOLD, (float) 20);
        table.setFont(newFont);
    }

    private void setConcentrateCreationDialogListener() {
        concentrateCreationDialog.getCreateBtn().addActionListener(e -> {
            if (listener != null) {
                String name = concentrateCreationDialog.getConcentrateName();
                String manufacturer = concentrateCreationDialog.getManufacturer();
                String flavourProfile = concentrateCreationDialog.getFlavourProfile();

                listener.concentrateCreated(name, flavourProfile, manufacturer);


            }
        });
    }

    public void setConcentrates(List<Concentrate> concentrates) {
        tableModel.setConcentrates(concentrates);
    }

    public void setFlavourProfiles(List<String> flavourProfiles) {
        this.flavourProfiles = flavourProfiles;
    }

    public void setManufacturers(List<String> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public void setListener(ConcentrateDialogListener listener) {
        this.listener = listener;
    }
}

class ConcentrateCatalogTableModel extends AbstractTableModel {
    private List<Concentrate> concentrates;
    private String[] colNames = new String[]{"Producent", "Nazwa"};

    @Override
    public int getRowCount() {
        if (concentrates != null) {
            return concentrates.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Concentrate concentrate = concentrates.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return concentrate.getManufacturer();
            case 1:
                return concentrate.getName();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    void setConcentrates(List<Concentrate> concentrates) {
        this.concentrates = concentrates;
    }
}
