package controller;

import gui.MainFrame;
import model.Database;
import model.Recipe;

public class SupervisingController {
     MainFrame mainFrame;
     RecipeCatalogController recipeCatalogController;
     RecipeCreationController recipeCreationController;
     private Database database = new Database();

    public SupervisingController(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        recipeCatalogController = new RecipeCatalogController(mainFrame.getRecipeCatalogPanel(), database.getRecipes(), mainFrame.getConcentrateDialog());
        recipeCreationController = new RecipeCreationController(mainFrame.getRecipeCreationPanel());


        recipeCreationController.setListener(object -> {
            Recipe recipe = (Recipe)object;
            database.addRecipe(recipe);
            recipeCatalogController.refreshTable();
        });
    }
}
