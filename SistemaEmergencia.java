import java.io.*;
import java.util.Scanner;

public class SistemaEmergencia {
    private PriorityQueue<Paciente> colaPacientes;
    private String archivoTexto;
    
    public SistemaEmergencia(String archivoTexto, boolean usarJavaCollections) {
        this.archivoTexto = archivoTexto;
        
        if (usarJavaCollections) {
            colaPacientes = new JavaCollectionsPriorityQueue<Paciente>();
        } else {
            colaPacientes = new VectorHeap<Paciente>();
        }
        
        cargarPacientes();
    }
    
    // Método para cargar pacientes desde un archivo de texto
    private void cargarPacientes() {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoTexto))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",", 3);
                if (campos.length == 3) {
                    String nombre = campos[0].trim();
                    String sintoma = campos[1].trim();
                    char codigo = campos[2].trim().charAt(0);
                    
                    Paciente paciente = new Paciente(nombre, sintoma, codigo);
                    colaPacientes.add(paciente);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }
    
    // Método para agregar un nuevo paciente
    public void agregarPaciente(String nombre, String sintoma, char codigoEmergencia) {
        Paciente nuevoPaciente = new Paciente(nombre, sintoma, codigoEmergencia);
        colaPacientes.add(nuevoPaciente);
        
        // Actualizar el archivo de texto
        try (FileWriter fw = new FileWriter(archivoTexto, true); 
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("\n" + nombre + ", " + sintoma + ", " + codigoEmergencia);
        } catch (IOException e) {
            System.out.println("Error al actualizar el archivo: " + e.getMessage());
        }
    }
    
    // Método para atender al siguiente paciente
    public Paciente atenderPaciente() {
        if (colaPacientes.isEmpty()) {
            return null;
        }
        
        Paciente paciente = colaPacientes.remove();
        
        // Actualizar el archivo de texto (eliminando al paciente atendido)
        actualizarArchivo();
        
        return paciente;
    }
    
    // Método para actualizar el archivo con los pacientes actuales
    private void actualizarArchivo() {
        try {
            // Crear una copia temporal de la cola actual
            PriorityQueue<Paciente> copiaCola;
            if (colaPacientes instanceof VectorHeap) {
                copiaCola = new VectorHeap<Paciente>();
            } else {
                copiaCola = new JavaCollectionsPriorityQueue<Paciente>();
            }
            
            // Guardar todos los pacientes en un arreglo temporal
            Paciente[] pacientes = new Paciente[colaPacientes.size()];
            for (int i = 0; i < pacientes.length; i++) {
                pacientes[i] = colaPacientes.remove();
                copiaCola.add(pacientes[i]);
            }
            
            // Restaurar la cola original
            for (Paciente p : pacientes) {
                colaPacientes.add(p);
            }
            
            // Escribir el archivo desde cero
            try (FileWriter fw = new FileWriter(archivoTexto, false); 
                 BufferedWriter bw = new BufferedWriter(fw)) {
                boolean first = true;
                while (!copiaCola.isEmpty()) {
                    Paciente p = copiaCola.remove();
                    if (!first) {
                        bw.write("\n");
                    }
                    bw.write(p.getNombre() + ", " + p.getSintoma() + ", " + p.getCodigoEmergencia());
                    first = false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar el archivo: " + e.getMessage());
        }
    }
    
    // Método para mostrar todos los pacientes en espera
    public void mostrarPacientes() {
        if (colaPacientes.isEmpty()) {
            System.out.println("No hay pacientes en espera.");
            return;
        }
        
        System.out.println("\nPacientes en espera (ordenados por prioridad):");
        
        // Crear una copia temporal de la cola
        PriorityQueue<Paciente> copiaCola;
        if (colaPacientes instanceof VectorHeap) {
            copiaCola = new VectorHeap<Paciente>();
        } else {
            copiaCola = new JavaCollectionsPriorityQueue<Paciente>();
        }
        
        // Mostrar y transferir
        Paciente[] pacientes = new Paciente[colaPacientes.size()];
        for (int i = 0; i < pacientes.length; i++) {
            pacientes[i] = colaPacientes.remove();
            System.out.println((i + 1) + ". " + pacientes[i]);
            copiaCola.add(pacientes[i]);
        }
        
        // Restaurar la cola original
        for (Paciente p : pacientes) {
            colaPacientes.add(p);
        }
    }
}