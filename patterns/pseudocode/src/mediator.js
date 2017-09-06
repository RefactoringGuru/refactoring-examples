// EN: Common mediator interface.
// 
// RU: Общий интерфейс посредников.
interface Mediator is
    method notify(type: string, sender: Component)


// EN: Concrete mediator. All chaotic communications between concrete components
// have been extracted to the mediator. Now components only talk with the
// mediator, which knows who has to handle a request.
// 
// RU: Конкретный посредник. Все связи между конкретными компонентами переехали
// в код посредника. Он получает извещения от своих компонентов и знает как на
// них реагировать.
class AuthenticationDialog implements Mediator is
    private field title: string
    private field loginOrRegister: Checkbox
    private field loginUsername, loginPassword: Textbox
    private field registrationUsername, registrationPassword, registrationEmail: Textbox
    private field ok, cancel: Button

    constructor AuthenticationDialog() is
        Create all component objects.
        Pass "this" to their constructor to register itself as a mediator.

    method notify(type, sender) is
        if (type == "check" and sender == loginOrRegister)
            Show login fields, hide registration fields or vise versa.
            if (loginOrRegister.checked)
                title = "Log in"
            else
                title = "Register"
        if (type == "click" and sender == ok)
            if (loginOrRegister.checked)
                Try to find user using login credentials.
                if (!found)
                    Show errors over login fields.
            else
                Create account using registration fields.
                Log user in.
        // EN: ...
        // 
        // RU: ...


// EN: Component classes communicate with mediator using common mediator
// interface. Thanks to that, you can use the same components with
// different mediators.
// 
// RU: Классы компонентов общаются с посредниками через их общий интерфейс.
// Благодаря этому, одни и те же компоненты можно использовать в
// разных посредниках.
class Component is
    field parent: Mediator

    constructor Component(parent) is
        this.parent = parent

    method click() is
        parent.notify("click", this)

    method keypress() is
        parent.notify("keypress", this)

// EN: Concrete components don't talk with each other. They have only one
// communication channel–sending requests to the mediator.
// 
// RU: Конкретные компоненты никак не связаны между собой. У них есть только
// один канал общения – через отправку уведомлений посреднику.
class Button extends Component is
    // EN: ...
    // 
    // RU: ...

class Textbox extends Component is
    // EN: ...
    // 
    // RU: ...

class Checkbox extends Component is
    method check() is
        parent.notify("check", this)
    // EN: ...
    // 
    // RU: ...
