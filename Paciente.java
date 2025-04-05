public class Paciente implements Comparable<Paciente> {
    private String nombre;
    private String sintoma;
    private char codigoEmergencia;
    
    public Paciente(String nombre, String sintoma, char codigoEmergencia) {
        this.nombre = nombre;
        this.sintoma = sintoma;
        this.codigoEmergencia = codigoEmergencia;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getSintoma() {
        return sintoma;
    }
    
    public char getCodigoEmergencia() {
        return codigoEmergencia;
    }
    
    @Override
    public int compareTo(Paciente otro) {
        // Ordenando por código de emergencia (asumiendo que A es más urgente que Z)
        return Character.compare(this.codigoEmergencia, otro.codigoEmergencia);
    }
    
    @Override
    public String toString() {
        return nombre + " - Sintoma: " + sintoma + " - Prioridad: " + codigoEmergencia;
    }
}