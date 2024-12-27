package UI;

import main.Main;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

public class MainUI extends JFrame {
    private JButton posaljiButton;
    private JButton generisiButton;
    private JButton generisiButton1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextArea textArea1;
    private JPanel contentPane;
    private JButton generisiButton2;
    private JButton ocistiButton;

    public MainUI() {
        setContentPane(contentPane);
        setTitle("");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 250);
        setLocationRelativeTo(null);
        setVisible(true);

        PrintStream outStream = new PrintStream(new TextAreaOutputStream(textArea1));
        textArea1.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));

        System.setOut( outStream );
        System.setErr( outStream );

        // Action listeners
        generisiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pSize = Integer.parseInt(comboBox1.getSelectedItem().toString());
                MainBus.p = BigInteger.probablePrime(pSize, new SecureRandom());
                System.out.println("p: " + MainBus.p);

                List<BigInteger> primitiveRoots = Util.findAllPrimitiveRoots(MainBus.p);

                comboBox2.removeAllItems();
                for (BigInteger root : primitiveRoots) {
                    comboBox2.addItem(root.toString());
                }
            }
        });
        generisiButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int aSize = Integer.parseInt(comboBox1.getSelectedItem().toString());
                MainBus.a = new BigInteger(aSize, new SecureRandom());
                System.out.println("a: " + MainBus.a);
            }
        });
        posaljiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BigInteger g = BigInteger.valueOf(Integer.parseInt(
                        comboBox2.getSelectedItem().toString()
                ));
                new Main(MainBus.p, g, MainBus.a, MainBus.b);
            }
        });
        generisiButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bSize = Integer.parseInt(comboBox1.getSelectedItem().toString());
                MainBus.b = new BigInteger(bSize, new SecureRandom());
                System.out.println("b: " + MainBus.b);
            }
        });
        ocistiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("");
            }
        });
    }

    public static void main(String[] args) {
        new MainUI();
    }

    public class TextAreaOutputStream extends OutputStream {
        private javax.swing.JTextArea jTextArea1;

        /**
         * Creates a new instance of TextAreaOutputStream which writes
         * to the specified instance of javax.swing.JTextArea control.
         *
         * @param textArea   A reference to the javax.swing.JTextArea
         *                  control to which the output must be redirected to.
         */
        public TextAreaOutputStream( JTextArea textArea ) {
            this.jTextArea1 = textArea;
        }

        public void write( int b ) throws IOException {
            jTextArea1.append( String.valueOf( ( char )b ) );
            jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
        }

        public void write(char[] cbuf, int off, int len) throws IOException {
            jTextArea1.append(new String(cbuf, off, len));
            jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
        }
    }
}
