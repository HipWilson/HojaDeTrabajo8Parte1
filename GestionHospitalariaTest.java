import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.PriorityQueue;

public class GestionHospitalariaTest {
    private static final String TEST_PACIENTES = "test_pacientes.txt";
    private static final String TEST_ATENDIDOS = "test_atendidos.txt";
    
    private GestionHospitalaria gestion;
    
    @BeforeEach
    public void setUp() throws IOException {
        // Crear archivos de prueba limpios
        new File(TEST_PACIENTES).delete();
        new File(TEST_ATENDIDOS).delete();
        
        gestion = new GestionHospitalaria();
        // Usamos reflexión para cambiar las rutas de archivo a las de prueba
        try {
            java.lang.reflect.Field field = GestionHospitalaria.class.getDeclaredField("ARCHIVO_PACIENTES");
            field.setAccessible(true);
            field.set(null, TEST_PACIENTES);
            
            field = GestionHospitalaria.class.getDeclaredField("ARCHIVO_ATENDIDOS");
            field.setAccessible(true);
            field.set(null, TEST_ATENDIDOS);
            
            field = GestionHospitalaria.class.getDeclaredField("colaPrioridad");
            field.setAccessible(true);
            field.set(null, new PriorityQueue<PersonaEnferma>());
        } catch (Exception e) {
            fail("Error configurando pruebas: " + e.getMessage());
        }
    }
    
    @AfterEach
    public void tearDown() {
        new File(TEST_PACIENTES).delete();
        new File(TEST_ATENDIDOS).delete();
    }
    
    @Test
    public void testCargarDatosIniciales() throws IOException {
        // Preparar archivo de prueba
        try (PrintWriter writer = new PrintWriter(TEST_PACIENTES)) {
            writer.println("Paciente1 | Dolor | A");
            writer.println("Paciente2 | Fiebre | B");
        }
        
        gestion.cargarDatosIniciales();
        
        PriorityQueue<PersonaEnferma> cola = getColaPrioridad();
        assertEquals(2, cola.size());
    }
    
    @Test
    public void testAgregarNuevoPaciente() {
        String nombre = "Test Paciente";
        String sintomas = "Test Síntomas";
        char urgencia = 'C';
        
        gestion.agregarNuevoPaciente(new Scanner(nombre + "\n" + sintomas + "\n" + urgencia + "\n"));
        
        PriorityQueue<PersonaEnferma> cola = getColaPrioridad();
        assertEquals(1, cola.size());
        
        PersonaEnferma paciente = cola.peek();
        assertEquals(nombre, paciente.getNombre());
        assertEquals(sintomas, paciente.getSintomas());
        assertEquals(urgencia, paciente.getNivelUrgencia());
    }
    
    @Test
    public void testProcesarPaciente() throws IOException {
        // Agregar paciente directamente a la cola
        PriorityQueue<PersonaEnferma> cola = getColaPrioridad();
        cola.add(new PersonaEnferma("Test", "Síntomas", 'A'));
        
        gestion.procesarPaciente();
        
        assertTrue(cola.isEmpty());
        // Verificar que se creó el archivo de atendidos
        assertTrue(new File(TEST_ATENDIDOS).exists());
    }
    
    @Test
    public void testProcesarPacienteConColaVacia() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        gestion.procesarPaciente();
        
        assertTrue(outContent.toString().contains("No hay pacientes en espera"));
        System.setOut(System.out);
    }
    
    private PriorityQueue<PersonaEnferma> getColaPrioridad() {
        try {
            java.lang.reflect.Field field = GestionHospitalaria.class.getDeclaredField("colaPrioridad");
            field.setAccessible(true);
            return (PriorityQueue<PersonaEnferma>) field.get(null);
        } catch (Exception e) {
            fail("Error accediendo a colaPrioridad: " + e.getMessage());
            return null;
        }
    }
}
