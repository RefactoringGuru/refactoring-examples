replace-method-with-method-object:java

###

1. Создайте новый класс. Дайте ему название, основываясь на предназначении метода, который рефакторите.

2. В новом классе создайте приватное поле для хранения ссылки на экземпляр класса, в котором раньше находился метод.

3. Кроме того, создайте по приватному полю для каждой локальной переменной и параметра метода.

4. Создайте конструктор, который принимает все параметры исходного метода и инициализирует соответствующие приватные поля.

5. Объявите основной метод, и скопируйте в него код оригинального метода, заменив локальные переменные приватным полями.

6. Замените тело оригинального метода в исходном классе созданием объекта-метода и вызовом его основного метода.



###

```
class Account {
  // ...
  int gamma(int inputVal, int quantity, int yearToDate) {
    int importantValue1 = (inputVal * quantity) + delta();
    int importantValue2 = (inputVal * yearToDate) + 100;
    if ((yearToDate - importantValue1) > 100) {
      importantValue2 -= 20;
    }
    int importantValue3 = importantValue2 * 7;
    // and so on...
    return importantValue3 - 2 * importantValue1;
  }
  // ...
}
```

###

```
class Account {
  // ...
  int gamma(int inputVal, int quantity, int yearToDate) {
    return new Gamma(this, inputVal, quantity, yearToDate).compute();
  }
  // ...
}

class Gamma {
  private final Account account;
  private int importantValue1;
  private int importantValue2;
  private int importantValue3;
  private int inputVal;
  private int quantity;
  private int yearToDate;
  public Gamma(Account source, int inputValArg, int quantityArg, int yearToDateArg) {
    this.account = source;
    inputVal = inputValArg;
    quantity = quantityArg;
    yearToDate = yearToDateArg;
  }
  public int compute() {
    int importantValue1 = (inputVal * quantity) + account.delta();
    int importantValue2 = (inputVal * yearToDate) + 100;
    importantThing();
    int importantValue3 = importantValue2 * 7;
    // and so on...
    return importantValue3 - 2 * importantValue1;
  }
  void importantThing() {
    if ((yearToDate - importantValue1) > 100) {
      importantValue2 -= 20;
    }
  }
}
```

###

Set step 1

# Для хорошего примера потребовалась бы целая глава, поэтому я продемонстрирую этот рефакторинг на методе, которому он не нужен (не задавайте вопросов о логике этого метода – она была придумана по ходу дела)

Select name of "gamma"

# Мы видим, что в одном из методов класса есть множество запутанных вычислений и хитросплетение локальных переменных. Всё это затрудняет дальнейший рефакторинг класса.

# Давайте преобразуем этот метод в отдельный класс так, чтобы локальные переменные стали полями этого класса. После чего можно будет переместить данный метод в новый класс.

Go to the end of file

# Для начала создадим новый класс.

Print:
```


class Gamma {
}
```

Set step 2

# Первым делом создадим в классе <code>Gamma</code> неизменяемое поле для хранения исходного объекта.

Go to the end of "Gamma"

Print:
```

  private final Account account;
```

Set step 3

Go to the end of "Gamma"

# А также перенесем все переменные из метода, который мы хотим отделить...


Print:
```

  private int importantValue1;
  private int importantValue2;
  private int importantValue3;
```

Go to the end of "Gamma"

# ...и создадим поля для каждого из параметров метода.

Print:
```

  private int inputVal;
  private int quantity;
  private int yearToDate;
```

Set step 4

Go to the end of "Gamma"

# Создадим конструктор, который будет принимать параметры метода и сохранять их в полях класса для дальнейшего использования.

Print:
```

  public Gamma(Account source, int inputValArg, int quantityArg, int yearToDateArg) {
    this.account = source;
    inputVal = inputValArg;
    quantity = quantityArg;
    yearToDate = yearToDateArg;
  }
```

#C Запустим компиляцию, чтобы проверить код на наличие ошибок.

#S Все отлично, пока что ничего не успело сломаться.

Set step 5

Select whole "gamma" in "Account"

# Теперь можно переместить исходный метод.

Go to the end of "Gamma"

Print:
```

  public int compute() {
    int importantValue1 = (inputVal * quantity) + delta();
    int importantValue2 = (inputVal * yearToDate) + 100;
    if ((yearToDate - importantValue1) > 100) {
      importantValue2 -= 20;
    }
    int importantValue3 = importantValue2 * 7;
    // and so on...
    return importantValue3 - 2 * importantValue1;
  }
```

Select "delta()" in "compute"

# При этом следует модифицировать любые вызовы методов <code>Account</code> так, чтобы они выполнялись через поле <code>account</code>.

Print "account.delta()"

Set step 6

Select body of "int gamma"

# После этого осталось только заменить тело старого метода на вызов метода в новом классе.

Print:
```
    return new Gamma(this, inputVal, quantity, yearToDate).compute();
```

#C Запустим компиляцию и тесты, чтобы проверить код на наличие ошибок.

#S Все работает отлично!

Select:
```
    if ((yearToDate - importantValue1) > 100) {
      importantValue2 -= 20;
    }
```

# Преимущество это рефакторинга в том, что теперь можно легко применить <a href="/extract-method">извлечение метода</a> к методу <code>compute()</code>, не беспокоясь о передаче аргументов.

Go to the end of "Gamma"

Print:
```

  void importantThing() {
    if ((yearToDate - importantValue1) > 100) {
      importantValue2 -= 20;
    }
  }
```

Wait 500ms

Select in "compute":
```
    if ((yearToDate - importantValue1) > 100) {
      importantValue2 -= 20;
    }
```

Replace "    importantThing();"

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.