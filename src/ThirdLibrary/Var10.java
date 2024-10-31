package ThirdLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Var10 {
    public JPanel contentPane;
    private JPanel Spring; // для вывода анимации
    private JButton releaseSpringButton; // кнопка для отжатия
    private JButton compressSpringButton; // кнопка для сжатия
    private JTextField changeL; // вывод изменения длины
    private JTextField changeT; // вывод изменения времени
    private JButton existButton; // выход из программы
    private JTextField maxL; // максимальная длина
    private JTextField F; // сила воздействия
    private JTextField k; // максимальный коэффициент сжатия
    private JSpinner spnTimeMult;
    private JProgressBar springView;

    private double timeValue = 0;
    private Spring mySpring; // экземпляр пружины

    public Var10() {
        // Установка значений по умолчанию
        maxL.setText("40.0");
        F.setText("5.0");
        k.setText("2.0");

        // Настройка JSpinner
        SpinnerModel model = new SpinnerNumberModel(100, 100, 10000, 100); // начальное значение, минимальное, максимальное, шаг
        spnTimeMult.setModel(model);

        initializeSpring(); // Инициализация пружины

        // Создание и настройка таймера
        javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // вычисление времени шага
                double time = Double.parseDouble(spnTimeMult.getValue().toString());

                // инкремент времени моделирования
                timeValue += time;
                // обновление таймера в модели пружины
                mySpring.updateTimer(time);
                // вычисление текущей длины пружины
                double currentLength = mySpring.getCurrentLength();

                springView.setValue((int) currentLength);
                changeT.setText("Прошедшее время: " + timeValue + " с");
                changeL.setText(String.valueOf(currentLength));
            }
        });

        compressSpringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mySpring.start();
                lockParameters(); // Блокируем параметры перед началом процесса
                mySpring.setCompress(true);
                timer.start(); // запускаем таймер для обновления
            }
        });

        releaseSpringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mySpring.start();
                lockParameters();
                mySpring.setCompress(false);
                timer.start(); // запускаем таймер для обновления
            }
        });

        existButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                mySpring.resetTimer(); // обнуляем таймер и сохраняем состояние пружины перед выходом
                unlockParameters();
            }
        });
    }

    private void initializeSpring() {
        double force = Double.parseDouble(F.getText());
        double coefficient = Double.parseDouble(k.getText());
        double length = Double.parseDouble(maxL.getText());
        springView.setValue((int) length);
        mySpring = new Spring(length, length / 3, coefficient, force);
    }

    // Метод для блокировки параметров
    private void lockParameters() {
        maxL.setEnabled(false);
        F.setEnabled(false);
        k.setEnabled(false);
        spnTimeMult.setEnabled(false);
    }

    // Метод для разблокировки параметров
    private void unlockParameters() {
        maxL.setEnabled(true);
        F.setEnabled(true);
        k.setEnabled(true);
        spnTimeMult.setEnabled(true);
    }
}



