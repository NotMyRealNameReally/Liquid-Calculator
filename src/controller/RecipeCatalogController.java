package controller;

import gui.RecipeCatalogPanel;
import model.Database;
import model.Recipe;

public class RecipeCatalogController {
    private Database database = new Database();
    private RecipeCatalogPanel recipeCatalogPanel;

    public RecipeCatalogController(RecipeCatalogPanel panel){
        this.recipeCatalogPanel = panel;
        recipeCatalogPanel.setRecipes(database.getRecipes());
    }
    public void addRecipe(Recipe recipe){
        database.addRecipe(recipe);
        recipeCatalogPanel.refresh();
    }
}
