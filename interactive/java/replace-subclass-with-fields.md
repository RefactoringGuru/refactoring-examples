replace-subclass-with-fields:java

###

1.ru. Примените к подклассам <a href="/replace-constructor-with-factory-method">замену конструктора фабричным методом</a>.
1.en. Apply <a href="/replace-constructor-with-factory-method">Replace Constructor with Factory Method</a> to the subclasses.
1.uk. Застосуйте до підкласів <a href="/replace-constructor-with-factory-method">заміну конструктора фабричним методом</a>.

2.ru. Замените вызовы конструкторов подклассов вызовами фабричного метода суперкласса.
2.en. Replace subclass constructor calls with superclass factory method calls.
2.uk. Якщо якийсь код посилається на підкласи, заміните його використанням суперкласу.

3.ru. Объявите в суперклассе поля для хранения значений каждого из методов подклассов, возвращающих константные значения.
3.en. In the superclass, declare fields for storing the values of each of the subclass methods that return constant values.
3.uk. Оголосіть в суперкласі поля для зберігання значень кожного з методів підкласів, що повертають константні значення.

4.ru. Создайте защищённый конструктор суперкласса для инициализации новых полей.
4.en. Create a protected superclass constructor for initializing the new fields.
4.uk. Створіть захищений конструктор суперкласу для ініціалізації нових полів.

5.ru. Создайте или модифицируйте имеющиеся конструкторы подклассов, чтобы они вызывали новый конструктор суперкласса.
5.en. Create or modify the existing subclass constructors so that they call the new superclass constructor.
5.uk. Створіть або змінюйте наявні конструктори підкласів, щоб вони викликали новий конструктор суперкласу.

6.ru. Реализуйте каждый константный метод в родительском классе так, чтобы он возвращал значение соответствующего поля, а затем удалите метод из подкласса.
6.en. Implement each constant method in the parent class so that it returns the value of the corresponding field. Then remove the method from the subclass.
6.uk. Реалізуйте кожен константний метод у батьківському класі так, щоб він повертав значення відповідного поля, а потім видалите метод з підкласу.

7.ru. Если конструктор подкласса имеет какую-то дополнительную функциональность,  примените <a href="/inline-method">встраивание метода</a> для встраивания его конструктора в фабричный метод суперкласса.
7.en. If the subclass constructor has additional functionality, use <a href="/inline-method">Inline Method</a> to incorporate the constructor into the superclass factory method.
7.uk. Якщо конструктор підкласу має якусь додаткову функціональність, застосуйте <a href="/inline-method">вбудовування методу</a> для вбудовування конструктора у фабричний метод суперкласу.

8.ru. Удалите подкласс.
8.en. Delete the subclass.
8.uk. Видаліть підклас.



###

```
abstract class Person {
  abstract boolean isMale();
  abstract char getCode();
}

class Male extends Person {
  @Override boolean isMale() {
    return true;
  }
  @Override char getCode() {
    return 'M';
  }
}
class Female extends Person {
  @Override boolean isMale() {
    return false;
  }
  @Override char getCode() {
    return 'F';
  }
}

// Client code
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
  char getCode() {
    return code;
  }
}

// Client code
Person kent = Person.createMale();
System.out.print("Person's gender is: " + kent.getCode());
```

###

Set step 1

#|ru| Рассмотрим рефакторинг <i>Замена подкласса полями</i> на примере всё того же класса человека и его подклассов, выделенных по признаку пола.
#|en| Let's look at <i>Replace Subclass With Fields</i>, using the example of the same person class and its gender subclasses.
#|uk| Розглянемо рефакторинг <i>Заміна підкласу полями<i> на прикладі все того ж класу людини та її підкласів, виділених за ознакою статі.

Select "return true"
+ Select "return false"
+ Select "return 'M'"
+ Select "return 'F'"

#|ru| ***Единственное различие между подклассами здесь в том, что в них есть реализации абстрактных методов, возвращающие жёстко заданные константы. От таких классов лучше избавиться.
#|en| The only difference between the subclasses is that they have implementations of the abstract methods that return hard-coded constants. It is preferable to get rid of such classes.
#|uk| Єдина відмінність між підклассами в данному втпадкому в тому, що в них є реалізації абстрактних методів, що повертають жорстко задані константи. Від таких класів краще позбутися.

Go to the beginning of "Person"

#|ru| Сначала следует применить <a href="/replace-constructor-with-factory-method">Замену конструктора фабричным методом</a>. В данном случае нам нужно создать фабричный метод для каждого подкласса.
#|en| First use <a href="/replace-constructor-with-factory-method">Replace Constructor With Factory Method</a>. In our case, we need the factory method for each subclass.
#|uk| Спочатку слід застосувати <a href="/replace-constructor-with-factory-method">Заміну конструктора фабричним методом</a>. В даному випадку нам потрібен фабричний метод для кожного підкласу.

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

