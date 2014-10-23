move-field:java

###

1.ru. Инкапсулируйте поле, если оно публичное.
1.uk. Інкапсулюйте поле, якщо воно є публічним.

2.ru. Создайте копию поля и методы для доступа к нему в целевом классе.
2.uk. Створіть копію поля і методи для доступу до нього в цільовому класі.

3.ru. Определите, как вы будете обращаться к целевому классу. Вполне возможно, что у вас уже есть поле или метод, которые возвращают подходящий экземпляр класса, но если таковых нет, то нужно будет создать (достаточно одного из них).
3.uk. Визначте, як ви будете звертатися до цільового класу. Цілком можливо, що у вас вже є поле або метод, які повертають відповідний екземпляр класу, але якщо таких немає, то потрібно буде створити (досить одного з них).

4.ru. Замените все обращения к старому полю на соответствующие вызовы методов в целевом классе.
4.uk. Замініть всі звернення до старого полю на відповідні виклики методів в цільовому класі.

5.ru. Удалите поле в исходном классе.
5.uk. Видаліть поле в початковому класі.



###

```
class Account {
  // ...
  private AccountType type;
  private double interestRate;

  public double interestForAmount_days(double amount, int days) {
    return interestRate * amount * days / 365.0;
  }
}

class AccountType {
  // ...
}
```

###

```
class Account {
  // ...
  private AccountType type;

  public double interestForAmount_days(double amount, int days) {
    return getInterestRate() * amount * days / 365.0;
  }
  public double interestForBigFamily(double familySize) {
    return getInterestRate() / familySize;
  }

  // other 10 methods, which use getInterestRate()

  private double getInterestRate() {
    return type.getInterestRate();
  }
  private void setInterestRate(double arg) {
    type.setInterestRate(arg);
  }
}

class AccountType {
  // ...
  private double interestRate;

  public double getInterestRate() {
    return interestRate;
  }
  public void setInterestRate(double arg) {
    interestRate = arg;
  }
}
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Перемещение поля</i> на примере класса банковского счета.
#|uk| Давайте розглянемо <i>Переміщення поля<i> на прикладі класу банківського рахунку.

Select "double |||interestRate|||"

#|ru| Мы хотим переместить поле процентной ставки <code>interestRate</code> в класс типа счета <code>AccountType</code>.
#|uk| Ми хочемо перемістити поле процентної ставки <code>interestRate</code> в клас типу рахунку <code>AccountType</code>.

Select name of "interestForAmount_days"

#|ru| Есть несколько методов, обращающихся к этому полю, одним из которых является метод <code>interestForAmount_days()</code>.
#|uk| Є кілька методів, що звертаються до цього поля, одним з яких є метод <code>interestForAmount_days()</code>.

Set step 2

Go to the end of "AccountType"

#|ru| Создадим такое же поле, а также методы доступа к нему в целевом классе.
#|uk| Створимо таке ж поле, а також методи доступу до нього в цільовому класі.

Print:
```

  private double interestRate;

  public double getInterestRate() {
    return interestRate;
  }
  public void setInterestRate(double arg) {
    interestRate = arg;
  }
```

#C|ru| На всякий случай запустим компиляцию.
#S Все хорошо, можно продолжать.

#C|uk| На всякий випадок запустимо компіляцію.
#S Все добре, можна продовжувати.

Set step 3

Select "AccountType |||type|||" in "Account"

#|ru| В рассматриваемом примере в классе <code>Account</code> есть поле для доступа к объекту типа счета, поэтому мы можем обращаться к перемещённому полю через него.
#|uk| У розглянутому прикладі в класі <code>Account</code> є поле для доступу до об'єкта типу рахунку, тому ми можемо звертатися до переміщеного поля через нього.

Set step 4

#|ru| Заменим все обращения к старому полю на соответствующие вызовы методов в целевом классе.
#|uk| Замінимо всі звернення до старого поля на відповідні виклики методів в цільовому класі.

Select "interestRate" in "interestForAmount_days"

Replace "type.getInterestRate()"

Set step 5

Select in "Account":
```
  private double interestRate;

```

#|ru| После осуществления всех замен исходное поле можно удалить.
#|uk| Після здійснення всіх замін вихідне поле можна видалити.

Remove selected

#C|ru| Тут же стоит запустить компиляцию и тесты, чтобы обнаружить потенциальные ошибки.
#S Все отлично, код исправно работает.

#C|uk| Тут же варто запустити компіляцію і тести, щоб виявити потенційні помилки.
#S Все добре, код справно працює.

#|ru| Стоит упомянуть, что если в классе есть много методов, которые используют перемещаемое поле, то имеет смысл его «самоинкапсулировать», чтобы впоследствии упростить рефакторинг. Давайте рассмотрим быстрый пример.
#|uk| Варто згадати, що якщо в класі є багато методів, які використовують переміщуване поле, то має сенс його «самоінкапсулювати», щоб згодом спростити рефакторинг. Давайте розглянемо швидкий приклад.

Select whole "Account"

Replace instantly:
```
class Account {
  // ...
  private AccountType type;
  private double interestRate;

  public double interestForAmount_days(double amount, int days) {
    return getInterestRate() * amount * days / 365.0;
  }
  public double interestForBigFamily(double familySize) {
    return getInterestRate() / familySize;
  }

  // other 10 methods, which use getInterestRate()

  private double getInterestRate() {
    return interestRate;
  }
  private void setInterestRate(double arg) {
    interestRate = arg;
  }
}

```

Select name of "interestForAmount_days"
+ Select name of "interestForBigFamily"
+ Select "other 10 methods"

#|ru| В этом случае вам не придётся делать замену во всех методах сразу...
#|uk| В цьому випадку вам не доведеться робити заміну в усіх методах відразу...

Select "interestRate = arg" in "setInterestRate"
+Select "return interestRate" in "getInterestRate"

#|ru| ...а всего лишь в методах доступа (геттере и сеттере).
#|uk| ...а всього лише в методах доступу (геттері та сеттері).

Select "return interestRate" in "getInterestRate"

Replace "return type.getInterestRate()"

Select "interestRate = arg" in "setInterestRate"

Replace "type.setInterestRate(arg)"

Select in "Account":
```
  private double interestRate;

```

#|ru| После чего исходное поле можно удалить.
#|uk| Після чого вихідне поле можна видалити.

Remove selected

Select name of "interestForAmount_days"
+ Select name of "interestForBigFamily"

#|ru| Позднее при желании можно выполнить переадресацию для клиентов методов доступа, чтобы они использовали новый объект.
#|uk| Пізніше при бажанні можна виконати переадресацію для клієнтів методів доступу, щоб вони використовували новий об'єкт.

Select name of "Account"

#|ru| Применение самоинкапсуляции позволяет выполнять рефакторинг более мелкими шагами. Это удобно, когда класс подвергается значительной переделке.
#|uk| Застосування самоінкапсуляціі дозволяє виконувати рефакторинг дрібнішими кроками. Це зручно, коли клас зазнає значної переробки.

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.