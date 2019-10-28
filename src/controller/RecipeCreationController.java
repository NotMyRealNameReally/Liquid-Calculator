package controller;

import gui.ConcentrateTableListener;
import gui.RecipeCreationPanel;
import gui.SpinnerType;
import model.ConcentrateInRecipe;
import model.Recipe;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecipeCreationController {
    private RecipeCreationPanel recipeCreationPanel;

    private double volume;
    private double desiredStrength;
    private double realStrength = desiredStrength;
    private double nicStrength;
    private double totalConcentratePercentage;
    private double desiredPgVgRatio;
    private double realPgVgRatio = desiredPgVgRatio;
    private double nicPgVgRatio;
    private int steepTime;
    private ArrayList<ConcentrateInRecipe> concentrates;
    private SaveListener listener;

    public RecipeCreationController(RecipeCreationPanel recipeCreationPanel) {
        this.recipeCreationPanel = recipeCreationPanel;
        setRecipePanelListeners();
        setConcentrateTableListener();
        recipeCreationPanel.setSpinnerValues(10, 3, 50, 18, 50, 0);
    }

    private void setRecipePanelListeners() {
        recipeCreationPanel.setListener(e -> {
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
        });
    }

    private void setConcentrateTableListener() {
        recipeCreationPanel.setConcentrateTableListener(new ConcentrateTableListener() {
            @Override
            public void percentageChanged(double percentage) {
                totalConcentratePercentage = percentage;
                calculateSummary();
            }
            @Override
            public void concentratesRequested(ArrayList<ConcentrateInRecipe> concentrates) {
                save();
            }

            @Override
            public void showConcentrateDialog() {

            }
        });
    }

    public void save() {
        String name = recipeCreationPanel.getRecipeName();
        Recipe recipe = new Recipe(name, realStrength, realPgVgRatio, volume, steepTime, concentrates);
        if (listener != null){
            listener.save(recipe);
        }
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

    public void setListener(SaveListener listener) {
        this.listener = listener;
    }
}
