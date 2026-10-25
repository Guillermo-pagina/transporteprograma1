package transporte.vista;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import transporte.conexion.ConexionMysql;

public class ventanapaquete extends JFrame {

    String[] nombres = {"idPaquete", "Descripcion", "Destinatario",
        "DireccionEnvio", "idCamionero", "idCiudad"};

    JTextField[] campos = new JTextField[6];
    DefaultTableModel modelo = new DefaultTableModel(nombres, 0);
    JTable tabla = new JTable(modelo);
    String clave;

    public ventanapaquete() {
        clave = JOptionPane.showInputDialog(null,
            "contraseña de mysql", "mysql", JOptionPane.PLAIN_MESSAGE);

        setTitle("paquetes");
        setSize(950, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(350, 400));

        for (int i = 0; i < 6; i++) {
            JLabel texto = new JLabel(nombres[i].toLowerCase());
            texto.setBounds(15, 20 + i * 40, 110, 25);
            panel.add(texto);

            campos[i] = new JTextField();
            campos[i].setBounds(125, 20 + i * 40, 190, 28);
            panel.add(campos[i]);
        }

        String[] botones = {"guardar", "actualizar", "eliminar"};

        for (int i = 0; i < 3; i++) {
            String accion = botones[i];
            JButton boton = new JButton(accion);
            boton.setBounds(10 + i * 115, 290, 110, 35);
            boton.setBackground(Color.WHITE);
            panel.add(boton);
            boton.addActionListener(e -> accion(accion));
        }

        tabla.setRowHeight(25);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                for (int i = 0; i < 6; i++) {
                    Object dato = modelo.getValueAt(fila, i);
                    campos[i].setText(dato == null ? "" : dato.toString());
                }
            }
        });

        add(panel, BorderLayout.WEST);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargar();
    }

    void accion(String tipo) {
        String sql = tipo.equals("guardar")
            ? "insert into Paquete(Descripcion,Destinatario,DireccionEnvio,idCamionero,idCiudad) values(?,?,?,?,?)"
            : tipo.equals("actualizar")
            ? "update Paquete set Descripcion=?,Destinatario=?,DireccionEnvio=?,idCamionero=?,idCiudad=? where idPaquete=?"
            : "delete from Paquete where idPaquete=?";

        try (Connection c = ConexionMysql.conectar(clave);
             PreparedStatement p = c.prepareStatement(sql)) {

            if (!tipo.equals("eliminar")) {
                p.setString(1, campos[1].getText());
                p.setString(2, campos[2].getText());
                p.setString(3, campos[3].getText());
                p.setInt(4, Integer.parseInt(campos[4].getText()));
                p.setInt(5, Integer.parseInt(campos[5].getText()));
            }

            if (!tipo.equals("guardar")) {
                p.setInt(tipo.equals("eliminar") ? 1 : 6,
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
             ResultSet r = s.executeQuery("select * from Paquete")) {

            while (r.next()) {
                Object[] fila = new Object[6];

                for (int i = 0; i < 6; i++) {
                    fila[i] = r.getObject(nombres[i]);
                }

                modelo.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ventanapaquete().setVisible(true);
    }
}