preserve-whole-object:java

###

1. Создайте параметр в методе для объекта, из которого можно получить нужные значения.

2. Теперь начинайте по одному удалять старые параметры из метода, заменяя их в коде вызовами соответствующих методов объекта-параметра. Тестируйте программу после каждой замены параметра.

3. Удалите код получения значений из объекта-параметра, который стоял перед вызовом метода.



###

```
class Room {
  // ...
  public boolean withinPlan(HeatingPlan plan) {
    int low = getLowestTemp();
    int high = getHighestTemp();
    return plan.withinRange(low, high);
  }
}

class HeatingPlan {
  private TempRange range;
  public boolean withinRange(int low, int high) {
    return (low >= range.getLow() && high <= range.getHigh());
  }
}
```

###

```
class Room {
  // ...
  public boolean withinPlan(HeatingPlan plan) {
    return plan.withinRange(this);
  }
}

class HeatingPlan {
  private TempRange range;
  public boolean withinRange(Room room) {
    return (room.getLowestTemp() >= range.getLow() && room.getHighestTemp() <= range.getHigh());
  }
}
```

###

Set step 1

# Рассмотрим класс описывающий комнату и регистрирующий самую высокую и самую низкую температуру в течение суток. 

Select "plan.withinRange"

# Он должен сравнивать этот диапазон с диапазоном в заранее установленном плане обогрева и потом в зависимости от этого проделывать какие-то действия (например, менять температуру или, скажем, отсылать email хозяину дома).

Select "low, high" in "withinPlan"

# В данный момент, для проверки соответствия мы передаём только температуру, но в любой момент может потребоваться проверять и что-то ещё из параметров комнаты, например, влажность.

# Другими словами, придётся создавать новые и новые параметры. Чтобы этого избежать, можно передавать вместо конкретных значений, весь объект-комнату. Это позволит использовать любые параметры комнаты, без изменения сигнатуры методов.

Go to parameters of "withinRange"

# Итак, на первом шаге добавим параметр в метод <code>withinRange</code>.

Print "Room room, "

Go to "plan.withinRange(|||"

Print "this, "

Set step 2

# Теперь начинаем по одному удалять из метода параметры, которые можно заменить вызовами полей или методов передаваемого объекта.

Select ", int high" in parameters of "withinRange"

Remove selected

Select "&& |||high|||"

Print "room.getHighestTemp()"

Wait 500ms

Select ", high"

Remove selected


Select ", int low" in parameters of "withinRange"

#C Запускаем компиляцию и тестирование, а затем повторяем действия для оставшегося параметра.

#S Отлично, все работает, продолжаем!

Remove selected

Select "|||low||| >="

Print "room.getLowestTemp()"

Wait 500ms

Select ", low"

Remove selected


#C Запускаем компиляцию и тестирование ещё раз, чтобы убедиться, что код остался рабочим.

#S Тесты успешно проходят!

Select:
```
    int low = getLowestTemp();
    int high = getHighestTemp();

```

Set step 3

# Напоследок, удаляем ненужные теперь переменные из <code>withinPlan</code>

Remove selected

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.