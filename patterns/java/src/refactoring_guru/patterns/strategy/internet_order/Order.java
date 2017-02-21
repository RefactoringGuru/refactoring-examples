package refactoring_guru.patterns.strategy.internet_order;

public class Order {
    private PayStrategy strategy;
    private int totalCost = 0;
    private CreditCard creditCard = new CreditCard(10000);
    private boolean isClosed = false;

    public void paymentOrder(String paymentMethod) {
        if (paymentMethod.equals("PayPal")) {
            String ppAccountEmail = "amanda@ya.com";
            String ppAccountUser = "Amanda";
            strategy = new PayByPayPal(ppAccountEmail, ppAccountUser);
            strategy.pay(totalCost);
        }
        if (paymentMethod.equals("CreditCard")) {
            strategy = new PayByCreditCard(creditCard);
            strategy.pay(totalCost);
        }
    }

    public void setTotalCost(int cost) {
        this.totalCost += cost;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
