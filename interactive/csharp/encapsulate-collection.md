encapsulate-collection:csharp

###

1.ru. Создайте методы для добавления и удаления элементов коллекции.
1.uk. Створіть методи для додавання і видалення елементів колекції.

2.ru. Присвойте полю пустую коллекцию в качестве начального значения.
2.uk. Присвойте полю порожню колекцію в якості початкового значення.

3.ru. Найдите вызовы сеттера свойства коллекции. Измените сеттер так, чтобы он использовал операции добавления и удаления элементов. При этом рассмотрите возможность извлечения тела сеттера в отдельный метод.
3.uk. Знайдіть визови сетера властивості, яка містить коллекцію. Змініть сетер так, щоб він використав операції додавання і видалення елементів. При цьому зважте можливість виділення сеттера в окремий метод.

4.ru. Найдите вызовы геттера коллекции, ведущие к её изменению. Поменяйте этот код так, чтобы там использовались новые методы добавления и удаления элементов коллекции.
4.uk. Знайдіть усі виклики геттера колекції, після яких відбувається зміна колекції. Поміняйте цей код так, щоб там використовувалися ваші нові методи додавання і видалення елементів колекції.

5.ru. Измените геттер, чтобы он возвращал представление коллекции, доступное только для чтения.
5.uk. Змініть геттер так, щоб він повертав представлення колекції, доступне тільки для читання.

6.ru. Исследуйте клиентский код, использующий коллекцию, в поисках кода, который бы лучше смотрелся внутри самого класса коллекции.
6.uk. Обстежте клієнтський код, що використовує колекцію, у пошуках ділянки, яку краще перемістити всередину самого класу колекції.



###

```
public class Course
{
  public bool IsAdvanced
  {
    get;
    set;
  }

  public Course(string name, bool isAdvanced = false)
  {
    // ...
  }
}

public class Person
{
  private List<Course> courses;

  public List<Course> Courses
  {
    get{
      return courses;
    }
    set{
      courses = value;
    }
  }
}

// Клиентский код
Person kent = new Person();
List<Course> s = new List<Course>();

s.Add(new Course("Smalltalk Programming"));
s.Add(new Course("Appreciating Single Malts", true));
kent.Courses = s;
Assert.AreEqual(2, kent.Courses.Count);

Course refact = new Course("Refactoring", true);
kent.Courses.Add(refact);
kent.Courses.Add(new Course("Brutal Sarcasm"));
Assert.AreEqual(4, kent.Courses.Count);

kent.Courses.Remove(refact);
Assert.AreEqual(3, kent.Courses.Count);

int count = 0;
foreach (Course c in kent.Courses)
{
  if (c.IsAdvanced)
    count++;
}
Console.WriteLine("Advanced courses: " + count);
```

###

```
public class Course
{
  public bool IsAdvanced
  {
    get;
    set;
  }

  public Course(string name, bool isAdvanced = false)
  {
    // ...
  }
}

public class Person
{
  private List<Course> courses = new List<Course>();

  public ReadOnlyCollection<Course> Courses
  {
    get{
      return new ReadOnlyCollection<Course>(courses);
    }
  }
  public int NumberOfAdvancedCourses
  {
    get{
      int count = 0;
      foreach (Course c in courses)
      {
        if (c.IsAdvanced)
          count++;
      }
      return count;
    }
  }
  public int NumberOfCourses
  {
    get{
      return courses.Count;
    }
  }

  public void InitializeCourses(List<Course> newCourses)
  {
    Assert.IsTrue(courses.Count == 0);
    courses.AddRange(newCourses);
  }
  public void AddCourse(Course course)
  {
    courses.Add(course);
  }
  public void RemoveCourse(Course course)
  {
    courses.Remove(course);
  }
}

// Клиентский код
Person kent = new Person();
kent.AddCourse(new Course("Smalltalk Programming"));
kent.AddCourse(new Course("Appreciating Single Malts", true));
Assert.AreEqual(2, kent.NumberOfCourses);

Course refact = new Course("Refactoring", true);
kent.AddCourse(refact);
kent.AddCourse(new Course("Brutal Sarcasm"));
Assert.AreEqual(4, kent.NumberOfCourses);

kent.RemoveCourse(refact);
Assert.AreEqual(3, kent.NumberOfCourses);

Console.WriteLine("Advanced courses: " + kent.NumberOfAdvancedCourses);
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Инкапсуляцию коллекции</i> на примере каталога обучающих курсов.
#|uk| Давайте розглянемо <i>Інкапсуляцію колекції<i> на прикладі каталогу навчальних курсів.

Select name of "Course"

#|ru| Класс курсов довольно прост.
#|uk| Клас курсів досить простий.

Select name of "Person"

#|ru| Кроме того есть ещё класс посетителей курсов.
#|uk| Крім того є ще клас відвідувачів курсів.

Go to "Person kent = |||new Person();"

#|ru| При таком интерфейсе клиенты добавляют курсы с помощью следующего кода.
#|uk| При такому інтерфейсі клієнти додають курси за допомогою наступного коду.

Go to the end of "public class Person"

#|ru| Итак, первым делом нужны надлежащие методы модификации для этой коллекции.
#|uk| Отже, насамперед потрібні належні методи модифікації для цієї колекції.

Print:
```


  public void AddCourse(Course course)
  {
    courses.Add(course);
  }
  public void RemoveCourse(Course course)
  {
    courses.Remove(course);
  }
