package com.example;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class RotinaInterfaceG extends JFrame {
    private final GerenciaRotina gerencia;

    public RotinaInterfaceG(GerenciaRotina gerencia) {
        this.gerencia = gerencia;
        setTitle("Rotina Diária - Menu Principal");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnAdicionar = new JButton("Adicionar Tarefa");
        btnAdicionar.addActionListener(e -> construirTarefa());

        JButton btnListar = new JButton("Listar Tarefas");
        btnListar.addActionListener(e -> listarTarefas());

        JButton btnConcluir = new JButton("Concluir Tarefa");
        btnConcluir.addActionListener(e -> concluirTarefa());

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> System.exit(0));

        add(btnAdicionar);
        add(btnListar);
        add(btnConcluir);
        add(btnSair);
    }

    private void construirTarefa() {
        JTextField titulo = new JTextField();
        JTextField descricao = new JTextField();
        JTextField data = new JTextField();  
        JTextField hora = new JTextField();  

        Object[] campos = {
                "Título:", titulo,
                "Descrição:", descricao,
                "Data (dd/MM/yyyy):", data,
                "Hora (HH:mm):", hora
        };

        int resultado = JOptionPane.showConfirmDialog(this, campos,
                "Nova Tarefa", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            String entrada = titulo.getText() + "\n" +
                    descricao.getText() + "\n" +
                    data.getText() + "\n" +
                    hora.getText();

            Scanner scanner = new Scanner(entrada);
            gerencia.construirTarefa(scanner, null);

        }
    }

    public void listarTarefas() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Tarefa tarefa : gerencia.getTarefas()) {
            sb.append(i++).append(". ").append(tarefa).append("\n");
        }

        JOptionPane.showMessageDialog(this,
                sb.length() == 0 ? "Nenhuma tarefa cadastrada." : sb.toString(),
                "Tarefas",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void concluirTarefa() {
        if (gerencia.getTarefas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma tarefa disponível.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gerencia.getTarefas().size(); i++) {
            sb.append(i + 1).append(". ").append(gerencia.getTarefas().get(i)).append("\n");
        }

        String escolha = JOptionPane.showInputDialog(this, sb + "\nDigite o número da tarefa a concluir:");

        if (escolha == null) return;

        Scanner scanner = new Scanner(escolha);
        gerencia.concluirTarefa(scanner, null);
    }

}
