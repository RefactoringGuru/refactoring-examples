duplicate-observed-data:csharp

###

1.ru. Создайте класс предметной области.
1.uk. Створіть клас предметної області.

2.ru. Примените паттерн Наблюдатель. Сделайте класс пользовательского интерфейса наблюдателем за классом предметной области.
2.uk. Застосуйте патерн Спостерігач. Зробіть клас користувальницького інтерфейсу спостерігачем за класом предметної області.

3.ru. Скройте прямой доступ к полям пользовательского интерфейса.
3.uk. Приховайте прямий доступ до полів користувальницького інтерфейсу.

4.ru. Используйте сеттеры для установки значений полей в ответ на активность пользователя в интерфейсе.
4.uk. Використовуйте сеттери для установки значень полів у відповідь на активність користувача в інтерфейсі.

5.ru. Переместите необходимые поля из класса GUI в класс предметной области. При этом, изменяйте методы доступа в классе интерфейса так, чтобы они обращались к полям класса предметной области.
5.uk. Перемістіть необхідні поля з класу GUI в клас предметної області. При цьому, змінюйте методи доступу в класі інтерфейсу так, щоб вони зверталися до полів класу предметної області.



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

#|ru| Давайте рассмотрим <i>Дублирование видимых данных</i> на примере класса для создания окна редактирования интервала чисел.
#|uk| Давайте розглянемо <i>Дублювання видимих ​​даних<i> на прикладі класу для створення вікна редагування інтервалу чисел.

Select name of "public IntervalWindow"

#|ru|< Окно состоит из трёх полей ввода: стартовое значение (Start), конечное значение (End) и результирующая длина (Length). <br/><img src="/images/refactoring/gui-window_csharp.png">
#|uk|< Вікно складається з трьох полів: стартове значення (Start), кінцеве значення (End) і результуюча довжина (Length).<br/><img src="/images/refactoring/gui-window_csharp.png">

Select name of "OnTextBoxLeave"

#|ru|V Перерасчёты новых значений происходят при потере фокуса элементом (обработчик привязан ко всем полям ввода). При изменении текстовых полей <code>Start</code> или <code>End</code> вычисляется <code>Length</code>, при изменении поля <code>Length</code> вычисляется <code>End</code>.
#|uk|V Перерахунки нових значень відбуваються при втраті фокуса елементом. При зміні полів <code>Start</code> або <code>End</code> обчислюється <code>Length</code>; при зміні поля <code>Length</code> обчислюється <code>End</code>.

Select "CalculateLength();"
+ Select "CalculateEnd();"

#|ru|V При этом конкретные вычисления происходят в служебных методах.
#|uk|V При цьому конкретні обчислення відбуваються в службових методах.

Select name of "CalculateLength"
+ Select name of "CalculateEnd"

#|ru| Эти методы вызывают вычисление новой длины (<code>CalculateLength</code>) или нового конечного значения (<code>CalculateEnd</code>) в зависимости от того, что поменялось в окне.
#|uk| Ці методи викликають обчислення нової довжини (<code>CalculateLength</code>) або нового кінцевого значення (<code>CalculateEnd</code>) залежно від того, що помінялося у вікні.

Go to the end of file

#|ru| Нашей задачей станет выделение всех перерасчётов длины и конечного значения в отдельный класс предметной области. Начнём с создания такого класса.
#|uk| Нашим завданням стане відділення всіх перерахунків довжини і кінцевого значення в окремий клас предметної області. Почнемо із створення такого класу.

Print:
```


public class Interval
{
}
```

#|ru| После того как был создан класс предметной области, стоит поместить ссылку на него из класса окна.
#|uk| Після того, як був створений клас предметної області, варто помістити посилання на нього з класу вікна.

Go to start of "class IntervalWindow"

Print:
```

  private Interval subject;

```

Wait 500ms

Set step 2

Select name of "class Interval"

#|ru| Теперь надо реализовать паттерн Наблюдатель. Для этого воспользуемся интерфейсами <code>IObservable&lt;T&gt;</code> и <code>IObserver&lt;T&gt;</code>.
#|uk| Тепер треба реалізувати патерн Спостерігач. Для цього скористаємося інтерфейсами <code>IObservable&lt;T&gt;</code> і <code>IObserver&lt;T&gt;</code>.

