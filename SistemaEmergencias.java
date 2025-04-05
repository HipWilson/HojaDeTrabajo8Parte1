import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.PriorityQueue;

public class SistemaEmergencias {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Sistema de Atención de Emergencias Hospitalarias");
        System.out.println("1. Usar VectorHeap (implementación propia)");
        System.out.println("2. Usar PriorityQueue de Java Collections");
        System.out.print("Seleccione la implementación a usar: ");
        
        int opcionImplementacion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        PriorityQueue<Paciente> colaPropia = null;
        JCFPriorityQueue<Paciente> colaJCF = null;
        
        if (opcionImplementacion == 1) {
            colaPropia = new VectorHeap<>();
            System.out.println("\nUsando implementación VectorHeap propia");
        } else {
            colaJCF = new JCFPriorityQueue<>();
            System.out.println("\nUsando PriorityQueue de Java Collections");
        }
        
        boolean salir = false;
        
        while (!salir) {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Cargar pacientes desde archivo");
            System.out.println("2. Agregar paciente manualmente");
            System.out.println("3. Atender siguiente paciente");
            System.out.println("4. Mostrar próximo paciente a atender");
            System.out.println("5. Mostrar cantidad de pacientes en espera");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            
            switch (opcion) {
                case 1:
                    cargarPacientesDesdeArchivo(colaPropia, colaJCF, scanner);
                    break;
                case 2:
                    agregarPacienteManual(colaPropia, colaJCF, scanner);
                    break;
                case 3:
                    atenderPaciente(colaPropia, colaJCF);
                    break;
                case 4:
                    mostrarProximoPaciente(colaPropia, colaJCF);
                    break;
                case 5:
                    mostrarCantidadPacientes(colaPropia, colaJCF);
                    break;
                case 6:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        
        scanner.close();
    }
    
    private static void cargarPacientesDesdeArchivo(PriorityQueue<Paciente> colaPropia, 
                                                  JCFPriorityQueue<Paciente> colaJCF, 
                                                  Scanner scanner) {
        System.out.print("Ingrese la ruta del archivo de pacientes (deje vacío para pacientes.txt): ");
        String ruta = scanner.nextLine();
        
        if (ruta.isEmpty()) {
            ruta = "pacientes.txt";
        }
        
        try {
            File archivo = new File(ruta);
            Scanner lector = new Scanner(archivo);
            int contador = 0;
            
            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                String[] partes = linea.split(",");
                
                if (partes.length == 3) {
                    String nombre = partes[0].trim();
                    String sintoma = partes[1].trim();
                    char codigo = partes[2].trim().charAt(0);
                    
                    Paciente paciente = new Paciente(nombre, sintoma, codigo);
                    
                    if (colaPropia != null) {
                        colaPropia.add(paciente);
                    } else {
                        colaJCF.add(paciente);
                    }
                    
                    contador++;
                }
            }
            
            lector.close();
            System.out.println("Se cargaron " + contador + " pacientes desde el archivo.");
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo encontrar el archivo: " + ruta);
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
    
    private static void agregarPacienteManual(PriorityQueue<Paciente> colaPropia, 
                                            JCFPriorityQueue<Paciente> colaJCF, 
                                            Scanner scanner) {
        System.out.print("Nombre del paciente: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Síntoma o descripción: ");
        String sintoma = scanner.nextLine();
        
        System.out.print("Código de emergencia (A-E): ");
        char codigo = scanner.nextLine().toUpperCase().charAt(0);
        
        try {
            Paciente paciente = new Paciente(nombre, sintoma, codigo);
            
            if (colaPropia != null) {
                colaPropia.add(paciente);
            } else {
                colaJCF.add(paciente);
            }
            
            System.out.println("Paciente agregado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void atenderPaciente(PriorityQueue<Paciente> colaPropia, 
                                      JCFPriorityQueue<Paciente> colaJCF) {
        try {
            Paciente paciente;
            
            if (colaPropia != null) {
                paciente = colaPropia.remove();
            } else {
                paciente = colaJCF.poll();
            }
            
            System.out.println("\nPaciente atendido:");
            System.out.println("Nombre: " + paciente.getNombre());
            System.out.println("Síntoma: " + paciente.getSintoma());
            System.out.println("Prioridad: " + paciente.getCodigoEmergencia());
        } catch (NoSuchElementException e) {
            System.out.println("No hay pacientes en espera para atender.");
        }
    }
    
    private static void mostrarProximoPaciente(PriorityQueue<Paciente> colaPropia, 
                                             JCFPriorityQueue<Paciente> colaJCF) {
        try {
            Paciente paciente;
            
            if (colaPropia != null) {
                paciente = colaPropia.peek();
            } else {
                paciente = colaJCF.peek();
            }
            
            System.out.println("\nPróximo paciente a atender:");
            System.out.println("Nombre: " + paciente.getNombre());
            System.out.println("Síntoma: " + paciente.getSintoma());
            System.out.println("Prioridad: " + paciente.getCodigoEmergencia());
        } catch (NoSuchElementException e) {
            System.out.println("No hay pacientes en espera.");
        }
    }
    
    private static void mostrarCantidadPacientes(PriorityQueue<Paciente> colaPropia, 
                                               JCFPriorityQueue<Paciente> colaJCF) {
        int cantidad;
        
        if (colaPropia != null) {
            cantidad = colaPropia.size();
        } else {
            cantidad = colaJCF.size();
        }
        
        System.out.println("Pacientes en espera: " + cantidad);
    }
}