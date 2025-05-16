package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciaRotina {
    private List<Tarefa> tarefas;
    private final String arquivo;

    public GerenciaRotina(String arquivo) {
        this.arquivo = arquivo;
        this.tarefas = carregarTarefas();
    }

    public void adicionarTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
        salvarTarefas();
    }

    public void listarTarefas() {
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println(i + 1 + ". " + tarefas.get(i));
        }
    }

    public void concluirTarefa(int indice) {
        if (indice >= 0 && indice < tarefas.size()) {
            tarefas.get(indice).marcarComoConcluida();
            salvarTarefas();
        } else {
            System.out.println("Índice inválido.");
        }
    }

    private void salvarTarefas() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(tarefas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar tarefas: " + e.getMessage());
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
