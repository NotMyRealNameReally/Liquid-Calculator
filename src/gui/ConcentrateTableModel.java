package gui;

import model.ConcentrateInRecipe;

import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ConcentrateTableModel extends AbstractTableModel {
    private ArrayList<ConcentrateInRecipe> concentrates;
    private double volume = 10;
    private static final int dropsInMl = 20;
    private double concentrateTotal;
    private ConcentrateTableListener listener;
    private DecimalFormat decimalFormat = new DecimalFormat("###0.00");

    private String[] colNames = {"Aromat", "%", "krople", "ml"};

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    @Override
    public int getRowCount() {
        return concentrates.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ConcentrateInRecipe concentrate = concentrates.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return concentrate.getConcentrate();
            case 1:
                return concentrate.getPercentage();
            case 2:
                return getDrops(volume, concentrate.getPercentage());
            case 3:
                return decimalFormat.format(getMl(volume, concentrate.getPercentage()));
            default:
                return null;

        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            concentrates.get(rowIndex).setPercentage((double) aValue);
            fireTableRowsUpdated(rowIndex, rowIndex);
            setConcentrateTotal();

            if (listener != null) {
                listener.percentageChanged(concentrateTotal);
            }
        }
    }

    private double getMl(double volume, double percentage) {
        return (volume * percentage / 100);
    }

    private int getDrops(double volume, double percentage) {
        double ml = getMl(volume, percentage);
        return (int) Math.round(ml * dropsInMl);
    }

    void setConcentrates(ArrayList<ConcentrateInRecipe> concentrates) {
        this.concentrates = concentrates;
    }

    void setVolume(int volume) {
        this.volume = (double) volume;
        fireTableDataChanged();
    }

    public void setVolume(double volume) {
        this.volume = volume;
        fireTableDataChanged();
    }

    ArrayList<ConcentrateInRecipe> getConcentrates() {
        return concentrates;
    }

    private void setConcentrateTotal() {
        double total = 0;
        for (ConcentrateInRecipe concentrate : concentrates) {
            total += concentrate.getPercentage();
            this.concentrateTotal = total;
        }
    }

    double getConcentrateTotal() {
        return concentrateTotal;
    }

    void setListener(ConcentrateTableListener listener) {
        this.listener = listener;
    }

    void requestConcentrates() {
        listener.concentratesRequested(concentrates);
    }


}
