import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.GerenciaRotina;
import com.example.Tarefa;

public class GerenciaRotinaTest {
    public Path tempArquivo;
    public GerenciaRotina gerenciaRotina;

    @Before
    public void setUp() throws IOException {
        tempArquivo = Files.createTempFile("rotina_test", ".txt");
        gerenciaRotina = new GerenciaRotina(tempArquivo.toString());
    }

    @After
    public void tearDown() throws IOException {
        tempArquivo.toFile().deleteOnExit();
    }

    @Test
    public void testAdicionarTarefa() {
        Tarefa tarefa = new Tarefa("Estudar", "Java avançado",
                LocalDate.of(2025, 5, 16), LocalTime.of(15, 0));
        gerenciaRotina.adicionarTarefa(tarefa);

        List<Tarefa> tarefas = gerenciaRotina.getTarefas();
        assertEquals(1, tarefas.size());
        assertEquals("Estudar", tarefas.get(0).toString().substring(4, 11));
    }

    @Test
    public void testConcluirTarefa() {
        Tarefa tarefa = new Tarefa("Ler", "Capítulo de Redes",
                LocalDate.of(2025, 5, 17), LocalTime.of(10, 0));
        gerenciaRotina.adicionarTarefa(tarefa);

        gerenciaRotina.concluirTarefa(0);

        assertTrue(gerenciaRotina.getTarefas().get(0).isConcluida());
    }

    @Test
    public void testConcluirTarefaIndiceInvalido() {
        gerenciaRotina.concluirTarefa(99);
        assertTrue(gerenciaRotina.getTarefas().isEmpty());
    }

    @Test
    public void testListarTarefasComListaVazia() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        gerenciaRotina.listarTarefas();

        String saida = outContent.toString().trim();
        assertTrue(saida.contains("Nenhuma tarefa encontrada."));

        System.setOut(System.out);
    }

    @Test
    public void testListarTarefasComUmaTarefa() {
        Tarefa tarefa = new Tarefa("Estudar", "JUnit",
            LocalDate.of(2025, 5, 20), LocalTime.of(10, 0));
        gerenciaRotina.adicionarTarefa(tarefa);

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        gerenciaRotina.listarTarefas();

        String saida = outContent.toString();
        assertTrue(saida.contains("1. [ ] Estudar"));
        assertTrue(saida.contains("Descrição: JUnit"));

        System.setOut(System.out);
    }

    @Test
    public void testConcluirTarefaPersisteNoArquivo() {
        Tarefa tarefa = new Tarefa("Apresentação", "Slides do trabalho",
            LocalDate.of(2025, 5, 22), LocalTime.of(14, 0));
        gerenciaRotina.adicionarTarefa(tarefa);
        gerenciaRotina.concluirTarefa(0);

        GerenciaRotina novaInstancia = new GerenciaRotina(tempArquivo.toString());
        List<Tarefa> tarefas = novaInstancia.getTarefas();

        assertEquals(1, tarefas.size());
        assertTrue(tarefas.get(0).isConcluida());
    }

}
