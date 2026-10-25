package transporte.modelo;

public class camion {

    int idCamion;
    String matricula;
    String modelo;
    String tipo;
    double potencia;

    public camion(int id, String mat, String mod,
            String tip, double pot) {

        idCamion = id;
        matricula = mat;
        modelo = mod;
        tipo = tip;
        potencia = pot;
    }
}