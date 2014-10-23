duplicate-observed-data:csharp

###

1. Создайте класс предметной области.

2. Примените паттерн Наблюдатель. Сделайте класс пользовательского интерфейса наблюдателем за классом предметной области.

3. Скройте прямой доступ к полям пользовательского интерфейса.

4. Используйте сеттеры для установки значений полей в ответ на активность пользователя в интерфейсе.

5. Переместите необходимые поля из класса GUI в класс предметной области. При этом, изменяйте методы доступа в классе интерфейса так, чтобы они обращались к полям класса предметной области.



###

```
public partial class IntervalWindow : Form
{
  public IntervalWindow()
  {
    InitializeComponent();
  }

  private void CalculateLength()
  {
    int start = int.Parse(tbStart.Text);
    int end = int.Parse(tbEnd.Text);
    int length = end - start;
    tbLength.Text = length.ToString();
  }
  private void CalculateEnd()
  {
    int start = int.Parse(tbStart.Text);
    int length = int.Parse(tbLength.Text);
    int end = start + length;
    tbEnd.Text = end.ToString();
  }

  private void OnTextBoxLeave(object sender, EventArgs e)
  {
    TextBox tb = sender as TextBox;
    
    if (tb != null)
    {
      int tmp;
      if (!int.TryParse(tb.Text, out tmp))
        tb.Text = "0";
      
      if (tb == tbStart)
      {
        CalculateLength();
      }
      else if (tb == tbEnd)
      {
        CalculateLength();
      }  
      else if (tb == tbLength)
      {
        CalculateEnd();
      }
    }
  }
}
```

###

```
public partial class IntervalWindow : Form, IObserver<Interval>
{
  private Interval subject;

  private string Start
  {
    get{ return subject.Start; }
    set{ subject.Start = value; }
  }
  private string End
  {
    get{ return subject.End; }
    set{ subject.End = value; }
  }
  private string Length
  {
    get{ return subject.Length; }
    set{ subject.Length = value; }
  }

  public IntervalWindow()
  {
    InitializeComponent();

    subject = new Interval();
    subject.Subscribe(this);
    OnNext(subject);
  }

  public void OnNext(Interval interval)
  {
    tbStart.Text = interval.Start;
    tbEnd.Text = interval.End;
    tbLength.Text = interval.Length;
  }
  // No implementation needed: Method is not called by the Interval class.
  public void OnError(Exception e)
  {
    // No implementation.
  }
  // No implementation needed: Method is not called by the Interval class.
  public void OnCompleted()
  {
    // No implementation.
  }
  private void OnTextBoxLeave(object sender, EventArgs e)
  {
    TextBox tb = sender as TextBox;
    
    if (tb != null)
    {
      int tmp;
      if (!int.TryParse(tb.Text, out tmp))
        tb.Text = "0";
      
      if (tb == tbStart)
      {
        this.Start = tb.Text;
        subject.CalculateLength();
      }
      else if (tb == tbEnd)
      {
        this.End = tb.Text;
        subject.CalculateLength();
      }  
      else if (tb == tbLength)
      {
        this.Length = tb.Text;
        subject.CalculateEnd();
      }
    }
  }
}

public class Interval: IObservable<Interval>
{
  private List<IObserver<Interval>> observers;
  private string  start = "0",
                  end = "0",
                  length = "0";

  public string Start
  {
    get{ return start; }
    set{ OnValueChanged(ref start, value); }
  }
  public string End
  {
    get{ return end; }
    set{ OnValueChanged(ref end, value); }
  }
  public string Length
  {
    get{ return length; }
    set{ OnValueChanged(ref length, value); }
  }

  public Interval()
  {
    observers = new List<IObserver<Interval>>();
  }

  private void OnValueChanged(ref string oldValue, string newValue)
  {
    if (!string.Equals(oldValue, newValue, StringComparison.Ordinal))
    {
      oldValue = newValue;
      foreach (var observer in observers)
        observer.OnNext(this);
    }
  }
  public IDisposable Subscribe(IObserver<Interval> observer)
  {
    if (!observers.Contains(observer))
    {
      observers.Add(observer);
      observer.OnNext(this);
    }
    return null;
  }
  public void CalculateLength()
  {
    int start = int.Parse(this.Start);
    int end = int.Parse(this.End);
    int length = end - start;
    this.Length = length.ToString();
  }
  public void CalculateEnd()
  {
    int start = int.Parse(this.Start);
    int length = int.Parse(this.Length);
    int end = start + length;
    this.End = end.ToString();
  }
}
```

