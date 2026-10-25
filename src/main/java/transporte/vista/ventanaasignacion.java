package transporte.vista;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import transporte.conexion.ConexionMysql;

public class ventanaasignacion extends JFrame {

    String[] nombres = {"idCamioneroCamion", "idCamionero", "idCamion"};
    JTextField[] campos = new JTextField[3];
    DefaultTableModel modelo = new DefaultTableModel(nombres, 0);
    JTable tabla = new JTable(modelo);
    String clave;

    public ventanaasignacion() {
        clave = JOptionPane.showInputDialog(null,
            "contraseña de mysql", "mysql", JOptionPane.PLAIN_MESSAGE);

        setTitle("asignaciones");
        setSize(750, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(350, 300));

        for (int i = 0; i < 3; i++) {
            JLabel texto = new JLabel(nombres[i].toLowerCase());
            texto.setBounds(15, 25 + i * 45, 125, 25);
            panel.add(texto);

            campos[i] = new JTextField();
            campos[i].setBounds(145, 25 + i * 45, 170, 28);
            panel.add(campos[i]);
        }

        String[] botones = {"guardar", "actualizar", "eliminar"};

        for (int i = 0; i < 3; i++) {
            String tipo = botones[i];
            JButton boton = new JButton(tipo);
            boton.setBounds(10 + i * 115, 190, 110, 35);
            boton.setBackground(Color.WHITE);
            panel.add(boton);
            boton.addActionListener(e -> accion(tipo));
        }

        tabla.setRowHeight(25);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                for (int i = 0; i < 3; i++) {
                    campos[i].setText(
                        String.valueOf(modelo.getValueAt(fila, i)));
                }
            }
        });

        add(panel, BorderLayout.WEST);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargar();
    }

    void accion(String tipo) {
        String sql = tipo.equals("guardar")
            ? "insert into CamioneroCamion(idCamionero,idCamion) values(?,?)"
            : tipo.equals("actualizar")
            ? "update CamioneroCamion set idCamionero=?,idCamion=? where idCamioneroCamion=?"
            : "delete from CamioneroCamion where idCamioneroCamion=?";

        try (Connection c = ConexionMysql.conectar(clave);
             PreparedStatement p = c.prepareStatement(sql)) {

            if (!tipo.equals("eliminar")) {
                p.setInt(1, Integer.parseInt(campos[1].getText()));
                p.setInt(2, Integer.parseInt(campos[2].getText()));
            }

            if (!tipo.equals("guardar")) {
                p.setInt(tipo.equals("eliminar") ? 1 : 3,
                    Integer.parseInt(campos[0].getText()));
            }

            p.executeUpdate();
            cargar();
            tabla.clearSelection();

            for (JTextField campo : campos) {
                campo.setText("");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void cargar() {
        modelo.setRowCount(0);

        try (Connection c = ConexionMysql.conectar(clave);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("select * from CamioneroCamion")) {

            while (r.next()) {
                Object[] fila = new Object[3];

                for (int i = 0; i < 3; i++) {
                    fila[i] = r.getObject(nombres[i]);
                }

                modelo.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ventanaasignacion().setVisible(true);
    }
}