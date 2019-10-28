package controller;

import model.Recipe;

interface RecipeCreationControllerInterface {
    void saveRecipe(Recipe recipe);
    void requestConcentrateDialog();
}