```

Set step 2

Go to "private List<Course> courses|||"

#|ru| Также давайте облегчим себе жизнь, проинициализировав поле:
#|uk| Також давайте полегшимо собі життя, проініціалізуваши поле:

Print " = new List<Course>()"

Set step 3

Select "|||set|||{" in "Person"

#|ru| Теперь посмотрим на пользователей сеттера свойства <code>Courses</code>. Если клиентов много и свойство интенсивно используется, необходимо заменить тело сеттера так, чтобы в нем использовались операции добавления и удаления.
#|uk| Тепер подивимося на користувачів сетера властивості <code>Courses</code>. Якщо клієнтів багато і властивість інтенсивно використовується, необхідно замінити тіло сеттера так, щоб в ньому використовувались операції додавання і видалення.

Select "kent.Courses = s;"

#|ru| Сложность этой процедуры зависит от способа использования сеттера коллекции. В простейшем из них клиент инициализирует значения с помощью сеттера, т.е. до его применения курсов не существует.
#|uk| Складність цієї процедури залежить від способу використання сетера колекції. У найпростішому з них клієнт ініціалізує значення за допомогою сетера, тобто до застосування методу курсів не існує.

Select "courses = value;"

#|ru| В этом случае нужно изменить тело сеттера так, чтобы в нем использовался метод добавления.
#|uk| В цьому випадку потрібно змінити тіло сетера так, щоб в ньому використовувався метод додавання.

Print:
```
Assert.IsTrue(courses.Count == 0);
      foreach (Course c in value)
        AddCourse(c);
```

Wait 500ms

Select:
```
      Assert.IsTrue(courses.Count == 0);
      foreach (Course c in value)
        AddCourse(c);
```

#|ru| Такому коду уже не место в сеттере свойства, поэтому стоит вынести его в отдельный метод, применив <a href="/extract-method">извлечение метода</a>.
#|uk| Такому коду вже не місце в сетері властивості, тому варто винести його в окремий метод, застосувавши <a href="/extract-method">витяг методу</a>.

Remove selected

Go to before "AddCourse"

Print:
```

  public void InitializeCourses(List<Course> newCourses)
  {
    Assert.IsTrue(courses.Count == 0);
    foreach (Course c in newCourses)
      AddCourse(c);
  }
```

Wait 500ms

Select "kent.Courses = s"

#|ru| После выделения сеттера в отдельный метод, надо заменить соответствующим образом и весь связанный с ним клиентский код.
#|uk| Після виділення сетера в окремий метод, треба замінити відповідним чином і весь пов'язаний з ним клієнтський код.

Replace "kent.InitializeCourses(s)"

Wait 500ms

Select:
```
    set{

    }

```

#|ru| Данный сеттер больше нигде не используется, поэтому можем удалить его из публичного доступа.
#|uk| Даний сетер більше ніде не використовується, тому можемо видалити його з публічного доступу.

Remove selected

Wait 500ms

Select name of "InitializeCourses"

#|ru| В общем случае мы должны сначала прибегнуть к методу удаления и убрать все элементы, а затем добавлять новые. Однако это происходит редко (как и бывает с общими случаями).
#|uk| У загальному випадку ми повинні спочатку вдатися до методу видалення і прибрати всі елементи, а потім додавати нові. Однак це відбувається рідко (як і буває з загальними випадками).

Select:
```
    foreach (Course c in newCourses)
      AddCourse(c);
```

#|ru| Если мы знаем, что другого поведения при добавлении элементов во время инициализации нет, можно убрать цикл и применить метод <code>AddRange()</code>.
#|uk| Якщо ми знаємо, що іншої поведінки при додаванні елементів під час ініціалізації немає, можна прибрати цикл і застосувати метод <code>AddRange()</code>.

Replace:
```
    courses.AddRange(newCourses);
```

Wait 500ms

Select "courses.AddRange(newCourses)"

#|ru| Стоит упомянуть, что мы не можем просто присвоить значение множеству, даже если предыдущее множество было пустым. Если клиент соберётся модифицировать множество после того, как передаст его, это станет нарушением инкапсуляции. Поэтому мы должны создать копию.
#|uk| Варто згадати, що ми не можемо просто привласнити значення множини, навіть якщо попередня множина була порожньою. Якщо клієнт збереться модифікувати множину після того, як передасть її, це стане порушенням інкапсуляції. Тому ми повинні створити копію.

Select:
```
List<Course> s = new List<Course>();


