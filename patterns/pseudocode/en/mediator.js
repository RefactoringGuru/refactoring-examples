// Интерфейс посредника.
interface Mediator is
    method notify(type: string, sender: Component)


// Конкретный посредник. Все связи между конкретными компонентами переехали в
// код посредника. Он получает извещения от своих компонентов и знает как на них
// реагировать.
class AuthenticationDialog implements Mediator is
    field title: string
    field loginOrRegister: Checkbox
    field loginUsername, loginPassword: Textbox
    field registrationUsername, registrationPassword, registrationEmail: Textbox
    field ok, cancel: Button

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
        // ...


// Классы компонентов общаются с посредником через общий интерфейс.
// Благодаря этому, одни и те же компоненты можно использовать в разных
// посредниках.
class Component is
    field parent: Mediator

    constructor Component(parent) is
        this.parent = parent

    method click() is
        parent.notify("click", this)

    method keypress() is
        parent.notify("keypress", this)

// Конкретные компоненты никак не связаны между собой. У них есть только один
// канал общения – через отправку уведомлений посреднику.
class Button extends Component is
    // ...

class Textbox extends Component is
    // ...

class Checkbox extends Component is
    method check() is
        parent.notify("check", this)
    // ...
