add-parameter:java

###

1. Проверьте, не определён ли метод в суперклассе или подклассе. Если метод в них присутствует, нужно будет повторить все шаги также в этих классах.

2. Следующий шаг важен, чтобы сохранить работоспособность программы во время рефакторинга. Итак, создайте новый метод, скопировав старый, и добавьте в него требуемый параметр. Замените код старого метода вызовом нового метода. Вы можете подставить любое значение в новый параметр (например <code>null</code> для объектов или ноль для чисел).

3. Найдите все обращения к старому методу и замените их обращениями к новому методу.

4. Удалите старый метод. Этот шаг неосуществим, если старый метод является частью публичного интерфейса. В этом случае старый метод нужно пометить как устаревший (<code>deprecated</code>).



###

```
class Calendar {
  // ...
  private Set appointments;
  public ArrayList<Appointment> findAppointments(Date date) {
    Set result = new ArrayList();
    Iterator iter = kent.getCourses().iterator();
    while (iter.hasNext()) {
      Appointment each = (Appointment) iter.next();
      if (date.compareTo(each.date) == 0) {
        result.add(date);
      }
    }
    return result;
  }
}

// Somewhere in client code
Date today = new Date();
appointments = calendar.findAppointments(today);
```

###

```
class Calendar {
  // ...
  private Set appointments;
  public ArrayList<Appointment> findAppointments(Date date, String name) {
    Set result = new ArrayList();
    Iterator iter = kent.getCourses().iterator();
    while (iter.hasNext()) {
      Appointment each = (Appointment) iter.next();
      if (date.compareTo(each.date) == 0) {
        if (name == null || (name != null && name == each.name)) {
          result.add(date);
        }
      }
    }
    return result;
  }
}

// Somewhere in client code
Date today = new Date();
appointments = calendar.findAppointments(today, null);
```

###

Set step 1

# У нас есть класс Календарь, в котором хранятся записи о запланированных встречах.

Select name of "findAppointments"

# В нём есть метод, который возвращает значения встреч на определенную дату.

# Было бы неплохо, если бы этот метод мог фильтровать посетителей ещё и по имени.

Set step 2

# Можно просто вписать новый параметр в описание метода, но в таком случае есть большая вероятность «сломать» вызовы этого метода в других фрагментах кода.

Go to the end of "Calendar"

# Поэтому мы будем продвигаться очень осторожно и создадим новый метод с желаемым параметром, для начала скопировав туда тело существующего.

Print:
```

  public ArrayList<Appointment> findAppointments(Date date, String name) {
    Set result = new ArrayList();
    Iterator iter = kent.getCourses().iterator();
    while (iter.hasNext()) {
      Appointment each = (Appointment) iter.next();
      if (date.compareTo(each.date) == 0) {
        result.add(date);
      }
    }
    return result;
  }
```

Select 2nd "        result.add(date);"

# Тело метода изменяем под использование нового метода.

Print:
```
        if (name == null || (name != null && name == each.name)) {
          result.add(date);
        }
```

Select body of "findAppointments"

# Теперь тело старого метода можно заменить вызовом нового метода.

Print:
```
    findAppointments(date, null);
```

Set step 3

Select name of "findAppointments"

# Далее нужно найти все обращения к старому методу и заменить их обращениями к новому.

Select "calendar.findAppointments(today);"

# Вот одно из них. Так как нам нечего подать в новый параметр, впишем значение <code>null</code>.

Go to "calendar.findAppointments(today|||);"

Print ", null"

Set step 4

Select whole "findAppointments"

# После проведения всех изменений старый метод можно удалить.

Remove selected

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.