###

Set step 1

Select name of "class IntervalWindow"

# Давайте рассмотрим <i>Дублирование видимых данных</i> на примере класса для создания окна редактирования интервала чисел.

Select name of "public IntervalWindow"

#< Окно состоит из трёх полей ввода: стартовое значение (Start), конечное значение (End) и результирующая длина (Length). <br/><img src="/images/refactoring/gui-window_csharp.png">

Select name of "OnTextBoxLeave"

#V Перерасчёты новых значений происходят при потере фокуса элементом (обработчик привязан ко всем полям ввода). При изменении текстовых полей <code>Start</code> или <code>End</code> вычисляется <code>Length</code>, при изменении поля <code>Length</code> вычисляется <code>End</code>.

Select "CalculateLength();"
+ Select "CalculateEnd();"

#V При этом конкретные вычисления происходят в служебных методах.

Select name of "CalculateLength"
+ Select name of "CalculateEnd"

# Эти методы вызывают вычисление новой длины (<code>CalculateLength</code>) или нового конечного значения (<code>CalculateEnd</code>) в зависимости от того, что поменялось в окне.

Go to the end of file

# Нашей задачей станет выделение всех перерасчётов длины и конечного значения в отдельный класс предметной области. Начнём с создания такого класса.

Print:
```


public class Interval
{
}
```

# После того как был создан класс предметной области, стоит поместить ссылку на него из класса окна.

Go to start of "class IntervalWindow"

Print:
```

  private Interval subject;

```

Wait 500ms

Set step 2

Select name of "class Interval"

# Теперь надо реализовать паттерн Наблюдатель. Для этого воспользуемся интерфейсами <code>IObservable&lt;T&gt;</code> и <code>IObserver&lt;T&gt;</code>.

# В нашем случае класс <code>Interval</code> будет реализовывать интерфейс Наблюдаемого (<code>Observable</code>), при этом данные для наблюдения будут храниться в нём же. Начнем с наследования класса от требуемого интерфейса.

Go to "public class Interval|||"

Print ": IObservable<Interval>"

Go to start of "public class Interval"

# Затем добавим в этот класс поле, отвечающее за хранение наблюдателей.

Print:
```

  private List<IObserver<Interval>> observers;
```

Go to end of "class Interval"

# Проинициализируем его в конструкторе класса.

Print:
```


  public Interval()
  {
    observers = new List<IObserver<Interval>>();
  }
```

# А также реализуем метод <code>IObservable&lt;T&gt;.Subscribe()</code>, позволяющий наблюдателям подписываться на уведомления.

Print:
```


  public IDisposable Subscribe(IObserver<Interval> observer)
  {
    if (!observers.Contains(observer))
    {
      observers.Add(observer);
      observer.OnNext(this);
    }
    return null;
  }
```

Select "IDisposable"
+ Select "return |||null|||;"

# Обратите внимание, что метод должен возвращать реализацию <code>IDisposable</code>, позволяющую наблюдателю отписаться от получения уведомлений в произвольный момент. В данном примере такой функционал не требуется, поэтому просто возвращаем <code>null</code>.

Select name of "class IntervalWindow"

# Теперь надо реализовать интерфейс Наблюдателя (<code>Observer</code>). Для этого унаследуем класс окна от интерфейса <code>IObserver&lt;T&gt;</code>.

Go to "IntervalWindow : Form|||"

Print ", IObserver<Interval>"

Go to before "OnTextBoxLeave"

# И реализуем методы интерфейса <code>IObserver&lt;T&gt;.OnNext()</code>, <code>IObserver&lt;T&gt;.OnError()</code> и <code>IObserver&lt;T&gt;.OnCompleted()</code>.

Print:
```
  public void OnNext(Interval interval)
  {
  }
  // No implementation needed: Method is not called by the Interval class.
  public void OnError(Exception e)
  {
    // No implementation.
  }
  // No implementation needed: Method is not called by the Interval class.
  public void OnCompleted()
  {
    // No implementation.
  }
```

Select name of "OnNext"

# Первый метод отвечает за обработку приходящих уведомлений. Его мы наполним позже.

Select name of "OnError"
+ Select name of "OnCompleted"

