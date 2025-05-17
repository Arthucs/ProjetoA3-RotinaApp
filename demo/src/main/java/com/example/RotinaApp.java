package com.example;

import java.util.Scanner;

public class RotinaApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciaRotina manager = new GerenciaRotina("rotina.txt");
        RotinaController controller = new RotinaController(scanner, manager);
        controller.executar();
    }
}