package controller;

import gui.MainFrame;

public class SupervisingController {
    private MainFrame mainFrame;
    private RecipeCreationController recipeCreationController;

    public SupervisingController(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        recipeCreationController = new RecipeCreationController(mainFrame.getRecipeCreationPanel());
    }
}
