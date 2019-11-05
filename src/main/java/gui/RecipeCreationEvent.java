package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.util.EventObject;

public class RecipeCreationEvent extends EventObject {

private SpinnerType spinnerType;

    public RecipeCreationEvent(Object source, SpinnerType spinnerType){
        super(source);
        this.spinnerType = spinnerType;
    }
    public SpinnerType getSpinnerType() {
        return spinnerType;
    }
}