```

#|ru| Если клиенты просто создают множество и пользуются методом установки, мы можем заставить их пользоваться методами добавления и удаления непосредственно...
#|uk| Якщо клієнти просто створюють множину і користуються методом установки, ми можемо змусити їх користуватися методами додавання та видалення безпосередньо ,а також повністю прибрати виклик методу ініціалізації.

Remove selected

Select:
```
|||s.Add|||(new Course("Smalltalk Programming"));
|||s.Add|||(new Course("Appreciating Single Malts", true));
```

Replace "kent.AddCourse"

Wait 500ms

Select:
```
kent.InitializeCourses(s);

```

#|ru| ...и полностью убрать вызов метода инициализации.
#|uk| ...і повністю прибрати виклик методу ініціалізації.

Remove selected

Set step 4

Select "kent.|||Courses.Add|||"
+ Select "kent.|||Courses.Remove|||"

#|ru| Теперь нужно рассмотреть, кто использует геттер коллекции. В первую очередь нас должны интересовать случаи модификации коллекции с его помощью.
#|uk| Тепер потрібно розглянути, хто використовує геттер колекції. В першу чергу нас повинні цікавити випадки модифікації колекції з його допомогою.

#|ru| Такие вызовы следует заменять вызовами метода добавления или удаления курсов.
#|uk| Такі виклики слід замінювати викликами методу додавання або видалення курсів.

Select "kent.|||Courses.Add|||"

Replace "AddCourse"

Wait 500ms

Select "kent.|||Courses.Remove|||"

Replace "RemoveCourse"

Set step 5

Select:
```
return |||courses|||;
```

#|ru| Последним штрихом следует изменить тело геттера так, чтобы он возвращал значение, доступное только для чтения (другими словами неизменяемое представление коллекции).
#|uk| Останнім штрихом слід змінити тіло геттера так, щоб він повертав значення, доступне тільки для читання (іншими словами незмінне уявлення колекції).

Print "new ReadOnlyCollection<Course>(courses)"

Wait 500ms

Select "public |||List|||<Course>" in "Person"

Replace "ReadOnlyCollection"

#C|ru| Запустим компиляцию, чтобы убедиться в отсутствии ошибок.
#S Отлично, все работает!

#C|uk| Запустимо компіляцію, щоб переконатися у відсутності помилок.
#S Супер, все працює.

Select:
```
private List<Course> |||courses|||
```

#|ru| После этого коллекцию можно считать полностью инкапсулированной. Никто не сможет изменить её элементы, кроме как через методы <code>Person</code>.
#|uk| Після цього колекцію можна вважати повністю інкапсульованою. Ніхто не зможе змінити її елементи, крім як через методи <code>Person</code>.

Set step 6

Select:
```
int count = 0;
foreach (Course c in kent.Courses)
{
  if (c.IsAdvanced)
    count++;
}

```

#|ru| После того как для класса <code>Person</code> был создан корректный интерфейс, мы можем заняться перемещением релевантного кода в этот класс. Вот пример такого кода.
#|uk| Після того як для класу <code>Person</code> був створений коректний інтерфейс, ми можемо зайнятися переміщенням релевантного коду в цей клас. Ось приклад такого коду.

#|ru| Давайте выделим его в публичное свойство, доступное только для чтения.
#|uk| Давайте виділимо його в публічне властивість, доступне лише для читання.

Go to before "InitializeCourses"

Print:
```
  public int NumberOfAdvancedCourses
  {
    get{
      int count = 0;
      foreach (Course c in courses)
      {
        if (c.IsAdvanced)
          count++;
      }
      return count;
    }
  }

```

Wait 500ms

Select:
```
int count = 0;
foreach (Course c in kent.Courses)
{
  if (c.IsAdvanced)
    count++;
}

```

Wait 500ms

Remove selected

Wait 500ms

Select:
```
Console.WriteLine("Advanced courses: " + |||count|||);
```

Replace "kent.NumberOfAdvancedCourses"

Wait 500ms

Select "kent.Courses.Count"

#|ru| Часто встречается такой код.
#|uk| Часто зустрічається такий код.

Go to before "InitializeCourses"

#|ru| Его также можно вынести в отдельное свойство класса <code>Person</code>
#|uk| Його також можна винести в окреме властивість класу <code>Person</code>

Print:
```
  public int NumberOfCourses
  {
    get{
      return courses.Count;
    }
  }

```

Select "kent.Courses.Count"

Replace "kent.NumberOfCourses"

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.