#|ru| В нашем случае класс <code>Interval</code> будет реализовывать интерфейс Наблюдаемого (<code>Observable</code>), при этом данные для наблюдения будут храниться в нём же. Начнем с наследования класса от требуемого интерфейса.
#|uk| В нашому випадку клас <code>Interval</code> буде реалізовувати інтерфейс Спостерігаємого (<code>Observable</code>), при цьому дані для спостереження будуть зберігатися в ньому ж. Почнемо з наслідування класу від запланованого інтерфейсу.

Go to "public class Interval|||"

Print ": IObservable<Interval>"

Go to start of "public class Interval"

#|ru| Затем добавим в этот класс поле, отвечающее за хранение наблюдателей.
#|uk| Потім додамо в цей клас поле, що відповідає за зберігання спостерігачів.

Print:
```

  private List<IObserver<Interval>> observers;
```

Go to end of "class Interval"

#|ru| Проинициализируем его в конструкторе класса.
#|uk| Проініціалізуємо його в конструкторі класу.

Print:
```


  public Interval()
  {
    observers = new List<IObserver<Interval>>();
  }
```

#|ru| А также реализуем метод <code>IObservable&lt;T&gt;.Subscribe()</code>, позволяющий наблюдателям подписываться на уведомления.
#|uk| А також реалізуємо метод <code>IObservable&lt;T&gt;.Subscribe()</code>, що дозволяє спостерігачам підписуватися на сповіщення.

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

#|ru| Обратите внимание, что метод должен возвращать реализацию <code>IDisposable</code>, позволяющую наблюдателю отписаться от получения уведомлений в произвольный момент. В данном примере такой функционал не требуется, поэтому просто возвращаем <code>null</code>.
#|uk| Зверніть увагу, що метод повинен повертати реалізацію <code>IDisposable</code>, що дозволяє спостерігачеві відписатися від одержання сповіщень в будь-який момент. В даному прикладі такий функціонал не потрібен, тому просто повертаємо <code>null</code>.

Select name of "class IntervalWindow"

#|ru| Теперь надо реализовать интерфейс Наблюдателя (<code>Observer</code>). Для этого унаследуем класс окна от интерфейса <code>IObserver&lt;T&gt;</code>.
#|uk| Тепер треба реалізувати інтерфейс Спостерігача (<code>Observer</code>). Для цього успадкуємо клас вікна від інтерфейсу <code>IObserver&lt;T&gt;</code>.

Go to "IntervalWindow : Form|||"

Print ", IObserver<Interval>"

Go to before "OnTextBoxLeave"

#|ru| И реализуем методы интерфейса <code>IObserver&lt;T&gt;.OnNext()</code>, <code>IObserver&lt;T&gt;.OnError()</code> и <code>IObserver&lt;T&gt;.OnCompleted()</code>.
#|uk| І реалізуємо методи інтерфейсу <code>IObserver&lt;T&gt;.OnNext()</code>, <code>IObserver&lt;T&gt;.OnError()</code> і <code>IObserver&lt;T&gt;.OnCompleted()</code >.

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

#|ru| Первый метод отвечает за обработку приходящих уведомлений. Его мы наполним позже.
#|uk| Перший метод відповідає за обробку надходячих сповіщень. Його ми наповнимо пізніше.

Select name of "OnError"
+ Select name of "OnCompleted"

#|ru| Оставшиеся два отвечают за обработку ошибок и завершение работы с уведомлениями. В нашем примере они не используются, поэтому мы не будем их наполнять, о чем и написано в соответствующих комментариях.
#|uk| Інші два відповідають за обробку помилок і завершення роботи із сповіщеннями. У нашому прикладі вони не використовуються, тому ми не будемо їх наповнювати, про що і написано у відповідних коментарях.

Select "subject"

