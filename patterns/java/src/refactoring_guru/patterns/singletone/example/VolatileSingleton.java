package refactoring_guru.patterns.singletone.example;

public class VolatileSingleton {
    private static volatile VolatileSingleton instance;

    private VolatileSingleton() {}

    public static VolatileSingleton getInstance() {
        if (instance == null) {
            synchronized (VolatileSingleton.class) {
                if (instance == null) {
                    instance = new VolatileSingleton();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) {
            return true;
        }
        if(object == null) {
            return false;
        }
        if(this.getClass() != object.getClass()) {
            return false;
        }
        VolatileSingleton singleton = (VolatileSingleton) object;
        return this.instance == singleton.instance;
    }
}
