package transporte.vista;

import javax.swing.*;
import java.awt.*;

public class sistema extends JFrame {

    public sistema() {
        setTitle("sistema de transporte");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));
        panel.setBackground(Color.WHITE);

        panel.add(new JLabel("sistema de transporte", SwingConstants.CENTER));

        JButton ciudades = new JButton("ciudades");
        JButton camiones = new JButton("camiones");
        JButton camioneros = new JButton("camioneros");
        JButton paquetes = new JButton("paquetes");
        JButton asignaciones = new JButton("asignaciones");

        JButton[] botones = {
            ciudades, camiones, camioneros, paquetes, asignaciones
        };

        for (JButton boton : botones) {
            boton.setBackground(Color.WHITE);
            boton.setFocusPainted(false);
            panel.add(boton);
        }

        ciudades.addActionListener(e -> new ventanaciudad().setVisible(true));
        camiones.addActionListener(e -> new ventanacamion().setVisible(true));
        camioneros.addActionListener(e -> new ventanacamionero().setVisible(true));
        paquetes.addActionListener(e -> new ventanapaquete().setVisible(true));
        asignaciones.addActionListener(e -> new ventanaasignacion().setVisible(true));

        add(panel);
    }

    public static void main(String[] args) {
        new sistema().setVisible(true);
    }
}