#|ru| Теперь надо создать код инициализации поля, содержащего экземпляр предметного класса <code>Interval</code>, а также подписать класс окна на уведомления. Весь этот код стоит поместить в конструктор <code>IntervalWindow</code>.
#|uk| Тепер треба створити код ініціалізації поля, яке містить екземпляр предметного класу <code>Interval</code>, а також підписати клас вікна на сповіщення. Весь цей код варто помістити в конструктор <code>IntervalWindow</code>.

Go to the end of "public IntervalWindow"

Wait 500ms

Print:
```


    subject = new Interval();
    subject.Subscribe(this);
    OnNext(subject);
```

Select "|||OnNext|||(subject);"

#|ru|^ Здесь вызов метода <code>OnNext()</code> гарантирует, что при открытии, объект окна (GUI) заполнится данными из объекта предметной области.
#|uk|^ Тут виклик методу <code>OnNext()</code> гарантує, що при відкритті, об'єкт вікна (GUI) заповниться даними з об'єкта предметної області.

#C|ru| Запустим компиляцию и тестирование. Да, никаких реальных изменений мы пока ещё не внесли, но ошибки можно совершить в простейших вещах, поэтому лучше всегда держать код проверенным.
#S Всё хорошо, можно продолжать.

#C|uk| Запустимо компіляцію і тестування. Так, жодних реальних змін ми поки ще не внесли, але помилки можна зробити в найпростіших речах, тому краще завжди тримати код перевіреним.
#S Все добре, можна продовжувати.

Set step 3

Select "tbStart.Text"
+ Select "tbEnd.Text"
+ Select "tbLength.Text"

#|ru| Пора браться за текстовые поля. Произведём их <a href="/self-encapsulate-field">самоинкапсуляцию</a>. Для этого создадим строковые свойства для содержимого каждого из полей. Начнём с поля начала интервала.
#|uk| Пора братися за текстові поля. Проведемо їх <a href="/self-encapsulate-field">самоінкапсуляцію</a>. Для цього створимо строкові властивості для вмісту кожного з полів. Почнемо з поля початку інтервалу.

Go to before "public IntervalWindow"

Print:
```

  private string Start
  {
    get{ return tbStart.Text; }
    set{ tbStart.Text = value; }
  }

```

#|ru|^ То же самое проделаем и для двух оставшихся полей.
#|uk|^ Те ж саме зробимо і для двох останніх полів.

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

#|ru| После чего можно заменить все прямые обращения к содержимому полей на обращения к соответствующим свойствам.
#|uk| Після чого можна замінити всі прямі звернення до вмісту полів на звернення до відповідних властивостей.

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

#|ru| Надо учесть, что в нашем случае, в отличие от обычной самоинкапсуляции, пользователь сам может менять значения текстовых полей в окне, поэтому нам нужно позаботиться о том, чтобы такие изменения сохранились. Для этого при потере фокуса полем будем принудительно вызывать сеттер соответствующего свойства, передавая в него содержимое текстового поля.
#|uk| Треба врахувати, що в нашому випадку, на відміну від звичайної самоінкапсуляціі, користувач сам може змінювати значення текстових полів у вікні, тому нам потрібно подбати про те, щоб такі зміни збереглися. Для цього при втраті фокуса полем будемо примусово викликати сеттер відповідної властивості, передаючи в нього вміст текстового поля.

#|ru| На последнем шаге мы изменим эти сеттеры таким образом, чтобы они меняли значения в экземпляре предметного класса, что, в свою очередь, приведёт к перерасчету интервалов и отправке уведомления с новыми значениями в наше окно. В итоге мы получим в текстовые поля актуальные (рассчитанные) значения. Итак, внесем соответствующие изменения.
#|uk| На останньому кроці ми змінимо ці сеттери таким чином, щоб вони змінювали значення в екземплярі предметного класу, що, в свою чергу, призведе до перерахунку інтервалів і відправці повідомлення з новими значеннями в наше вікно. В результаті ми отримаємо в текстових полях актуальні (розраховані) значення. Отже, внесемо відповідні зміни.

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

#|ru| Отлично! После того как поля ввода полностью инкапсулированы, мы можем добавить соответствующие поля в класс предметной области.
#|uk| Супер! Після того, як поля вводу повністю інкапсульовані, ми можемо додати відповідні поля в клас предметної області.

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

