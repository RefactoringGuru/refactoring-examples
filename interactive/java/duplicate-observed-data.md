duplicate-observed-data:java

###

1. Создайте класс предметной области.

2. Примените паттерн Наблюдатель. Сделайте класс пользовательского интерфейса наблюдателем за классом предметной области.

3. Скройте прямой доступ к полям пользовательского интерфейса.

4. Используйте сеттеры для установки значений полей в ответ на активность пользователя в интерфейсе.

5. Переместите необходимые поля из класса GUI в класс предметной области. При этом, изменяйте методы доступа в классе интерфейса так, чтобы они обращались к полям класса предметной области.



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
    subject.setEnd(arg);
  }
  void setEnd(String arg) {
    subject.setEnd(arg);
  }
  String getStart() {
    subject.setStart(arg);
  }
  void setStart(String arg) {
    subject.setStart(arg);
  }
  String getLength() {
    subject.setLength(arg);
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

# Давайте рассмотрим <i>Дублирование видимых данных</i> на примере класса для создания окна редактирования интервала чисел.

Select 1st "lengthField"
+ Select 1st "startField"
+ Select 1st "endField"

#< Окно состоит из трёх полей ввода: стартовое значение (Start), конечное значение (End) и результирующая длина (length). <br/><img src="/images/refactoring/gui-window.png">

Select name of "focusLost"

#V+ Перерасчёты новых значений происходят при потере фокуса элементом. При изменении текстовых полей <code>Start</code> или <code>End</code> вычисляется <code>length</code>, при изменении поля <code>length</code> вычисляется <code>End</code>.

Select name of "StartField_FocusLost"
+ Select name of "EndField_FocusLost"
+ Select name of "LengthField_FocusLost"

#V= При этом конкретные вычисления происходят в служебных методах, относящихся к каждому из полей.

Select name of "calculateLength"
+ Select name of "calculateEnd"

# Эти методы вызывают вычисление новой длины (<code>calculateLength</code>) или нового конечного значения (<code>calculateEnd</code>) в зависимости от того, что поменялось в окне.

Go to the end of file

# Нашей задачей станет выделение всех перерасчётов длины и конечного значения в отдельный класс предметной области. Начнём с создания такого класса.

Print:
```


class Interval extends Observable {
}
```

# После того, как был создан класс предметной области, стоит поместить ссылку на него из класса окна.

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

# Затем надо создать код инициализации этого поля, а также сделать класс окна наблюдателем предметного класса. Весь этот код стоит поместить в конструктор <code>IntervalWindow</code>.

Go to the end of "public IntervalWindow"

Print:
```


    subject = new Interval();
    subject.addObserver(this);
    update(subject, null);
```

Select "|||update|||(subject, null);"

#^ Здесь вызов функции <code>update</code> гарантирует, что объект окна (GUI) заполнится данными из объекта предметной области. Но чтобы это заработало, нам нужно кое-что ещё.

Go to:
```
class IntervalWindow extends Frame|||
```

# Во-первых, нужно объявить класс <code>IntervalWindow</code> реализующим интерфейс наблюдателя (<code>Observer</code>).

Print:
```
 implements Observer
```

# Во-вторых, реализовать этот интерфейс, создав сам метод <code>update()</code>.

Go to the end of "class IntervalWindow"

Print:
```


  public void update(Observable observed, Object arg) {
  }
```

#C Запустим компиляцию и тестирование. Да, никаких реальных изменений мы пока ещё не внесли, но ошибки можно совершить в простейших вещах, поэтому лучше всегда держать код проверенным.

#S Всё хорошо, можно продолжать.

Set step 3

Select 1st "lengthField"
+ Select 1st "startField"
+ Select 1st "endField"

# Теперь пора взяться за поля. Проведём <a href="/self-encapsulate-field">самоинкапсуляцию</a> каждого из полей окна интервала. Создадим для каждого из полей геттер и сеттер, возвращающий и принимающий строковое значение. Начнём с поля конца интервала:

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

# После чего можно заменить все обращения к <code>endField</code> вызовами соответствующих методов.

Select "endField.getText" in "class SymFocus"

Wait 1000ms

Type "getEnd"

Select "endField.setText" in "class SymFocus"

Wait 1000ms

Type "setEnd"

Set step 4

Select name of "EndField_FocusLost"

# В нашем случае, в отличие от обычной самоинкапсуляции, сам пользователь может поменять значение поля <code>End</code> в окне, поэтому нам нужно позаботиться о том, чтобы такое изменение сохранилось.

Go to start of "EndField_FocusLost"

Print:
```

      setEnd(endField.getText());
```

Select "setEnd(|||endField.getText()|||);"

# Обратите внимание на то, что в этом вызове мы обращаемся к полю непосредственно. Мы делаем так потому, что после продолжения рефакторинга <code>getEnd()</code> будет получать значение от объекта предметной области, а не из поля. А в этом конкретном случае нам нужно именно значение поля в окне (GUI).

# В обратном случае вызов метода <code>getEnd()</code> привёл бы к тому, что при каждом изменении пользователем значения поля, этот код возвращал бы старое значение обратно, поэтому здесь нужно использовать прямой доступ.

Set step 5

Select name of "Interval"

# Отлично! После того, как поле <code>End</code> полностью инкапсулировано, мы можем добавить соответствующее поле в класс предметной области.

Go to start of "class Interval"

Print:
```

  private String end = "0";
```

Select "private String end = |||"0"|||;"

# Мы инициализируем это поля тем же значением, что и поле в GUI.

Go to the end of "class Interval"

# После этого добавим методы доступа к этому полю.

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

# Как видите, при изменении значения мы оповещаем наблюдающий объект окна о том, что пора обновить своё значение из объекта предметной области...

+ Select "void |||update|||"

# ...что приводит нас к вызову метода <code>update()</code> в классе окна. В нём сейчас ничего нет, поэтому давайте добавим нужный код, чтобы всё заработало.

Go to the start of "update"

Print:
```

    endField.setText(subject.getEnd());
```

Select:
```
|||endField.setText|||(subject.getEnd());
```
# Это ещё одно место, где необходим прямой доступ, а вызов метода установки привёл бы к бесконечной рекурсии.

#C После завершения всех перестановок с полем, можно запустить компиляцию и тестирование.

#S Всё отлично, можно продолжать.

Select 1st "lengthField"
+ Select 1st "startField"

# Теперь проделаем такие же изменения с остальными полями — <code>Start</code> и <code>Length</code>.

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

# Добавляем поле в класс интервала...

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

# Осталось расправиться с полем длины.

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

# Добавляем поле в класс интервала...

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

# Теперь было бы неплохо переместить методы <code>calculateEnd()</code> и <code>calculateLength()</code> в класс интервала.

Select body of "setEnd"
+ Select body of "setStart"
+ Select body of "setLength"

# Но для этого нужно сперва сделать так, чтобы сеттеры полей класса <code>IntervalWindow</code> заполняли значения в классе <code>Interval</code>.

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

# Мы убрали установку значения в поле пользовательского интерфейса потому, что оно все-равно будет выставлено при вызове сеттеров класса интервала (вспомните о реализации Наблюдателя и методе <code>update</code>).

Select body of "getEnd"
+ Select body of "getStart"
+ Select body of "getLength"

# То же можно проделать и с геттерами.

Select body of "getEnd"

Replace:
```
    subject.setEnd(arg);
```

Wait 500ms

Select body of "getStart"

Replace:
```
    subject.setStart(arg);
```

Wait 500ms

Select body of "getLength"

Replace:
```
    subject.setLength(arg);
```

Select name of "calculateEnd"
+ Select name of "calculateLength"

# И вот теперь уже можно приступить к переносу <code>calculateEnd()</code> и <code>calculateLength()</code> в класс интервала.

Select:
```

    void calculateLength() {
```
+ Select whole "calculateLength"

# Начнем с <code>calculateLength</code>.

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

# В итоге мы получим класс предметной области, содержащий все поведения и вычисления над исходными данными, причём отдельно от кода пользовательского интерфейса.

#C Запускаем финальную компиляцию и тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.