package transporte.modelo;

public class paquete {

    int idPaquete;
    String descripcion;
    String destinatario;
    String direccionEnvio;
    int idCamionero;
    int idCiudad;

    public paquete(int id, String des, String dest,
            String dir, int cam, int ciu) {

        idPaquete = id;
        descripcion = des;
        destinatario = dest;
        direccionEnvio = dir;
        idCamionero = cam;
        idCiudad = ciu;
    }
}
