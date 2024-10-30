import ThirdLibrary.Spring;
import ThirdLibrary.Var10;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        /*SpringModel spring = new SpringModel(10.0); // Максимальное сжатие 10 единиц
        spring.applyForce(5.0); // Применить силу
        try {
            Thread.sleep(2000); // Ждать, чтобы увидеть сжатие
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Текущее сжатие: " + spring.getCurrentCompression());

        spring.release(); // Освободить пружину
        try {
            Thread.sleep(2000); // Ждать, чтобы увидеть разжатие
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Текущее сжатие после разжатия: " + spring.getCurrentCompression());*/


        // Создаем объект пружины
        double initialLength = 10.0; // Начальная длина пружины (в см)
        double maxCompression = 2.0; // Максимальное сжатие (в см)
        double k = 100.0; // Коэффициент жесткости
        double force = 50.0; // Сила воздействия (в Н)

        Spring spring = new Spring(initialLength, maxCompression, k, force);

        // Включаем моделирование
        spring.modelingOn = true;

        // Применяем силу и выводим текущую длину пружины
        System.out.println("Применение силы на пружину: " + force + " Н");
        spring.applyForce(force, 0); // начальное состояние
        System.out.println("Текущая длина пружины: " + spring.getCurrentLength() + " см");

        // Задаем временные значения для сжатия пружины
        long[] timeValues = {1000, 2000, 3000, 4000, 5000}; // время в миллисекундах

        // Симуляция сжатия
        for (long time : timeValues) {
            spring.applyForce(force, time);
            System.out.println("Текущая длина пружины через " + time + " мс: " + spring.getCurrentLength() + " см");
        }

        // Освобождаем пружину
        System.out.println("\nОсвобождение пружины...");
        for (long time : timeValues) {
            spring.release(force, time);
            System.out.println("Текущая длина пружины через " + time + " мс после освобождения: " + spring.getCurrentLength() + " см");
        }
    }
}
