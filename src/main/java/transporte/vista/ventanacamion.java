package transporte.vista;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import transporte.conexion.ConexionMysql;

public class ventanacamion extends JFrame {

    String[] nombres = {"idCamion", "Matricula", "Modelo", "Tipo", "Potencia"};
    JTextField[] campos = new JTextField[5];
    DefaultTableModel modelo = new DefaultTableModel(nombres, 0);
    JTable tabla = new JTable(modelo);
    String clave;

    public ventanacamion() {
        clave = JOptionPane.showInputDialog(null,
            "contraseña de mysql", "mysql", JOptionPane.PLAIN_MESSAGE);

        setTitle("camiones");
        setSize(850, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel izquierda = new JPanel(null);
        izquierda.setPreferredSize(new Dimension(350, 400));
        izquierda.setBackground(Color.WHITE);

        for (int i = 0; i < 5; i++) {
            JLabel texto = new JLabel(nombres[i].toLowerCase());
            texto.setBounds(20, 20 + i * 45, 100, 25);
            izquierda.add(texto);

            campos[i] = new JTextField();
            campos[i].setBounds(125, 20 + i * 45, 190, 28);
            izquierda.add(campos[i]);
        }

        String[] acciones = {"guardar", "actualizar", "eliminar"};

        for (int i = 0; i < 3; i++) {
            String accion = acciones[i];
            JButton boton = new JButton(accion);
            boton.setBounds(10 + i * 115, 270, 110, 35);
            boton.setBackground(Color.WHITE);
            izquierda.add(boton);
            boton.addActionListener(e -> guardar(accion));
        }

        tabla.setRowHeight(25);
        tabla.setBackground(Color.WHITE);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0)
                for (int i = 0; i < 5; i++)
                    campos[i].setText(String.valueOf(modelo.getValueAt(fila, i)));
        });

        add(izquierda, BorderLayout.WEST);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargar();
    }

    void guardar(String accion) {
        String sql = accion.equals("guardar")
            ? "insert into Camion(Matricula,Modelo,Tipo,Potencia) values(?,?,?,?)"
            : accion.equals("actualizar")
            ? "update Camion set Matricula=?,Modelo=?,Tipo=?,Potencia=? where idCamion=?"
            : "delete from Camion where idCamion=?";

        try (Connection c = ConexionMysql.conectar(clave);
             PreparedStatement p = c.prepareStatement(sql)) {

            if (!accion.equals("eliminar"))
                for (int i = 1; i < 5; i++)
                    p.setString(i, campos[i].getText());

            if (!accion.equals("guardar"))
                p.setInt(accion.equals("eliminar") ? 1 : 5,
                    Integer.parseInt(campos[0].getText()));

            p.executeUpdate();
            cargar();
            tabla.clearSelection();
            for (JTextField campo : campos) campo.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void cargar() {
        modelo.setRowCount(0);

        try (Connection c = ConexionMysql.conectar(clave);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("select * from Camion")) {

            while (r.next()) {
                Object[] fila = new Object[5];
                for (int i = 0; i < 5; i++)
                    fila[i] = r.getObject(nombres[i]);
                modelo.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ventanacamion().setVisible(true);
    }
}