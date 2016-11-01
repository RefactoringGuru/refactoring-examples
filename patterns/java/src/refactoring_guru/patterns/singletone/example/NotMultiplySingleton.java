package refactoring_guru.patterns.singletone.example;

public final class NotMultiplySingleton {
    private static NotMultiplySingleton instance;

    private NotMultiplySingleton() {}

    public static NotMultiplySingleton getInstance() {
        if (instance == null) {
            instance = new NotMultiplySingleton();
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
        NotMultiplySingleton singleton = (NotMultiplySingleton) object;
        return this.instance == singleton.instance;
    }
}
