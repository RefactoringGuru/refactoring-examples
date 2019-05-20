remove-assignments-to-parameters:csharp

###

1.ru. Создайте локальную переменную и присвойте ей начальное значение вашего параметра.
1.en. Create a local variable and assign the initial value of your parameter.
1.uk. Створіть локальну змінну і присвойте початкове значення вашого параметру.

2.ru. Замените использование параметра в теле метода вашей локальной переменной.
2.en. In all method code that follows this line, replace the parameter with your new local variable.
2.uk. В усьому коді методу після цього рядка зміните використання параметра на вашу локальну змінну.



###

```
int Discount(int inputVal, int quantity, int yearToDate)
{
  if (inputVal > 50)
  {
    inputVal -= 2;
  }
  if (quantity > 100)
  {
    inputVal -= 1;
  }
  if (yearToDate > 10000)
  {
    inputVal -= 4;
  }

  return inputVal;
}
```

###

```
int Discount(int inputVal, int quantity, int yearToDate)
{
  int result = inputVal;

  if (inputVal > 50)
  {
    result -= 2;
  }
  if (quantity > 100)
  {
    result -= 1;
  }
  if (yearToDate > 10000)
  {
    result -= 4;
  }

  return result;
}
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Удаление присваиваний параметрам</i> на примере небольшого метода расчёта скидки.
#|en| Let's look at <i>Remove Assignments to Parameters</i> using a small discount calculation method as an example.
#|uk| Давайте розглянемо <i>Видалення присвоювань параметрам</i> на прикладі невеличкого методу розрахунку знижки.

Select "inputVal" in parameters of "Discount"

#|ru|+ Обратите внимание на параметр <code>inputVal</code>.
#|en|+ Take a look at the <code>inputVal</code> parameter.
#|uk|+ Зверніть увагу на параметр <code>inputVal</code>.

Select 3nd "inputVal"
+Select 4th "inputVal"
+Select 5th "inputVal"

#|ru|^= Значение этого параметра изменяется в теле метода.
#|en|^= The initial value of this parameter changes inside the method body.
#|uk|^= Значення цього параметра змінюється в тілі методу.

Set step 2

#|ru|^ Заменим использование параметра новой переменной, значение которой мы будем изменять, после чего вернем её как результат этого метода.
#|en|^ Let's replace usage of the parameter with a new variable. We will be changing that variable's value instead of the parameter and then return it as the result of our method.
#|uk|^ Замінимо використання параметра нової змінної, значення якої ми змінюватимемо, після чого повернемо її як результат цього методу.

Go to the start of "Discount"

Print:
```

  int result = inputVal;

```

Select "int result = inputVal"

#|ru| Инициализируем нашу переменную значением параметра.
#|en| First, we initialize our variable with the parameter value.
#|uk| Ініціалізуємо нашу змінну значенням параметру.

Select 4th "inputVal"
+Select 5th "inputVal"
+Select 6th "inputVal"
+Select 7th "inputVal"

#|ru| В теле метода заменим все обращения к параметру на созданную нами переменную.
#|en| Then, we should replace all references to the parameter in method's body with the variable that we have created.
#|uk| У тілі методу замінимо всі звернення до параметру на створену нами змінну.

Print "result"

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.