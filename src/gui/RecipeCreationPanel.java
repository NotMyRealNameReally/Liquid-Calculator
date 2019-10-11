package gui;

import model.Concentrate;
import model.ConcentrateInRecipe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RecipeCreationPanel extends JPanel {
    private ConcentrateTablePanel concentrateTablePanel;
    private SummaryPanel summaryPanel;

    private JLabel nameLabel;
    private JLabel volumeLabel;
    private JLabel desiredStrengthLabel;
    private JLabel desiredGlycolLabel;
    private JLabel desiredGlycerineLabel;
    private JLabel nicStrengthLabel;
    private JLabel nicGlycolLabel;
    private JLabel nicGlycerineLabel;
    private JLabel steepTimeLabel;

    private JSpinner volumeSpinner;
    private JSpinner desiredStrengthSpinner;
    private JSpinner desiredGlycolSpinner;
    private JSpinner desiredGlycerineSpinner;
    private JSpinner nicStrengthSpinner;
    private JSpinner nicGlycolSpinner;
    private JSpinner nicGlycerineSpinner;
    private JSpinner steepTimeSpinner;

    private JCheckBox maxGlycerineCheckBox;
    private JTextField nameField;

    public void setListener(RecipeCreationListener listener) {
        this.listener = listener;
    }

    private RecipeCreationListener listener;

    public RecipeCreationPanel() {
        setupComponents();
        setNameFieldFont();

        ArrayList<ConcentrateInRecipe> database = new ArrayList<>();
        ConcentrateInRecipe truskawka = new ConcentrateInRecipe(new Concentrate("Truskawka", "Inawera", "Truskawka"));
        ConcentrateInRecipe malina = new ConcentrateInRecipe(new Concentrate("Malina", "Inawera", "Malina"));
        database.add(truskawka);
        database.add(malina);
        concentrateTablePanel.setDatabase(database);
        addSpinnerListeners();

        layoutComponents();
        concentrateTablePanel.refresh();

    }

    private void setupComponents() {
        concentrateTablePanel = new ConcentrateTablePanel();
        summaryPanel = new SummaryPanel();

        nameLabel = new JLabel("Nazwa:");
        volumeLabel = new JLabel("Docelowa objętość:");
        desiredStrengthLabel = new JLabel("Docelowa moc:");
        desiredGlycolLabel = new JLabel("Zawartość PG:");
        desiredGlycerineLabel = new JLabel("Zawartość VG:");
        nicStrengthLabel = new JLabel("Moc bazy:");
        nicGlycolLabel = new JLabel("Zawartość PG w bazie:");
        nicGlycerineLabel = new JLabel("Zawartość VG w bazie:");
        steepTimeLabel = new JLabel("Czas przegryzania:");

        volumeSpinner = new JSpinner(setDefaultModel());
        desiredStrengthSpinner = new JSpinner(setDefaultModel());
        desiredGlycolSpinner = new JSpinner(setPercentModel());
        desiredGlycerineSpinner = new JSpinner(setPercentModel());
        nicStrengthSpinner = new JSpinner(setDefaultModel());
        nicGlycolSpinner = new JSpinner(setPercentModel());
        nicGlycerineSpinner = new JSpinner(setPercentModel());
        steepTimeSpinner = new JSpinner(setDefaultModel());

        maxGlycerineCheckBox = new JCheckBox("Max VG");
        nameField = new JTextField(20);
    }

    private SpinnerNumberModel setPercentModel() {
        return new SpinnerNumberModel(50, 0, 100, 1);
    }

    private SpinnerNumberModel setDefaultModel() {
        return new SpinnerNumberModel(0, 0, 999, 1);
    }

    private void setNameFieldFont() {
        Font defaultFont = nameField.getFont();
        Font newFont = defaultFont.deriveFont(Font.BOLD, (float) 20.0);
        nameField.setFont(newFont);
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        Insets rightPadding = new Insets(0, 0, 0, 10);
        Insets leftPadding = new Insets(0, 10, 0, 0);
        Insets noPadding = new Insets(0, 0, 0, 0);

        ///////First column(from the left)////////
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.ipady = 20;
        gc.gridwidth = 3;

        add(nameField, gc);
        gc.gridwidth = 1;

        gc.gridy = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        gc.ipady = 0;

        add(volumeLabel, gc);

        gc.gridy++;
        add(desiredStrengthLabel, gc);

        gc.gridy++;
        add(desiredGlycolLabel, gc);

        gc.gridy++;
        add(desiredGlycerineLabel, gc);

        gc.gridy++;
        //empty row

        gc.gridy++;
        add(nicStrengthLabel, gc);

        gc.gridy++;
        add(nicGlycolLabel, gc);

        gc.gridy++;
        add(nicGlycerineLabel, gc);

        gc.gridy++;
        add(steepTimeLabel, gc);

        gc.gridy++;
        gc.gridwidth = 3;
        add(concentrateTablePanel, gc);

        gc.gridy++;
        gc.gridwidth = 2;
        add(summaryPanel, gc);
        gc.gridwidth = 1;

        ///////Second column////////

        gc.gridx = 1;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridy = 0;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = noPadding;
        //First row is empty

        //gc.weightx = 0.001;
        //gc.weighty = 0.2;

        gc.gridy++;
        add(volumeSpinner, gc);

        gc.gridy++;
        add(desiredStrengthSpinner, gc);

        gc.gridy++;
        add(desiredGlycolSpinner, gc);

        gc.gridy++;
        add(desiredGlycerineSpinner, gc);

        gc.gridy++;
        add(maxGlycerineCheckBox, gc);

        gc.gridy++;
        add(nicStrengthSpinner, gc);

        gc.gridy++;
        add(nicGlycolSpinner, gc);

        gc.gridy++;
        add(nicGlycerineSpinner, gc);

        gc.gridy++;
        add(steepTimeSpinner, gc);

        ///////Third column////////

        gc.gridx = 2;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        //Top row is empty

        //gc.weightx = 1;
        //gc.weighty = 0.2;

        gc.gridy = 1;
        add(new JLabel("ml"), gc);

        gc.gridy++;
        add(new JLabel("mg"), gc);

        gc.gridy++;
        add(new JLabel("%"), gc);

        gc.gridy++;
        add(new JLabel("%"), gc);

        gc.gridy++;
        //empty row

        gc.gridy++;
        add(new JLabel("mg"), gc);

        gc.gridy++;
        add(new JLabel("%"), gc);

        gc.gridy++;
        add(new JLabel("%"), gc);

        gc.gridy++;
        add(new JLabel("dni"), gc);

    }

    public void setConcentrateTableListener(ConcentrateTableListener tableListener) {
        concentrateTablePanel.setTableListener(tableListener);
    }

    private void addSpinnerListeners() {
        volumeSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.volume);
                listener.eventOccurred(recipeEvent);
            }
        });
        desiredStrengthSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.desiredStrength);
                listener.eventOccurred(recipeEvent);
            }

        });
        desiredGlycolSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.desiredGlycol);
                listener.eventOccurred(recipeEvent);
            }

        });
        desiredGlycerineSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.desiredGlycerine);
                listener.eventOccurred(recipeEvent);
            }

        });
        nicStrengthSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.nicStrength);
                listener.eventOccurred(recipeEvent);
            }

        });
        nicGlycolSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.nicGlycol);
                listener.eventOccurred(recipeEvent);
            }

        });
        nicGlycerineSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.nicGlycerine);
                listener.eventOccurred(recipeEvent);
            }

        });
        steepTimeSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.steepTime);
                listener.eventOccurred(recipeEvent);
            }

        });
    }

    public void setSummaryValues(String strength, String ratio, String concentrate) {
        summaryPanel.setSummaryValues(strength, ratio, concentrate);
    }
}
