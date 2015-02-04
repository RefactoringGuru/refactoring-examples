replace-type-code-with-class:java

###

1.ru. Создайте новый класс и дайте ему понятное название, соответствующее предназначению закодированного типа.
1.en. Create a new class and give it a new name that corresponds to the purpose of the coded type. Here we will call it <i>type class</i>.
1.uk. Створіть новий клас і дайте йому зрозумілу назву, що відповідає призначенню закодованого типу. Ми називатимемо його <i>клас типу</i>.

2.ru. В <i>класс типа</i> скопируйте поле, содержащее кодирование типа, и сделайте его приватным. Кроме того, создайте для этого поля геттер. Устанавливаться значение в это поле будет только из конструктора.
2.en. Copy the field containing type code to the <i>type class</i> and make it private. Then create a getter for the field. A value will be set for this field only from the constructor.
2.uk. У <i>клас типу</i> скопіюйте поле, що містить кодування типу, і зробіть його приватним. Крім того, створіть для цього поля геттер. Встановлюватися значення в це поле буде тільки з конструктора.

3.ru. Для каждого значения закодированного типа, создайте статический метод в <i>классе типа</i>.
3.en. For each value of the coded type, create a static method in the <i>type class</I>.
3.uk. Для кожного значення закодованого типу, створіть статичний метод в <i>класі типу</i>.

4.ru. В исходном классе, замените тип закодированного поля на <i>класс типа</i>. Создавайте новый объект этого типа в конструкторе, а также в сеттере поля. Измените геттер поля так, чтобы он вызывал геттер <i>класса типа</i>.
4.en. In the original class, replace the type of the coded field with <i>type class</i>. Create a new object of this type in the constructor as well as in the field setter. Change the field getter so that it calls the <i>type class</i> getter.
4.uk. У початковому класі, замініть тип закодованого поля на <i>клас типу</i>. Створіть новий об'єкт цього типу в конструкторі, а також в сетерові поля. Змініть геттер поля так, щоб він викликав геттер <i>класу типу</i>.

5.ru. Замените любые упоминания значений закодированного типа вызовами соответствующих статических методов <i>класса типа</i>.
5.en. Replace any mentions of values of the coded type with calls of the relevant <i>type class</i> static methods.
5.uk. Замініть будь-які згадки значень закодованого типу викликами відповідних статичних методів <i>класу типу</i>.

6.ru. Удалите константы закодированного типа из исходного класса.
6.en. Remove the coded type constants from the original class.
6.uk. Видаліть константи закодованого типу з початкового класу.



###

```
class Person {
  public static final int О = 0;
  public static final int A = 1;
  public static final int B = 2;
  public static final int AB = 3;

  private int bloodGroup;

  public Person(int code) {
    bloodGroup = code;
  }
  public void setBloodGroup(int code) {
    bloodGroup = code;
  }
  public int getBloodGroup() {
    return bloodGroup;
  }
}

// Somewhere in client code.
Person parent = new Person(Person.O);
if (parent.getBloodGroup() == Person.AB) {
  // ...
}
child.setBloodGroup(parent.getBloodGroup());
```

###

```
class Person {
  private BloodGroup bloodGroup;

  public Person(BloodGroup bloodGroup) {
    bloodGroup = bloodGroup;
  }
  public void setBloodGroup(BloodGroup bloodGroup) {
    bloodGroup = bloodGroup;
  }
  public BloodGroup getBloodGroup() {
    return bloodGroup;
  }
}

class BloodGroup {
  private final int code;

  private BloodGroup(int code) {
    this.code = code;
  }
  public int getCode() {
    return this.code;
  }

  public static BloodGroup O() {
    return new BloodGroup(0);
  }
  public static BloodGroup A() {
    return new BloodGroup(1);
  }
  public static BloodGroup B() {
    return new BloodGroup(2);
  }
  public static BloodGroup AB() {
    return new BloodGroup(3);
  }
}

// Somewhere in client code.
Person parent = new Person(BloodGroup.O());
if (parent.getBloodGroup() == BloodGroup.AB()) {
  // ...
}
child.setBloodGroup(parent.getBloodGroup());
```

###

Set step 1

