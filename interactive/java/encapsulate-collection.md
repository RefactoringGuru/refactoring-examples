encapsulate-collection:java

###

1. Создайте методы для добавления и удаления элементов коллекции.

2. Присвойте полю пустую коллекцию в качестве начального значения.

3. Найдите вызовы сеттера поля коллекции. Измените сеттер так, чтобы он использовал операции добавления и удаления элементов.

4. Найдите вызовы геттера коллекции, ведущие к её изменению. Поменяйте этот код так, чтобы там использовались новые методы добавления и удаления элементов коллекции.

5. Измените геттер, чтобы он возвращал представление коллекции, доступное только для чтения.

6. Исследуйте клиентский код, использующий коллекцию, в поисках кода, который бы лучше смотрелся внутри самого класса коллекции.



###

```
class Course {
  public Course(String name, boolean isAdvanced) {
    // ...
  }
  public boolean isAdvanced() {
    // ...
  }
}

class Person {
  private Set courses;

  public Set getCourses() {
    return courses;
  }
  public void setCourses(Set arg) {
    courses = arg;
  }
}

// Клиентский код
Person kent = new Person();
Set s = new HashSet();
s.add(new Course("Smalltalk Programming", false));
s.add(new Course("Appreciating Single Malts", true));
kent.setCourses(s);
Assert.equals(2, kent.getCourses().size());
Course refact = new Course("Refactoring", true);
kent.getCourses().add(refact);
kent.getCourses().add(new Course("Brutal Sarcasm", false));
Assert.equals(4, kent.getCourses().size());
kent.getCourses().remove(refact);
Assert.equals(3, kent.getCourses().size());

Iterator iter = kent.getCourses().iterator();
int count = 0;
while (iter.hasNext()) {
  Course each = (Course) iter.next();
  if (each.isAdvanced()) {
    count++;
  }
}
System.out.print("Advanced courses: " + count);
```

###

```
class Course {
  public Course(String name, boolean isAdvanced) {
    // ...
  }
  public boolean isAdvanced() {
    // ...
  }
}

class Person {
  private Set courses = new HashSet();

  public Set getCourses() {
    return Collections.unmodifiableSet(courses);
  }
  public void initializeCourses(Set arg) {
    Assert.isTrue(courses.isEmpty());
    courses.addAll(arg);
  }
  public void addCourse(Course arg) {
    courses.add(arg);
  }
  public void removeCourse(Course arg) {
    courses.remove(arg);
  }
  public int numberOfAdvancedCourses() {
    Iterator iter = getCourses().iterator();
    int count = 0;
    while (iter.hasNext()) {
      Course each = (Course) iter.next();
      if (each.isAdvanced()) {
        count++;
      }
    }
    return count;
  }
  public int numberOfCourses() {
    return courses.size();
  }
}

// Клиентский код
Person kent = new Person();
kent.addCourse(new Course("Smalltalk Programming", false));
kent.addCourse(new Course("Appreciating Single Malts", true));
Assert.equals(2, kent.numberOfCourses());
Course refact = new Course("Refactoring", true);
kent.addCourse(refact);
kent.addCourse(new Course("Brutal Sarcasm", false));
Assert.equals(4, kent.numberOfCourses());
kent.removeCourse(refact);
Assert.equals(3, kent.numberOfCourses());

System.out.print("Advanced courses: " + kent.numberOfAdvancedCourses());
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

Go to the end of "class Person"

# Итак, первым делом нужны надлежащие методы модификации для этой коллекции.

Print:
```

  public void addCourse(Course arg) {
    courses.add(arg);
  }
  public void removeCourse(Course arg) {
    courses.remove(arg);
  }
```

Set step 2

Go to "private Set courses|||"

# Также давайте облегчим себе жизнь, проинициализировав поле:

Print " = new HashSet()"

Set step 3

Select name of "setCourses"

# Теперь посмотрим на пользователей сеттера <code>setCourses</code>. Если клиентов много и сеттер интенсивно используется, необходимо заменить тело метода так, чтобы в нем использовались операции добавления и удаления.

Select "kent.setCourses(s)"

# Сложность этой процедуры зависит от способа использования сеттера коллекции. В простейшем из них клиент инициализирует значения с помощью сеттера, т.е. до его применения курсов не существует.

Select body of "setCourses"

# В этом случае нужно изменить тело сеттера так, чтобы в нем использовался метод добавления.

Print:
```
    Assert.isTrue(courses.isEmpty());
    Iterator iter = arg.iterator();
    while (iter.hasNext()) {
      addCourse((Course) iter.next());
    }
