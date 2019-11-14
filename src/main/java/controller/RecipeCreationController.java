package controller;

import gui.RecipeCreationListener;
import gui.RecipeCreationPanel;
import model.Calculator;
import model.Concentrate;
import model.ConcentrateInRecipe;
import model.Recipe;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecipeCreationController implements RecipeCreationListener {
    private RecipeCreationPanel recipeCreationPanel;
    private Calculator calculator;

    private RecipeCreationControllerInterface listener;

    RecipeCreationController(RecipeCreationPanel recipeCreationPanel) {
        this.recipeCreationPanel = recipeCreationPanel;
        recipeCreationPanel.setListener(this);
        calculator = new Calculator();
        makeNewRecipe();
    }

    void loadRecipe(Recipe recipe) {
        calculator.setVolume(recipe.getVolume());
        calculator.setDesiredStrength(recipe.getStrength());
        calculator.setDesiredPgVgRatio(recipe.getPgVgRatio());
        calculator.setSteepTime(recipe.getSteepTime());
        recipeCreationPanel.setRecipeName(recipe.getName());

        ArrayList<ConcentrateInRecipe> concentrates = recipe.cloneConcentrates();
        calculator.setConcentrates(concentrates);

        recipeCreationPanel.setConcentrates(concentrates);
        recipeCreationPanel.refreshTable();
        setSpinnerValues();
    }

    void addConcentrateInRecipe(Concentrate concentrate) {
        ConcentrateInRecipe concentrateInRecipe = new ConcentrateInRecipe(concentrate, 0);
        calculator.addConcentrate(concentrateInRecipe);
        recipeCreationPanel.refreshTable();
    }

    boolean getRecipeOverwriteConfirmation() {
        return recipeCreationPanel.showRecipeOverwriteConfirmation();
    }

    /////RecipeCreationListener

    @Override
    public void saveRecipe() {
        String name = recipeCreationPanel.getRecipeName();
        Recipe recipe = calculator.createRecipe(name);
        if (listener != null) {
            listener.saveRecipe(recipe);
        }
    }

    @Override
    public void removeConcentrate(int row) {
        calculator.removeConcentrate(row);
        recipeCreationPanel.refreshTable();
    }

    @Override
    public void requestConcentrateDialog() {
        listener.requestConcentrateDialog();
    }

    @Override
    public void volumeChanged(double volume) {
        calculator.setVolume(volume);
        calculateSummary();
        recipeCreationPanel.setVolume(volume);
    }

    @Override
    public void desiredStrengthChanged(double strength) {
        calculator.setDesiredStrength(strength);
        calculateSummary();
    }

    @Override
    public void ratioChanged(double glycol) {
        calculator.setDesiredPgVgRatio(glycol);
        recipeCreationPanel.setRatioSpinners(glycol);
        calculateSummary();
    }

    @Override
    public void nicStrengthChanged(double strength) {
        calculator.setNicStrength(strength);
        calculateSummary();
    }

    @Override
    public void nicRatioChanged(double glycol) {
        calculator.setNicPgVgRatio(glycol);
        recipeCreationPanel.setNicRatioSpinners(glycol);
        calculateSummary();
    }

    @Override
    public void steepTimeChanged(int steepTime) {
        calculator.setSteepTime(steepTime);
    }

    @Override
    public void concentrateTotalChanged(double concentrateTotal) {
        calculator.setTotalConcentratePercentage(concentrateTotal);
        calculateSummary();
    }

    private void calculateSummary() {
        calculator.calculateSummary();
        setSummaryValues();
    }

    private void setSummaryValues() {
        DecimalFormat df = new DecimalFormat("###0.0");
        String strengthSummary = df.format(calculator.getRealStrength()) + " mg";
        String ratio = df.format(calculator.getRealPgVgRatio()) + " / " + df.format(100 - calculator.getRealPgVgRatio());
        String concentrateTotal = df.format(calculator.getTotalConcentratePercentage()) + "%  /  " + df.format(calculator.getConcentrateVolume()) + "ml";

        String nicAmountSummary = df.format(calculator.getNicAmount()) + " ml";
        String glycolToAddSummary = df.format(calculator.getGlycolToAdd()) + " ml";
        String glycerineToAddSummary = df.format(calculator.getGlycerineToAdd()) + "ml";

        recipeCreationPanel.setSummaryValues(strengthSummary, ratio, concentrateTotal, nicAmountSummary, glycolToAddSummary, glycerineToAddSummary);
    }

    private void setSpinnerValues() {
        recipeCreationPanel.setSpinnerValues(calculator.getVolume(), calculator.getDesiredStrength(),
                calculator.getDesiredPgVgRatio(), calculator.getNicStrength(), calculator.getNicPgVgRatio(), calculator.getSteepTime());
    }

    private void makeNewRecipe() {
        calculator.setVolume(50);
        calculator.setDesiredStrength(3);
        calculator.setNicStrength(18);
        calculator.setTotalConcentratePercentage(0);
        calculator.setDesiredPgVgRatio(20);
        calculator.setNicPgVgRatio(50);
        calculator.setSteepTime(0);

        ArrayList<ConcentrateInRecipe> concentrates = new ArrayList<>();
        calculator.setConcentrates(concentrates);

        recipeCreationPanel.setConcentrates(concentrates);
        recipeCreationPanel.refreshTable();
        setSpinnerValues();
    }

    void setListener(RecipeCreationControllerInterface listener) {
        this.listener = listener;
    }
}
