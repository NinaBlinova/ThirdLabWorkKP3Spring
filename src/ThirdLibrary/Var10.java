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
    private JButton saveButton;
    private double valueTime = 0;
    private Spring mySpring; // экземпляр пружины
    private GraphPanel graphPanel;


    // Создание и настройка таймера
    javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            double time = speedSlader.getValue();
            // вычисление времени шага
            double l = mySpring.getCurrentLength();
            // инкремент времени моделирования
            valueTime += time;
            // обновление таймера в модели пружины
            mySpring.updateTimer(valueTime);
            // вычисление текущей длины пружины
            double maxLength = Double.parseDouble(maxL.getText());
            double compressionPercentage = (l / maxLength) * 100;
            springView.setValue((int) compressionPercentage);
            changeT.setText("Прошедшее время: " + valueTime + " с");
            changeL.setText(String.valueOf(l));
            graphPanel.updateCompression((int) compressionPercentage);
        }
    });

    public Var10() {

        // Установка значений по умолчанию
        maxL.setText("107.0");
        F.setText("30.0");
        k.setText("14.0");


        // Инициализация Spring
        Spring.setLayout(new BorderLayout()); // Установите нужный LayoutManager

        // Инициализация graphPanel
        graphPanel = new GraphPanel();
        Spring.add(graphPanel, BorderLayout.CENTER); // Добавляем graphPanel в центр Spring


        JSlider speed = new JSlider(0, 100, 2000, 1000);
        speedSlader.setModel(speed.getModel());

        // Добавление слушателя для изменения значения слайдера
        speedSlader.addChangeListener(e -> {
            int speedValue = speedSlader.getValue();
            updateTimerInterval(speedValue);
        });


        compressSpringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mySpring.setCompress(true);
                mySpring.startStart();
                lockParameters();
                int currentCompression = (int) mySpring.getCurrentLength();
                graphPanel.updateCompression(currentCompression);
                // Запускаем таймер для обновления
                timer.start();
                // Проверка состояния через отдельный таймер
                new javax.swing.Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (mySpring.isAtCom()) {
                            ((javax.swing.Timer) evt.getSource()).stop(); // Остановка этого таймера
                            valueTime = 0;
                            new javax.swing.Timer(1000, new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    if (mySpring.isAtRel()) {
                                        ((javax.swing.Timer) evt.getSource()).stop(); // Остановка этого таймера
                                        valueTime = 0;
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
                mySpring.setCompress(false);
                mySpring.startStart();
                lockParameters();
                int currentCompression = (int) mySpring.getCurrentLength();
                graphPanel.updateCompression(currentCompression);
                // Запускаем таймер для обновления
                timer.start();
                // Проверка состояния через отдельный таймер
                new javax.swing.Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (mySpring.isAtRel()) {
                            unlockParameters(); // Разблокировка параметров, если пружина разжата
                            ((javax.swing.Timer) evt.getSource()).stop(); // Остановка этого таймера
                            timer.stop();
                            valueTime = 0;
                        }
                    }
                }).start();
            }
        });

        existButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mySpring.resetTimer(); // обнуляем таймер и сохраняем состояние пружины перед выходом
                unlockParameters();
                int currentCompression = (int) mySpring.getCurrentLength();
                graphPanel.updateCompression(currentCompression);
                timer.stop();
                valueTime = 0;
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializeSpring(); // Инициализация пружины
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

    private void updateTimerInterval(int interval) {
        if (timer != null) {
            timer.stop(); // Останавливаем текущий таймер
            timer.setDelay(interval); // Устанавливаем новый интервал
            timer.start(); // Запускаем таймер с новым интервалом
        }
    }
}



