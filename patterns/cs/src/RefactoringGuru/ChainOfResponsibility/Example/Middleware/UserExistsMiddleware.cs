namespace RefactoringGuru.ChainOfResponsibility.Example.Middleware
{
    class UserExistsMiddleware : Middleware
    {

        /**
         * RU: Конкретный элемент цепи обрабатывает запрос по-своему.
         */
        public override bool Check(string email, string password)
        {
            if (!Server.HasEmail(email))
            {
                return false;
            }
            return CheckNext(email, password);
        }
    }
}
