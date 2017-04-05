using System;

namespace NonThreadSafeSingleton.Example
{
    class Singleton
    {
        private static Singleton instance;
        public String value;

        private Singleton(String value)
        {
            // EN: Following code emulates slow initialization.
            // 
            // RU: Этот код эмулирует медленную инициализацию.
            System.Threading.Thread.Sleep(50);
            this.value = value;
        }

        public static Singleton GetInstance(String value)
        {
            if (instance == null)
            {
                instance = new Singleton(value);
            }
            return instance;
        }
    }
}
