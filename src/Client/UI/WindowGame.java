package Client.UI;

import javax.swing.*;
import java.awt.*;

public class WindowGame extends JFrame {
    JPanel contentPane;
    JTextField textField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    WindowGame frame = new WindowGame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Create the frame.
     */
    public WindowGame() {

        setTitle("\u6A4B\u724C123");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 980, 500);
        contentPane = new JPanel();

        setContentPane(contentPane);
        contentPane.setLayout(null);





        JTextArea textArea = new JTextArea();
        textArea.setBounds(650, 0, 304, 423);
        textArea.setBackground(Color.red);
        contentPane.add(textArea);
    }
}