# Оставшиеся два отвечают за обработку ошибок и завершение работы с уведомлениями. В нашем примере они не используются, поэтому мы не будем их наполнять, о чем и написано в соответствующих комментариях.

Select "subject"

# Теперь надо создать код инициализации поля, содержащего экземпляр предметного класса <code>Interval</code>, а также подписать класс окна на уведомления. Весь этот код стоит поместить в конструктор <code>IntervalWindow</code>.

Go to the end of "public IntervalWindow"

Wait 500ms

Print:
```


    subject = new Interval();
    subject.Subscribe(this);
    OnNext(subject);
```

Select "|||OnNext|||(subject);"

#^ Здесь вызов метода <code>OnNext()</code> гарантирует, что при открытии, объект окна (GUI) заполнится данными из объекта предметной области.

#C Запустим компиляцию и тестирование. Да, никаких реальных изменений мы пока ещё не внесли, но ошибки можно совершить в простейших вещах, поэтому лучше всегда держать код проверенным.

#S Всё хорошо, можно продолжать.

Set step 3

Select "tbStart.Text"
+ Select "tbEnd.Text"
+ Select "tbLength.Text"

# Пора браться за текстовые поля. Произведём их <a href="/self-encapsulate-field">самоинкапсуляцию</a>. Для этого создадим строковые свойства для содержимого каждого из полей. Начнём с поля начала интервала.

Go to before "public IntervalWindow"

Print:
```

  private string Start
  {
    get{ return tbStart.Text; }
    set{ tbStart.Text = value; }
  }

```

#^ То же самое проделаем и для двух оставшихся полей.

Go to before "public IntervalWindow"

Print:
```
  private string End
  {
    get{ return tbEnd.Text; }
    set{ tbEnd.Text = value; }
  }
  private string Length
  {
    get{ return tbLength.Text; }
    set{ tbLength.Text = value; }
  }

```

# После чего можно заменить все прямые обращения к содержимому полей на обращения к соответствующим свойствам.

Select "tbStart.Text" in "CalculateLength"
+ Select "tbStart.Text" in "CalculateEnd"

Wait 1000ms

Type "this.Start"

Wait 500ms

Select "tbEnd.Text" in "CalculateLength"
+ Select "tbEnd.Text" in "CalculateEnd"

Wait 1000ms

Type "this.End"

Wait 500ms

Select "tbLength.Text" in "CalculateLength"
+ Select "tbLength.Text" in "CalculateEnd"

Wait 1000ms

Type "this.Length"

Wait 500ms

Set step 4

Select name of "OnTextBoxLeave"

# Надо учесть, что в нашем случае, в отличие от обычной самоинкапсуляции, пользователь сам может менять значения текстовых полей в окне, поэтому нам нужно позаботиться о том, чтобы такие изменения сохранились. Для этого при потере фокуса полем будем принудительно вызывать сеттер соответствующего свойства, передавая в него содержимое текстового поля.

# На последнем шаге мы изменим эти сеттеры таким образом, чтобы они меняли значения в экземпляре предметного класса, что, в свою очередь, приведёт к перерасчету интервалов и отправке уведомления с новыми значениями в наше окно. В итоге мы получим в текстовые поля актуальные (рассчитанные) значения. Итак, внесем соответствующие изменения.

Go to:
```
if (tb == tbStart)
      {|||
```

Print:
```

        this.Start = tb.Text;
```

Wait 500ms

Go to:
```
else if (tb == tbEnd)
      {|||
```

Print:
```

        this.End = tb.Text;
```

Wait 500ms

Go to:
```
else if (tb == tbLength)
      {|||
```

Print:
```

        this.Length = tb.Text;
```

Wait 500ms

Set step 5

Select name of "Interval"

# Отлично! После того как поля ввода полностью инкапсулированы, мы можем добавить соответствующие поля в класс предметной области.

Go to:
```
private List<IObserver<Interval>> observers;
|||
```

Print:
```
  private string  start = "0",
                  end = "0",
                  length = "0";

```

Select ""0"" in "Interval"

# Инициализируем мы эти поля теми же значениями, что и поля в GUI.

Go to before "public Interval"

# Создадим публичные свойства для доступа к полям.

Print:
```

  public string Start
  {
    get{ return start; }
    set{ }
  }
  public string End
  {
    get{ return end; }
    set{ }
  }
  public string Length
  {
    get{ return length; }
    set{ }
  }

```

Select "set{ }"

