/*package InterfazGrafica;

import org.example.multijugador.ClienteM;
import org.example.multijugador.ServidorM;
import org.example.unjugador.ClienteU;
import org.example.unjugador.ClienteU;
import org.example.unjugador.ServidorU;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cliente {


    private JFrame frame;
    private JTextField respuestaField;
    private JTextArea descripcionArea;

    public Cliente() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Juego del País - Cliente");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        descripcionArea = new JTextArea();
        descripcionArea.setEditable(false);
        frame.getContentPane().add(new JScrollPane(descripcionArea), BorderLayout.CENTER);

        respuestaField = new JTextField();
        frame.getContentPane().add(respuestaField, BorderLayout.SOUTH);
        respuestaField.setColumns(10);

        JButton enviarButton = new JButton("Enviar");
        enviarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarRespuesta();
            }
        });
        frame.getContentPane().add(enviarButton, BorderLayout.EAST);

        JButton unijugadorButton = new JButton("Unijugador");
        unijugadorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarServidorYClienteUnijugador();
            }
        });
        frame.getContentPane().add(unijugadorButton, BorderLayout.WEST);

        JButton multijugadorButton = new JButton("Multijugador");
        multijugadorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarServidorYClientesMultijugador();
            }
        });
        frame.getContentPane().add(multijugadorButton, BorderLayout.SOUTH);
    }

    private void enviarRespuesta() {
        String respuesta = respuestaField.getText();
        // Aquí debes implementar la lógica para enviar la respuesta al servidor
    }

    private void iniciarServidorYClienteUnijugador() {
        // Lógica para iniciar el servidor y cliente unijugador
        new Thread(() -> ServidorU.main(new String[]{})).start();
        new Thread(() -> ClienteU.main(new String[]{})).start();
    }

    private void iniciarServidorYClientesMultijugador() {
        // Lógica para iniciar el servidor y clientes multijugador
        new Thread(() -> ServidorM.main(new String[]{})).start();
        new Thread(() -> ClienteM.main(new String[]{})).start();
        new Thread(() -> ClienteM.main(new String[]{})).start();
    }

    public void mostrarVentana() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cliente window = new Cliente();
                    window.mostrarVentana();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
*/