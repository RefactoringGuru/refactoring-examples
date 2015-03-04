replace-type-code-with-class:csharp

###

1.ru. Создайте новый класс и дайте ему понятное название, соответствующее предназначению закодированного типа.
1.en. Create a new class and give it a new name that corresponds to the purpose of the coded type. Here we will call it <i>type class</i>.
1.uk. Створіть новий клас і дайте йому зрозумілу назву, що відповідає призначенню закодованого типу. Ми називатимемо його <i>клас типу</i>.

2.ru. ***В <i>класс типа</i> скопируйте поле, содержащее кодирование типа, и сделайте его приватным. Кроме того, создайте свойство для чтения этого поля. Устанавливаться значение в это поле будет только из конструктора.
2.en. Copy the field containing type code to the <i>type class</i> and make it private. Then create a read-only property for the field. A value will be set for this field only from the constructor.
2.uk. У <i>клас типу</i> скопіюйте поле, що містить кодування типу, і зробіть його приватним. Крім того, створіть властивість для читання цього поля. Встановлюватися значення в це поле буде тільки з конструктора.

3.ru. Для каждого значения закодированного типа, создайте статическое свойство в <i>классе типа</i>.
3.en. For each value of the coded type, create a static property in the <i>type class</I>.
3.uk. Для кожного значення закодованого типу, створіть статичне властивість в <i>класі типу</i>.

4.ru. ***В исходном классе, замените тип закодированного поля на <i>класс типа</i>. Создавайте новый объект этого типа в конструкторе, а также в сеттере поля. Измените геттер поля так, чтобы он обращался к соответствующему свойству <i>класса типа</i>.
4.en. In the original class, replace the type of the coded field with <i>type class</i>. Create a new object of this type in the constructor as well as in the field setter. Change the field getter so that it access the <i>type class</i> property.
4.uk. У початковому класі, замініть тип закодованого поля на <i>клас типу</i>. Створіть новий об'єкт цього типу в конструкторі, а також в сетерові поля. Змініть геттер поля так, щоб він звертався до відповідного властивості <i>класу типу</i>.

5.ru. Замените любые упоминания значений закодированного типа вызовами соответствующих статических методов <i>класса типа</i>.
5.en. Replace any mentions of values of the coded type with calls of the relevant <i>type class</i> static methods.
5.uk. Замініть будь-які згадки значень закодованого типу викликами відповідних статичних методів <i>класу типу</i>.

6.ru. ***Удалите константы закодированного типа из исходного класса и закройте конструктор <i>класса типа</i>.
6.en. Remove the coded type constants from the original class and make the <i>type class</i> constructor private.
6.uk. Видаліть константи закодованого типу з початкового класу і закрийте конструктор <i>класу типу</i>.



###

```
public class Person
{
  public const int O = 0,
                   A = 1,
                   B = 2,
                   AB = 3;

  private int bloodGroupCode;

  public int BloodGroupCode
  {
    get{ return bloodGroupCode; }
    set{ bloodGroupCode = value; }
  }

  public Person(int code)
  {
    this.bloodGroupCode = code;
  }
}

// Somewhere in client code.
Person parent = new Person(Person.O);
if (parent.BloodGroupCode == Person.AB)
{
  // ...
}
child.BloodGroupCode = parent.BloodGroupCode;
```

###

```
public class Person
{
  private BloodGroup bloodGroup;

  public BloodGroup BloodGroup
  {
    get{ return bloodGroup; }
    set{ bloodGroup = value; }
  }

  public Person(BloodGroup bloodGroup)
  {
    this.bloodGroup = bloodGroup;
  }
}

public class BloodGroup
{
  private int code;

  public int Code
  {
    get{ return code; }
  }
  public static BloodGroup O
  {
    get{ return new BloodGroup(0); }
  }
  public static BloodGroup A
  {
    get{ return new BloodGroup(1); }
  }
  public static BloodGroup B
  {
    get{ return new BloodGroup(2); }
  }
  public static BloodGroup AB
  {
    get{ return new BloodGroup(3); }
  }

  private BloodGroup(int code)
  {
    this.code = code;
  }
}

// Somewhere in client code.
Person parent = new Person(BloodGroup.O);
if (parent.BloodGroup == BloodGroup.AB)
{
  // ...
}
child.BloodGroup = parent.BloodGroup;
```

###

Set step 1

#|ru| Рассмотрим рефакторинг <i>Замена кодирования типа классом</i> на примере класса человека, в котором есть поля группы крови.
#|en| Let's look at <i>Replace Type Code With Class</i>, using the example of a person class that contains blood type fields.
#|uk| Розглянемо рефакторинг <i>Заміна кодування типу класом<i> на прикладі класу людини, в якому є поля групи крові.

Select:
```
  public const int |||O = 0|||,
                   |||A = 1|||,
                   |||B = 2|||,
                   |||AB = 3|||;
```

#|ru| Группы крови закодированы в четырёх константах этого класса.
#|en| Blood types are coded as four constants of this class.
#|uk| Групи крові закодовані в чотирьох константах цього класу.

Go to after "Person"