```

Select name of "setCourses"

# После такой модификации стоит с помощью <a href="/rename-method">переименования метода</a> сделать намерения более ясными.

Select "setCourses"

Replace "initializeCourses"

# В общем случае мы должны сначала прибегнуть к методу удаления и убрать все элементы, а затем добавлять новые. Однако это происходит редко (как и бывает с общими случаями).

# Если мы знаем, что другого поведения при добавлении элементов во время инициализации нет, можно убрать цикл и применить метод <code>addAll()</code>.


Select:
```
    Iterator iter = arg.iterator();
    while (iter.hasNext()) {
      addCourse((Course) iter.next());
    }
```

Replace:
```
    courses.addAll(arg);
```

# Стоит упомянуть, что мы не можем просто присвоить значение множеству, даже если предыдущее множество было пустым. Если клиент соберётся модифицировать множество после того, как передаст его, это станет нарушением инкапсуляции. Поэтому мы должны создать копию.

Select:
```
Set s = new HashSet();

```

# Если клиенты просто создают множество и пользуются методом установки, мы можем заставить их пользоваться методами добавления и удаления непосредственно...

Remove selected

Select:
```
|||s.add|||(new Course("Smalltalk Programming", false));
|||s.add|||(new Course("Appreciating Single Malts", true));
```

Replace "kent.addCourse"

Wait 500ms

Select:
```
kent.initializeCourses(s);

```

# ...и полностью убрать вызов метода инициализации.

Remove selected

Set step 4


Select "getCourses().add"
+ Select "getCourses().remove"

# Теперь нужно рассмотреть, кто использует геттер коллекции. В первую очередь нас должны интересовать случаи модификации коллекции с его помощью.

# Такие вызовы следует заменять вызовами метода добавления или удаления курсов.

Select "getCourses().add"

Replace "addCourse"

Wait 500ms

Select "getCourses().remove"

Replace "removeCourse"

Set step 5

Select:
```
return |||courses|||;
```

# Последним штрихом следует изменить тело геттера так, чтобы он возвращал значение, доступное только для чтения (другими словами неизменяемое представление коллекции).


Print:
```
Collections.unmodifiableSet(courses)
```

#C Запустим компиляцию, чтобы убедиться в отсутствии ошибок.

#S Отлично, все работает!

Select:
```
private Set |||courses|||
```

# После этого коллекцию можно считать полностью инкапсулированной. Никто не сможет изменить её элементы, кроме как через методы <code>Person</code>.

Set step 6

Select:
```
Iterator iter = kent.getCourses().iterator();
int count = 0;
while (iter.hasNext()) {
  Course each = (Course) iter.next();
  if (each.isAdvanced()) {
    count++;
  }
}

```

# После того как для класса <code>Person</code> был создан корректный интерфейс, мы можем заняться перемещением релевантного кода в этот класс. Вот пример такого кода.

# Применим <a href="/extract-method">извлечение метода</a> к этому коду, чтобы переместить его в <code>Person</code>.

Go to the end of "class Person"

Print:
```

  public int numberOfAdvancedCourses() {
    Iterator iter = getCourses().iterator();
    int count = 0;
    while (iter.hasNext()) {
      Course each = (Course) iter.next();
      if (each.isAdvanced()) {
        count++;
      }
    }
    return count;
  }
```

Select:
```
Iterator iter = kent.getCourses().iterator();
int count = 0;
while (iter.hasNext()) {
  Course each = (Course) iter.next();
  if (each.isAdvanced()) {
    count++;
  }
}

```

Remove selected

Select:
```
System.out.print("Advanced courses: " + |||count|||);
```

Replace "kent.numberOfAdvancedCourses()"

Select "kent.getCourses().size()"

# Часто встречается такой код.

Go to the end of "Person"

# Его можно заменить более читабельной версией.

Print:
```

  public int numberOfCourses() {
    return courses.size();
  }
```

Select "kent.getCourses().size()"

Replace "kent.numberOfCourses()"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.