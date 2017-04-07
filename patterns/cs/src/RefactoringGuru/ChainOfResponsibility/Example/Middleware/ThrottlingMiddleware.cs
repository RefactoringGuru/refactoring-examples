using System;
using System.Threading;

namespace RefactoringGuru.ChainOfResponsibility.Example.Middleware
{
    /**
     * RU: Конкретный элемент цепи обрабатывает запрос по-своему.
     */
    class ThrottlingMiddleware : Middleware
    {
        private static readonly DateTime Jan1st1970 = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);
        private int requestPerMinute;
        private int request;
        private long currentTime;

        public ThrottlingMiddleware(int requestPerMinute)
        {
            this.requestPerMinute = requestPerMinute;
            currentTime = CurrentTimeMillis();
        }

        /**
         * EN: Please, not that checkNext() call can be inserted both in the
         * beginning of this method and in the end.
         * 
         * This gives much more flexibility than a simple loop over all middleware
         * objects. For instance, an element of a chain can change the order of
         * checks by running its check after all other checks.
         * 
         * RU: Обратите внимание, вызов checkNext() можно вставить как в начале
         * этого метода, так и в середине или в конце.
         * 
         * Это даёт еще один уровень гибкости по сравнению с проверками в цикле.
         * Например, элемент цепи может пропустить все остальные проверки вперёд и
         * запустить свою проверку в конце.
         */
        public override bool Check(String email, String password)
        {
            if (CurrentTimeMillis() > currentTime + 60000) {
                request = 0;
                currentTime = CurrentTimeMillis();
            }

            request++;

            if (request > requestPerMinute)
            {
                Console.WriteLine("Request limit exceeded!");
                Thread.CurrentThread.Abort();
            }
            return CheckNext(email, password);
        }

        public static long CurrentTimeMillis()
        {
            return (long)(DateTime.UtcNow - Jan1st1970).TotalMilliseconds;
        }
    }
}
