import org.junit.Before;
import org.junit.Test;

import com.example.Autenticador;

import static org.junit.Assert.*;

public class AutenticadorTest {
    private Autenticador autenticador;

    @Before
    public void setUp() {
        autenticador = new Autenticador();
    }

    @Test
    public void testAutenticarUsuarioAdmin() {
        assertTrue(autenticador.autenticar("admin", "1234"));
    }

    @Test
    public void testAutenticarUsuarioUser() {
        assertTrue(autenticador.autenticar("user", "abcd"));
    }

    @Test
    public void testNaoAutenticarComUsuarioInvalido() {
        assertFalse(autenticador.autenticar("invalido", "1234"));
    }

    @Test
    public void testNaoAutenticarComSenhaIncorreta() {
        assertFalse(autenticador.autenticar("admin", "errada"));
    }

    @Test
    public void testNaoAutenticarComUsuarioEsenhaInvalidos() {
        assertFalse(autenticador.autenticar("desconhecido", "senha"));
    }
}
