package controller;

import gui.ConcentrateDialog;
import gui.RecipeCatalogPanel;
import model.Recipe;

import java.util.List;

public class RecipeCatalogController {
    private List<Recipe> recipes;
    private RecipeCatalogPanel recipeCatalogPanel;
    private ConcentrateDialog concentrateDialog;

    public RecipeCatalogController(RecipeCatalogPanel panel, List<Recipe> recipes, ConcentrateDialog concentrateDialog){
        this.recipeCatalogPanel = panel;
        this.recipes = recipes;
        this.concentrateDialog = concentrateDialog;
        recipeCatalogPanel.setRecipes(recipes);

    }
    public void refreshTable(){
        recipeCatalogPanel.refresh();
    }

    void showConcentrateDialog(){
        concentrateDialog.setVisible(true);
    }
}
