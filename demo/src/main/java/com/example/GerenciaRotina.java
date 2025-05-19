package com.example;

import java.awt.Component;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class GerenciaRotina {
    private List<Tarefa> tarefas;
    private final String arquivo;

    public GerenciaRotina(String arquivo) {
        this.arquivo = arquivo;
        this.tarefas = carregarTarefas();
    }

    private String lerEntrada(Scanner scanner) {
    String entrada = scanner.nextLine();
    return entrada;
    }

    public void adicionarTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
        salvarTarefas();
    }

    public void construirTarefa(Scanner scanner, Component parentComponent) {

        String titulo = lerEntrada(scanner);
        String descricao = lerEntrada(scanner);

        LocalDate data = null;
        while (data == null) {
            String dataStr = lerEntrada(scanner);
            try {
                data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (data.isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(parentComponent, "A data não pode ser no passado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    data = null;
                }
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(parentComponent, "Data inválida! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        LocalTime horario = null;
        while (horario == null) {
            String horaStr = lerEntrada(scanner);
            try {
                horario = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));
                if (data.isEqual(LocalDate.now()) && horario.isBefore(LocalTime.now())) {
                    JOptionPane.showMessageDialog(parentComponent, "Horário não pode ser no passado para hoje. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    horario = null;
                }
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(parentComponent, "Horário inválido! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        Tarefa novaTarefa = new Tarefa(titulo, descricao, data, horario);
        adicionarTarefa(novaTarefa);
        JOptionPane.showMessageDialog(parentComponent, "Tarefa adicionada com sucesso!", "Tarefa adicionada!", JOptionPane.INFORMATION_MESSAGE);

    }

    public void listarTarefas() {
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println(i + 1 + ". " + tarefas.get(i));
        }
    }

    public void concluirTarefa(Scanner scanner, Component parentComponent) {
        try {
            String entrada = lerEntrada(scanner);
            int indice = Integer.parseInt(entrada) - 1;

            if (indice >= 0 && indice < tarefas.size()) {
                Tarefa tarefa = tarefas.get(indice);

                if (tarefa.isConcluida()) {
                    tarefa.desmarcarComoConcluida();
                    JOptionPane.showMessageDialog(parentComponent,
                            "Tarefa desmarcada com sucesso!",
                            "Tarefa desmarcada!",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    tarefa.marcarComoConcluida();
                    JOptionPane.showMessageDialog(parentComponent,
                            "Tarefa concluída com sucesso!",
                            "Tarefa concluída!",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                salvarTarefas();

            } else {
                JOptionPane.showMessageDialog(parentComponent, "Índice inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
             JOptionPane.showMessageDialog(parentComponent, "Entrada inválida. Por favor, digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void excluirTarefa(Scanner scanner, Component parentComponent) {
        String entrada = lerEntrada(scanner).trim();

        if (entrada.equalsIgnoreCase("Limpar")) {
            int confirmacao = JOptionPane.showConfirmDialog(parentComponent,
                    "Tem certeza que deseja excluir todas as tarefas?",
                    "Confirmar Limpeza",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                tarefas.clear();
                salvarTarefas();
                JOptionPane.showMessageDialog(parentComponent, "Todas as tarefas foram removidas.", "Limpeza concluída", JOptionPane.INFORMATION_MESSAGE);
            }

        } else {
            try {
                int indice = Integer.parseInt(entrada) - 1;

                if (indice >= 0 && indice < tarefas.size()) {
                    Tarefa removida = tarefas.remove(indice);
                    salvarTarefas();
                    JOptionPane.showMessageDialog(parentComponent,
                            "Tarefa \"" + removida.getTitulo() + "\" excluída com sucesso!",
                            "Tarefa excluída", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(parentComponent,
                            "Índice inválido. Nenhuma tarefa foi excluída.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parentComponent,
                        "Entrada inválida. Digite o número da tarefa ou \"Limpar\" para remover todas.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void salvarTarefas() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(tarefas);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar tarefas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Tarefa> carregarTarefas() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Tarefa>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }
}
