replace-subclass-with-fields:csharp

###

1.ru. Примените к подклассам <a href="/replace-constructor-with-factory-method">замену конструктора фабричным методом</a>.
1.en. Apply <a href="/replace-constructor-with-factory-method">Replace Constructor with Factory Method</a> to the subclasses.
1.uk. Застосуйте до підкласів <a href="/replace-constructor-with-factory-method">заміну конструктора фабричним методом</a>.

2.ru. Замените вызовы конструкторов подклассов вызовами фабричного метода базового класса.
2.en. Replace subclass constructor calls with base class factory method calls.
2.uk. Якщо якийсь код посилається на підкласи, заміните його використанням базового класу.

3.ru. Объявите в базовом классе поля для хранения значений каждого из свойств подклассов, возвращающих константные значения.
3.en. In the base class, declare fields for storing the values of each of the subclass properties that return constant values.
3.uk. Оголосіть в базовому класі поля для зберігання значень кожного з властивостей підкласів, що повертають константні значення.

4.ru. Создайте в базовом классе защищённый конструктор для инициализации новых полей.
4.en. Create a protected base class constructor for initializing the new fields.
4.uk. Створіть у базовому класі захищений конструктор для ініціалізації нових полів.

5.ru. Создайте или модифицируйте имеющиеся конструкторы подклассов, чтобы они вызывали новый конструктор базового класса.
5.en. Create or modify the existing subclass constructors so that they call the new base class constructor.
5.uk. Створіть або змінюйте наявні конструктори підкласів, щоб вони викликали новий конструктор базового класу.

6.ru. Реализуйте каждое свойство в родительском классе так, чтобы оно возвращало значение соответствующего поля, а затем удалите дублирующее свойство из подкласса.
6.en. Implement each property in the parent class so that it returns the value of the corresponding field. Then remove the duplicate property from the subclass.
6.uk. Реалізуйте кожне властивість у батьківському класі так, щоб воно повертало значення відповідного поля, а потім видалите дублюючі властивість з підкласу.

7.ru. Если конструктор подкласса имеет какую-то дополнительную функциональность,  примените <a href="/inline-method">встраивание метода</a> для встраивания его конструктора в фабричный метод родительского класса.
7.en. If the subclass constructor has additional functionality, use <a href="/inline-method">Inline Method</a> to incorporate the constructor into the parent class factory method.
7.uk. Якщо конструктор підкласу має якусь додаткову функціональність, застосуйте <a href="/inline-method">вбудовування методу</a> для вбудовування конструктора у фабричний метод батьківського класу.

8.ru. Удалите подкласс.
8.en. Delete the subclass.
8.uk. Видаліть підклас.



###

```
public abstract class Person
{
  public abstract bool IsMale
  { get; }
  public abstract char Code
  { get; }
}

public class Male: Person
{
  public override bool IsMale
  {
    get{ return true; }
  }
  public override char Code
  {
    get{ return 'M'; }
  }
}
public class Female: Person
{
  public override bool IsMale
  {
    get{ return false; }
  }
  public override char Code
  {
    get{ return 'F'; }
  }
}

// Client code
Person kent = new Male();
Console.WriteLine("Person's gender is: " + kent.Code);
```

###

```
public class Person
{
  private bool isMale;
  private char code;

  public bool IsMale
  {
    get{ return isMale; }
  }
  public char Code
  {
    get{ return code; }
  }

  protected Person(bool isMale, char code)
  {
    this.isMale = isMale;
    this.code = code;
  }
  public static Person CreateMale()
  {
    return new Person(true, 'M');
  }
  public static Person CreateFemale()
  {
    return new Person(false, 'F');
  }
}

// Client code
Person kent = Person.CreateMale();
Console.WriteLine("Person's gender is: " + kent.Code);
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

#|ru| Единственное различие между подклассами здесь в том, что в них есть реализации абстрактных свойств, возвращающие жёстко заданные константы. От таких классов лучше избавиться.
#|en| The only difference between the subclasses is that they have implementations of the abstract properties that return hard-coded constants. It is preferable to get rid of such classes.
#|uk| Єдина відмінність між підклассами в данному втпадкому в тому, що в них є реалізації абстрактних властивостей, що повертають жорстко задані константи. Від таких класів краще позбутися.

Go to the end of "Person"

#|ru| Сначала следует применить <a href="/replace-constructor-with-factory-method">Замену конструктора фабричным методом</a>. В данном случае нам нужно создать фабричный метод для каждого подкласса.
#|en| First use <a href="/replace-constructor-with-factory-method">Replace Constructor With Factory Method</a>. In our case, we need the factory method for each subclass.
#|uk| Спочатку слід застосувати <a href="/uk/replace-constructor-with-factory-method">Заміну конструктора фабричним методом</a>. В даному випадку нам потрібен фабричний метод для кожного підкласу.

Print:
```


  public static Person CreateMale()
  {
    return new Male();
  }
  public static Person CreateFemale()
  {
    return new Female();
  }
