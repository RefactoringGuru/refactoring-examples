duplicate-observed-data:java

###

1.ru. Создайте класс предметной области.
1.en. Create a domain class.
1.uk. Створіть клас предметної області.

2.ru. Примените паттерн Наблюдатель. Сделайте класс пользовательского интерфейса наблюдателем за классом предметной области.
2.en. Use the Observer pattern. Make the user interface class an observer of the domain class.
2.uk. Застосуйте патерн Спостерігач. Зробіть клас користувальницького інтерфейсу спостерігачем за класом предметної області.

3.ru. Скройте прямой доступ к полям пользовательского интерфейса.
3.en. Hide direct access to the fields of the user interface.
3.uk. Приховайте прямий доступ до полів користувальницького інтерфейсу.

4.ru. Используйте сеттеры для установки значений полей в ответ на активность пользователя в интерфейсе.
4.en. Use setters to set the values of the fields in response to the user's activity in the interface.
4.uk. Використовуйте сеттери для установки значень полів у відповідь на активність користувача в інтерфейсі.

5.ru. Переместите необходимые поля из класса GUI в класс предметной области. При этом, изменяйте методы доступа в классе интерфейса так, чтобы они обращались к полям класса предметной области.
5.en. Move the necessary fields from the GUI class to the domain class. Change the access methods in the interface class so that they refer to the fields of the domain class.
5.uk. Перемістіть необхідні поля з класу GUI в клас предметної області. При цьому, змінюйте методи доступу в класі інтерфейсу так, щоб вони зверталися до полів класу предметної області.



###

```
class IntervalWindow extends Frame {
  java.awt.TextField startField;
  java.awt.TextField endField;
  java.awt.TextField lengthField;

  public IntervalWindow() {
    startField = new java.awt.TextField();
    endField = new java.awt.TextField();
    lengthField = new java.awt.TextField();
    SymFocus focusListener = new SymFocus();
    startField.addFocusListener(focusListener);
    endField.addFocusListener(focusListener);
    lengthField.addFocusListener(focusListener);
  }

  class SymFocus extends java.awt.event.FocusAdapter {
    public void focusLost(java.awt.event.FocusEvent event) {
      Object object = event.getSource();
      if (object == startField) {
        StartField_FocusLost(event);
      }
      else if (object == endField) {
        EndField_FocusLost(event);
      }
      else if (object == lengthField) {
        LengthField_FocusLost(event);
      }
    }

    void StartField_FocusLost(java.awt.event.FocusEvent event) {
      if (isNotInteger(startField.getText())) {
        startField.setText("0");
      }
      calculateLength();
    }

    void EndField_FocusLost(java.awt.event.FocusEvent event) {
      if (isNotInteger(endField.getText())) {
        endField.setText("0");
      }
      calculateLength();
    }

    void LengthField_FocusLost(java.awt.event.FocusEvent event) {
      if (isNotInteger(lengthField.getText())) {
        lengthField.setText("0");
      }
      calculateEnd();
    }

    void calculateLength() {
      try {
        int start = Integer.parseInt(startField.getText());
        int end = Integer.parseInt(endField.getText());
        int length = end - start;
        lengthField.setText(String.valueOf(length));
      } catch (NumberFormatException e) {
        throw new RuntimeException ("Unexpected Number Format Error");
      }
    }
    void calculateEnd() {
      try {
        int start = Integer.parseInt(startField.getText());
        int length = Integer.parseInt(lengthField.getText());
        int end = start + length;
        endField.setText(String.valueOf(end));
      } catch (NumberFormatException e) {
        throw new RuntimeException ("Unexpected Number Format Error");
      }
    }
  }
}
```

###

