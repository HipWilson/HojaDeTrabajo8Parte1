// Archivo: Main.java
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Verificar si existe el archivo de pacientes, si no, crearlo
        File archivo = new File("pacientes.txt");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                System.out.println("Archivo 'pacientes.txt' creado exitosamente.");
            } catch (IOException e) {
                System.out.println("Error al crear el archivo: " + e.getMessage());
                return;
            }
        }
        
        // Pedir al usuario que elija la implementación
        System.out.println("=== SISTEMA DE EMERGENCIAS HOSPITALARIAS ===");
        System.out.println("Seleccione la implementación a utilizar:");
        System.out.println("1. VectorHeap (implementación propia)");
        System.out.println("2. Java Collections Framework PriorityQueue");
        
        int opcion = 0;
        while (opcion != 1 && opcion != 2) {
            System.out.print("Ingrese su opción (1-2): ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                if (opcion != 1 && opcion != 2) {
                    System.out.println("Opción inválida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
        
        // Crear el sistema de emergencia con la implementación elegida
        SistemaEmergencia sistema = new SistemaEmergencia("pacientes.txt", opcion == 2);
        
        // Menú principal
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Agregar paciente");
            System.out.println("2. Atender al siguiente paciente");
            System.out.println("3. Mostrar pacientes en espera");
            System.out.println("4. Salir");
            
            System.out.print("Ingrese su opción (1-4): ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1:
                        agregarPaciente(scanner, sistema);
                        break;
                    case 2:
                        atenderPaciente(sistema);
                        break;
                    case 3:
                        sistema.mostrarPacientes();
                        break;
                    case 4:
                        salir = true;
                        System.out.println("¡Gracias por usar el sistema!");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
        
        scanner.close();
    }
    
    // Método para agregar un paciente
    private static void agregarPaciente(Scanner scanner, SistemaEmergencia sistema) {
        System.out.println("\n=== AGREGAR PACIENTE ===");
        
        System.out.print("Nombre del paciente: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Síntoma: ");
        String sintoma = scanner.nextLine();
        
        char codigo = ' ';
        boolean codigoValido = false;
        while (!codigoValido) {
            System.out.print("Código de emergencia (A-E, siendo A la más urgente): ");
            String entrada = scanner.nextLine().toUpperCase();
            
            if (entrada.length() == 1 && entrada.charAt(0) >= 'A' && entrada.charAt(0) <= 'E') {
                codigo = entrada.charAt(0);
                codigoValido = true;
            } else {
                System.out.println("Código inválido. Debe ser una letra entre A y E.");
            }
        }
        
        sistema.agregarPaciente(nombre, sintoma, codigo);
        System.out.println("Paciente agregado exitosamente.");
    }
    
    // Método para atender al siguiente paciente
    private static void atenderPaciente(SistemaEmergencia sistema) {
        System.out.println("\n=== ATENDER PACIENTE ===");
        
        Paciente paciente = sistema.atenderPaciente();
        if (paciente == null) {
            System.out.println("No hay pacientes en espera.");
        } else {
            System.out.println("Paciente atendido: " + paciente);
        }
    }
}