```

Set step 2

Select "Person kent = |||new Male()|||"

#|ru| После этого следует заменить все вызовы конструкторов подклассов вызовами соответствующих фабричных методов.
#|en| Then replace all calls to subclass constructors with calls to the relevant factory methods.
#|uk| Після цього слід замінити всі виклики конструкторів підкласів викликами відповідних фабричних методів.

Print "Person.CreateMale()"

#|ru| После замены всех этих вызовов в коде не должно остаться упоминаний подклассов.
#|en| After replacing all these calls, the code should not contain any more mentions of the subclasses.
#|uk| Після заміни всіх цих викликів в коді не повинно залишитися згадок про підкласи.

Set step 3

Go to the beginning of "Person"

#|ru| Теперь в родительском классе объявим поля для каждого свойства, возвращавшего константы в подклассах.
#|en| Now, in the parent class, we should declare fields for each property that returns constants in subclasses.
#|uk| Тепер в батьківському класі оголосимо поля для кожної властивості, який повертає константи в підкласах.

Print:
```

  private bool isMale;
  private char code;

```

Wait 500ms

Set step 4

Go to before "CreateMale" in "Person"

#|ru| Добавляем в родительский класс защищённый конструктор.
#|en| Add a protected constructor to the parent class.
#|uk| Додаємо в батьківський клас захищений конструктор.

Print:
```

  protected Person(bool isMale, char code)
  {
    this.isMale = isMale;
    this.code = code;
  }
```

Set step 5

Go to the end of "Male"

#|ru| Добавляем конструкторы, вызывающие этот новый конструктор в подклассах.
#|en| Add constructors that call this new constructor in subclasses.
#|uk| Додаємо конструктори, які викликають цей новий конструктор в підкласах.

Print:
```


  public Male(): base(true, 'M')
  {}
```

Go to the end of "Female"

Print:
```


  public Female(): base(false, 'F')
  {}
```

#C|ru| После этого можно выполнить компиляцию и тестирование.
#S Всё отлично, можем продолжать!

#C|en| Then we can compile and test.
#S Everything is OK! We can keep going.

#C|uk| Після цього можна виконати компіляцію і тестування.
#S Все добре, можна продовжувати.

Set step 6

Select "public abstract bool IsMale"
+Select "public abstract char Code"

#|ru| Поля создаются и инициализируются, но пока не используются. Теперь мы можем ввести их в эксплуатацию, заполнив в родительском классе соответствующие геттеры и удалив дублирующие свойства из подклассов.
#|en| The fields are created and initialized, but are not yet used. Now we can get the fields "in the game" by filling appropriate getters in the parent class and removing duplicate properties of subclasses.
#|uk| Поля створюються та ініціалізуються, але поки не використовуються. Тепер ми можемо ввести їх в експлуатацію, заповнивши в батьківському класі відповідні геттери і видаливши дублюючі властивості з підкласів.

Select:
```
  public abstract bool IsMale
  {||| get; |||}
```

Replace:
```

    get{ return isMale; }
  
```

Select "public |||abstract |||bool IsMale"

Remove selected

Wait 500ms

Select:
```
  public override bool IsMale
  {
    get{ return true; }
  }

```
+Select:
```
  public override bool IsMale
  {
    get{ return false; }
  }

```

Wait 500ms

Remove selected

Wait 500ms

Select:
```
  public abstract char Code
  {||| get; |||}
```

Replace:
```

    get{ return code; }
  
```

Select "public |||abstract |||char Code"

Remove selected

Wait 500ms

Select:
```
  public override char Code
  {
    get{ return 'M'; }
  }


```
+Select:
```
  public override char Code
  {
    get{ return 'F'; }
  }


```

Wait 500ms

Remove selected

Wait 500ms

Set step 7

Select "|||abstract||| class Person"
+ Select "new Male()"
+ Select "new Female()"

#|ru| В итоге все подклассы оказываются пустыми, поэтому мы снимаем пометку abstract с класса Person и с помощью <a href="/ru/inine-method">Встраивания метода</a> встраиваем конструкторы подклассов в родительский класс.
#|en| All subclasses are empty at this point. That allows us to remove the "abstract" keyword from the Person class and use its constructor instead the ones from subclasses (that we could simply remove).
#|uk| У підсумку всі підкласи виявляються порожніми, тому ми знімаємо позначку abstract з класу Person і за допомогою <a href="/uk/inine-method">вбудовування методу</a> вбудовуємо конструктори підкласів в батьківський клас.

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