#|ru| Рефакторинг мы начинаем с того, что создаём новый класс <code>BloodGroup</code>, который будет отвечать за группы крови.
#|en| We start refactoring by creating a new <code>BloodGroup</code> class, which will be responsible for blood types.
#|uk| Рефакторинг ми починаємо з того, що створюємо новий клас <code>BloodGroup</code>, який відповідатиме за групи крові.

Type:
```


public class BloodGroup
{
}
```

Set step 2

#|ru| В этот класс мы помещаем поле группы крови из класса <code>Person</code>, его геттер и конструктор, инициализирующий значение поля.
#|en| We place the blood type field from the <code>Person</code> class, its getter and the constructor, which initialize the field value.
#|uk| У цей клас ми поміщаємо поле групи крові з класу <code>Person</code>, його геттер та конструктор, який ініціалізує значення поля.

Go to the end of "BloodGroup"

Type:
```

  private int code;

  public int Code
  {
    get{ return code; }
  }

  public BloodGroup(int code)
  {
    this.code = code;
  }
```

Set step 3

Go to before "public BloodGroup"

#|ru| ***Теперь создаём статические свойства для каждого значения закодированного типа из оригинального класса. Эти свойства должны возвращать экземпляры класса <code>BloodGroup</code>.
#|en| Now let's create static properties for each of the type code values from the original class. These properties should return instances of the <code>BloodGroup</code> class.
#|uk| Тепер, створюємо статичні властивості для кожного значення закодованого типу з оригінального класу. Ці властивості повинні повертати екземпляри класу <code>BloodGroup</code>.

Print:
```


```

Go to:
```
get{ return code; }
  }
|||
```

Print:
```
  public static BloodGroup O
  {
    get{ return new BloodGroup(0); }
  }
  public static BloodGroup A
  {
    get{ return new BloodGroup(1); }
  }
  public static BloodGroup B
  {
    get{ return new BloodGroup(2); }
  }
  public static BloodGroup AB
  {
    get{ return new BloodGroup(3); }
  }
```


#C|ru| Можем провести компиляцию и тестирование, чтобы убедиться в правильности кода.
#S Всё хорошо, можем продолжать.

#C|en| Let's compile and test our code.
#S All is well, so let's continue.

#C|uk| Можемо провести компіляцію і тестування, щоб переконатися в правильності коду.
#S Все добре, можемо продовжувати.

Set step 4

Select:
```
  private |||int||| bloodGroupCode;
```

#|ru| Заменим в исходном классе тип закодированного поля на <code>BloodGroup</code>.
#|en| In the original class, change the type of the coded field to <code>BloodGroup</code>.
#|uk| Замінимо у вихідному класі тип закодованого поля на <code>BloodGroup</code>.

Type "BloodGroup"

Select "bloodGroupCode" in "public Person"
+Select "return |||bloodGroupCode|||"
+Select "|||bloodGroupCode||| = value"
+Select "private BloodGroup |||bloodGroupCode|||"

#|ru| ***Вместе с тем переименуем и само поле.
#|en| Also rename the field itself.
#|uk| Разом з тим перейменуємо і саме поле.

Type "bloodGroup"

Select "set{ |||bloodGroup = value;||| }"
+ Select "this.bloodGroup = code;"

#|ru| Затем нужно поменять код сеттера и конструктора.
#|en| Change the code of the setter and constructor accordingly.
#|uk| Потім потрібно поміняти код сетера і конструктора.

Select "set{ bloodGroup = |||value|||; }"

Replace "new BloodGroup(value)"

Wait 500ms

Select "this.bloodGroup = |||code|||;"

Replace "new BloodGroup(code)"

Wait 500ms

Go to:
```
return bloodGroup|||;
```

#|ru| ***И наконец изменяем геттер свойства так, чтобы он возвращал свойство класса <code>BloodGroup</code>
#|en| Then change the property getter so that it return the property of the <code>BloodGroup</code> class.
#|uk| І, нарешті, змінюємо геттер властивості так, щоб він повертав властивість класу <code>BloodGroup</code>

Print ".Code"

Set step 5

Select:
```
  public const int |||O = 0|||,
                   |||A = 1|||,
                   |||B = 2|||,
                   |||AB = 3|||;
```

#|ru| ***Настала пора заменить любые упоминания значений закодированного типа вызовами геттеров соответствующих статических свойств <i>класса типа</i>.
#|en| It is now time to replace all type code values with calls to the getters of corresponding static properties of the <i>type class</i>.
#|uk| А тепер саме час замінити будь-які згадки значень закодованого типу викликами геттерів відповідних статичних властивостей <i>класу типу<i>.

#|ru| ***Сначала меняем значения всех констант старого закодированного типа на обращение к соответствующим свойствам класса <code>BloodGroup</code>.
#|en| First replace the values of all constants with access to the corresponding properties of the <code>BloodGroup</code> class.
#|uk| Спочатку замінюємо значення всіх констант старого закодованого типу на звернення до відповідних властивостей класу <code>BloodGroup</code>.

Select "O = |||0|||,"

Replace "BloodGroup.O.Code"

