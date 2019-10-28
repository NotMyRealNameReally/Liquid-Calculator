package controller;

import gui.RecipeCatalogPanel;
import model.Recipe;

import java.util.List;

public class RecipeCatalogController {
    private List<Recipe> recipes;
    private RecipeCatalogPanel recipeCatalogPanel;

    public RecipeCatalogController(RecipeCatalogPanel panel, List<Recipe> recipes){
        this.recipeCatalogPanel = panel;
        this.recipes = recipes;
        recipeCatalogPanel.setRecipes(recipes);

    }
    public void refreshTable(){
        recipeCatalogPanel.refresh();
    }

}
