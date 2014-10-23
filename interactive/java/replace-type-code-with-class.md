replace-type-code-with-class:java

###

1.ru. Создайте новый класс и дайте ему понятное название, соответствующее предназначению закодированного типа.
1.uk. Створіть новий клас і дайте йому зрозумілу назву, що відповідає призначенню закодованого типу. Ми називатимемо його <i>клас типу</i>.

2.ru. В <i>класс типа</i> скопируйте поле, содержащее кодирование типа, и сделайте его приватным. Кроме того, создайте для этого поля геттер. Устанавливаться значение в это поле будет только из конструктора.
2.uk. У <i>клас типу</i> скопіюйте поле, що містить кодування типу, і зробіть його приватним. Крім того, створіть для цього поля геттер. Встановлюватися значення в це поле буде тільки з конструктора.

3.ru. Для каждого значения закодированного типа, создайте статический метод в <i>классе типа</i>.
3.uk. Для кожного значення закодованого типу, створіть статичний метод в <i>класі типу</i>.

4.ru. В исходном классе, замените тип закодированного поля на <i>класс типа</i>. Создавайте новый объект этого типа в конструкторе, а также в сеттере поля. Измените геттер поля так, чтобы он вызывал геттер <i>класса типа</i>.
4.uk. У початковому класі, замініть тип закодованого поля на <i>клас типу</i>. Створіть новий об'єкт цього типу в конструкторі, а також в сетерові поля. Змініть геттер поля так, щоб він викликав геттер <i>класу типу</i>.

5.ru. Замените любые упоминания значений закодированного типа вызовами соответствующих статических методов <i>класса типа</i>.
5.uk. Замініть будь-які згадки значень закодованого типу викликами відповідних статичних методів <i>класу типу</i>.

6.ru. Удалите константы закодированного типа из исходного класса.
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
  public int getBloodGroup() {
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
#|uk| Розглянемо рефакторинг <i>Заміна кодування типу класом<i> на прикладі класу людини, в якому є поля групи крові.

Select:
```
  public static final int |||О = 0|||;
  public static final int |||A = 1|||;
  public static final int |||B = 2|||;
  public static final int |||AB = 3|||;
```

#|ru| Группы крови закодированы в четырёх константах этого класса.
#|uk| Групи крові закодовані в чотирьох константах цього класу.

Go to after "Person"

#|ru| Рефакторинг мы начинаем с того, что создаём новый класс <code>BloodGroup</code>, который будет отвечать за группы крови.
#|uk| Рефакторинг ми починаємо з того, що створюємо новий клас <code>BloodGroup</code>, який відповідатиме за групи крові.

Type:
```


class BloodGroup {
}
```

Set step 2

#|ru| В этот класс мы помещаем поле группы крови из класса <code>Person</code>, конструктор, инициализирующий значение поля, а также его геттер.
#|uk| У цей клас ми поміщаємо поле групи крові з класу <code>Person</code>, конструктор, який ініціалізує значення поля, а також його геттер.

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

#C|uk| Можемо провести компіляцію і тестування, щоб переконатися в правильності коду.
#S Все добре, можемо продовжувати.

Set step 4

Select:
```
  private |||int||| bloodGroup;
```

#|ru| Теперь в исходном классе заменим тип закодированного поля на <code>BloodGroup</code>.
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
#|uk| А тепер саме час замінити будь-які згадки значень закодованого типу викликами відповідних статичних методів <i>класу типу<i>.

#|ru| Сначала заменяем значения всех констант старого закодированного типа вызовами соответствующих методов класса <code>BloodGroup</code>.
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
#|uk|^ Зараз, по суті, все використання констант делегуються в методи <code>BlodGroup</code>.

Select "new Person(|||Person.O|||);"

#|ru| Тем не менее, мы пойдём дальше и избавимся от прямых обращений к константам класса <code>Person</code> в остальном коде, заменяя их вызовами методов класса <code>BloodGroup</code>.
#|uk| Тим не менш, ми підемо далі і позбудемося прямих звернень до констант класу <code>Person</code> в іншому коді, замінюючи їх викликами методів класу <code>BloodGroup</code>.

Type "BloodGroup.O().getCode()"

Wait 500ms

Select "parent.getBloodGroup() == |||Person.AB|||"

Wait 500ms

Type "BloodGroup.AB().getCode()"

#C|ru| После всех замен, стоит запустить компиляцию и тестирование.
#S Всё работает, отлично!

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
  public int getBloodGroup() {
    return bloodGroup|||.getCode()|||;
  }
```

#|ru| После всех замен нужно постараться вообще избавиться от использования числовых кодов <code>BloodGroup</code> и использовать вместо этого объекты. Давайте попробуем сделать это в классе <code>Person</code>.
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
  public int getBloodGroup() {
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
#|uk| Наостанок можна видалити константи з класу <code>Person</code>.

Remove selected

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.