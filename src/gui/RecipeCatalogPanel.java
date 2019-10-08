package gui;

import javax.swing.*;
import java.awt.*;

public class RecipeCatalogPanel extends JPanel {
    private JTable table;

    RecipeCatalogPanel(){
        setLayout(new BorderLayout());

        table = new JTable();
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
