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
        if (isCompressed){
            this.currentLength = simulateCompression();
        }
        else{
            this.currentLength = simulateRelease();
        }
        return this.currentLength;
    }

    private double simulateCompression() {
        // Если моделирование не включено, возвращаем текущую длину
        if (!this.modelingOn) return this.currentLength;

        if (currentLength > maxCompression) {
            currentLength -= (this.compressionForce / this.k) * (1 - Math.exp(-k * this.timerValue));
        }
        return this.currentLength;
    }

    private double simulateRelease() {
        // Если моделирование не включено, возвращаем текущую длину
        if (!this.modelingOn) return this.currentLength;

        // Восстанавливаем пружину до начальной длины, если она не превышает её
        if (currentLength < initialLength) {
            currentLength += (this.compressionForce / this.k) * (1 - Math.exp(-k * this.timerValue));

            // Ограничиваем длину пружины, чтобы она не превышала начальную
            if (currentLength > initialLength) {
                currentLength = initialLength;
            }
        }
        return this.currentLength;
    }


    public void updateTimer(double elapsedSeconds) {
        if (!modelingOn) return; // Если моделирование не включено, ничего не делаем

        this.timerValue += elapsedSeconds;

        // Получаем текущую длину пружины
        double currentLength = getCurrentLength();

        // Проверяем состояние пружины
        if (currentLength <= maxCompression) {
            stop(); // Останавливаем моделирование
        } else if (currentLength >= initialLength) {
            stop(); // Останавливаем моделирование
        }
    }


    void resetTimer() {
        this.timerValue = 0.0; // Обнуляем таймер
        this.referenceLength = this.currentLength; // Сохраняем опорное значение текущей длины пружины
    }

    public void start() {
        this.modelingOn = true;
        this.timerValue = 0;
    }

    // останов моделирования
    public void stop() {
        this.modelingOn = false;
    }

}