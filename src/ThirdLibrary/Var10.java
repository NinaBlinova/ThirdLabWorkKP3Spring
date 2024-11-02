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
    private JProgressBar springView;
    private JSlider speedSlader;

    private double timeValue = 0;
    private Spring mySpring; // экземпляр пружины

    private GraphPanel graphPanel;

    // Создание и настройка таймера
    javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // вычисление времени шага
            double time = speedSlader.getValue();
            // инкремент времени моделирования
            timeValue += time;
            // обновление таймера в модели пружины
            mySpring.updateTimer(time);
            // вычисление текущей длины пружины
            double maxLength = Double.parseDouble(maxL.getText());
            double compressionPercentage = (mySpring.getCurrentLength() / maxLength) * 100;
            springView.setValue((int) compressionPercentage);
            changeT.setText("Прошедшее время: " + timeValue + " с");
            changeL.setText(String.valueOf(mySpring.getCurrentLength()));
            graphPanel.updateCompression((int) compressionPercentage);
        }
    });

    public Var10() {

        // Установка значений по умолчанию
        maxL.setText("77.0");
        F.setText("100.0");
        k.setText("14.0");

        graphPanel = new GraphPanel();

        JSlider speed = new JSlider(0, 100, 2000, 1000);
        speedSlader.setModel(speed.getModel());

        initializeSpring(); // Инициализация пружины


        compressSpringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                mySpring.setCompress(true);
                mySpring.startStart();
                lockParameters();

                // Запускаем таймер для обновления
                timer.start();

                // Проверка состояния через отдельный таймер
                new javax.swing.Timer(100, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (mySpring.isAtCom()) {
                            ((javax.swing.Timer) evt.getSource()).stop(); // Остановка этого таймера
                            timeValue = 0;
                            new javax.swing.Timer(100, new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    if (mySpring.isAtRel()) {
                                        ((javax.swing.Timer) evt.getSource()).stop(); // Остановка этого таймера
                                        timeValue = 0;
                                        timer.stop();
                                        unlockParameters();
                                    }
                                }
                            }).start();
                        }
                    }
                }).start();
            }
        });

        releaseSpringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                mySpring.setCompress(false);
                mySpring.startStart();
                lockParameters();

                // Запускаем таймер для обновления
                timer.start();

                // Проверка состояния через отдельный таймер
                new javax.swing.Timer(100, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (mySpring.isAtRel()) {
                            unlockParameters(); // Разблокировка параметров, если пружина разжата
                            ((javax.swing.Timer) evt.getSource()).stop(); // Остановка этого таймера
                            timer.stop();
                            timeValue = 0;

                        }
                    }
                }).start();
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
        springView.setValue((int) length * 100);
        mySpring = new Spring(length, length / 3, coefficient, force);
    }

    // Метод для блокировки параметров
    private void lockParameters() {
        maxL.setEnabled(false);
        F.setEnabled(false);
        k.setEnabled(false);
    }

    // Метод для разблокировки параметров
    private void unlockParameters() {
        maxL.setEnabled(true);
        F.setEnabled(true);
        k.setEnabled(true);
    }

}



