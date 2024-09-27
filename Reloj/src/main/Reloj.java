package main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressWarnings("serial")
public class Reloj extends JFrame {
	
    private JLabel relojDigital;
    private JPanel panelReloj;
    private boolean esDigital = true; // Para alternar entre digital y analógico
    private Timer timer;

    public Reloj() {
        setTitle("Reloj Digital y Analógico");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Etiqueta para el reloj digital
        JPanel panelDigital = new JPanel(new BorderLayout());
        relojDigital = new JLabel("", SwingConstants.CENTER);
        relojDigital.setFont(new Font("Arial", Font.BOLD, 72)); // Tamaño de fuente grande para ocupar espacio
        panelDigital.add(relojDigital, BorderLayout.CENTER); // Añadir reloj al centro del panel

        // Panel para mostrar el reloj (digital o analógico)
        panelReloj = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!esDigital) {
                    dibujarRelojAnalogico(g);
                }
            }
        };

        panelReloj.setPreferredSize(new Dimension(400, 200));

        // Botón para cambiar entre formato digital y analógico
        JButton botonCambiarFormato = new JButton("Cambiar a Analógico");
        botonCambiarFormato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                esDigital = !esDigital;
                if (esDigital) {
                    botonCambiarFormato.setText("Cambiar a Analógico");
                    relojDigital.setVisible(true);
                } else {
                    botonCambiarFormato.setText("Cambiar a Digital");
                    relojDigital.setVisible(false);
                }
                panelReloj.repaint();
            }
        });

        add(relojDigital, BorderLayout.NORTH);
        add(panelReloj, BorderLayout.CENTER);
        add(botonCambiarFormato, BorderLayout.SOUTH);

        iniciarReloj();
    }

    private void iniciarReloj() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (esDigital) {
                    String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                    relojDigital.setText(hora);
                } else {
                    panelReloj.repaint();
                }
            }
        });
        timer.start();
    }

    private void dibujarRelojAnalogico(Graphics g) {
        Calendar calendario = Calendar.getInstance();
        int horas = calendario.get(Calendar.HOUR);
        int minutos = calendario.get(Calendar.MINUTE);
        int segundos = calendario.get(Calendar.SECOND);

        // Coordenadas del centro del reloj
        int centroX = panelReloj.getWidth() / 2;
        int centroY = panelReloj.getHeight() / 2;
        int radio = Math.min(centroX, centroY) - 10;

        // Dibujar círculo del reloj
        g.drawOval(centroX - radio, centroY - radio, radio * 2, radio * 2);

        // Dibujar las manecillas
        dibujarManecilla(g, centroX, centroY, radio * 0.5, (horas + minutos / 60.0) * 30); // Horas
        dibujarManecilla(g, centroX, centroY, radio * 0.7, minutos * 6); // Minutos
        dibujarManecilla(g, centroX, centroY, radio * 0.9, segundos * 6, Color.RED); // Segundos
    }

    private void dibujarManecilla(Graphics g, int centroX, int centroY, double longitud, double angulo) {
        dibujarManecilla(g, centroX, centroY, longitud, angulo, Color.BLACK);
    }

    private void dibujarManecilla(Graphics g, int centroX, int centroY, double longitud, double angulo, Color color) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        angulo = Math.toRadians(angulo - 90); // Ajustar ángulo para que empiece a las 12 en punto
        int x = (int) (centroX + longitud * Math.cos(angulo));
        int y = (int) (centroY + longitud * Math.sin(angulo));
        g2d.drawLine(centroX, centroY, x, y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Reloj().setVisible(true);
            }
        });
    }
}
