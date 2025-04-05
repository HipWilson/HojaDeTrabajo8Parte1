import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonaEnfermaTest {

    @Test
    public void testComparacionPrioridad() {
        PersonaEnferma paciente1 = new PersonaEnferma("Juan", "Fiebre", 'A');
        PersonaEnferma paciente2 = new PersonaEnferma("Maria", "Dolor de cabeza", 'B');
        PersonaEnferma paciente3 = new PersonaEnferma("Pedro", "Fractura", 'A');
        
        assertTrue(paciente1.compareTo(paciente2) < 0); // A es más urgente que B
        assertEquals(0, paciente1.compareTo(paciente3)); // Misma prioridad
        assertTrue(paciente2.compareTo(paciente1) > 0); // B es menos urgente que A
    }

    @Test
    public void testToString() {
        PersonaEnferma paciente = new PersonaEnferma("Ana", "Dolor abdominal", 'C');
        String expected = "Nombre: Ana\nSíntomas: Dolor abdominal\nNivel de urgencia: C";
        assertEquals(expected, paciente.toString());
    }

    @Test
    public void testObtenerDatosArchivo() {
        PersonaEnferma paciente = new PersonaEnferma("Luis", "Mareos", 'D');
        assertEquals("Luis | Mareos | D", paciente.obtenerDatosArchivo());
    }
}