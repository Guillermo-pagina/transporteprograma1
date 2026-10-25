package transporte.vista;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import transporte.conexion.ConexionMysql;

public class ventanaciudad extends JFrame {

    JTextField id = new JTextField(8);
    JTextField nombre = new JTextField(10);
    DefaultTableModel m = new DefaultTableModel(
        new String[]{"id", "nombre"}, 0);
    JTable tabla = new JTable(m);
    String clave;

    public ventanaciudad() {
        clave = JOptionPane.showInputDialog(null, "contraseña de mysql");

        setTitle("ciudades");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("id"));
        panel.add(id);
        panel.add(new JLabel("nombre"));
        panel.add(nombre);

        for (String b : new String[]{"guardar", "actualizar", "eliminar"}) {
            JButton boton = new JButton(b);
            panel.add(boton);
            boton.addActionListener(e -> accion(b));
        }

        add(panel, "North");
        add(new JScrollPane(tabla), "Center");

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int f = tabla.getSelectedRow();
            if (f >= 0) {
                id.setText(m.getValueAt(f, 0).toString());
                nombre.setText(m.getValueAt(f, 1).toString());
            }
        });

        cargar();
    }

    void accion(String b) {
        String sql = b.equals("guardar")
            ? "insert into Ciudad(Nombre) values(?)"
            : b.equals("actualizar")
            ? "update Ciudad set Nombre=? where idCiudad=?"
            : "delete from Ciudad where idCiudad=?";

        try (Connection c = ConexionMysql.conectar(clave);
             PreparedStatement p = c.prepareStatement(sql)) {

            if (!b.equals("eliminar"))
                p.setString(1, nombre.getText());

            if (!b.equals("guardar"))
                p.setInt(b.equals("eliminar") ? 1 : 2,
                    Integer.parseInt(id.getText()));

            p.executeUpdate();
            cargar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void cargar() {
        m.setRowCount(0);

        try (Connection c = ConexionMysql.conectar(clave);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("select * from Ciudad")) {

            while (r.next()) {
                m.addRow(new Object[]{
                    r.getInt("idCiudad"),
                    r.getString("Nombre")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ventanaciudad().setVisible(true);
    }
}