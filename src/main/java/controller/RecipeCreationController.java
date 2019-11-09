package controller;

import gui.RecipeCreationListener;
import gui.RecipeCreationPanel;
import model.Concentrate;
import model.ConcentrateInRecipe;
import model.Recipe;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecipeCreationController implements RecipeCreationListener {
    private RecipeCreationPanel recipeCreationPanel;

    private double volume;
    private double desiredStrength;
    private double realStrength;
    private double nicStrength;
    private double totalConcentratePercentage;
    private double desiredPgVgRatio;
    private double realPgVgRatio;
    private double nicPgVgRatio;
    private int steepTime;
    private ArrayList<ConcentrateInRecipe> concentrates;
    private RecipeCreationControllerInterface listener;

    RecipeCreationController(RecipeCreationPanel recipeCreationPanel) {
        this.recipeCreationPanel = recipeCreationPanel;
        recipeCreationPanel.setListener(this);
        makeNewRecipe();
    }

    void loadRecipe(Recipe recipe) {
        volume = recipe.getVolume();
        desiredStrength = recipe.getStrength();
        desiredPgVgRatio = recipe.getPgVgRatio();
        steepTime = recipe.getSteepTime();
        recipeCreationPanel.setRecipeName(recipe.getName());
        setSpinnerValues();
        concentrates.clear();
        concentrates = recipe.cloneConcentrates();
        recipeCreationPanel.setConcentrates(concentrates);
        recipeCreationPanel.refreshTable();
    }

    void addConcentrateInRecipe(Concentrate concentrate) {
        ConcentrateInRecipe concentrateInRecipe = new ConcentrateInRecipe(concentrate, 0);
        concentrates.add(concentrateInRecipe);
        recipeCreationPanel.refreshTable();
    }

    boolean getRecipeOverwriteConfirmation() {
        return recipeCreationPanel.showRecipeOverwriteConfirmation();
    }

    void setListener(RecipeCreationControllerInterface listener) {
        this.listener = listener;
    }

    /////RecipeCreationListener

    @Override
    public void saveRecipe() {
        String name = recipeCreationPanel.getRecipeName();
        Recipe recipe = new Recipe(name, desiredStrength, desiredPgVgRatio, volume, steepTime, concentrates);
        if (listener != null) {
            listener.saveRecipe(recipe);
        }
    }

    @Override
    public void removeConcentrate(int row) {
        concentrates.remove(row);
        recipeCreationPanel.refreshTable();
    }

    @Override
    public void requestConcentrateDialog() {
        listener.requestConcentrateDialog();
    }

    @Override
    public void volumeChanged(double volume) {
        this.volume = volume;
        calculateSummary();
        recipeCreationPanel.setVolume(volume);
    }

    @Override
    public void desiredStrengthChanged(double strength) {
        this.desiredStrength = strength;
        calculateSummary();
    }

    @Override
    public void ratioChanged(double glycol) {
        this.desiredPgVgRatio = glycol;
        recipeCreationPanel.setRatioSpinners(glycol);
        calculateSummary();
    }

    @Override
    public void nicStrengthChanged(double strength) {
        this.nicStrength = strength;
        calculateSummary();
    }

    @Override
    public void nicRatioChanged(double glycol) {
        this.nicPgVgRatio = glycol;
        recipeCreationPanel.setNicRatioSpinners(glycol);
        calculateSummary();
    }

    @Override
    public void steepTimeChanged(int steepTime) {
        this.steepTime = steepTime;
    }

    @Override
    public void concentrateTotalChanged(double concentrateTotal) {
        totalConcentratePercentage = concentrateTotal;
        calculateSummary();
    }

    private void calculateSummary() {
        realStrength = desiredStrength;
        realPgVgRatio = desiredPgVgRatio;
        double concentrateVolume = (volume * totalConcentratePercentage) / 100; // concentrate treated as 100% glycol
        double nicAmount = (volume * desiredStrength) / nicStrength;

        if (Double.isNaN(nicAmount)) {
            nicAmount = 0;
        }
        if (nicAmount >= (volume - concentrateVolume)) {
            nicAmount = volume - concentrateVolume;
            realStrength = (nicAmount * nicStrength) / volume;
            double glycolFromNic = (nicAmount * nicPgVgRatio) / 100;
            double totalGlycol = glycolFromNic + concentrateVolume;
            realPgVgRatio = (totalGlycol / volume) * 100;

            setSummaryValues(nicAmount, 0, 0, concentrateVolume);
        } else {
            double glycolFromNic = (nicAmount * nicPgVgRatio) / 100;
            double glycerineFromNic = (nicAmount * (100 - nicPgVgRatio)) / 100;

            double desiredGlycol = (volume * desiredPgVgRatio) / 100;
            double desiredGlycerine = (volume * (100 - desiredPgVgRatio)) / 100;

            double glycolToAdd = desiredGlycol - glycolFromNic - concentrateVolume;
            double glycerineToAdd = desiredGlycerine - glycerineFromNic;

            if (glycolToAdd < 0) {
                glycerineToAdd = volume - nicAmount - concentrateVolume;
                glycolToAdd = 0;
                double totalGlycol = glycolFromNic + concentrateVolume;
                realPgVgRatio = (totalGlycol / volume) * 100;
            }
            if (glycerineToAdd < 0) {
                glycolToAdd = volume - nicAmount - concentrateVolume;
                glycerineToAdd = 0;
                double totalGlycol = glycolFromNic + concentrateVolume + glycolToAdd;
                realPgVgRatio = (totalGlycol / volume) * 100;
            }
            setSummaryValues(nicAmount, glycolToAdd, glycerineToAdd, concentrateVolume);
        }
    }

    private void setSummaryValues(double nicAmount, double glycolToAdd, double glycerineToAdd, double concentrateVolume) {
        DecimalFormat df = new DecimalFormat("###0.0");
        String strengthSummary = df.format(realStrength) + " mg";
        String ratio = df.format(realPgVgRatio) + " / " + df.format(100 - realPgVgRatio);
        String concentrateTotal = df.format(totalConcentratePercentage) + "%  /  " + df.format(concentrateVolume) + "ml";

        String nicAmountSummary = df.format(nicAmount) + " ml";
        String glycolToAddSummary = df.format(glycolToAdd) + " ml";
        String glycerineToAddSummary = df.format(glycerineToAdd) + "ml";

        recipeCreationPanel.setSummaryValues(strengthSummary, ratio, concentrateTotal, nicAmountSummary, glycolToAddSummary, glycerineToAddSummary);
    }

    private void setSpinnerValues() {
        recipeCreationPanel.setSpinnerValues(volume, desiredStrength, desiredPgVgRatio, nicStrength, nicPgVgRatio, steepTime);
    }

    private void makeNewRecipe() {
        volume = 50;
        desiredStrength = 3;
        nicStrength = 18;
        totalConcentratePercentage = 0;
        desiredPgVgRatio = 20;
        nicPgVgRatio = 50;
        steepTime = 0;

        setSpinnerValues();

        concentrates = new ArrayList<>();
        recipeCreationPanel.setConcentrates(concentrates);
        recipeCreationPanel.refreshTable();
    }
}