#|ru| После этого следует заменить все вызовы конструкторов подклассов вызовами соответствующих фабричных методов.
#|en| Then replace all calls to subclass constructors with calls to the relevant factory methods.
#|uk| Після цього слід замінити всі виклики конструкторів підкласів викликами відповідних фабричних методів.

Print "Person.createMale()"

#|ru| После замены всех этих вызовов в коде не должно остаться упоминаний подклассов.
#|en| After replacing all these calls, the code should not contain any more mentions of the subclasses.
#|uk| Після заміни всіх цих викликів в коді не повинно залишитися згадок про підкласи.

Set step 3

Go to after "createFemale"

#|ru| Теперь в родительском классе объявим поля для каждого метода, возвращавшего константы в подклассах.
#|en| Now, in the parent class, we should declare fields for each method that returns constants in subclasses.
#|uk| Тепер в батьківському класі оголосимо поля для кожного методу, який повертає константи в підкласах.

Print:
```


  private final boolean isMale;
  private final char code;

```

Set step 4

Go to after "createFemale"

#|ru| Добавляем в родительский класс защищённый конструктор.
#|en| Add a protected constructor to the parent class.
#|uk| Додаємо в батьківський клас захищений конструктор.

Print:
```

  protected Person(boolean isMale, char code) {
    this.isMale = isMale;
    this.code = code;
  }
```

Set step 5

Go to the start of "Male"

#|ru| Добавляем конструкторы, вызывающие этот новый конструктор в подклассах.
#|en| Add constructors that call this new constructor in subclasses.
#|uk| Додаємо конструктори, які викликають цей новий конструктор в підкласах.

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

#C|ru| После этого можно выполнить компиляцию и тестирование.
#S Всё отлично, можем продолжать!

#C|en| Then we can compile and test.
#S Everything is OK! We can keep going.

#C|uk| Після цього можна виконати компіляцію і тестування.
#S Все добре, можна продовжувати.

Set step 6

Select "  abstract boolean isMale();"

#|ru| ***Поля создаются и инициализируются, но пока не используются. Теперь мы можем ввести их в эксплуатацию, поместив в родительском классе методы доступа и удалив методы подклассов.
#|en| The fields are created and initialized, but are not yet used. Now we can get the fields "in the game" by placing access methods in the parent class and removing subclass methods.
#|uk| Поля створюються та ініціалізуються, але поки не використовуються. Тепер ми можемо ввести їх в експлуатацію, помістивши в батьківському класі методи доступу і видаливши методи підкласів.

Print:
```
  boolean isMale() {
    return isMale;
  }
```

Wait 500ms

Select:
```
  @Override boolean isMale() {
    return true;
  }

```

Remove selected

Wait 500ms

Select:
```
  @Override boolean isMale() {
    return false;
  }

```

Remove selected

Select "  abstract char getCode();"

Replace:
```
  char getCode() {
    return code;
  }
```

Wait 500ms

Select:
```
  @Override char getCode() {
    return 'M';
  }

```

Remove selected

Wait 500ms

Select:
```
  @Override char getCode() {
    return 'F';
  }

```

Remove selected

Set step 7

Select "|||abstract||| class Person"
+ Select "new Male()"
+ Select "new Female()"

#|ru| ***В итоге все подклассы оказываются пустыми, поэтому мы снимаем пометку abstract с класса Person и с помощью <a href="/inine-method">Встраивания метода</a> встраиваем конструкторы подклассов в родительский класс.
#|en| All subclasses are empty at this point. That allows us to remove the "abstract" keyword from the Person class and use its constructor instead the ones from subclasses (that we could simply remove).
#|uk| У підсумку всі підкласи виявляються порожніми, тому ми знімаємо позначку abstract з класу Person і за допомогою <a href="/inine-method">вбудовування методу</a> вбудовуємо конструктори підкласів в батьківський клас.

Select "|||abstract |||class Person"

Remove selected

Wait 500ms

Select "new Male()"

Replace "new Person(true, 'M')"

Set step 8

Select whole "Male"

#|ru| После этого класс <code>Male</code> можно спокойно удалить.
#|en| The <code>Male</code> class should now be removed.
#|uk| Після цього клас <code>Male</code> можна спокійно видалити.

Remove selected

#C|ru| Проведём компиляцию и тестирование, чтобы убедиться, что никакой другой код не сломался.
#S Всё хорошо, можно проделать ту же операцию с классом <code>Female</code>

#C|en| Compile and test to make sure nothing has been broken by mistake.
#S Looking good! Let's do the same with the <code>Female</code> class.

#C|uk| Проведемо компіляцію і тестування, щоб переконатися, що ніякої іншої код не зламався.
#S Все добре, можна виконати ту ж операцію з класом <code>Female</code.


Select "new Female()"

Replace "new Person(false, 'F')"

Wait 500ms

Select whole "Female"
+Select:
```
|||
|||// Client code
```

Remove selected

#C|ru| Запускаем финальную компиляцию и тестирование.
#S Отлично, все работает!

#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію і тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.