using System;

namespace RefactoringGuru.Strategy.Example.Strategies
{
    /**
     * EN: Dummy credit card class.
     * 
     * RU: Очень наивная реализация кредитной карты.
     */
    class CreditCard
    {
        private int amount;
        private String number;
        private String date;
        private String cvv;

        public CreditCard(String number, String date, String cvv)
        {
            this.amount = 100000;
            this.number = number;
            this.date = date;
            this.cvv = cvv;
        }

        public void SetAmount(int amount)
        {
            this.amount = amount;
        }

        public int GetAmount()
        {
            return amount;
        }
    }
}
