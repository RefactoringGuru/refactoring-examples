pull-up-constructor-body:java

###

1. Создайте конструктор в суперклассе.

2. Извлеките общий код из начала конструктора каждого из подклассов в конструктор суперкласса. Перед этим действием стоит попробовать поместить как можно больше общего кода в начало конструктора.

3. Поместите вызов конструктора суперкласса первой строкой в конструкторах подклассов.



###

```
class Employee {
  // ...
  protected String name;
  protected String id;
}
   
class Manager extends Employee {
  // ...
  private int grade;
  public Manager(String name, String id, int grade) {
    this.name = name;
    this.id = id;
    this.grade = grade;
  }
}
```

###

```
class Employee {
  // ...
  protected String name;
  protected String id;
  protected Employee(String name, String id) {
    this.name = name;
    this.id = id;
  }
}
   
class Manager extends Employee {
  // ...
  private int grade;
  public Manager(String name, String id, int grade) {
    super(name, id);
    this.grade = grade;
  }
}
```

###

Set step 1

# Начнём с классов менеджера и служащего. В данном случае <code>Employee</code> вообще не имеет конструктора, а его поля заполняются в классе <code>Manager</code>, который реально используется в программе.

# Таким образом, если мы захотим создать ещё один подкласс <code>Employee</code>, нам придётся дублировать части конструктора, чтобы инициализировать поля <code>Employee</code>.

# Вместо этого мы можем поднять часть тела конструктора <code>Manager</code> в его суперкласс.

Go to the end of "Employee"

# Определим конструктор и сделаем его защищённым. Это даст знать другим программистам, что его нужно вызывать в подклассах.

Print:
```

  protected Employee(String name, String id) {
    this.name = name;
    this.id = id;
  }
```

Set step 3

# После того как новый конструктор стал доступен, его можно вызвать из конструктора <code>Manager</code>.

Select in "Manager":
```
    this.name = name;
    this.id = id;

```

Replace:
```
    super(name, id);

```

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.