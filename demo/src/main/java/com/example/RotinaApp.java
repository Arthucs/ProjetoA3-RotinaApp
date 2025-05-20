package com.example;
import javax.swing.SwingUtilities;

public class RotinaApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}