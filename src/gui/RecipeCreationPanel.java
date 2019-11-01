package gui;

import model.ConcentrateInRecipe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RecipeCreationPanel extends JPanel {
    private ConcentrateTablePanel concentrateTablePanel;
    private SummaryPanel summaryPanel;

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

    private JButton saveBtn;

    private RecipeCreationListener listener;

    RecipeCreationPanel() {
        setupComponents();
        setNameFieldFont();

        addSpinnerListeners();
        setConcentrateTableListener();
        setConcentrateButtonsListener();

        saveBtn.addActionListener(e -> {
            if (!nameField.getText().matches(" *")) {
                if (listener != null){
                    listener.saveRecipe();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Nazwa nie może być pusta.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        layoutComponents();
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void setupComponents() {
        concentrateTablePanel = new ConcentrateTablePanel();
        summaryPanel = new SummaryPanel();

        volumeLabel = new JLabel("Docelowa objętość:");
        desiredStrengthLabel = new JLabel("Docelowa moc:");
        desiredGlycolLabel = new JLabel("Docelowa zawartość PG:");
        desiredGlycerineLabel = new JLabel("Docelowa zawartość VG:");
        nicStrengthLabel = new JLabel("Moc bazy:");
        nicGlycolLabel = new JLabel("Zawartość PG w bazie:");
        nicGlycerineLabel = new JLabel("Zawartość VG w bazie:");
        steepTimeLabel = new JLabel("Czas przegryzania:");

        volumeSpinner = new JSpinner(setDecimalModel());
        desiredStrengthSpinner = new JSpinner(setDecimalModel());
        desiredGlycolSpinner = new JSpinner(setPercentModel());
        desiredGlycerineSpinner = new JSpinner(setPercentModel());
        nicStrengthSpinner = new JSpinner(setDecimalModel());
        nicGlycolSpinner = new JSpinner(setPercentModel());
        nicGlycerineSpinner = new JSpinner(setPercentModel());
        steepTimeSpinner = new JSpinner(setDefaultModel());

        maxGlycerineCheckBox = new JCheckBox("Max VG");
        nameField = new JTextField(20);

        saveBtn = new JButton("Zapisz");
    }

    private SpinnerNumberModel setPercentModel() {
        return new SpinnerNumberModel(50, 0, 100, 1.0);
    }

    private SpinnerNumberModel setDefaultModel() {
        return new SpinnerNumberModel(0, 0, 999, 1.0);
    }

    private SpinnerNumberModel setDecimalModel() {
        return new SpinnerNumberModel(0, 0, 999, 0.1);
    }

    private void setNameFieldFont() {
        Font defaultFont = nameField.getFont();
        Font newFont = defaultFont.deriveFont(Font.BOLD, (float) 20.0);
        nameField.setFont(newFont);
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        Insets horizontalPadding = new Insets(0, 10, 0, 10);
        Insets noPadding = new Insets(0, 0, 0, 0);

        ///////First column(from the left)////////
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 0.8;
        gc.fill = GridBagConstraints.HORIZONTAL;

        gc.ipady = 10;
        gc.insets = horizontalPadding;
        gc.gridwidth = 3;
        add(nameField, gc);

        gc.ipady = 0;
        gc.insets = noPadding;
        gc.gridwidth = 1;

        gc.gridy = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;

        add(volumeLabel, gc);

        gc.gridy++;
        add(desiredStrengthLabel, gc);

        gc.gridy++;
        add(desiredGlycolLabel, gc);

        gc.gridy++;
        add(desiredGlycerineLabel, gc);

        gc.gridy++;
        //empty cell

        gc.gridy++;
        add(nicStrengthLabel, gc);

        gc.gridy++;
        add(nicGlycolLabel, gc);

        gc.gridy++;
        add(nicGlycerineLabel, gc);

        gc.gridy++;
        add(steepTimeLabel, gc);

        gc.gridy++;
        gc.weighty = 1;
        gc.gridwidth = 3;
        gc.anchor = GridBagConstraints.CENTER;
        add(concentrateTablePanel, gc);

        gc.gridy++;
        gc.gridwidth = 3;
        add(summaryPanel, gc);

        gc.gridy++;
        add(saveBtn, gc);

        gc.gridwidth = 1;

        ///////Second column////////

        gc.gridx = 1;
        gc.weightx = 0.1;
        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridy = 0;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = noPadding;
        //First row is empty

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
        gc.weighty = 0.8;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = noPadding;
        //Top row is empty


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

    private void addSpinnerListeners() {
        volumeSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.volume);
                listener.spinnerChanged(recipeEvent);
            }
        });
        desiredStrengthSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.desiredStrength);
                listener.spinnerChanged(recipeEvent);
            }

        });
        desiredGlycolSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.desiredGlycol);
                listener.spinnerChanged(recipeEvent);
            }

        });
        desiredGlycerineSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.desiredGlycerine);
                listener.spinnerChanged(recipeEvent);
            }

        });
        nicStrengthSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.nicStrength);
                listener.spinnerChanged(recipeEvent);
            }

        });
        nicGlycolSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.nicGlycol);
                listener.spinnerChanged(recipeEvent);
            }

        });
        nicGlycerineSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.nicGlycerine);
                listener.spinnerChanged(recipeEvent);
            }

        });
        steepTimeSpinner.addChangeListener(e -> {
            if (listener != null) {
                RecipeCreationEvent recipeEvent = new RecipeCreationEvent(e.getSource(), SpinnerType.steepTime);
                listener.spinnerChanged(recipeEvent);
            }

        });
    }

    private void setConcentrateButtonsListener(){
        ButtonPanel buttonPanel = concentrateTablePanel.getBtnPanel();
        buttonPanel.removeBtn.addActionListener(e -> {
            int row = concentrateTablePanel.getTable().getSelectedRow();
            if (listener != null){
                if (row >= 0) {
                    listener.removeConcentrate(row);
                }
            }
        });
        buttonPanel.addBtn.addActionListener(e -> {
            if (listener != null){
                listener.requestConcentrateDialog();
            }
        });
    }

    public void setSpinnerValues(double volume, double strength, double pgVgRatio, double nicStrength, double nicPgVgRatio, double steepTime) {
        volumeSpinner.setValue(volume);
        desiredStrengthSpinner.setValue(strength);
        desiredGlycolSpinner.setValue(pgVgRatio);
        nicStrengthSpinner.setValue(nicStrength);
        nicGlycolSpinner.setValue(nicPgVgRatio);
        steepTimeSpinner.setValue(steepTime);
    }

    public void setListener(RecipeCreationListener listener) {
        this.listener = listener;
    }

    public void setSummaryValues(String strength, String ratio, String concentrate, String nicVolume, String glycolVolume, String glycerineVolume) {
        summaryPanel.setSummaryValues(strength, ratio, concentrate, nicVolume, glycolVolume, glycerineVolume);
    }

    public void updateVolume(double volume) {
        concentrateTablePanel.updateVolume(volume);
    }

    public void setRatioSpinners(double glycol) {
        desiredGlycolSpinner.setValue(glycol);
        desiredGlycerineSpinner.setValue(100 - glycol);
    }

    public void setNicRatioSpinners(double glycol) {
        nicGlycolSpinner.setValue(glycol);
        nicGlycerineSpinner.setValue(100 - glycol);
    }

    private void setConcentrateTableListener() {
        concentrateTablePanel.setTableListener(e -> {
            if (listener != null){
                listener.concentrateTotalChanged(concentrateTablePanel.getConcentrateTotal());
            }
        });
    }

    public String getRecipeName() {
        return nameField.getText();
    }

    public void setConcentrates(ArrayList<ConcentrateInRecipe> concentrates) {
        concentrateTablePanel.setConcentrates(concentrates);
    }

    public void refreshTable(){
        concentrateTablePanel.refresh();
    }

    public void setRecipeName(String name) {
        nameField.setText(name);
    }
}
