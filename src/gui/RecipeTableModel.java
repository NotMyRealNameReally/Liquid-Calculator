package gui;

import model.Recipe;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class RecipeTableModel extends AbstractTableModel {
   private List<Recipe> recipes;
   private String[] colNames = new String[]{"Nazwa", "Moc"};

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public int getRowCount() {
       if (recipes != null){
           return recipes.size();
       }else {
           return 0;
       }
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) return recipes.get(rowIndex).getName();
        return recipes.get(rowIndex).getStrength();
    }
    public void setRecipes(List<Recipe> recipes){
        this.recipes = recipes;
    }
}
