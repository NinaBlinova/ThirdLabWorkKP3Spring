package ThirdLibrary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Var10 {
    public JPanel contentPane;
    private JPanel Spring; // для вывода анимации
    private JButton releaseSpringButton; // кнопка для сжатия
    private JButton compressSpringButton; // кнопка для отжатия
    private JTextField changeL;// вывод изменения длины
    private JTextField changeT; // вывод изменения времени
    private JButton existButton; // выход из программы
    private JTextField maxL; // максимальная длина
    private JTextField F; //сила воздействия
    private JTextField k; // максимальный коэффициент сжатия
    private JTextField t; // время для ускореня или замедления


    private Spring springModel; // Модель пружины
    private Timer timer; // Таймер для обновления значений
    private double time = 0; // Время для обновления


    public Var10() {
        // Инициализация модели пружины
    }
}
