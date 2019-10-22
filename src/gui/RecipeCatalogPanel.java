package gui;

import model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecipeCatalogPanel extends JPanel {
    private JTable table;
    private RecipeTableModel tableModel;

    RecipeCatalogPanel() {
        setLayout(new BorderLayout());
        tableModel = new RecipeTableModel();
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
    public void setRecipes(List<Recipe> recipes){
        tableModel.setRecipes(recipes);
    }
    public void refresh(){
        tableModel.fireTableDataChanged();
    }
}
