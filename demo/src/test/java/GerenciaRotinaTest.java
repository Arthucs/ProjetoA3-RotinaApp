import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        Tarefa tarefa = new Tarefa("Estudar", "Matemática", LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        gerenciaRotina.adicionarTarefa(tarefa);

        String input = "1\n";  // índice da tarefa
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        gerenciaRotina.concluirTarefa(scanner, null);

        assertTrue(gerenciaRotina.getTarefas().get(0).isConcluida());
    }

    @Test
    public void testConcluirTarefaIndiceInvalido() {
        String input = "5\n";  // nenhum índice válido
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        gerenciaRotina.concluirTarefa(scanner, null);

        // Nenhuma exceção lançada e nada alterado
        assertEquals(0, gerenciaRotina.getTarefas().size());
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
        Tarefa tarefa = new Tarefa("Trabalho", "Apresentação", LocalDate.now().plusDays(1), LocalTime.of(14, 0));
        gerenciaRotina.adicionarTarefa(tarefa);

        String input = "1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        gerenciaRotina.concluirTarefa(scanner, null);

        List<Tarefa> tarefas = gerenciaRotina.getTarefas();

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
        gerenciaRotina.construirTarefa(scanner, null);

        assertEquals(1, gerenciaRotina.getTarefas().size());
        Tarefa tarefa = gerenciaRotina.getTarefas().get(0);
        assertEquals("Estudar", tarefa.getTitulo());
        assertEquals("Revisar matéria", tarefa.getDescricao());
    }

    @Test
    public void testDataInvalida() {
        String dataInvalida = "32/13/2025";
        String dataValida = LocalDate.now().plusDays(1).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String entrada = String.join("\n",
                "Teste data",              
                "Descrição qualquer",      
                dataInvalida,               
                dataValida,                 
                "14:00"                     
        );

        Scanner scanner = new Scanner(new ByteArrayInputStream(entrada.getBytes()));
        gerenciaRotina.construirTarefa(scanner, null);

        assertEquals(1, gerenciaRotina.getTarefas().size());
        assertEquals("Teste data", gerenciaRotina.getTarefas().get(0).getTitulo());
    }

    @Test
    public void testHorarioInvalido() {
        String dataValida = LocalDate.now().plusDays(1).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String entrada = String.join("\n",
                "Teste hora",             
                "Descrição qualquer",      
                dataValida,                 
                "25:99",                    
                "15:30"                     
        );

        Scanner scanner = new Scanner(new ByteArrayInputStream(entrada.getBytes()));
        gerenciaRotina.construirTarefa(scanner, null);

        assertEquals(1, gerenciaRotina.getTarefas().size());
        assertEquals("Teste hora", gerenciaRotina.getTarefas().get(0).getTitulo());
    }

    @Test
    public void testEntradaInvalidaDuranteConcluirTarefa() {
        Tarefa tarefa = new Tarefa("Estudar", "Física", LocalDate.now().plusDays(1), LocalTime.of(8, 0));
        gerenciaRotina.adicionarTarefa(tarefa);

        String input = "abc\n";  
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        gerenciaRotina.concluirTarefa(scanner, null);

        assertFalse(gerenciaRotina.getTarefas().get(0).isConcluida());
    }

    @Test
    public void deveRepetirEntradaAposDataNoPassado() {
        LocalDate dataPassada = LocalDate.now().minusDays(1);
        LocalDate dataValida = LocalDate.now().plusDays(1);

        String entrada = String.join("\n",
                "Tarefa com data passada",                          
                "Descrição",                                        
                dataPassada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),   
                dataValida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),    
                "10:00" 
        );

        Scanner scanner = new Scanner(new ByteArrayInputStream(entrada.getBytes()));
        gerenciaRotina.construirTarefa(scanner, null);

        assertEquals(1, gerenciaRotina.getTarefas().size());
        assertEquals("Tarefa com data passada", gerenciaRotina.getTarefas().get(0).getTitulo());
    }

    @Test
    public void deveRepetirEntradaAposHorarioNoPassadoParaHoje() {
        LocalDate hoje = LocalDate.now();
        String dataHoje = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Horário no passado (1 hora atrás)
        LocalTime horarioPassado = LocalTime.now().minusHours(1);
        LocalTime horarioValido = LocalTime.now().plusHours(1);

        String entrada = String.join("\n",
                "Tarefa com hora passada",
                "Descrição",                                    
                dataHoje,                                       
                horarioPassado.format(DateTimeFormatter.ofPattern("HH:mm")),  
                horarioValido.format(DateTimeFormatter.ofPattern("HH:mm"))    
        );

        Scanner scanner = new Scanner(new ByteArrayInputStream(entrada.getBytes()));
        gerenciaRotina.construirTarefa(scanner, null);

        assertEquals(1, gerenciaRotina.getTarefas().size());
        assertEquals("Tarefa com hora passada", gerenciaRotina.getTarefas().get(0).getTitulo());
    }
}
