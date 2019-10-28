package gui;

public interface RecipeCreationListener {
    void spinnerChanged(RecipeCreationEvent e);
    void concentrateTotalChanged(double concentrateTotal);
    void saveRecipe();
    void removeConcentrate(int row);
    void requestConcentrateDialog();
}