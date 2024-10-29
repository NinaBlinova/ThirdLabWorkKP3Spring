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


        Spring spring = new Spring(10.0, 5.0, 0.5);
        spring.applyForce(10.0); // Применяем силу
        // В ожидании завершения сжатия
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        spring.release(); // Отпускаем пружину
    }
}