# Сеттеры этих свойств должны не только менять значения соответствующих полей, но еще и оповещать наблюдателей о внесенных изменениях, если устанавливаемое значение не совпадает с предыдущим. Реализуем описанную логику в отдельном методе, чтобы избежать дублирования кода.

Go to before "Subscribe" in "Interval"

Print:
```

  private void OnValueChanged(ref string oldValue, string newValue)
  {
    if (!string.Equals(oldValue, newValue, StringComparison.Ordinal))
    {
      oldValue = newValue;
      foreach (var observer in observers)
        observer.OnNext(this);
    }
  }
```

Select name of "OnValueChanged"

# После чего добавим вызов этого метода в наши сеттеры.

Go to:
```
start; }
    set{ |||
```

Print "OnValueChanged(ref start, value); "

Wait 500ms

Go to:
```
end; }
    set{ |||
```

Print "OnValueChanged(ref end, value); "

Wait 500ms

Go to:
```
length; }
    set{ |||
```

Print "OnValueChanged(ref length, value); "

Wait 500ms

Select "private string |||Start|||"
+ Select "private string |||End|||"
+ Select "private string |||Length|||"

# После того как в предметный класс были добавлены дублирующие свойства, изменим сеттеры и геттеры инкапсулирующих свойств класса-наблюдателя так, чтобы они делегировали доступ к свойствам экземпляра предметного класса.

Select "tbStart.Text" in "IntervalWindow"

Replace "subject.Start"

Wait 500ms

Select "tbEnd.Text" in "IntervalWindow"

Replace "subject.End"

Wait 500ms

Select "tbLength.Text" in "IntervalWindow"

Replace "subject.Length"

Wait 500ms

Select "observer.OnNext(this);" in "OnValueChanged"

# Как было сказано ранее, при изменении значений свойств в предметном классе мы оповещаем наблюдающий объект окна о том, что пора обновить значения своих полей ввода...

+ Select "void |||OnNext|||"

# ...что приводит нас к вызову метода <code>OnNext()</code> в классе окна. Он сейчас пуст, поэтому давайте наполним его соответствующим кодом.

Go to the start of "OnNext"

Print:
```

    tbStart.Text = interval.Start;
    tbEnd.Text = interval.End;
    tbLength.Text = interval.Length;
```

Select "tbStart.Text"
+ Select "tbEnd.Text"
+ Select "tbLength.Text"

# В этом методе необходим прямой доступ к текстовым полям, т.к. использование сеттеров инкапсулирующих свойств привело бы к бесконечной рекурсии.

#C После завершения всех перестановок с полями можно запустить компиляцию и тестирование.

#S Всё отлично, можно продолжать.

Select name of "CalculateLength"
+ Select name of "CalculateEnd"

# Для завершения рефакторинга нам осталось только перенести методы <code>CalculateLength()</code> и <code>CalculateEnd()</code> в предметный класс.

Select whole "CalculateLength"

# Переносим метод расчёта длины.

Remove selected

Wait 500ms

Go to the end of "class Interval"

Print:
```

  public void CalculateLength()
  {
    int start = int.Parse(this.Start);
    int end = int.Parse(this.End);
    int length = end - start;
    this.Length = length.ToString();
  }
```

Wait 500ms

Select "CalculateLength()" in "OnTextBoxLeave"

# После чего соответствующим образом изменяем вызывающий код.

Replace "subject.CalculateLength()"

Wait 500ms

Select:
```

  private void CalculateEnd()
  {
    int start = int.Parse(this.Start);
    int length = int.Parse(this.Length);
    int end = start + length;
    this.End = end.ToString();
  }
```

# Аналогичным образом поступаем и с методом расчёта конечного значения.

Remove selected

Wait 500ms

Go to the end of "class Interval"

Print:
```

  public void CalculateEnd()
  {
    int start = int.Parse(this.Start);
    int length = int.Parse(this.Length);
    int end = start + length;
    this.End = end.ToString();
  }
```

Wait 500ms

Select "CalculateEnd()" in "OnTextBoxLeave"

Wait 500ms

Replace "subject.CalculateEnd()"

Wait 500ms

Select name of "Interval"

# В результате проведённого рефакторинга мы получили класс предметной области, содержащий в себе все поведения и вычисления над исходными данными, причём отдельно от кода пользовательского интерфейса.

#C Запускаем финальную компиляцию и тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.