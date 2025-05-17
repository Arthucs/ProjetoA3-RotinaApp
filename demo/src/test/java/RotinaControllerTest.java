import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.GerenciaRotina;
import com.example.RotinaController;
import com.example.Tarefa;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RotinaControllerTest {

    private Path tempArquivo;
    private GerenciaRotina gerenciaRotina;
    private ByteArrayOutputStream outContent;

    @Before
    public void setUp() throws IOException {
        tempArquivo = Files.createTempFile("rotina_controller_test", ".txt");
        gerenciaRotina = new GerenciaRotina(tempArquivo.toString());

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() throws IOException {
        tempArquivo.toFile().deleteOnExit();
        System.setOut(System.out);
    }

    @Test
    public void testAdicionarEListarTarefa() {
        String hoje = java.time.LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String input = String.join("\n",
                "1",                  // opção: adicionar tarefa
                "Estudar",           // título
                "Matemática",        // descrição
                hoje,                // data válida
                "14:00",             // horário válido
                "2",                 // opção: listar tarefas
                "4"                  // sair
        );

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        RotinaController controller = new RotinaController(scanner, gerenciaRotina);
        controller.executar();

        assertEquals(1, gerenciaRotina.getTarefas().size());
        assertTrue(outContent.toString().contains("Estudar"));
        assertTrue(outContent.toString().contains("Matemática"));
    }

    @Test
    public void testConcluirTarefa() {
        gerenciaRotina.adicionarTarefa(new Tarefa("Dormir", "Descansar", 
            java.time.LocalDate.now().plusDays(1), java.time.LocalTime.of(22, 0)));

        String input = String.join("\n",
                "3",  // opção: concluir tarefa
                "1",  // escolher primeira tarefa
                "4"   // sair
        );

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        RotinaController controller = new RotinaController(scanner, gerenciaRotina);
        controller.executar();

        assertTrue(gerenciaRotina.getTarefas().get(0).isConcluida());
        assertTrue(outContent.toString().contains("Dormir"));
    }

    @Test
    public void testOpcaoInvalida() {
        String input = String.join("\n",
                "9", // opção inválida
                "4"  // sair
        );

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        RotinaController controller = new RotinaController(scanner, gerenciaRotina);
        controller.executar();

        assertTrue(outContent.toString().contains("Opção inválida"));
    }
}
