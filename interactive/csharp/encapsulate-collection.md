encapsulate-collection:csharp

###

1. Создайте методы для добавления и удаления элементов коллекции.

2. Присвойте полю пустую коллекцию в качестве начального значения.

3. Найдите вызовы сеттера свойства коллекции. Измените сеттер так, чтобы он использовал операции добавления и удаления элементов. При этом рассмотрите возможность извлечения тела сеттера в отдельный метод.

4. Найдите вызовы геттера коллекции, ведущие к её изменению. Поменяйте этот код так, чтобы там использовались новые методы добавления и удаления элементов коллекции.

5. Измените геттер, чтобы он возвращал представление коллекции, доступное только для чтения.

6. Исследуйте клиентский код, использующий коллекцию, в поисках кода, который бы лучше смотрелся внутри самого класса коллекции.



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

# Давайте рассмотрим <i>Инкапсуляцию коллекции</i> на примере каталога обучающих курсов.

Select name of "Course"

# Класс курсов довольно прост.

Select name of "Person"

# Кроме того есть ещё класс посетителей курсов.

Go to "Person kent = |||new Person();"

# При таком интерфейсе клиенты добавляют курсы с помощью следующего кода.

Go to the end of "public class Person"

# Итак, первым делом нужны надлежащие методы модификации для этой коллекции.

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

# Также давайте облегчим себе жизнь, проинициализировав поле:

Print " = new List<Course>()"

Set step 3

Select "|||set|||{" in "Person"

# Теперь посмотрим на пользователей сеттера свойства <code>Courses</code>. Если клиентов много и свойство интенсивно используется, необходимо заменить тело сеттера так, чтобы в нем использовались операции добавления и удаления.

Select "kent.Courses = s;"

# Сложность этой процедуры зависит от способа использования сеттера коллекции. В простейшем из них клиент инициализирует значения с помощью сеттера, т.е. до его применения курсов не существует.

Select "courses = value;"

# В этом случае нужно изменить тело сеттера так, чтобы в нем использовался метод добавления.

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

# Такому коду уже не место в сеттере свойства, поэтому стоит вынести его в отдельный метод, применив <a href="/extract-method">извлечение метода</a>.

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

# После выделения сеттера в отдельный метод надо заменить соответствующим образом и весь связанный с ним клиентский код.

Replace "kent.InitializeCourses(s)"

Wait 500ms

Select:
```
    set{

    }

```

# Данный сеттер больше нигде не используется, поэтому можем удалить его из публичного доступа.

Remove selected

Wait 500ms

Select name of "InitializeCourses"

# В общем случае мы должны сначала прибегнуть к методу удаления и убрать все элементы, а затем добавлять новые. Однако это происходит редко (как и бывает с общими случаями).

Select:
```
    foreach (Course c in newCourses)
      AddCourse(c);
```

# Если мы знаем, что другого поведения при добавлении элементов во время инициализации нет, можно убрать цикл и применить метод <code>AddRange()</code>.

Replace:
```
    courses.AddRange(newCourses);
```

Wait 500ms

Select "courses.AddRange(newCourses)"

# Стоит упомянуть, что мы не можем просто присвоить значение множеству, даже если предыдущее множество было пустым. Если клиент соберётся модифицировать множество после того, как передаст его, это станет нарушением инкапсуляции. Поэтому мы должны создать копию.

Select:
```
List<Course> s = new List<Course>();


```

# Если клиенты просто создают множество и пользуются методом установки, мы можем заставить их пользоваться методами добавления и удаления непосредственно...

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

# ...и полностью убрать вызов метода инициализации.

Remove selected

Set step 4

Select "kent.|||Courses.Add|||"
+ Select "kent.|||Courses.Remove|||"

# Теперь нужно рассмотреть, кто использует геттер коллекции. В первую очередь нас должны интересовать случаи модификации коллекции с его помощью.

# Такие вызовы следует заменять вызовами метода добавления или удаления курсов.

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

# Последним штрихом следует изменить тело геттера так, чтобы он возвращал значение, доступное только для чтения (другими словами неизменяемое представление коллекции).

Print "new ReadOnlyCollection<Course>(courses)"

Wait 500ms

Select "public |||List|||<Course>" in "Person"

Replace "ReadOnlyCollection"

#C Запустим компиляцию, чтобы убедиться в отсутствии ошибок.

#S Отлично, все работает!

Select:
```
private List<Course> |||courses|||
```

# После этого коллекцию можно считать полностью инкапсулированной. Никто не сможет изменить её элементы, кроме как через методы <code>Person</code>.

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

# После того как для класса <code>Person</code> был создан корректный интерфейс, мы можем заняться перемещением релевантного кода в этот класс. Вот пример такого кода.

# Давайте выделим его в публичное свойство, доступное только для чтения.

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

# Часто встречается такой код.

Go to before "InitializeCourses"

# Его также можно вынести в отдельное свойство класса <code>Person</code>

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

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.