```
class IntervalWindow extends Frame implements Observer {
  java.awt.TextField startField;
  java.awt.TextField endField;
  java.awt.TextField lengthField;
  private Interval subject;

  public IntervalWindow() {
    startField = new java.awt.TextField();
    endField = new java.awt.TextField();
    lengthField = new java.awt.TextField();
    SymFocus focusListener = new SymFocus();
    startField.addFocusListener(focusListener);
    endField.addFocusListener(focusListener);
    lengthField.addFocusListener(focusListener);

    subject = new Interval();
    subject.addObserver(this);
    update(subject, null);
  }

  class SymFocus extends java.awt.event.FocusAdapter {
    public void focusLost(java.awt.event.FocusEvent event) {
      Object object = event.getSource();
      if (object == startField) {
        StartField_FocusLost(event);
      }
      else if (object == endField) {
        EndField_FocusLost(event);
      }
      else if (object == lengthField) {
        LengthField_FocusLost(event);
      }
    }

    void StartField_FocusLost(java.awt.event.FocusEvent event) {
      setStart(startField.getText());
      if (isNotInteger(getStart())) {
        setStart("0");
      }
      subject.calculateLength();
    }

    void EndField_FocusLost(java.awt.event.FocusEvent event) {
      setEnd(endField.getText());
      if (isNotInteger(getEnd())) {
        setEnd("0");
      }
      subject.calculateLength();
    }

    void LengthField_FocusLost(java.awt.event.FocusEvent event) {
      setLength(lengthField.getText());
      if (isNotInteger(getLength())) {
        setLength("0");
      }
      subject.calculateEnd();
    }
  }

  public void update(Observable observed, Object arg) {
    endField.setText(subject.getEnd());
    startField.setText(subject.getStart());
    lengthField.setText(subject.getLength());
  }

  String getEnd() {
    return subject.getEnd(arg);
  }
  void setEnd(String arg) {
    subject.setEnd(arg);
  }
  String getStart() {
    return subject.getStart(arg);
  }
  void setStart(String arg) {
    subject.setStart(arg);
  }
  String getLength() {
    return subject.getLength(arg);
  }
  void setLength(String arg) {
    subject.setLength(arg);
  }
}

class Interval extends Observable {
  private String end = "0";
  private String start = "0";
  private String length = "0";

  String getEnd() {
    return end;
  }
  void setEnd(String arg) {
    end = arg;
    setChanged();
    notifyObservers();
  }
  String getStart() {
    return start;
  }
  void setStart(String arg) {
    start = arg;
    setChanged();
    notifyObservers();
  }
  String getLength() {
    return length;
  }
  void setLength(String arg) {
    length = arg;
    setChanged();
    notifyObservers();
  }

  void calculateLength() {
    try {
      int start = Integer.parseInt(getStart());
      int end = Integer.parseInt(getEnd());
      int length = end - start;
      setLength(String.valueOf(length));
    } catch (NumberFormatException e) {
      throw new RuntimeException ("Unexpected Number Format Error");
    }
  }
  void calculateEnd() {
    try {
      int start = Integer.parseInt(getStart());
      int length = Integer.parseInt(getLength());
      int end = start + length;
      setEnd(String.valueOf(end));
    } catch (NumberFormatException e) {
      throw new RuntimeException ("Unexpected Number Format Error");
    }
  }
}
```

###

Set step 1

Select name of "class IntervalWindow"

#|ru| Давайте рассмотрим <i>Дублирование видимых данных</i> на примере класса для создания окна редактирования интервала чисел.
#|en| Let's look at <i>Duplicate Observed Data</i> using the class that creates a window for editing numeric intervals.
#|uk| Давайте розглянемо <i>Дублювання видимих ​​даних</i> на прикладі класу для створення вікна редагування інтервалу чисел.

Select 1st "lengthField"
+ Select 1st "startField"
+ Select 1st "endField"

#|ru|< Окно состоит из трёх полей ввода: стартовое значение (Start), конечное значение (End) и результирующая длина (length). <br/><img src="/images/refactoring/diagrams/gui-window.png">
#|en|< The window consists of three parts: Start value, End value, and Length. <br/><img src="/images/refactoring/diagrams/gui-window.png">
#|uk|< Вікно складається з трьох полів: стартове значення (Start), кінцеве значення (End) і результуюча довжина (length).<br/><img src="/images/refactoring/diagrams/gui-window.png">