#|ru| Рассмотрим рефакторинг <i>Замена кодирования типа классом</i> на примере класса человека, в котором есть поля группы крови.
#|en| Let's look at <i>Replace Type Code With Class</i>, using the example of a person class that contains blood type fields.
#|uk| Розглянемо рефакторинг <i>Заміна кодування типу класом<i> на прикладі класу людини, в якому є поля групи крові.

Select:
```
  public static final int |||О = 0|||;
  public static final int |||A = 1|||;
  public static final int |||B = 2|||;
  public static final int |||AB = 3|||;
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


class BloodGroup {
}
```

Set step 2

#|ru| В этот класс мы помещаем поле группы крови из класса <code>Person</code>, его геттер и конструктор, инициализирующий значение поля.
#|en| We place the blood type field from the <code>Person</code> class, its getter and the constructor, which initialize the field value.
#|uk| У цей клас ми поміщаємо поле групи крові з класу <code>Person</code>, його геттер та конструктор, який ініціалізує значення поля.

Go to the end of "BloodGroup"

Type:
```

  private final int code;

  private BloodGroup(int code) {
    this.code = code;
  }
  public int getCode() {
    return this.code;
  }
```

Set step 3

#|ru| Теперь, создаём статические методы для каждого из значений закодированного типа из оригинального класса. Эти методы должны возвращать экземпляры класса <code>BloodGroup</code>.
#|en| Now let's create static methods for each of the type code values from the original class. These methods should return instances of the <code>BloodGroup</code> class.
#|uk| Тепер, створюємо статичні методи для кожного зі значень закодованого типу з оригінального класу. Ці методи повинні повертати екземпляри класу <code>BloodGroup</code>.

Go to the end of "class BloodGroup"

Print:
```


  public static BloodGroup O() {
    return new BloodGroup(0);
  }
  public static BloodGroup A() {
    return new BloodGroup(1);
  }
  public static BloodGroup B() {
    return new BloodGroup(2);
  }
  public static BloodGroup AB() {
    return new BloodGroup(3);
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
  private |||int||| bloodGroup;
```

#|ru| Теперь в исходном классе заменим тип закодированного поля на <code>BloodGroup</code>.
#|en| In the original class, change the type of the coded field to <code>BloodGroup</code>.
#|uk| Тепер у вихідному класі замінимо тип закодованого поля на <code>BloodGroup</code>.


Type:
```
BloodGroup
```


Select:
```
  public Person(int code) {
    bloodGroup = |||code|||;
  }
  public void setBloodGroup(int code) {
    bloodGroup = |||code|||;
  }
```

#|ru| Соответственно нужно поменять код конструктора и сеттера.
#|en| Change the code of the constructor and setter accordingly.
#|uk| Відповідно потрібно поміняти код конструктора і сетера.

Type:
```
new BloodGroup(code)
```

Go to:
```
return bloodGroup|||;
```

#|ru| Затем изменяем геттер поля так, чтобы он вызывал геттер класса <code>BloodGroup</code>
#|en| Then change the field getter so that it calls the getter of the <code>BloodGroup</code> class.
#|uk| Потім змінюємо геттер поля так, щоб він викликав геттер класу <code>BloodGroup</code>

Print ".getCode()"

Set step 5

Select:
```
  public static final int |||О = 0|||;
  public static final int |||A = 1|||;
  public static final int |||B = 2|||;
  public static final int |||AB = 3|||;
```

#|ru| Настала пора заменить любые упоминания значений закодированного типа вызовами соответствующих статических методов <i>класса типа</i>.
#|en| It is now time to replace all type code values with calls to the corresponding static methods of the <i>type class</i>.
#|uk| А тепер саме час замінити будь-які згадки значень закодованого типу викликами відповідних статичних методів <i>класу типу<i>.

#|ru| Сначала заменяем значения всех констант старого закодированного типа вызовами соответствующих методов класса <code>BloodGroup</code>.
#|en| First replace the values of all constants with calls to the corresponding methods of the <code>BloodGroup</code> class.
#|uk| Спочатку замінюємо значення всіх констант старого закодованого типу викликами відповідних методів класу <code>BloodGroup</code>.

Select "public static final int О = |||0|||;"

Replace "BloodGroup.O().getCode()"

Select "public static final int A = |||1|||;"

Replace "BloodGroup.A().getCode()"

Select "public static final int B = |||2|||;"

Replace "BloodGroup.B().getCode()"

Select "public static final int AB = |||3|||;"

