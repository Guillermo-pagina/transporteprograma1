package transporte;

import java.sql.Connection;
import transporte.conexion.ConexionMysql;

public class pruebaconexion {

    public static void main(String[] args) {
        try {
            Connection cn = ConexionMysql.conectar("Guillermo");

            if (cn != null) {
                System.out.println("conexion exitosa");
                cn.close();
            } else {
                System.out.println("no se pudo conectar");
            }

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}