Select name of "focusLost"

#|ru|V+ Перерасчёты новых значений происходят при потере фокуса элементом. При изменении текстовых полей <code>Start</code> или <code>End</code> вычисляется <code>length</code>, при изменении поля <code>length</code> вычисляется <code>End</code>.
#|en|V+ Recalculations of new values occur when the element loses focus. When a change occurs in <code>Start</code> or <code>End</code> text fields, <code>length</code> is calculated. When <code>length</code> changes, <code>End</code> is calculated.
#|uk|V+ Перерахунки нових значень відбуваються при втраті фокуса елементом. При зміні полів <code>Start</code> або <code>End</code> обчислюється <code>length</code>; при зміні поля <code>length</code> обчислюється <code>End</code>.

Select name of "StartField_FocusLost"
+ Select name of "EndField_FocusLost"
+ Select name of "LengthField_FocusLost"

#|ru|V= При этом конкретные вычисления происходят в служебных методах, относящихся к каждому из полей.
#|en|V= The specific calculations take place in utility methods for each of the fields.
#|uk|V= При цьому конкретні обчислення відбуваються в службових методах, що відносяться до кожного з полів.

Select name of "calculateLength"
+ Select name of "calculateEnd"

#|ru| Эти методы вызывают вычисление новой длины (<code>calculateLength</code>) или нового конечного значения (<code>calculateEnd</code>) в зависимости от того, что поменялось в окне.
#|en| These methods call calculation of the new length (<code>calculateLength</code>) or new end value (<code>calculateEnd</code>) depending on what has changed in the window.
#|uk| Ці методи викликають обчислення нової довжини (<code>calculateLength</code>) або нового кінцевого значення (<code>calculateEnd</code>) залежно від того, що помінялося у вікні.

Go to the end of file

#|ru| Нашей задачей станет выделение всех перерасчётов длины и конечного значения в отдельный класс предметной области. Начнём с создания такого класса.
#|en| Our task is to separate all recalculations of length and end value into a separate domain class. Let's start by creating such class.
#|uk| Нашим завданням стане відділення всіх перерахунків довжини і кінцевого значення в окремий клас предметної області. Почнемо із створення такого класу.

Print:
```


class Interval extends Observable {
}
```

#|ru| После того как был создан класс предметной области, стоит поместить ссылку на него из класса окна.
#|en| After creation of a domain class, let's place a reference to it from the window class.
#|uk| Після того, як був створений клас предметної області, варто помістити посилання на нього з класу вікна.

Go to:
```
  java.awt.TextField startField;
  java.awt.TextField endField;
  java.awt.TextField lengthField;
|||
```

Print:
```
  private Interval subject;

```

Set step 2

Select name of "public IntervalWindow"

#|ru| Затем надо создать код инициализации этого поля, а также сделать класс окна наблюдателем предметного класса. Весь этот код стоит поместить в конструктор <code>IntervalWindow</code>.
#|en| Then we create the code for initializing this reference field and make the window class an observer of the domain class. We should place all this code in the <code>IntervalWindow</code> constructor.
#|uk| Потім треба створити код ініціалізації цього поля, а також зробити клас вікна спостерігачем предметного класу. Весь цей код варто помістити в конструктор <code>IntervalWindow</code>.

Go to the end of "public IntervalWindow"

Print:
```


    subject = new Interval();
    subject.addObserver(this);
    update(subject, null);
```

Select "|||update|||(subject, null);"

#|ru|^ Здесь вызов функции <code>update</code> гарантирует, что объект окна (GUI) заполнится данными из объекта предметной области. Но чтобы это заработало, нам нужно кое-что ещё.
#|en|^ Here, the call to the <code>update</code> function guarantees that the window object (GUI) will be filled with data from the domain object. But we need some other things in order for this to work.
#|uk|^ Тут виклик функції <code>update</code> гарантує, що об'єкт вікна (GUI) заповниться даними з об'єкта предметної області. Але щоб це запрацювало, нам потрібно дещо ще.

