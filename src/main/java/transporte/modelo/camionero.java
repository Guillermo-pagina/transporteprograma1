package transporte.modelo;

public class camionero {

    int idCamionero;
    String nombre;
    String telefono;
    String direccion;
    double salario;
    String poblacion;

    public camionero(int id, String nom, String tel,
            String dir, double sal, String pob) {

        idCamionero = id;
        nombre = nom;
        telefono = tel;
        direccion = dir;
        salario = sal;
        poblacion = pob;
    }
}