package controller;

import gui.MainFrame;
import model.Recipe;

public class SupervisingController {
     MainFrame mainFrame;
     RecipeCatalogController recipeCatalogController;
     RecipeCreationController recipeCreationController;

    public SupervisingController(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        recipeCatalogController = new RecipeCatalogController(mainFrame.getRecipeCatalogPanel());
        recipeCreationController = new RecipeCreationController(mainFrame.getRecipeCreationPanel());

        recipeCreationController.setListener(object -> {
            Recipe recipe = (Recipe)object;
            recipeCatalogController.addRecipe(recipe);
        });
    }
}
