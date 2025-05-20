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
    public void deveAutenticarUsuarioAdminComSenhaCorreta() {
        assertTrue(autenticador.autenticar("admin", "1234"));
    }

    @Test
    public void deveAutenticarUsuarioUserComSenhaCorreta() {
        assertTrue(autenticador.autenticar("user", "abcd"));
    }

    @Test
    public void naoDeveAutenticarComUsuarioInvalido() {
        assertFalse(autenticador.autenticar("invalido", "1234"));
    }

    @Test
    public void naoDeveAutenticarComSenhaIncorreta() {
        assertFalse(autenticador.autenticar("admin", "errada"));
    }

    @Test
    public void naoDeveAutenticarComUsuarioEsenhaInvalidos() {
        assertFalse(autenticador.autenticar("desconhecido", "senha"));
    }
}