Go to:
```
class IntervalWindow extends Frame|||
```

#|ru| Во-первых, нужно объявить класс <code>IntervalWindow</code> реализующим интерфейс наблюдателя (<code>Observer</code>).
#|en| First, declare the <code>IntervalWindow</code> class as the one, which implements <code>Observer</code> interface.
#|uk| По-перше, потрібно оголосити клас <code>IntervalWindow</code> реалізуючим інтерфейс спостерігача (<code>Observer</code>).

Print:
```
 implements Observer
```

#|ru| Во-вторых, реализовать этот интерфейс, создав сам метод <code>update()</code>.
#|en| Second, create the <code>update()</code> method to provide the actual implementation.
#|uk| По-друге, реалізувати цей інтерфейс, створивши сам метод <code>update()</code>.

Go to the end of "class IntervalWindow"

Print:
```


  public void update(Observable observed, Object arg) {
  }
```

#C|ru| Запустим компиляцию и тестирование. Да, никаких реальных изменений мы пока ещё не внесли, но ошибки можно совершить в простейших вещах, поэтому лучше всегда держать код проверенным.
#S Всё хорошо, можно продолжать.

#C|en| Compile and test. While we have not yet made any "real" changes, mistakes can be often made in the simplest things, and it is best to keep all code checked at all times.
#S All is well, so let's continue.

#C|uk| Запустимо компіляцію і тестування. Так, жодних реальних змін ми поки ще не внесли, але помилки можна зробити в найпростіших речах, тому краще завжди тримати код перевіреним.
#S Все добре, можна продовжувати.

Set step 3

Select 1st "lengthField"
+ Select 1st "startField"
+ Select 1st "endField"

#|ru| Теперь пора взяться за поля. Проведём <a href="/ru/self-encapsulate-field">самоинкапсуляцию</a> каждого из полей окна интервала. Создадим для каждого из полей геттер и сеттер, возвращающий и принимающий строковое значение. Начнём с поля конца интервала:
#|en| Now let's take care of the fields. Perform <a href="/self-encapsulate-field">Self-Encapsulate Field</a> on each of the fields of the interval window. For each field, create a getter and setter that return and accept a string value. Start with the field of the end of the interval:
#|uk| Саме час взятися за поля. Проведемо <a href="/uk/self-encapsulate-field">самоінкапсуляцію</a> кожного з полів вікна інтервалу. Створимо для кожного з полів геттер і сетер, який повертає і приймає строкове значення. Почнемо з поля кінця інтервалу:

Go to the end of "class IntervalWindow"

Print:
```


  String getEnd() {
    return endField.getText();
  }
  void setEnd(String arg) {
    endField.setText(arg);
  }
```

#|ru| После чего можно заменить все обращения к <code>endField</code> вызовами соответствующих методов.
#|en| Then we can replace all references to <code>endField</code> with calls to the relevant methods.
#|uk| Після чого можна замінити всі звернення до <code>endField</code> викликами відповідних методів.

Select "endField.getText" in "class SymFocus"

Wait 1000ms

Type "getEnd"

Select "endField.setText" in "class SymFocus"

Wait 1000ms

Type "setEnd"

Set step 4

Select name of "EndField_FocusLost"

#|ru| В нашем случае, в отличие от обычной самоинкапсуляции, пользователь сам может менять значение поля <code>End</code> в окне, поэтому нам нужно позаботиться о том, чтобы такое изменение сохранилось.
#|en| In our case, unlike ordinary self-encapsulation, the user can independently change the value of the <code>End</code> field in the window. So we make sure that this change is saved if it is made.
#|uk| У нашому випадку, на відміну від звичайної самоінкапсуляціі, сам користувач може поміняти значення поля <code>End</code> в вікні, тому нам потрібно подбати про те, щоб така зміна збереглася.

