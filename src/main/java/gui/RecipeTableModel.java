package gui;

import model.Recipe;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class RecipeTableModel extends AbstractTableModel {
    private List<Recipe> recipes;
    private String[] colNames = new String[]{"Nazwa", "Autor", "Moc"};

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public int getRowCount() {
        if (recipes != null) {
            return recipes.size();
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
        switch (columnIndex) {
            case 0:
                return recipes.get(rowIndex).getName();
            case 1:
                return recipes.get(rowIndex).getAuthor();
            case 2:
                return recipes.get(rowIndex).getStrength();
            default:
                return null;
        }
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
