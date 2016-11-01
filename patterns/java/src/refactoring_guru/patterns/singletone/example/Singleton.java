package refactoring_guru.patterns.singletone.example;

public final class Singleton {
    private static Singleton instance;

    private Singleton() {}

    // Double-Checked Locking
    // http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
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
        Singleton singleton = (Singleton) object;
        return this.instance == singleton.instance;
    }
}
