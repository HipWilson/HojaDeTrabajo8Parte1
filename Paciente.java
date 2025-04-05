public class Paciente implements Comparable<Paciente> {
    private String nombre;
    private String sintoma;
    private char codigoEmergencia;

    /**
    @param nombre           
    @param sintoma          
    @param codigoEmergencia 
     */
    public Paciente(String nombre, String sintoma, char codigoEmergencia) {
        this.nombre = nombre;
        this.sintoma = sintoma;
        this.codigoEmergencia = codigoEmergencia;
    }

    /**
     @return El nombre del paciente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
    @return El síntoma que presenta el paciente.
     */
    public String getSintoma() {
        return sintoma;
    }

    /**
    @return El código de emergencia (letra de 'A' a 'E').
     */
    public char getCodigoEmergencia() {
        return codigoEmergencia;
    }

    /**
    @param otro El otro paciente con el que se va a comparar.
    @return Un número negativo si este paciente tiene mayor prioridad,
     
     */
    @Override
    public int compareTo(Paciente otro) {
        return Character.compare(this.codigoEmergencia, otro.codigoEmergencia);
    }

    /**
    @return Una cadena con el formato: nombre, síntoma, códigoEmergencia
     */
    @Override
    public String toString() {
        return nombre + ", " + sintoma + ", " + codigoEmergencia;
    }
}
