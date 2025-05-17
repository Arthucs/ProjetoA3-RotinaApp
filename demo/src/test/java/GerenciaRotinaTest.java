import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.GerenciaRotina;
import com.example.Tarefa;

public class GerenciaRotinaTest {
    public Path tempArquivo;
    public GerenciaRotina gerenciaRotina;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() throws IOException {
        tempArquivo = Files.createTempFile("rotina_test", ".txt");
        gerenciaRotina = new GerenciaRotina(tempArquivo.toString());
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() throws IOException {
        tempArquivo.toFile().deleteOnExit();
        System.setOut(originalOut);
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

        @Test
    public void testConstruirTarefaValida() {
        String dataFutura = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String input = String.join("\n",
                "Estudar",          
                "Revisar matéria",
                dataFutura,
                "14:00"             
        );

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        gerenciaRotina.construirTarefa(scanner);

        assertEquals(1, gerenciaRotina.getTarefas().size());
        Tarefa tarefa = gerenciaRotina.getTarefas().get(0);
        assertEquals("Estudar", tarefa.getTitulo());
        assertEquals("Revisar matéria", tarefa.getDescricao());
    }

    @Test
    public void testDataInvalida() {
        String input = String.join("\n",
                "Título",             
                "Descrição",          
                "32/13/2025",         
                "15/04/2026",         
                "01/01/2020",         
                LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                "10:00"               
        );

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        gerenciaRotina.construirTarefa(scanner);

        String saida = outContent.toString();

        assertTrue(saida.contains("Data Inválida! Tente novamente."));
        assertEquals(1, gerenciaRotina.getTarefas().size());
    }

    @Test
    public void testHorarioInvalido() {
        LocalDate hoje = LocalDate.now();
        String data = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String input = String.join("\n",
                "Dormir",                   
                "Descansar bem",            
                data,                       
                "25:00",                    
                "12:60",                    
                LocalTime.now().minusHours(1).format(DateTimeFormatter.ofPattern("HH:mm")), // horário no passado
                LocalTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm"))   // horário válido
        );

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        gerenciaRotina.construirTarefa(scanner);

        String saida = outContent.toString();
        assertTrue(saida.contains("Horário Inválido"));
        assertTrue(saida.contains("Horário não pode ser no passado para a data de hoje. Tente novamente."));
        assertEquals(1, gerenciaRotina.getTarefas().size());
    }
}