Select "A = |||1|||,"

Replace "BloodGroup.A.Code"

Select "B = |||2|||,"

Replace "BloodGroup.B.Code"

Select "AB = |||3|||;"

Replace "BloodGroup.AB.Code"

Select "const" in "Person"

#|ru|^ ***При этом надо заменить объявленную константу на статическое поле, иначе возникнет ошибка при компиляции.
#|en|^ Thus it is necessary to replace the declared constant to a static field, otherwise you will get an error when compiling.
#|uk|^ При цьому треба замінити оголошену константу на статичне поле, інакше виникне помилка при компіляції.

Replace "static readonly"

Select " ||||||A =" in "Person"
+Select " ||||||B =" in "Person"
+Select " ||||||AB =" in "Person"
Replace "          "

#|ru|^ Сейчас, по сути, все использования констант уже делегируются в методы <code>BloodGroup</code>.
#|en|^ In effect, all uses of constants are now delegated to the methods of <code>BloodGroup</code>.
#|uk|^ Зараз, по суті, все використання констант делегуються в методи <code>BloodGroup</code>.

Select "new Person(|||Person.O|||);"

#|ru| Тем не менее, мы пойдём дальше и избавимся от прямых обращений к константам класса <code>Person</code> в остальном коде, заменяя их вызовами методов класса <code>BloodGroup</code>.
#|en| We will go one step further and get rid of direct references to constants of the <code>Person</code> class in the remaining code. We can use calls to the methods of the <code>BloodGroup</code> class instead.
#|uk| Тим не менш, ми підемо далі і позбудемося прямих звернень до констант класу <code>Person</code> в іншому коді, замінюючи їх викликами методів класу <code>BloodGroup</code>.

Type "BloodGroup.O.Code"

Wait 500ms

Select "parent.BloodGroupCode == |||Person.AB|||"

Wait 500ms

Type "BloodGroup.AB.Code"

#C|ru| После внесения изменений стоит запустить компиляцию и тестирование.
#S Всё работает, отлично!

#C|en| After all the changes, test everything carefully.
#S All working. Excellent!

#C|uk| Після внесення змін, варто запустити компіляцію і тестування.
#S Все працює, супер.

Select:
```
    get{ return bloodGroup.|||Code|||; }
    set{ bloodGroup = new BloodGroup(value); }
  }

  public Person(|||int code|||)
```

#|ru| После всех замен нужно постараться вообще избавиться от использования числовых кодов <code>BloodGroup</code>, и использовать вместо этого объекты. Давайте попробуем сделать это в классе <code>Person</code>.
#|en| In the end, it is better to avoid using any numeric codes for <code>BloodGroup</code> and use objects instead. Let's try to do so in the <code>Person</code> class.
#|uk| Після всіх замін потрібно постаратися взагалі позбутися від використання числових кодів <code>BloodGroup</code> і використовувати замість цього об'єкти. Давайте спробуємо зробити це в класі <code>Person</code>.

Select "public Person(|||int code|||)"

Replace "BloodGroup bloodGroup"

Wait 500ms

Select "new BloodGroup(code)"

Replace "bloodGroup"

Wait 500ms

Select "public |||int||| BloodGroup"

Replace "BloodGroup"

Wait 500ms

Select "new BloodGroup(value)"

Replace "value"

Wait 500ms

Select "return bloodGroup|||.Code|||;"

Wait 500ms

Remove selected

Wait 500ms

Select "BloodGroupCode"

#|ru| ***После замены числовых кодов на объекты имеет смысл переименовать и само свойство.
#|en| After replacing the numeric codes for objects, rename property.
#|uk| Після заміни числових кодів на об'єкти має сенс перейменувати і саме властивість.

Replace "BloodGroup"

Select:
```
Person parent = new Person(BloodGroup.O|||.Code|||);
if (parent.BloodGroup == BloodGroup.AB|||.Code|||)
{
  // ...
}
```

#|ru| ***Вероятно теперь сломается клиентский код, но это просто исправить, избавившись от кодов и там.
#|en| These changes will probably cause the client code to break, but this can be fixed by simply getting rid of the codes there as well.
#|uk| Ймовірно тепер зламається клієнтський код, але це просто виправити, позбувшись кодів і там.

Remove selected

Set step 6

Select:
```
  public static readonly int O = BloodGroup.O.Code,
                             A = BloodGroup.A.Code,
                             B = BloodGroup.B.Code,
                             AB = BloodGroup.AB.Code;


```

#|ru| ***Можно удалить неиспользуемые константы из класса <code>Person</code>.
#|en| You can remove unused constants from the <code>Person</code> class.
#|uk| Можна видалити невикористовувані константи з класу <code>Person</code>.

Remove selected

Select "|||public||| BloodGroup" in "BloodGroup"

#|ru| ***И напоследок следует закрыть конструктор класса <code>BloodGroup</code> от доступа извне.
#|en| And finally, you should make the <code>BloodGroup</code> constructor private.
#|uk| І наостанок слід закрити конструктор класу <code>BloodGroup</code> від доступу ззовні.

Replace "private"

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.