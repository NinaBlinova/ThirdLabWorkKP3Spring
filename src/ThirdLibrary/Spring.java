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

    public void setCompress(boolean compress) {
        this.isCompressed = compress;
    }

    public void setTimeValue(double time) {
        this.timerValue  += time;
    }


    public double getCurrentLength() {
        if (isCompressed) {
            this.currentLength = simulateCompression();
        } else {
            this.currentLength = simulateRelease();
        }
        return this.currentLength;
    }

    private double simulateCompression() {
        // Если моделирование не включено, возвращаем текущую длину
        if (!this.modelingOn) return this.currentLength;
        System.out.println(timerValue);
        if (this.currentLength > this.maxCompression) {
            this.currentLength -= (this.currentLength / this.k) * (1 - Math.exp(-this.timerValue * this.compressionForce * this.k));
        } else {
            this.timerValue = 0.0;
            // Обнуляем таймер при достижении максимального сжатия
            this.currentLength = this.maxCompression;
            this.isCompressed = false; // Переключаем состояние на разжатие
        }

        return this.currentLength;
    }

    private double simulateRelease() {
        // Если моделирование не включено, возвращаем текущую длину
        if (!this.modelingOn) return this.currentLength;
        // Восстанавливаем пружину до начальной длины, если она не превышает её
        if (this.currentLength < this.initialLength) {
            this.currentLength += (this.currentLength / this.k) * (1 - Math.exp(-this.timerValue * this.compressionForce * this.k));

            // Ограничиваем длину пружины, чтобы она не превышала начальную
            if (this.currentLength > this.initialLength) {
                this.currentLength = this.initialLength;
            }
        }
        return this.currentLength;
    }


    public void updateTimer(double elapsedSeconds) {
        if (!this.modelingOn) return; // Если моделирование не включено, ничего не делаем
        setTimeValue(elapsedSeconds);
        // Получаем текущую длину пружины
        double currentLength = getCurrentLength();
        // Проверяем состояние пружины
        if (currentLength < this.maxCompression) {
            currentLength = this.maxCompression;
            stopSpring(); // Останавливаем моделирование
        } else if (currentLength >= this.initialLength) {
            stopSpring(); // Останавливаем моделирование
        }
    }

    void resetTimer() {
        this.timerValue = 0.0; // Обнуляем таймер
        this.referenceLength = this.currentLength; // Сохраняем опорное значение текущей длины пружины
    }

    public void startStart() {
        this.modelingOn = true;
        this.timerValue = 0.0;
    }

    // останов моделирования
    public void stopSpring() {
        this.modelingOn = false;
        this.timerValue = 0.0;
    }

    public boolean isAtCom() {
        return this.currentLength <= this.maxCompression;
    }

    public boolean isAtRel() {
        return this.currentLength >= this.initialLength;
    }
}