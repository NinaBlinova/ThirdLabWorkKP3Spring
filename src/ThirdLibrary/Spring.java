package ThirdLibrary;

public class Spring {
    final private double initialLength; // Начальная длина пружины
    final private double maxCompression; // Максимальное сжатие
    private double currentLength; // Текущая длина пружины
    private boolean isCompressed; // Сжата ли пружина
    private double compressionForce; // Сила воздействия
    private double k;
    public boolean modelingOn;

    public Spring(double initialLength, double maxCompression, double k, double F) {
        this.initialLength = initialLength;
        this.maxCompression = maxCompression;
        this.currentLength = initialLength;
        this.isCompressed = false;
        this.modelingOn = false;
        this.compressionForce = F;
        this.k = k;
    }

    public double getForse() {
        return this.compressionForce;
    }

    public void setForse(double F) {
        if (!this.modelingOn) {
            this.compressionForce = F;
        }
    }

    public double getK() {
        return this.k;
    }

    public void setK(double k) {
        if (!this.modelingOn) {
            this.k = k;
        }
    }


    public void applyForce(double force, long totalTimeMillis) {
        this.compressionForce = force;
        this.isCompressed = true;
        simulateCompression(totalTimeMillis);
    }

    public void release(double force, long totalTimeMillis) {
        this.compressionForce = force;
        this.isCompressed = false;
        simulateRelease(totalTimeMillis);
    }

    public double getCurrentLength() {
        return currentLength;
    }


    private double simulateCompression(long totalTimeMillis) {
        // Если моделирование не включено, возвращаем текущую длину
        if (!this.modelingOn) return this.currentLength;

        // Переводим миллисекунды в секунды
        double totalTimeSeconds = totalTimeMillis / 1000.0;

        if (currentLength > maxCompression) {
            currentLength -= (this.compressionForce / this.k) * (1 - Math.exp(-k * totalTimeSeconds));
        }
        return this.currentLength;
    }

    private double simulateRelease(long totalTimeMillis) {
        // Если моделирование не включено, возвращаем текущую длину
        if (!this.modelingOn) return this.currentLength;

        // Переводим миллисекунды в секунды
        double totalTimeSeconds = totalTimeMillis / 1000.0;

        // Восстанавливаем пружину до начальной длины, если она не превышает её
        if (currentLength < initialLength) {
            currentLength += (this.compressionForce / this.k) * (1 - Math.exp(-k * totalTimeSeconds));

            // Ограничиваем длину пружины, чтобы она не превышала начальную
            if (currentLength > initialLength) {
                currentLength = initialLength;
            }
        }
        return this.currentLength;
    }


}