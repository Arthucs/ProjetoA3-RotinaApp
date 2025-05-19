package com.example;
import javax.swing.SwingUtilities;

public class RotinaApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GerenciaRotina gerencia = new GerenciaRotina("rotina.txt");
            RotinaInterfaceG app = new RotinaInterfaceG(gerencia);
            app.setVisible(true);
        });
    }
}