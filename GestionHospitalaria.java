import java.util.*;
import java.io.*;

public class GestionHospitalaria {
    private static final String ARCHIVO_PACIENTES = "datos_pacientes.txt";
    private static final String ARCHIVO_ATENDIDOS = "historico_atendidos.txt";
    private static PriorityQueue<PersonaEnferma> colaPrioridad = new PriorityQueue<>();

    public static void main(String[] args) {
        cargarDatosIniciales();
        mostrarInterfazUsuario();
    }

    private static void cargarDatosIniciales() {
        try (BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO_PACIENTES))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length == 3) {
                    colaPrioridad.add(new PersonaEnferma(
                        datos[0].trim(),
                        datos[1].trim(),
                        datos[2].trim().charAt(0)
                    )); // Este paréntesis estaba mal cerrado
                }
            }
        } catch (IOException e) {
            System.out.println("Iniciando con lista de pacientes vacía.");
        }
    }

    private static void mostrarInterfazUsuario() {
        Scanner input = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- SISTEMA DE GESTIÓN HOSPITALARIA ---");
            System.out.println("1. Registrar nuevo paciente");
            System.out.println("2. Atender paciente con mayor prioridad");
            System.out.println("3. Salir del sistema");
            System.out.print("Seleccione una opción (1-3): ");
            
            opcion = input.nextInt();
            input.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    agregarNuevoPaciente(input);
                    break;
                case 2:
                    procesarPaciente();
                    break;
                case 3:
                    System.out.println("Cerrando el sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 3);
    }

    private static void agregarNuevoPaciente(Scanner input) {
        System.out.println("\n--- REGISTRO DE NUEVO PACIENTE ---");
        System.out.print("Nombre del paciente: ");
        String nombre = input.nextLine();
        
        System.out.print("Descripción de síntomas: ");
        String sintomas = input.nextLine();
        
        System.out.print("Grado de urgencia (A-E, siendo A más urgente): ");
        char urgencia = input.nextLine().charAt(0);

        PersonaEnferma paciente = new PersonaEnferma(nombre, sintomas, urgencia);
        colaPrioridad.add(paciente);
        guardarPaciente(paciente);
        
        System.out.println("Paciente registrado exitosamente.");
    }

    private static void guardarPaciente(PersonaEnferma paciente) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_PACIENTES, true))) {
            writer.println(paciente.obtenerDatosArchivo());
        } catch (IOException e) {
            System.out.println("Error al guardar los datos del paciente.");
        }
    }

    private static void procesarPaciente() {
        if (colaPrioridad.isEmpty()) {
            System.out.println("\nNo hay pacientes en espera.");
            return;
        }

        PersonaEnferma paciente = colaPrioridad.poll();
        System.out.println("\nAtendiendo paciente:");
        System.out.println(paciente);
        
        registrarAtencionCompletada(paciente);
        actualizarListaPacientes(paciente);
    }

    private static void registrarAtencionCompletada(PersonaEnferma paciente) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_ATENDIDOS, true))) {
            writer.println(paciente.obtenerDatosArchivo());
        } catch (IOException e) {
            System.out.println("Error al registrar la atención.");
        }
    }

    private static void actualizarListaPacientes(PersonaEnferma pacienteAtendido) {
        List<String> pacientesRestantes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_PACIENTES))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.equals(pacienteAtendido.obtenerDatosArchivo())) {
                    pacientesRestantes.add(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer los datos de pacientes.");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_PACIENTES))) {
            for (String paciente : pacientesRestantes) {
                writer.println(paciente);
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar la lista de pacientes.");
        }
    }
}