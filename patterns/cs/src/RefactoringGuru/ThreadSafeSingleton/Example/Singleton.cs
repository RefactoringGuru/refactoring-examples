using System;

namespace ThreadSafeSingleton.Example
{
    class Singleton
    {
        private static object _lock = new object();
        private static volatile Singleton instance;
        public String value;

        private Singleton(String value)
        {
            this.value = value;
        }

        public static Singleton GetInstance(String value)
        {
            if (instance == null)
            {
                lock (_lock)
                {
                    if (instance == null)
                    {
                        instance = new Singleton(value);
                    }
                }
            }
            return instance;
        }
    }
}
