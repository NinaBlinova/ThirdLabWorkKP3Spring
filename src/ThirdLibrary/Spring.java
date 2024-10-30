package ThirdLibrary;

public class Spring {
    final private double initialLength; // Начальная длина пружины
    final private double maxCompression; // Максимальное сжатие
    private double currentLength; // Текущая длина пружины
    private boolean isCompressed; // Сжата ли пружина
    private double compressionForce; // Сила воздействия
    private double k;
    public boolean modelingOn;

    private double timerValue; // Значение таймера в секундах
    private double referenceLength; // Опорное значение текущей длины пружины

    public Spring(double initialLength, double maxCompression, double k, double F) {
        this.initialLength = initialLength;
        this.maxCompression = maxCompression;
        this.currentLength = initialLength;
        this.isCompressed = false;
        this.modelingOn = false;
        this.compressionForce = F;
        this.k = k;
        this.timerValue = 0.0; // Инициализация таймера
        this.referenceLength = initialLength; // Инициализация опорной длины
    }

    public boolean getCompress() {
        return this.isCompressed;
    }

    public void setCompress(boolean compress) {
        this.isCompressed = compress;
    }

    public boolean getModeling() {
        return this.isCompressed;
    }

    public void setModeling(boolean isModeling) {
        this.modelingOn = isModeling;
    }

    public double getCurrentLength() {
        return currentLength;
    }

    public void updateTimer(double elapsedSeconds) {

        double deltaLength = (compressionForce / k) * (1 - Math.exp(-k * elapsedSeconds));
        if (isCompressed) {
            // Моделирование сжатия
            currentLength -= deltaLength;
            if (currentLength < maxCompression) {
                currentLength = maxCompression;
            }
        } else {
            currentLength += deltaLength;
            if (currentLength > initialLength) {
                currentLength = initialLength;
            }
        }
    }

    void resetTimer() {
        this.timerValue = 0.0; // Обнуляем таймер
        this.referenceLength = this.currentLength; // Сохраняем опорное значение текущей длины пружины
    }

    public void start() {
        this.modelingOn = true;
    }

    // останов моделирования
    public void stop() {
        this.modelingOn = false;
    }

}