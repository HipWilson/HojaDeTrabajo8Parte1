public class PersonaEnferma implements Comparable<PersonaEnferma> {
    private String nombre;
    private String sintomas;
    private char nivelUrgencia;

    public PersonaEnferma(String nombre, String sintomas, char nivelUrgencia) {
        this.nombre = nombre;
        this.sintomas = sintomas;
        this.nivelUrgencia = nivelUrgencia;
    }

    public String obtenerDatosArchivo() {
        return nombre + " | " + sintomas + " | " + nivelUrgencia;
    }

    @Override
    public int compareTo(PersonaEnferma otro) {
        return Character.compare(this.nivelUrgencia, otro.nivelUrgencia);
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\nSíntomas: " + sintomas + 
               "\nNivel de urgencia: " + nivelUrgencia;
    }
}

