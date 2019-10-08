package gui;

import model.ConcentrateInRecipe;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ConcentrateTableModel extends AbstractTableModel {
    private List<ConcentrateInRecipe> database;
    private double volume = 10;
    private static final int dropsInMl = 20;


    public void setVolume(int volume) {
        this.volume = (double) volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    private String[] colNames = {"Aromat", "%", "krople", "ml"};

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public int getRowCount() {
        return database.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ConcentrateInRecipe concentrate = database.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return concentrate.getConcentrate();
            case 1:
                return concentrate.getPercentage();
            case 2:
                return getDrops(volume, concentrate.getPercentage());
            case 3:
                return getMl(volume, concentrate.getPercentage());
            default:
                return null;

        }
    }

    private double getMl(double volume, double percentage) {
        return (volume * percentage / 100);
    }

    private int getDrops(double volume, double percentage) {
        double ml = getMl(volume, percentage);
        return (int)Math.round(ml * dropsInMl);
    }

    public void setDatabase(List<ConcentrateInRecipe> database) {
        this.database = database;
    }

    public void setConcentratePercentage(int row, double percentage) {
        database.get(row).setPercentage(percentage);
    }
}
