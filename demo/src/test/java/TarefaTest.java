import static org.junit.Assert.*;


import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import com.example.Tarefa;

public class TarefaTest {
    public Tarefa tarefa;

    @Before
    public void setUp() {
        tarefa = new Tarefa("Estudar", "Revisar conteúdo de Java", 
                                    LocalDate.of(2025, 5, 16), 
                                    LocalTime.of(14, 0));
    }

   @Test
    public void testTarefaInicialmenteNaoConcluida() {
        assertFalse(tarefa.isConcluida());
    }

    @Test
    public void testMarcarComoConcluida() {
        tarefa.marcarComoConcluida();
        assertTrue(tarefa.isConcluida());
    }

    @Test
    public void testToStringQuandoNaoConcluida() {
        String expected = "[ ] Estudar - 16/05/2025 14:00\nDescrição: Revisar conteúdo de Java";
        assertEquals(expected, tarefa.toString());
    }

    @Test
    public void testToStringQuandoConcluida() {
        tarefa.marcarComoConcluida();
        String expected = "[X] Estudar - 16/05/2025 14:00\nDescrição: Revisar conteúdo de Java";
        assertEquals(expected, tarefa.toString());
    }

    @Test
    public void testGetters() {
        LocalDate data = tarefa.getData();
        LocalTime horario = tarefa.getHorario();

        assertEquals("Estudar", tarefa.getTitulo());
        assertEquals("Revisar conteúdo de Java", tarefa.getDescricao());
        assertEquals(data, tarefa.getData());
        assertEquals(horario, tarefa.getHorario());
        assertFalse(tarefa.isConcluida()); 
    }
}
