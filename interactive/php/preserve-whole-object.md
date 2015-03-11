preserve-whole-object:php

###

1.ru. Создайте параметр в методе для объекта, из которого можно получить нужные значения.
1.en. Create a parameter in the method for the object from which you can get the necessary values.
1.uk. Створіть параметр в методі для об'єкту, з якого можна отримати потрібні значення.

2.ru. Теперь начинайте по одному удалять старые параметры из метода, заменяя их в коде вызовами соответствующих методов объекта-параметра. Тестируйте программу после каждой замены параметра.
2.en. Now start removing the old parameters from the method one by one, replacing them with calls to the relevant methods of the parameter object. Test the program after each replacement of a parameter.
2.uk. Тепер починайте по одному видаляти старі параметри з методу, замінюючи їх в коді викликами відповідних методів об'єкту-параметра. Тестуйте програму після кожної заміни параметра.

3.ru. Удалите код получения значений из объекта-параметра, который стоял перед вызовом метода.
3.en. Delete the getter code from the parameter object that had preceded the method call.
3.uk. Видаліть код отримання значень з об'єкту-параметра, який стояв перед викликом методу.



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

#|ru| Рассмотрим класс, описывающий комнату и регистрирующий самую высокую и самую низкую температуру в течение суток.
#|en| Let's look at this refactoring using the class that describes hotel rooms and logs their daily temperature.
#|uk| Розглянемо клас, що описує кімнату та реєструє найвищу і найнижчу температуру протягом доби.

Select "$plan->withinRange"

#|ru| Он должен сравнивать этот диапазон с диапазоном в заранее установленном плане обогрева и потом, в зависимости от результатов сравнения, проделывать какие-то действия (например, менять температуру или, скажем, отсылать email хозяину дома).
#|en| The class should analyze room's micro climate and react with certain actions. For now, only temperature is compared with a predefined temperature plan. Then, depending on the results of the comparison, class could issue a heat or cool command, or even send an email to the house owner if the temperature is dangerously high.
#|uk| Він повинен порівнювати цей діапазон з діапазоном в заздалегідь встановленому плані обігріву і потім, в залежності від результатів порівняння, проробляти якісь дії (наприклад, змінювати температуру або, скажімо, відсилати email господареві будинку).

Select "$low, $high" in "withinPlan"

#|ru| В данный момент для проверки соответствия мы передаём только температуру, но в любой момент может потребоваться проверить и что-нибудь ещё из параметров комнаты, например, влажность.
#|en| Currently, we are passing only the temperature for analysis but at any time we may need to check another room parameter, such as humidity.
#|uk| В даний момент для перевірки відповідності ми передаємо тільки температуру, але в будь-який момент може знадобитися перевіряти і щось ще з параметрів кімнати, наприклад, вологість.

#|ru| В текущей реализации для этого придётся добавлять всё новые и новые параметры в метод анализа данных. Чтобы этого избежать, можно передавать вместо конкретных значений весь объект-комнату. Это позволит использовать любые параметры комнаты без изменения сигнатуры методов.
#|en| With current implementation, we would have to add more and more parameters to the method. To avoid this, we can pass the entire room object instead of specific values. That will allow to take any room data straight from the room object, without changing signature of the method.
#|uk| З нинішньою реалізацією доведеться створювати нові і нові параметри в методі аналізу даних. Щоб цього уникнути, можна передавати замість конкретних значень весь об'єкт-кімнату. Це дозволить використовувати будь-які параметри кімнати без зміни сигнатури методів.

Go to parameters of "withinRange"

#|ru| Итак, на первом шаге добавим параметр в метод <code>withinRange</code>.
#|en| So for the first step, we add a parameter to the <code>withinRange</code> method.
#|uk| Отже, на першому кроці додамо параметр в метод <code>withinRange</code>.

Print "Room $room, "

Go to "$plan->withinRange(|||"

Print "$this, "

Set step 2

#|ru| Теперь начинаем по одному удалять из метода параметры, которые можно заменить вызовами полей или методов передаваемого объекта.
#|en| One by one, we should remove parameters with data, that could be retrieved from the object we pass into the method.
#|uk| Тепер починаємо по одному видаляти з методу параметри, які можна замінити викликами полів або методів переданого об'єкта.

Select ", $high" in parameters of "withinRange"

Wait 250ms

Remove selected

Select "&& |||$high|||"

Replace "$room->getHighestTemp()"

Wait 500ms

Select ", $high"

Remove selected

#C|ru| Запускаем тестирование, а затем повторяем действия для оставшегося параметра.
#S Отлично, все работает, продолжаем!

#C|en| Test and then repeat these actions for the remaining parameter.
#S Everything is good! Let's continue.

#C|uk| Запускаємо тестування, а потім повторюємо дії для залишився параметра.
#S Супер, все працює, продовжуємо.

Select ", $low" in parameters of "withinRange"

Wait 500ms

Remove selected

Select "|||$low||| >="

Replace "$room->getLowestTemp()"

Wait 500ms

Select ", $low"

Remove selected


#C|ru| Запускаем тестирование ещё раз, чтобы убедиться, что код остался рабочим.
#S Тесты успешно проходят!

#C|en| Test again to make sure the code still works.
#S The tests are completed successfully!

#C|uk| Запускаємо тестування ще раз, щоб переконатися, що код залишився робочим.
#S Тести успішно проходять.

Select:
```
    $low = getLowestTemp();
    $high = getHighestTemp();

```

Set step 3

#|ru| Напоследок, удаляем ненужные теперь переменные из <code>withinPlan</code>.
#|en| And finally, let's remove the unused variables from <code>withinPlan</code>.
#|uk| Наостанок, видаляємо непотрібні тепер змінні з <code>withinPlan</code>.

Remove selected

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|en| Let's start the final testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.