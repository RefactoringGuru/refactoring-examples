using System;

namespace RefactoringGuru.ChainOfResponsibility.Example.Middleware
{
    /**
     * EN: Base middleware class.
     * 
     * RU: Базовый класс цепочки.
     */
    abstract class Middleware
    {
        private Middleware next;

        /**
         * EN: Builds chains of middleware objects.
         * 
         * RU: Помогает строить цепь из объектов-проверок.
         */
        public Middleware LinkWith(Middleware next)
        {
            this.next = next;
            return next;
        }

        /**
         * EN: Subclasses will implement this method with concrete checks.
         * 
         * RU: Подклассы реализуют в этом методе конкретные проверки.
         */
        public abstract bool Check(String email, String password);

        /**
         * EN: Runs check on the next object in chain or ends traversing if we're in
         * last object in chain.
         * 
         * RU: Запускает проверку в следующем объекте или завершает проверку, если
         * мы в последнем элементе цепи.
         */
        protected bool CheckNext(String email, String password)
        {
            if (next == null)
            {
                return true;
            }
            return next.Check(email, password);
        }
    }
}