Go to start of "EndField_FocusLost"

Print:
```

      setEnd(endField.getText());
```

Select "setEnd(|||endField.getText()|||);"

#|ru| Обратите внимание на то, что в этом вызове мы обращаемся к полю непосредственно. Мы делаем так потому, что после продолжения рефакторинга <code>getEnd()</code> будет получать значение от объекта предметной области, а не из поля. А в этом конкретном случае нам нужно именно значение поля в окне (GUI).
#|en| Note that in this call we are accessing the field directly. This is because after continuing the refactoring, <code>getEnd()</code> will be getting its value from the domain object, not the field. And in this particular case, we need the value of the field in the window (GUI).
#|uk| Зверніть увагу на те, що в цьому виклику ми звертаємося до поля безпосередньо. Ми робимо так тому, що після продовження рефакторинга <code>getEnd()</code> отримуватиме значення від об'єкта предметної області, а не з поля. А в цьому конкретному випадку нам потрібно саме значення поля у вікні (GUI).

#|ru| В обратном случае вызов метода <code>getEnd()</code> привёл бы к тому, что при каждом изменении пользователем значения поля, этот код возвращал бы старое значение обратно, поэтому здесь нужно использовать прямой доступ.
#|en| Otherwise, when the user changes the value of the field, this code would always return the old value. That's why we need a direct access.
#|uk| У зворотньому випадку виклик методу <code>getEnd()</code> привів би до того, що при кожній зміні користувачем значення поля, цей код повертав би старе значення назад, тому тут потрібно використовувати прямий доступ.

Set step 5

Select name of "Interval"

#|ru| Отлично! После того как поле <code>End</code> полностью инкапсулировано, мы можем добавить соответствующее поле в класс предметной области.
#|en| Excellent! Once the <code>End</code> field is fully encapsulated, we can add the relevant field to the domain class.
#|uk| Відмінно! Після того, як поле <code>End</code> повністю інкапсульоване, ми можемо додати відповідне поле в клас предметної області.

Go to start of "class Interval"

Print:
```

  private String end = "0";
```

Select "private String end = |||"0"|||;"

#|ru| Мы инициализируем это поле тем же значением, что и поле в GUI.
#|en| We should initialize the field with the same value as the field in the GUI.
#|uk| Ми ініціалізуємо це поле тим же значенням, що й поле в GUI.

Go to the end of "class Interval"

#|ru| После этого добавим методы доступа к этому полю.
#|en| Then add methods for accessing the field.
#|uk| Після цього додамо методи доступу до цього поля.

Print:
```


  String getEnd() {
    return end;
  }
  void setEnd(String arg) {
    end = arg;
    setChanged();
    notifyObservers();
  }
```

Select:
```
  void setEnd(String arg) {
    end = arg;
    |||setChanged()|||;
    |||notifyObservers()|||;
  }
```

#|ru| Как видите, при изменении значения мы оповещаем наблюдающий объект окна о том, что пора обновить своё значение из объекта предметной области…
#|en| As you see, when a value gets changed, we notify the window observer that it is time to update its value from the domain object…
#|uk| Як бачите, при зміні значення ми даємо знати  спостерігаючому об'єкту вікна про те, що пора оновити своє значення з об'єкта предметної області…

+ Select "void |||update|||"

#|ru| …что приводит нас к вызову метода <code>update()</code> в классе окна. В нём сейчас ничего нет, поэтому давайте добавим нужный код, чтобы всё заработало.
#|en| …which leads us to calling the <code>update()</code> method in the window class. It does not have anything in it yet, so let's add the necessary code to make everything work.
#|uk| …що приводить нас до виклику методу <code>update()</code> в класі вікна. У ньому зараз нічого немає, тому давайте додамо потрібний код, щоб все запрацювало.

Go to the start of "update"

Print:
```

    endField.setText(subject.getEnd());
```

