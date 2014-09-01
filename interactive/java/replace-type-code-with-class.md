replace-type-code-with-class:java

###

1. Создайте новый класс и дайте ему понятное название, соответствующее предназначению закодированного типа.

2. В <i>класс типа</i> скопируйте поле, содержащее кодирование типа, и сделайте его приватным. Кроме того, создайте для этого поля геттер. Устанавливаться значение в это поле будет только из конструктора.

3. Для каждого значения закодированного типа, создайте статический метод в <i>классе типа</i>.

4. В исходном классе, замените тип закодированного поля на <i>класс типа</i>. Создавайте новый объект этого типа в конструкторе, а также в сеттере поля. Измените геттер поля так, чтобы он вызывал геттер <i>класса типа</i>.

5. Замените любые упоминания значений закодированного типа вызовами соответствующих статических методов <i>класса типа</i>.

6. Удалите константы закодированного типа из исходного класса.



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

# Рассмотрим рефакторинг <i>Замена кодирования типа классом</i> на примере класса человека, в котором есть поля группы крови.

Select:
```
  public static final int |||О = 0|||;
  public static final int |||A = 1|||;
  public static final int |||B = 2|||;
  public static final int |||AB = 3|||;
```

# Группы крови закодированы в четырёх константах этого класса.

Go to after "Person"

# Рефакторинг мы начинаем с того, что создаём новый класс <code>BloodGroup</code>, который будет отвечать за группы крови.

Type:
```


class BloodGroup {
}
```

Set step 2

# В этот класс мы помещаем поле группы крови из класса <code>Person</code>, конструктор, инициализирующий значение поля, а также его геттер.

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

# Теперь, создаём статические методы для каждого из значений закодированного типа из оригинального класса. Эти методы должны возвращать экземпляры класса <code>BloodGroup</code>.

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


#C Можем провести компиляцию и тестирование, чтобы убедиться в правильности кода.

#S Всё хорошо, можем продолжать.

Set step 4

Select:
```
  private |||int||| bloodGroup;
```

# Теперь в исходном классе заменим тип закодированного поля на <code>BloodGroup</code>.


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

# Соответственно нужно поменять код конструктора и сеттера.

Type:
```
new BloodGroup(code)
```

Go to:
```
return bloodGroup|||;
```

# Затем изменяем геттер поля так, чтобы он вызывал геттер класса <code>BloodGroup</code>

Print ".getCode()"

Set step 5

Select:
```
  public static final int |||О = 0|||;
  public static final int |||A = 1|||;
  public static final int |||B = 2|||;
  public static final int |||AB = 3|||;
```

# Настала пора заменить любые упоминания значений закодированного типа вызовами соответствующих статических методов <i>класса типа</i>.

# Сначала заменяем значения всех констант старого закодированного типа вызовами соответствующих методов класса <code>BloodGroup</code>.

Select "public static final int О = |||0|||;"

Replace "BloodGroup.O().getCode()"

Select "public static final int A = |||1|||;"

Replace "BloodGroup.A().getCode()"

Select "public static final int B = |||2|||;"

Replace "BloodGroup.B().getCode()"

Select "public static final int AB = |||3|||;"

Replace "BloodGroup.AB().getCode()"

#^ Сейчас, по сути, все использования констант делегируются в методы <code>BlodGroup</code>.

Select "new Person(|||Person.O|||);"

# Тем не менее, мы пойдём дальше и избавимся от прямых обращений к константам класса <code>Person</code> в остальном коде, заменяя их вызовами методов класса <code>BloodGroup</code>.

Type "BloodGroup.O().getCode()"

Wait 500ms

Select "parent.getBloodGroup() == |||Person.AB|||"

Wait 500ms

Type "BloodGroup.AB().getCode()"

#C После всех замен, стоит запустить компиляцию и тестирование.

#S Всё работает, отлично!

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

# После всех замен нужно постараться вообще избавиться от использования числовых кодов <code>BloodGroup</code> и использовать вместо этого объекты. Давайте попробуем сделать это в классе <code>Person</code>.

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

# После этих изменений, вероятно, сломается клиентский код, но это просто исправить, избавившись от кодов и там.

Remove selected


Set step 6
Select:
```
  public static final int О = BloodGroup.O().getCode();
  public static final int A = BloodGroup.A().getCode();
  public static final int B = BloodGroup.B().getCode();
  public static final int AB = BloodGroup.AB().getCode();


```

# Напоследок можно удалить константы из класса <code>Person</code>.

Remove selected

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.