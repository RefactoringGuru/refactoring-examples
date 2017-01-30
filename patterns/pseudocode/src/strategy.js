// EN: Common interface for all strategies.
// 
// RU: Общий интерфейс всех стратегий.
interface Strategy is
    method algorithm(a, b)

// EN: Each concrete strategy provides unique implementation.
// 
// RU: Каждая конкретная стратегия реализует общий интерфейс своим способом.
class ConcreteStrategyAdd implements Strategy is
    method algorithm(a, b) is
        return a + b

class ConcreteStrategySubtract implements Strategy is
    method algorithm(a, b) is
        return a - b

class ConcreteStrategyMultiply implements Strategy is
    method algorithm(a, b) is
        return a * b

// EN: Context (as a client) always works with strategies through a common
// interface. It does not know or care which strategy is currently active.
// 
// RU: Контекст (как клиент) всегда работает со стратегиями через общий
// интерфейс. Он не знает какая именно стратегия ему подана.
class Context is
    private strategy: Strategy

    method setStrategy(Strategy strategy) is
        this.strategy = strategy

    method executeStrategy(int a, int b) is
        return strategy.execute(a, b)


// EN: The concrete strategy is picked on a higher level (for example, by
// application config) and passed to the client object. At any time, the
// strategy object can be replaced by a different strategy.
// 
// RU: Конкретная стратегия конфигурируется на более высоком уровне, например,
// конфигуратором всего приложения. Готовый объект-стратегия подаётся в
// клиентский объект. Он может быть заменён другой стратегией в любой момент
// на лету.
class ExampleApplication is
    method main() is
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

        result = context.executeStrategy(First number, Second number);
