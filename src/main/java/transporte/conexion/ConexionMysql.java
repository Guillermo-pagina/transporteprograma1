/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transporte.conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Guillermo
 */
public class ConexionMysql {
    public static Connection conectar(String clave) throws SQLException {
    String url = "jdbc:mysql://localhost:3306/db_paqueteria";
    String usuario = "root";

    Connection con = DriverManager.getConnection(url, usuario, clave);
    return con;
}
}