Select:
```
|||endField.setText|||(subject.getEnd());
```
#|ru| Это ещё одно место, где необходим прямой доступ, а вызов метода установки привёл бы к бесконечной рекурсии.
#|en| That is another place where direct access is needed, since calling a setter would lead to infinite recursion.
#|uk| Це ще одне місце, де необхідний прямий доступ, а виклик методу установки привів би до нескінченної рекурсії.

#C|ru| После завершения всех перестановок с полем можно запустить компиляцию и тестирование.
#S Всё отлично, можем продолжать!

#C|en| After finishing all the field rearrangements, we'd better compile and test.
#S Everything is OK! We can keep going.

#C|uk| Після завершення всіх перестановок з полем можна запустити компіляцію і тестування.
#S Все добре, можна продовжувати.

Select 1st "lengthField"
+ Select 1st "startField"

#|ru| Теперь проделаем такие же изменения с остальными полями — <code>Start</code> и <code>Length</code>.
#|en| Now we do the same thing with the remaining fields, <code>Start</code> and <code>Length</code>
#|uk| Тепер проробимо такі ж зміни з рештою полів – <code>Start</code> і <code>Length</code>.

Go to the end of "class IntervalWindow"

Print:
```

  String getStart() {
    return startField.getText();
  }
  void setStart(String arg) {
    startField.setText(arg);
  }
```

Wait 500ms

Select "startField.getText" in "class SymFocus"

Replace "getStart"

Wait 1000ms

Select "startField.setText" in "class SymFocus"

Replace "setStart"

Wait 1000ms

Go to start of "StartField_FocusLost"

Print:
```

      setStart(startField.getText());
```

Wait 500ms

Go to:
```
  private String end = "0";|||
```

#|ru| Добавляем поле в класс интервала…
#|en| Add the field to the interval class.
#|uk| Додаємо поле в клас інтервалу …

Print:
```

  private String start = "0";
```

Wait 500ms

Go to the end of "class Interval"

Print:
```

  String getStart() {
    return start;
  }
  void setStart(String arg) {
    start = arg;
    setChanged();
    notifyObservers();
  }
```

Wait 500ms

Go to the end of "update"

Print:
```

    startField.setText(subject.getStart());
```


Go to the end of "class IntervalWindow"

#|ru| Осталось расправиться с полем длины.
#|en| Now we deal with the length field.
#|uk| Залишилося розправитися з полем довжини.

Print:
```

  String getLength() {
    return lengthField.getText();
  }
  void setLength(String arg) {
    lengthField.setText(arg);
  }
```

Wait 500ms

Select "lengthField.getText" in "class SymFocus"

Type "getLength"

Wait 500ms

Select "lengthField.setText" in "class SymFocus"

Type "setLength"

Wait 500ms

Go to start of "LengthField_FocusLost"

Print:
```

      setLength(lengthField.getText());
```

Wait 500ms

Go to:
```
  private String start = "0";|||
```

#|ru| Добавляем поле в класс интервала…
#|en| Add the field to the interval class.
#|uk| Додаємо поле в клас інтервалу …

Print:
```

  private String length = "0";
```

Wait 500ms

Go to the end of "class Interval"

Print:
```

  String getLength() {
    return length;
  }
  void setLength(String arg) {
    length = arg;
    setChanged();
    notifyObservers();
  }
```

Wait 500ms

Go to the end of "update"

Print:
```

    lengthField.setText(subject.getLength());
```


Select name of "calculateEnd"
+ Select name of "calculateLength"

#|ru| Теперь было бы неплохо переместить методы <code>calculateEnd()</code> и <code>calculateLength()</code> в класс интервала.
#|en| At this point, it would be a good time to move the <code>calculateEnd()</code> and <code>calculateLength()</code> methods to the interval class.
#|uk| Тепер було б непогано перемістити методи <code>calculateEnd()</code> і <code>calculateLength()</code> в клас інтервалу.

Select body of "setEnd"
+ Select body of "setStart"
+ Select body of "setLength"

