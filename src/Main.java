import ThirdLibrary.Spring;
import ThirdLibrary.Var10;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("spring");
        frame.setContentPane(new Var10().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Центрирование окна
    }
}
