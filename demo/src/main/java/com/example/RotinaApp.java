package com.example;

import java.util.Scanner;

import javax.swing.SwingUtilities;

public class RotinaApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SwingUtilities.invokeLater(() -> {
            GerenciaRotina gerencia = new GerenciaRotina("rotina.txt");
            RotinaInterfaceG app = new RotinaInterfaceG(gerencia);
            app.setVisible(true);
        });
    }
}