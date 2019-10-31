package controller;

import gui.RecipeCreationEvent;
import gui.RecipeCreationListener;
import gui.RecipeCreationPanel;
import gui.SpinnerType;
import model.Concentrate;
import model.ConcentrateInRecipe;
import model.Recipe;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecipeCreationController {
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

    public RecipeCreationController(RecipeCreationPanel recipeCreationPanel, RecipeCreationControllerInterface listener) {
        this.recipeCreationPanel = recipeCreationPanel;
        this.listener = listener;
        setRecipePanelListeners();
        makeNewRecipe();

    }
    private void save() {
        String name = recipeCreationPanel.getRecipeName();
        Recipe recipe = new Recipe(name, realStrength, realPgVgRatio, volume, steepTime, concentrates);
        if (listener != null) {
            listener.saveRecipe(recipe);
        }
    }

    private void setRecipePanelListeners() {
        recipeCreationPanel.setListener(new RecipeCreationListener() {
            @Override
            public void spinnerChanged(RecipeCreationEvent e) {
                SpinnerType spinnerType = e.getSpinnerType();
                JSpinner sourceSpinner = (JSpinner) e.getSource();
                double value = (double) sourceSpinner.getValue();

                switch (spinnerType) {
                    case volume:
                        volumeChanged(value);
                        break;
                    case desiredStrength:
                        desiredStrengthChanged(value);
                        break;
                    case desiredGlycol:
                        ratioChanged(value);
                        break;
                    case desiredGlycerine:
                        ratioChanged(100 - value);
                        break;
                    case nicStrength:
                        nicStrengthChanged(value);
                        break;
                    case nicGlycol:
                        nicRatioChanged(value);
                        break;
                    case nicGlycerine:
                        nicRatioChanged(100 - value);
                        break;
                    case steepTime:
                        steepTimeChanged(value);
                }
                calculateSummary();
            }

            @Override
            public void concentrateTotalChanged(double concentrateTotal) {
                totalConcentratePercentage = concentrateTotal;
                calculateSummary();
            }

            @Override
            public void saveRecipe() {
                save();
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
        });
    }

    private void setSpinnerValues(){
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

    private void volumeChanged(double value) {
        this.volume = value;
        recipeCreationPanel.updateVolume(value);
    }

    private void desiredStrengthChanged(double value) {
        this.desiredStrength = value;
    }

    private void ratioChanged(double glycol) {
        this.desiredPgVgRatio = glycol;
        recipeCreationPanel.setRatioSpinners(glycol);
    }

    private void nicStrengthChanged(double value) {
        this.nicStrength = value;
    }

    private void nicRatioChanged(double glycol) {
        this.nicPgVgRatio = glycol;
        recipeCreationPanel.setNicRatioSpinners(glycol);

    }

    private void steepTimeChanged(double value) {
        steepTime = (int) value;
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

    void addConcentrateInRecipe(Concentrate concentrate){
        ConcentrateInRecipe concentrateInRecipe = new ConcentrateInRecipe(concentrate, 0);
        concentrates.add(concentrateInRecipe);
        recipeCreationPanel.refreshTable();
    }

    void loadRecipe(Recipe recipe){
        volume = recipe.getVolume();
        desiredStrength = recipe.getStrength();
        desiredPgVgRatio = recipe.getPgVgRatio();
        steepTime = recipe.getSteepTime();
        setSpinnerValues();
        concentrates.clear();
        concentrates = recipe.cloneConcentrates();
        recipeCreationPanel.setConcentrates(concentrates);
        recipeCreationPanel.refreshTable();
    }
}
