package ThirdLibrary;

public class Spring {
    private double initialLength; // Начальная длина пружины
    private double maxCompression; // Максимальное сжатие
    private double currentLength; // Текущая длина пружины
    private boolean isCompressed; // Сжата ли пружина
    private double compressionForce; // Сила воздействия
    private double time; // Время в моделировании
    private double k;

    public Spring(double initialLength, double maxCompression, double k) {
        this.initialLength = initialLength;
        this.maxCompression = maxCompression;
        this.currentLength = initialLength;
        this.isCompressed = false;
        this.time = 0;
        this.k = k;
    }

    public void applyForce(double force) {
        this.compressionForce = force;
        this.isCompressed = true;
        simulateCompression();
    }

    public void release() {
        this.isCompressed = false;
        simulateRelease();
    }

    private void simulateCompression() {
        // Экспоненциальная модель сжатия
        double targetLength = maxCompression;
        double decayRate = 0.01;
        while ((int) currentLength > (int) targetLength) {
            currentLength -= decayRate * (currentLength - targetLength + compressionForce / k);
            time += 0.1; // Увеличиваем время моделирования
            //printStatus();
            try {
                Thread.sleep(100); // Задержка для визуализации процесса
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // Устанавливаем текущую длину на целевую, чтобы избежать небольших колебаний
        currentLength = targetLength;
    }

    private void simulateRelease() {
        // Экспоненциальная модель разжатия
        double targetLength = initialLength;
        double decayRate = 0.01; // Коэффициент разжатия, можно настроить
        while ((int) currentLength < (int) targetLength) {
            currentLength += decayRate * (targetLength - currentLength + compressionForce / k);
            time += 0.1; // Увеличиваем время моделирования
            //printStatus();
            try {
                Thread.sleep(100); // Задержка для визуализации процесса
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // Устанавливаем текущую длину на целевую, чтобы избежать небольших колебаний
        currentLength = targetLength;
    }

//    private void printStatus() {
//        System.out.printf("Время: %.1f, Текущая длина пружины: %.2f\n", time, currentLength);
//    }

    public double getCurrentLength() {
        return currentLength;
    }

    public double getTime() {
        return time;
    }

    public boolean isCompressed() {
        return isCompressed;
    }


}