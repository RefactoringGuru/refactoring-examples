// Интерфейс компонентов.
interface DataSource is
    method writeData(data)
    method readData():data

// Один из конкретных компонент, реализует базовую функциональность.
class FileDataSource implements DataSource is
    constructor FileDataSource(filename)
    method writeData(data) is
        Write data to file.
    method readData():data is
        Read data from file.

// Родитель всех Декораторов содержит код обёртывания.
class DataSourceDecorator implements DataSource is
    field wrappee: DataSource
    constructor DataEncyptionDecorator(source: DataSource) is
        wrappee = source
    method writeData(data) is
        wrappee.writeData(data)
    method readData():data is
        return wrappee.readData()

// Конкретные Декораторы расширяют базовое поведение компонента.
class EncyptionDecorator extends DataSourceDecorator is
    method writeData(data) is
        Encrypt passed data.
        Pass the compressed data to wrappee's writeData() method.
    method readData():data is
        Get the data from wrappee's readData() method.
        Decrypt and return that data.

// Декорировать можно не только базовые компоненты, но и
// уже обёрнутые объекты.
class CompressionDecorator extends DataSourceDecorator is
    method writeData(data) is
        Compress passed data
        Pass the compressed data to wrappee's writeData() method.
    method readData():data is
        Get the data from wrappee's readData() method.
        Uncompress and return that data.

// Вариант 1. Простой пример сборки и использования декораторов.
class Application is
    method dumbUsageExample() is
        source = new FileDataSource('somefile.dat')
        source.writeData(salaryRecords)
        // в файл записаны чистые данные

        source = new CompressionDecorator(source)
        source.writeData(salaryRecords)
        // файл сжат

        source = new EncyptionDecorator(source)
        source.writeData(salaryRecords)
        // файл сжат и зашифрован

// Вариант 2. Клиентский код, использующий внешний источник данных.
// Класс SalaryManager ничего не знает о том как именно будут считаны и
// записаны данные. Он получает уже готовый источник данных.
class SalaryManager is
    field source: DataSource
    constructor SalaryManager(source: DataSource)
    method load()
        return source.readData()
    method save()
        source.writeData(salaryRecords)
    // ...Остальные полезные методы...

// Приложение может по-разному собирать декорируемые объекты, в
// зависимости от условий использования.
class ApplicationConfigurator is
    method configurationExample() is
        source = new FileDataSource('salary.dat');
        if (enabledEncryption)
            source = new EncyptionDecorator(source)
        if (enabledCompression)
            source = new CompressionDecorator(source)

        logger = new SalaryLogger(source)
        salary = logger.load();
    // ...Остальной код приложения
