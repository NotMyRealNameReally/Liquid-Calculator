package gui;

public interface RecipeCreationListener {
    void volumeChanged(double volume);
    void desiredStrengthChanged(double strength);
    void ratioChanged(double glycol);
    void nicStrengthChanged(double strength);
    void nicRatioChanged(double glycol);
    void steepTimeChanged(int steepTime);
    void concentrateTotalChanged(double concentrateTotal);
    void saveRecipe();
    void removeConcentrate(int row);
    void requestConcentrateDialog();
}