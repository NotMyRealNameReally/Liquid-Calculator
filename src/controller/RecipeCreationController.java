package controller;

import gui.MainFrame;
import gui.RecipeCreationPanel;
import gui.SpinnerType;

import javax.swing.*;

public class RecipeCreationController {
    private RecipeCreationPanel recipeCreationPanel;

    public RecipeCreationController(RecipeCreationPanel recipeCreationPanel){
        this.recipeCreationPanel = recipeCreationPanel;
        setRecipePanelListener();
    }
    private void setRecipePanelListener(){
        recipeCreationPanel.setListener(e -> {
            SpinnerType spinnerType = e.getSpinnerType();
            JSpinner sourceSpinner = (JSpinner)e.getSource();

            switch (spinnerType){
                case volume:
                    volumeChanged(sourceSpinner);
                    break;
                case desiredStrength:
                    desiredStrengthChanged(sourceSpinner);
                    break;
                case desiredGlycol:
                    desiredGlycolChanged(sourceSpinner);
                    break;
                case desiredGlycerine:
                    desiredGlycerineChanged(sourceSpinner);
                    break;
                case nicStrength:
                    nicStrengthChanged(sourceSpinner);
                    break;
                case nicGlycol:
                    nicGlycolChanged(sourceSpinner);
                    break;
                case nicGlycerine:
                    nicGlycerineChanged(sourceSpinner);
                    break;
                case steepTime:
                    steepTimeChanged(sourceSpinner);
            }
        });
    }

    private void volumeChanged(JSpinner spinner) {

    }
}
