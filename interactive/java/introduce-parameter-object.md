introduce-parameter-object:java

###

1. Создайте новый класс, который будет представлять вашу группу параметров. Сделайте так, чтобы данные объектов этого класса нельзя было изменить после создания.

2. В методе, к которому применяем рефакторинг, <a href="/add-parameter">добавьте новый параметр</a>, в котором будет передаваться ваш объект-параметр. Во всех вызовах метода передавайте в этот параметр объект, создаваемый из старых параметров метода.

3. Теперь начинайте по одному удалять старые параметры из метода, заменяя их в коде полями объекта-параметра. Тестируйте программу после каждой замены параметра.

4. По окончанию оцените, есть ли возможность и смысл перенести какую-то часть метода (а иногда и весь метод) в класс объекта-параметра. Если так, используйте <a href="/move-method">перемещение метода</a> или <a href="/extract-method">извлечение метода</a>, чтобы осуществить перенос.



###

```
class Account {
  // ...
  private Vector transactions = new Vector();

  public double getFlowBetween(Date start, Date end) {
    double result = 0;
    Enumeration e = transactions.elements();
    while (e.hasMoreElements()) {
      Entry each = (Entry) e.nextElement();
      if (each.getDate().equals(start) ||
          each.getDate().equals(end) ||
          (each.getDate().after(start) && each.getDate().before(end)))
      {
        result += each.getValue();
      }
    }
    return result;
  }
}

class Transaction {
  private Date chargeDate;
  private double value;

  public Transaction(double value, Date chargeDate) {
    this.value = value;
    this.chargeDate = chargeDate;
  }
  public Date getDate() {
    return chargeDate;
  }
  public double getValue() {
    return value;
  }
}
   
// Somewhere in client code...
double flow = account.getFlowBetween(startDate, endDate);
```

###

```
class Account {
  // ...
  private Vector transactions = new Vector();

  public double getFlowBetween(DateRange range) {
    double result = 0;
    Enumeration e = transactions.elements();
    while (e.hasMoreElements()) {
      Entry each = (Entry) e.nextElement();
      if (range.includes(each.getDate()))
      {
        result += each.getValue();
      }
    }
    return result;
  }
}

class Transaction {
  private Date chargeDate;
  private double value;

  public Transaction(double value, Date chargeDate) {
    this.value = value;
    this.chargeDate = chargeDate;
  }
  public Date getDate() {
    return chargeDate;
  }
  public double getValue() {
    return value;
  }
}

class DateRange {
  private final Date start;
  private final Date end;

  public DateRange(Date start, Date end) {
    this.start = start;
    this.end = end;
  }
  public Date getStart() {
    return start;
  }
  public Date getEnd() {
    return end;
  }    
  public boolean includes(Date arg) {
    return (arg.equals(start) || arg.equals(end) || (arg.after(start) && arg.before(end)));
  }
}
   
// Somewhere in client code...
double flow = account.getFlowBetween(new DateRange(startDate, endDate));
```

###

Set step 1

# Расмотрим этот рефакторинг на примере класса банковского счета и его транзакций.

Select name of "getFlowBetween"

# Нас интересует метод получения суммы транзакиций за указанный период времени.

Select parameters in "getFlowBetween"

# Как видите, метод принимает в качестве параметров диапазон из двух даты. Это доволно частая картина и было бы неплохо вместо передачи двух дат передавать объект диапазона дат.

# В дальнейшем, в такой объект можно было бы перенести операции по проверке вхождения даты в диапазон и проче.

Go to after "Transaction"

# Итак, создадим простой класс диапазонов.

Print:
```


class DateRange {
  private final Date start;
  private final Date end;

  public DateRange(Date start, Date end) {
    this.start = start;
    this.end = end;
  }
  public Date getStart() {
    return start;
  }
  public Date getEnd() {
    return end;
  }    
}
```

Select "private" in "DateRange"

# Заметьте, что класс сделан неизменяемым — поменять даты диапазона после его создания невозможно, т.к. поля дат объявлены как приватные, а сеттеров для них мы не создали.

# Этот шаг позволит избежать многих ошибок связанных с передачей объектов в параметрах по ссылкам.

Set step 2

Go to the parameters end of "getFlowBetween"

# Теперь мы готовы добавить параметр дипазона в метод получения суммы транзакий.

Print ", DateRange range"

# Теперь найдем все места, где вызывается этот метод и допишем в этих новых вызовах новый параметр, а именно объект, созданных из уже подаваемых в метод дат.

Go to ", endDate|||"

Print ", new DateRange(startDate, endDate)"

Set step 3

# Теперь, когда новый параметр уже на месте, вернемся в описание метода и попробуем избавиться в нем от старых параметров дат.

Select "Date start" in parameters of "getFlowBetween"

# Сперва займемся параметром <code>start</code>

Select "start" in body of "getFlowBetween"

Wait 500ms

Print "range.getStart()"

Select "Date start, " in parameters of "getFlowBetween"

# После замен в теле метода, параметр можно удалить из сигнатуры метода и из его вызовов.

Remove selected

Wait 500ms

Select "getFlowBetween(|||startDate, |||"

Remove selected

Select "Date end" in parameters of "getFlowBetween"

# Теперь займёмся оставшимся параметром.

Select "end" in body of "getFlowBetween"

Wait 500ms

Print "range.getEnd()"

Wait 500ms

Select "Date end, " in parameters of "getFlowBetween"

Remove selected

Select "getFlowBetween(|||endDate, |||"

Remove selected

#C После всех переносов можно запустить компиляцию и тестирование.

#S Отлично, все работает, продолжаем!


Set step 4

# После того, как все необходимые параметры были удалены, можно подумать о перенесении в объект-параметр каких-то поведений, которые ему подходят.

Select:
```
each.getDate().equals(range.getStart()) ||
          each.getDate().equals(range.getEnd()) ||
          (each.getDate().after(range.getStart()) && each.getDate().before(range.getEnd()))
```

# В нашем случае, можно перенести проверку вхождения дат в диапазон, избавившись от корявого кода внутри <code>getFlowBetween</code>.

Go to the end of "DateRange"

Print:
```

  public boolean includes(Date arg) {
    return (arg.equals(start) || arg.equals(end) || (arg.after(start) && arg.before(end)));
  }
```

Wait 500ms

Select:
```
each.getDate().equals(range.getStart()) ||
          each.getDate().equals(range.getEnd()) ||
          (each.getDate().after(range.getStart()) && each.getDate().before(range.getEnd()))
```

Wait 500ms

Print "range.includes(each.getDate())"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.