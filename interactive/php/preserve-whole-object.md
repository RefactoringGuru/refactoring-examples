preserve-whole-object:php

###

1. Создайте параметр в методе для объекта, из которого можно получить нужные значения.

2. Теперь начинайте по одному удалять старые параметры из метода, заменяя их в коде вызовами соответствующих методов объекта-параметра. Тестируйте программу после каждой замены параметра.

3. Удалите код получения значений из объекта-параметра, который стоял перед вызовом метода.



###

```
class Room {
  // ...
  public function withinPlan(HeatingPlan $plan) {
    $low = getLowestTemp();
    $high = getHighestTemp();
    return $plan->withinRange($low, $high);
  }
}

class HeatingPlan {
  private $range; // TempRange
  public function withinRange($low, $high) {
    return ($low >= $range->getLow() && $high <= $range->getHigh());
  }
}
```

###

```
class Room {
  // ...
  public function withinPlan(HeatingPlan $plan) {
    return $plan->withinRange($this);
  }
}

class HeatingPlan {
  private $range; // TempRange
  public function withinRange(Room $room) {
    return ($room->getLowestTemp() >= $range->getLow() && $room->getHighestTemp() <= $range->getHigh());
  }
}
```

###

Set step 1

# Рассмотрим класс, описывающий комнату и регистрирующий самую высокую и самую низкую температуру в течение суток.

Select "$plan->withinRange"

# Он должен сравнивать этот диапазон с диапазоном в заранее установленном плане обогрева и потом, в зависимости от результатов сравнения, проделывать какие-то действия (например, менять температуру или, скажем, отсылать email хозяину дома).

Select "$low, $high" in "withinPlan"

# В данный момент для проверки соответствия мы передаём только температуру, но в любой момент может потребоваться проверять и что-то ещё из параметров комнаты, например, влажность.

# Другими словами, придётся создавать новые и новые параметры. Чтобы этого избежать, можно передавать вместо конкретных значений весь объект-комнату. Это позволит использовать любые параметры комнаты без изменения сигнатуры методов.

Go to parameters of "withinRange"

# Итак, на первом шаге добавим параметр в метод <code>withinRange</code>.

Print "Room $room, "

Go to "$plan->withinRange(|||"

Print "$this, "

Set step 2

# Теперь начинаем по одному удалять из метода параметры, которые можно заменить вызовами полей или методов передаваемого объекта.

Select ", $high" in parameters of "withinRange"

Remove selected

Select "&& |||$high|||"

Replace "$room->getHighestTemp()"

Wait 500ms

Select ", $high"

Remove selected


Select ", $low" in parameters of "withinRange"

#C Запускаем тестирование, а затем повторяем действия для оставшегося параметра.

#S Отлично, все работает, продолжаем!

Remove selected

Select "|||$low||| >="

Replace "$room->getLowestTemp()"

Wait 500ms

Select ", $low"

Remove selected


#C Запускаем тестирование ещё раз, чтобы убедиться, что код остался рабочим.

#S Тесты успешно проходят!

Select:
```
    $low = getLowestTemp();
    $high = getHighestTemp();

```

Set step 3

# Напоследок, удаляем ненужные теперь переменные из <code>withinPlan</code>.

Remove selected

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.