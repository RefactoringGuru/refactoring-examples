package refactoring_guru.patterns.singleton.example.non_thread_safe;

public class DemoMultiThread {
    public static void main(String[] args) throws Exception {
        System.out.println("RESULTS" + "\n" +
                           "Single value means singleton was created just once." + "\n" +
                           "Multiple values mean several singleton objects were created." + "\n");
        Thread threadFoo = new Thread(new ThreadFoo());
        Thread threadBar = new Thread(new ThreadBar());
        threadFoo.start();
        threadBar.start();
    }

    static class ThreadFoo implements Runnable {
        @Override
        public void run() {
            Singleton singleton = Singleton.getInstance("FOO");
            System.out.println(singleton.value);
        }
    }

    static class ThreadBar implements Runnable {
        @Override
        public void run() {
            Singleton singleton = Singleton.getInstance("BAR");
            System.out.println(singleton.value);
        }
    }
}