#|ru| Инициализируем мы эти поля теми же значениями, что и поля в GUI.
#|uk| Ми ініціалізуємо ці поля тим же значенням, що й поле в GUI.

Go to before "public Interval"

#|ru| Создадим публичные свойства для доступа к полям.
#|uk| Створимо публічні властивості для доступу до полів.

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

#|ru| Сеттеры этих свойств должны не только менять значения соответствующих полей, но еще и оповещать наблюдателей о внесенных изменениях, если устанавливаемое значение не совпадает с предыдущим. Реализуем описанную логику в отдельном методе, чтобы избежать дублирования кода.
#|uk| Сетери цих властивостей повинні не тільки міняти значення відповідних полів, але ще і оповіщати спостерігачів про внесені зміни, якщо встановлюване значення не співпадає з попереднім. Реалізуємо описану логіку в окремому методі, щоб уникнути дублювання коду.

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

#|ru| После чего добавим вызов этого метода в наши сеттеры.
#|uk| Після чого додамо виклик цього методу в наші сеттери.

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

#|ru| После того как в предметный класс были добавлены дублирующие свойства, изменим сеттеры и геттеры инкапсулирующих свойств класса-наблюдателя так, чтобы они делегировали доступ к свойствам экземпляра предметного класса.
#|uk| Після того як в предметний клас були додані дублюючі властивості, змінимо сеттери і геттери інкапсулюючих властивостей класу-спостерігача так, щоб вони делегували доступ до властивостей екземпляра предметного класу.

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

#|ru| Как было сказано ранее, при изменении значений свойств в предметном классе мы оповещаем наблюдающий объект окна о том, что пора обновить значения своих полей ввода...
#|uk| Як було сказано раніше, при зміні значень властивостей в предметному класі ми оповіщаємо що спостерігає об'єкт вікна про те, що пора оновити значення своїх полів введення...

+ Select "void |||OnNext|||"

#|ru| ...что приводит нас к вызову метода <code>OnNext()</code> в классе окна. Он сейчас пуст, поэтому давайте наполним его соответствующим кодом.
#|uk| ...що приводить нас до виклику методу <code>OnNext()</code> в класі вікна. Він зараз порожній, тому давайте наповнимо його відповідним кодом.

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

#|ru| В этом методе необходим прямой доступ к текстовым полям, т.к. использование сеттеров инкапсулирующих свойств привело бы к бесконечной рекурсии.
#|uk| В цьому методі необхідний прямий доступ до текстових полів, т.к. використання сеттерів інкапсулюючих властивостей призвело б до нескінченної рекурсії.

#C|ru| После завершения всех перестановок с полями можно запустить компиляцию и тестирование.
#S Всё отлично, можно продолжать.

#C|uk| Після завершення всіх перестановок з полями можна запустити компіляцію і тестування.
#S Все добре, можна продовжувати.

Select name of "CalculateLength"
+ Select name of "CalculateEnd"

#|ru| Для завершения рефакторинга нам осталось только перенести методы <code>CalculateLength()</code> и <code>CalculateEnd()</code> в предметный класс.
#|uk| Для завершення рефакторінга нам залишилося тільки перенести методи <code>CalculateLength()</code> і <code>CalculateEnd()</code> в предметний клас.

Select whole "CalculateLength"

#|ru| Переносим метод расчёта длины.
#|uk| Переносимо метод розрахунку довжини.

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

#|ru| После чего соответствующим образом изменяем вызывающий код.
#|uk| Після чого відповідним чином змінюємо викликаючий код.

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

#|ru| Аналогичным образом поступаем и с методом расчёта конечного значения.
#|uk| Аналогічним чином робимо і з методом розрахунку кінцевого значення.

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

#|ru| В результате проведённого рефакторинга мы получили класс предметной области, содержащий в себе все поведения и вычисления над исходными данными, причём отдельно от кода пользовательского интерфейса.
#|uk| В результаті ми отримаємо клас предметної області, що містить всі поведінки і обчислення над вихідними даними, причому окремо від коду користувальницького інтерфейсу.

#C|ru| Запускаем финальную компиляцию и тестирование.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію і тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.