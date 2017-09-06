class Database is
    private field instance: Database

    static method getInstance() is
        if (this.instance == null) then
            acquireThreadLock() and then
                // EN: Ensure that instance has not yet been initialized by
                // other thread while this one was waiting for the lock release.
                // 
                // RU: На всякий случай ещё раз проверим не был ли объект создан
                // другим потоком, пока текущий ждал освобождения блокировки.
                if (this.instance == null) then
                    this.instance = new Database()
        return this.instance

    private constructor Database() is
        // EN: Some initialization code, such as the actual connection to a
        // database server.
        // ...
        // 
        // RU: Здесь может жить код инициализации подключения к серверу
        // баз данных.
        // ...

    public method query(sql) is
        // EN: All database queries of an app will go through this methods.
        // Therefore, you can place here a throttling or caching logic.
        // ...
        // 
        // RU: Все запросы к базе данных будут проходить через этот метод.
        // Поэтому имеет смысл поместить сюда какую-то логику кеширования.
        // ...

class Application is
    method main() is
        Database foo = Database.getInstance()
        foo.query("SELECT ...")
        // ...
        Database bar = Database.getInstance()
        bar.query("SELECT ...")
        // EN: bar would contain the same object as foo.
        // 
        // RU: В переменной bar содержится тот же объект, что и в foo.
