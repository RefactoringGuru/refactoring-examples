// Общий интерфейс всех стратегий.
interface Strategy is
    method algorithm(a, b)

// Каждая стратегия реализует общий интерфейс своим способом.
class ConcreteStrategyAdd implements Strategy is
    method algorithm(a, b) is
        return a + b

class ConcreteStrategySubtract implements Strategy is
    method algorithm(a, b) is
        return a - b

class ConcreteStrategyMultiply implements Strategy is
    method algorithm(a, b) is
        return a * b

// Клиент всегда работает со стратегиями через общий интерфейс. Он не
// знает какая именно стратегия ему подана.
class Context is
    private strategy: Strategy

    method setStrategy(Strategy strategy) is
        this.strategy = strategy;

    method executeStrategy(int a, int b) is
        return strategy.execute(a, b);


// Конкретная стратегия конфигурируется на более высоком уровне, например
// конфигуратором всего приложения. Готовый объект-стратегия подаётся в
// клиентский объект.
class ExampleApplication is
    method main()
        Create context object.

        Read first number
        Read last number
        Read the desired action from user input

        if (action == addition) then
            context.setStrategy(new ConcreteStrategyAdd());

        if (action == subtraction) then
            context.setStrategy(new ConcreteStrategySubtract());

        if (action == multiplication) then
            context.setStrategy(new ConcreteStrategyMultiply());

        result = context.executeStrategy(first number, second number);
