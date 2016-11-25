// EN: Common interface for all components.
// 
// RU: Общий интерфейс компонентов.
interface DataSource is
    method writeData(data)
    method readData():data

// EN: One of the concrete components can act as a base layer.
// 
// RU: Один из конкретных компонент, реализует базовую функциональность.
class FileDataSource implements DataSource is
    constructor FileDataSource(filename) { ... }

    method writeData(data) is
        Write data to file.

    method readData():data is
        Read data from file.

// EN: All other concrete components may act as wrappers.
// 
// RU: Родитель всех Декораторов содержит код обёртывания.
class DataSourceDecorator implements DataSource is
    field wrappee: DataSource

    constructor DataEncyptionDecorator(source: DataSource) is
        wrappee = source

    method writeData(data) is
        wrappee.writeData(data)

    method readData():data is
        return wrappee.readData()

// EN: Concrete Decorators extend the functionality of a component they wrap.
// 
// RU: Конкретные Декораторы расширяют базовое поведение компонента.
class EncyptionDecorator extends DataSourceDecorator is
    method writeData(data) is
        Encrypt passed data.
        Pass the compressed data to wrappee's writeData() method.

    method readData():data is
        Get the data from wrappee's readData() method.
        Decrypt and return that data.

// EN: You can wrap objects in several layers of decorators.
// 
// RU: Декорировать можно не только базовые компоненты, но и уже
// обёрнутые объекты.
class CompressionDecorator extends DataSourceDecorator is
    method writeData(data) is
        Compress passed data
        Pass the compressed data to wrappee's writeData() method.

    method readData():data is
        Get the data from wrappee's readData() method.
        Uncompress and return that data.


// EN: Option 1. A simple example of decorator assembly.
// 
// RU: Вариант 1. Простой пример сборки и использования декораторов.
class Application is
    method dumbUsageExample() is
        source = new FileDataSource('somefile.dat')
        source.writeData(salaryRecords)
        // EN: a file with plain data
        // 
        // RU: в файл записаны чистые данные

        source = new CompressionDecorator(source)
        source.writeData(salaryRecords)
        // EN: compressed file
        // 
        // RU: файл сжат

        source = new EncyptionDecorator(source)
        source.writeData(salaryRecords)
        // EN: compressed and encrypted file
        // 
        // RU: файл сжат и зашифрован



// EN: Option 2. Client code, which uses an external data source. SalaryManager
// neither knows not cares about data storage specifics. It receives already
// configured data source.
// 
// RU: Вариант 2. Клиентский код, использующий внешний источник данных. Класс
// SalaryManager ничего не знает о том как именно будут считаны и записаны
// данные. Он получает уже готовый источник данных.
class SalaryManager is
    field source: DataSource

    constructor SalaryManager(source: DataSource) { ... }

    method load() is
        return source.readData()

    method save() is
        source.writeData(salaryRecords)
    // EN: ...Other useful methods...
    // 
    // RU: ...Остальные полезные методы...


// EN: Application can assemble objects with a different set of functionality
// using the same decorators at run time, depending on the configuration
// or environment.
// 
// RU: Приложение может по-разному собирать декорируемые объекты, в зависимости
// от условий использования.
class ApplicationConfigurator is
    method configurationExample() is
        source = new FileDataSource("salary.dat");
        if (enabledEncryption)
            source = new EncyptionDecorator(source)
        if (enabledCompression)
            source = new CompressionDecorator(source)

        logger = new SalaryLogger(source)
        salary = logger.load();
    // EN: ...Rest of an application code.
    // 
    // RU: ...Остальной код приложения
