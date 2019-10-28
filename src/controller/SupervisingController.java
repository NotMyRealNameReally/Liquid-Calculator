package controller;

import gui.MainFrame;
import model.Database;
import model.Recipe;

public class SupervisingController implements RecipeCreationControllerInterface{
     MainFrame mainFrame;
     RecipeCatalogController recipeCatalogController;
     RecipeCreationController recipeCreationController;
     private Database database = new Database();

    public SupervisingController(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        recipeCatalogController = new RecipeCatalogController(mainFrame.getRecipeCatalogPanel(), database.getRecipes(), mainFrame.getConcentrateDialog());
        recipeCreationController = new RecipeCreationController(mainFrame.getRecipeCreationPanel(), this);
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        database.addRecipe(recipe);
        recipeCatalogController.refreshTable();
    }

    @Override
    public void requestConcentrateDialog() {
        recipeCatalogController.showConcentrateDialog();
        System.out.println("sdg");
    }
}
