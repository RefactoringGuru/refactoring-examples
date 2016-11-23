class Singleton is
    private field instance: Singleton

    private method Singleton() is
        Some initialization code.

    static method getInstance() is
        if (this.instance == null) then
            acquireThreadLock() and then
                this.instance = new Singleton()
        return this.instance

class Application is
    method main() is
        Singleton foo = Singleton.getInstance()
        Singleton bar = Singleton.getInstance()
        // В переменной foo содержится тот же объект, что и в bar
