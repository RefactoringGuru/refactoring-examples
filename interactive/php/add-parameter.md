add-parameter:php

###

1. Проверьте, не определён ли метод в суперклассе или подклассе. Если метод в них присутствует, нужно будет повторить все шаги также в этих классах.

2. Следующий шаг важен, чтобы сохранить работоспособность программы во время рефакторинга. Итак, создайте новый метод, скопировав старый, и добавьте в него требуемый параметр. Замените код старого метода вызовом нового метода. Вы можете подставить любое значение в новый параметр (например <code>null</code> для объектов или ноль для чисел).

3. Найдите все обращения к старому методу и замените их обращениями к новому методу.

4. Удалите старый метод. Этот шаг неосуществим, если старый метод является частью публичного интерфейса. В этом случае старый метод нужно пометить как устаревший (<code>deprecated</code>).



###

```
class Calendar {
  // ...
  private $appointments; // array
  public function findAppointment(DateTime $date) {
    $result = array();
    foreach ($this->appointments as $each) {
      if ($date->format('Y-m-d') == $each->date->format('Y-m-d')) {
        $result[] = $date;
      }
    }
    return $result;
  }
}

// Somewhere in client code
$today = new DateTime();
$appointments = $calendar->findAppointment($today);
```

###

```
class Calendar {
  // ...
  private $appointments; // array
  public function findAppointmentByDateAndName(DateTime $date, $name) {
    $result = array();
    foreach ($this->appointments as $each) {
      if ($date->format('Y-m-d') == $each->date->format('Y-m-d')) {
        if ($name == null || ($name != null && $name == $each->name)) {
          $result[] = $date;
        }
      }
    }
    return $result;
  }
}

// Somewhere in client code
$today = new DateTime();
$appointments = $calendar->findAppointmentByDateAndName($today, null);
```

###

Set step 1

# У нас есть класс календаря, в котором хранятся записи встреч.

Select name of "findAppointment"

# В нём есть метод, который возвращает встречи по дате.

# Было бы неплохо, если бы этот метод мог фильтровать посетителей ещё и по имени.

Set step 2

# Можно просто вписать новый параметр в описание метода, но в таком случае есть большая вероятность сломать вызовы этого метода в остальном коде.

Go to the end of "Calendar"

# Поэтому, мы будем продвигаться очень осторожно и создадим новый метод с желаемым параметром, скопировав туда тело существующего. Так как в PHP отсутствует маханизм перегрузки (overload) функций, мы даём методу новое имя.

Print:
```

  public function findAppointmentByDateAndName(DateTime $date, $name) {
    $result = array();
    foreach ($this->appointments as $each) {
      if ($date->format('Y-m-d') == $each->date->format('Y-m-d')) {
        $result[] = $date;
      }
    }
    return $result;
  }
```

Select 2nd "        $result[] = $date;"

# Тело метода изменяем под использование нового метода.

Print:
```
        if ($name == null || ($name != null && $name == $each->name)) {
          $result[] = $date;
        }
```

Select body of "findAppointment"

# Теперь тело старого метода можно заменить вызовом нового метода.

Print:
```
    findAppointmentByDateAndName($date, null);
```

Set step 3

Select name of "findAppointment"

# Теперь нужно найти все обращения к старому методу и заменить их обращениями к новому.

Select "$calendar->findAppointment($today);"

# Вот одно из них. Так как нам нечего подать в новый параметр, впишем значение <code>null</code>.

Select "$calendar->|||findAppointment|||($today);"

Print "findAppointmentByDateAndName"

Go to "$calendar->findAppointmentByDateAndName($today|||);"

Print ", null"

Set step 4

Select whole "findAppointment"

# После всех изменений, старый метод можно удалить.

Remove selected

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.