Replace "BloodGroup.AB().getCode()"

#|ru|^ Сейчас, по сути, все использования констант делегируются в методы <code>BlodGroup</code>.
#|en|^ In effect, all uses of constants are now delegated to the methods of <code>BloodGroup</code>.
#|uk|^ Зараз, по суті, все використання констант делегуються в методи <code>BlodGroup</code>.

Select "new Person(|||Person.O|||);"

#|ru| Тем не менее, мы пойдём дальше и избавимся от прямых обращений к константам класса <code>Person</code> в остальном коде, заменяя их вызовами методов класса <code>BloodGroup</code>.
#|en| We will go one step further and get rid of direct references to constants of the <code>Person</code> class in the remaining code. We can use calls to the methods of the <code>BloodGroup</code> class instead.
#|uk| Тим не менш, ми підемо далі і позбудемося прямих звернень до констант класу <code>Person</code> в іншому коді, замінюючи їх викликами методів класу <code>BloodGroup</code>.

Type "BloodGroup.O().getCode()"

Wait 500ms

Select "parent.getBloodGroup() == |||Person.AB|||"

Wait 500ms

Type "BloodGroup.AB().getCode()"

#C|ru| После всех замен, стоит запустить компиляцию и тестирование.
#S Всё работает, отлично!

#C|en| After all the changes, test everything carefully.
#S All working. Excellent!

#C|uk| Після всіх замін, варто запустити компіляцію і тестування.
#S Все працює, супер.

Select:
```
  public Person(|||int code|||) {
    bloodGroup = new BloodGroup(code);
  }
  public void setBloodGroup(|||int code|||) {
    bloodGroup = new BloodGroup(code);
  }
  public |||int||| getBloodGroup() {
    return bloodGroup|||.getCode()|||;
  }
```

#|ru| После всех замен нужно постараться вообще избавиться от использования числовых кодов <code>BloodGroup</code> и использовать вместо этого объекты. Давайте попробуем сделать это в классе <code>Person</code>.
#|en| In the end, it is better to avoid using any numeric codes for <code>BloodGroup</code> and use objects instead. Let's try to do so in the <code>Person</code> class.
#|uk| Після всіх замін потрібно постаратися взагалі позбутися від використання числових кодів <code>BloodGroup</code> і використовувати замість цього об'єкти. Давайте спробуємо зробити це в класі <code>Person</code>.

Select:
```
  public Person(|||int code|||) {
    bloodGroup = new BloodGroup(code);
  }
  public void setBloodGroup(|||int code|||) {
    bloodGroup = new BloodGroup(code);
  }
```

Replace "BloodGroup bloodGroup"

Wait 500ms

Select:
```
  public Person(BloodGroup bloodGroup) {
    bloodGroup = |||new BloodGroup(code)|||;
  }
  public void setBloodGroup(BloodGroup bloodGroup) {
    bloodGroup = |||new BloodGroup(code)|||;
  }
```

Replace "bloodGroup"

Wait 500ms

Select:
```
  public |||int||| getBloodGroup() {
    return bloodGroup.getCode();
  }
```
Replace "BloodGroup"


Wait 500ms

Select:
```
  public BloodGroup getBloodGroup() {
    return bloodGroup|||.getCode()|||;
  }
```

Remove selected

Select:
```
Person parent = new Person(BloodGroup.O()|||.getCode()|||);
if (parent.getBloodGroup() == BloodGroup.AB()|||.getCode()|||) {
  // ...
}
```

#|ru| После этих изменений, вероятно, сломается клиентский код, но это просто исправить, избавившись от кодов и там.
#|en| These changes will probably cause the client code to break, but this can be fixed by simply getting rid of the codes there as well.
#|uk| Після цих змін, ймовірно, зламається клієнтський код, але це просто виправити, позбувшись кодів і там.

Remove selected


Set step 6
Select:
```
  public static final int О = BloodGroup.O().getCode();
  public static final int A = BloodGroup.A().getCode();
  public static final int B = BloodGroup.B().getCode();
  public static final int AB = BloodGroup.AB().getCode();


```

#|ru| Напоследок можно удалить константы из класса <code>Person</code>.
#|en| And finally, remove the constants from the <code>Person</code> class.
#|uk| Наостанок можна видалити константи з класу <code>Person</code>.

Remove selected

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