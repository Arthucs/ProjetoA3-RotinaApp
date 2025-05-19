package com.example;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class RotinaInterfaceG extends JFrame {
    private final GerenciaRotina gerencia;

    public RotinaInterfaceG(GerenciaRotina gerencia) {
        this.gerencia = gerencia;
        setTitle("Menu Principal");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("OrganizApp", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(5, 1, 10, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50)); 

        JButton btnAdicionar = criarBotao("Adicionar Tarefa", e -> construirTarefa());
        JButton btnListar = criarBotao("Listar Tarefas", e -> listarTarefas());
        JButton btnConcluir = criarBotao("Concluir Tarefa", e -> concluirTarefa());
        JButton btnSair = criarBotao("Sair", e -> System.exit(0));
        JButton btnExcluir = criarBotao("Excluir Tarefa", e -> excluirTarefa());
        
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnListar);
        painelBotoes.add(btnConcluir);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnSair);

        add(painelBotoes, BorderLayout.CENTER);
    }

    private JButton criarBotao(String texto, java.awt.event.ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(200, 30));
        botao.setFont(new Font("Arial", Font.PLAIN, 14));
        botao.addActionListener(acao);
        return botao;
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

    public void excluirTarefa() {
        if (gerencia.getTarefas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma tarefa disponível.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gerencia.getTarefas().size(); i++) {
            sb.append(i + 1).append(". ").append(gerencia.getTarefas().get(i)).append("\n");
        }

        sb.append("\nDigite o número da tarefa a excluir ou 'Limpar' para apagar todas.");

        String escolha = JOptionPane.showInputDialog(this, sb.toString());

        if (escolha == null) return;

        Scanner scanner = new Scanner(escolha);
        gerencia.excluirTarefa(scanner, this);
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