#|ru| Но для этого нужно сперва сделать так, чтобы сеттеры полей класса <code>IntervalWindow</code> заполняли значения в классе <code>Interval</code>.
#|en| But to do this, you must first configure the setters of the fields of the <code>IntervalWindow</code> class to fill values in the <code>Interval</code> class.
#|uk| Але для цього потрібно спершу зробити так, щоб сеттери полів класу <code>IntervalWindow</code> заповнювали значення в класі <code>Interval</code>.

Select body of "setEnd"

Replace:
```
    subject.setEnd(arg);
```

Wait 500ms

Select body of "setStart"

Replace:
```
    subject.setStart(arg);
```

Wait 500ms

Select body of "setLength"

Replace:
```
    subject.setLength(arg);
```

#|ru| Мы убрали установку значения в поле пользовательского интерфейса потому, что оно все-равно будет выставлено при вызове сеттеров класса интервала (вспомните о реализации Наблюдателя и методе <code>update</code>).
#|en| We removed the value assignment in the GUI interface field because the value will still be set when the setter of the interval class is called (remember about implementation of Observer in the <code>update</code> method).
#|uk| Ми прибрали установку значення в поле користувальницького інтерфейсу тому, що воно все-одно буде виставлено при виклику сеттерів класу інтервалу (згадайте про реалізацію Спостерігача і методі <code>update</code>) .

Select body of "getEnd"
+ Select body of "getStart"
+ Select body of "getLength"

#|ru| То же можно проделать и с геттерами.
#|en| We should do the same with getters.
#|uk| Те ж можна виконати і з гетерами.

Select body of "getEnd"

Replace:
```
    return subject.getEnd(arg);
```

Wait 500ms

Select body of "getStart"

Replace:
```
    return subject.getStart(arg);
```

Wait 500ms

Select body of "getLength"

Replace:
```
    return subject.getLength(arg);
```

Select name of "calculateEnd"
+ Select name of "calculateLength"

#|ru| И вот теперь уже можно приступить к переносу <code>calculateEnd()</code> и <code>calculateLength()</code> в класс интервала.
#|en| Now we can start moving <code>calculateEnd()</code> and <code>calculateLength()</code> to the interval class.
#|uk| І ось тепер вже можна приступити до переносу <code>calculateEnd()</code> і <code>calculateLength()</code> в клас інтервалу.

Select:
```

    void calculateLength() {
```
+ Select whole "calculateLength"

#|ru| Начнем с <code>calculateLength</code>.
#|en| Let's start by moving <code>calculateLength</code>.
#|uk| Почнемо з <code>calculateLength</code>.

Remove selected

Go to the end of "class Interval"

Print:
```


  void calculateLength() {
    try {
      int start = Integer.parseInt(getStart());
      int end = Integer.parseInt(getEnd());
      int length = end - start;
      setLength(String.valueOf(length));
    } catch (NumberFormatException e) {
      throw new RuntimeException ("Unexpected Number Format Error");
    }
  }
```

Select "calculateLength()" in "SymFocus"

Replace "subject.calculateLength()"


Select whole "calculateEnd"

Remove selected

Go to the end of "class Interval"

Print:
```

  void calculateEnd() {
    try {
      int start = Integer.parseInt(getStart());
      int length = Integer.parseInt(getLength());
      int end = start + length;
      setEnd(String.valueOf(end));
    } catch (NumberFormatException e) {
      throw new RuntimeException ("Unexpected Number Format Error");
    }
  }
```

Select "calculateEnd()" in "SymFocus"

Replace "subject.calculateEnd()"


Select name of "Interval"

#|ru| В итоге мы получим класс предметной области, содержащий все поведения и вычисления над исходными данными, причём отдельно от кода пользовательского интерфейса.
#|en| Ultimately, we get the domain class, which contains all behaviors and calculations on the source data separate from the GUI code.
#|uk| В результаті ми отримаємо клас предметної області, що містить всі поведінки і обчислення над вихідними даними, причому окремо від коду користувальницького інтерфейсу.

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
