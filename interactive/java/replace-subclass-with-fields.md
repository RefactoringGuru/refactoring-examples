replace-subclass-with-fields:java

###

1. Примените к подклассам <a href="/replace-constructor-with-factory-method">замену конструктора фабричным методом</a>.

2. Замените вызовы конструкторов подклассов вызовами фабричного метода суперкласса.

3. Объявите в суперклассе поля для хранения значений каждого из методов подклассов, возвращающих константные значения.

4. Создайте защищённый конструктор суперкласса для инициализации новых полей.

5. Создайте или модифицируйте имеющиеся конструкторы подклассов, чтобы они вызывали новый конструктор суперкласса.

6. Реализуйте каждый константный метод в родительском классе так, чтобы он возвращал значение соответствующего поля, а затем удалите метод из подкласса.

7. Если конструктор подкласса имеет какую-то дополнительную функциональность,  примените <a href="/ainline-method">встраивание метода</a> для встраивания его конструктора в фабричный метод суперкласса.

8. Удалите подкласс.



###

```
abstract class Person {
  abstract boolean isMale();
  abstract char getCode();
}

class Male extends Person {
  boolean isMale() {
    return true;
  }
  char getCode() {
    return 'M';
  }
}
class Female extends Person {
  boolean isMale() {
    return false;
  }
  char getCode() {
    return 'F';
  }
}

// Клиентский код
Person kent = new Male();
System.out.print("Person's gender is: " + kent.getCode());
```

###

```
class Person {
  static Person createMale() {
    return new Person(true, 'M');
  }
  static Person createFemale() {
    return new Person(false, 'F');
  }
  protected Person(boolean isMale, char code) {
    this.isMale = isMale;
    this.code = code;
  }

  private final boolean isMale;
  private final char code;

  boolean isMale() {
    return isMale;
  }
  boolean getCode() {
    return code;
  }
}


// Клиентский код
Person kent = Person.createMale();
System.out.print("Person's gender is: " + kent.getCode());
```

###

Set step 1

# Рассмотрим рефакторинг <i>Замена подкласса полями</i> на примере всё того же класса человека и его подклассов, выделенных по полу.

Select "return true"
+ Select "return false"
+ Select "return 'M'"
+ Select "return 'F'"

# Единственное различие между подклассами здесь в том, что в них есть реализации абстрактного метода, возвращающие жёстко закодированные константы. От таких классов лучше избавиться.

Go to the beginning of "Person"

# Сначала следует применить <a href="/replace-constructor-with-factory-method">Замену конструктора фабричным методом</a>. В данном случае нам нужен фабричный метод для каждого подкласса.

Print:
```

  static Person createMale() {
    return new Male();
  }
  static Person createFemale() {
    return new Female();
  }
```

Set step 2

Select "Person kent = |||new Male()|||"

# После этого следует заменить все вызовы конструкторов подклассов вызовами соответствующих фабричных методов.

Print "Person.createMale()"

# После замены всех этих вызовов, в коде не должно остаться упоминаний подклассов.

Set step 3

Go to after "createFemale"

# Теперь в родительском классе объявим поля для каждого метода, возвращавшего константы в подклассах.

Print:
```


  private final boolean isMale;
  private final char code;

```

Set step 4

Go to after "createFemale"

# Добавляем в родительский класс защищённый конструктор.

Print:
```

  protected Person(boolean isMale, char code) {
    this.isMale = isMale;
    this.code = code;
  }
```

Set step 5

Go to the start of "Male"

# Добавляем конструкторы, вызывающие этот новый конструктор в подклассах.

Print:
```

  Male() {
    super(true, 'M');
  }
```

Go to the start of "Female"

Print:
```

  Female() {
    super(false, 'F');
  }
```

#C После этого можно выполнить компиляцию и тестирование.

#S Всё отлично, можно продолжать.

Set step 6

Select "  abstract boolean isMale();"

# Поля создаются и инициализируются, но пока они не используются. Теперь мы можем  ввести поля в игру, поместив в родительском классе методы доступа и удалив методы подклассов.

Print:
```
  boolean isMale() {
    return isMale;
  }
```

Wait 500ms

Select whole "isMale" in "Male"

Remove selected

Wait 500ms

Select whole "isMale" in "Female"

Remove selected

Select "  abstract char getCode();"

Print:
```
  boolean getCode() {
    return code;
  }
```

Wait 500ms

Select whole "getCode" in "Male"

Remove selected

Wait 500ms

Select whole "getCode" in "Female"

Remove selected

Set step 7

Select "|||abstract||| class Person"
+ Select "new Male()"
+ Select "new Female()"

# В итоге все подклассы оказываются пустыми, поэтому я снимаю пометку abstract с класса Person и с помощью <a href="/inine-method">Встраивания метода</a> встраиваю конструктор подкласса в родительский класс.

Select "|||abstract |||class Person"

Remove selected

Wait 500ms

Select "new Male()"

Wait 500ms

Print "new Person(true, 'M')"

Set step 8

Select whole "Male"

# После этого класс <code>Male</code> можно спокойно удалить.

Remove selected

#C Проведём компиляцию и тестирование, чтобы убедиться, что никакой другой код не сломался.

#S Всё хорошо, можно проделать ту же операцию с классом <code>Female</code>


Select "new Female()"

Wait 500ms

Print "new Person(false, 'F')"

Wait 500ms

Select whole "Female"

Remove selected

#C Запускаем финальную компиляцию и тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.