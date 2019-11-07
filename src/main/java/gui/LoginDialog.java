package gui;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private JTextField userField;
    private JButton okBtn;
    private JButton cancelBtn;
    private LoginDialogInterface listener;

    public LoginDialog(Frame parent) {
        super(parent, "Zaloguj się", true);
        setupComponents();
        layoutComponents();

        okBtn.addActionListener(e -> {
            String username = userField.getText();
            if (listener != null) {
                listener.userNameEntered(username);
            }
        });
        cancelBtn.addActionListener(e -> {
            if (listener != null) {
                listener.loginCancelled();
            }
        });
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(290, 100);
    }

    private void setupComponents() {
        userField = new JTextField(10);
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Wyjdź");
    }

    private void layoutComponents() {
        setLayout(new FlowLayout());
        add(new JLabel("Nazwa użytkownika: "));
        add(userField);
        add(okBtn);
        add(cancelBtn);
    }

    public void setListener(LoginDialogInterface listener) {
        this.listener = listener;
    }
}
