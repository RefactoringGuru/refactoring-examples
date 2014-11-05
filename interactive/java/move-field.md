move-field:java

###

1.ru. Инкапсулируйте поле, если оно публичное.
1.en. If the field is public, encapsulate it.
1.uk. Інкапсулюйте поле, якщо воно є публічним.

2.ru. Создайте копию поля и методы для доступа к нему в целевом классе.
2.en. Create a field copy and methods for accessing the field in the target class.
2.uk. Створіть копію поля і методи для доступу до нього в цільовому класі.

3.ru. Определите, как вы будете обращаться к целевому классу. Вполне возможно, что у вас уже есть поле или метод, которые возвращают подходящий экземпляр класса, но если таковых нет, то нужно будет создать (достаточно одного из них).
3.en. Decide how you will refer to the target class. It is quite possible that you already have a field or method that returns an appropriate class instance, but if not, you will need to create one of these.
3.uk. Визначте, як ви будете звертатися до цільового класу. Цілком можливо, що у вас вже є поле або метод, які повертають відповідний екземпляр класу, але якщо таких немає, то потрібно буде створити (досить одного з них).

4.ru. Замените все обращения к старому полю на соответствующие вызовы методов в целевом классе.
4.en. Replace all references to the old class with the relevant calls to methods in the target class.
4.uk. Замініть всі звернення до старого полю на відповідні виклики методів в цільовому класі.

5.ru. Удалите поле в исходном классе.
5.en. Delete the field in the original class.
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
#|en| Let's look at <i>Move Field</i> using a bank account class as our example.
#|uk| Давайте розглянемо <i>Переміщення поля<i> на прикладі класу банківського рахунку.

Select "double |||interestRate|||"

#|ru| Мы хотим переместить поле процентной ставки <code>interestRate</code> в класс типа счета <code>AccountType</code>.
#|en| We want to move the <code>interestRate</code> field to the <code>AccountType</code> class.
#|uk| Ми хочемо перемістити поле процентної ставки <code>interestRate</code> в клас типу рахунку <code>AccountType</code>.

Select name of "interestForAmount_days"

#|ru| Есть несколько методов, обращающихся к этому полю, одним из которых является метод <code>interestForAmount_days()</code>.
#|en| Several methods refer to this field. One of them is the <code>interestForAmount_days()</code> method.
#|uk| Є кілька методів, що звертаються до цього поля, одним з яких є метод <code>interestForAmount_days()</code>.

Set step 2

Go to the end of "AccountType"

#|ru| Создадим такое же поле, а также методы доступа к нему в целевом классе.
#|en| Create the same field and same access methods in the target class.
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

#C|en| To stay on the safe side, let's compile.
#S All is well, so let's continue.

#C|uk| На всякий випадок запустимо компіляцію.
#S Все добре, можна продовжувати.

Set step 3

Select "AccountType |||type|||" in "Account"

#|ru| В рассматриваемом примере в классе <code>Account</code> есть поле для доступа к объекту типа счета, поэтому мы можем обращаться к перемещённому полю через него.
#|en| In our example, the <code>Account</code> class contains a field for accessing the account type object. Therefore we can access the moved field through it.
#|uk| У розглянутому прикладі в класі <code>Account</code> є поле для доступу до об'єкта типу рахунку, тому ми можемо звертатися до переміщеного поля через нього.

Set step 4

#|ru| Заменим все обращения к старому полю на соответствующие вызовы методов в целевом классе.
#|en| Replace all references to the old field with appropriate calls to methods in the target class.
#|uk| Замінимо всі звернення до старого поля на відповідні виклики методів в цільовому класі.

Select "interestRate" in "interestForAmount_days"

Replace "type.getInterestRate()"

Set step 5

Select in "Account":
```
  private double interestRate;

```

#|ru| После осуществления всех замен исходное поле можно удалить.
#|en| Once changes are complete, remove the original field.
#|uk| Після здійснення всіх замін вихідне поле можна видалити.

Remove selected

#C|ru| Тут же стоит запустить компиляцию и тесты, чтобы обнаружить потенциальные ошибки.
#S Все отлично, код исправно работает.

#C|en| Here you should compile and test for potential errors.
#S Outstanding. The code is doing what we intended.

#C|uk| Тут же варто запустити компіляцію і тести, щоб виявити потенційні помилки.
#S Все добре, код справно працює.

#|ru| Стоит упомянуть, что если в классе есть много методов, которые используют перемещаемое поле, то имеет смысл его «самоинкапсулировать», чтобы впоследствии упростить рефакторинг. Давайте рассмотрим быстрый пример.
#|en| Remember that if a class has many methods that use the moved field, you may want to self-encapsulate it to simplify later refactoring. Let's look at a quick example.
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
#|en| In this case, you will not need to make changes in all methods right away…
#|uk| В цьому випадку вам не доведеться робити заміну в усіх методах відразу...

Select "interestRate = arg" in "setInterestRate"
+Select "return interestRate" in "getInterestRate"

#|ru| ...а всего лишь в методах доступа (геттере и сеттере).
#|en| …only in the access methods (getter and setter).
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
#|en| Then the original field can be removed.
#|uk| Після чого вихідне поле можна видалити.

Remove selected

Select name of "interestForAmount_days"
+ Select name of "interestForBigFamily"

#|ru| Позднее при желании можно выполнить переадресацию для клиентов методов доступа, чтобы они использовали новый объект.
#|en| Later, if desired, you can redirect access methods for clients so that they use the new object.
#|uk| Пізніше при бажанні можна виконати переадресацію для клієнтів методів доступу, щоб вони використовували новий об'єкт.

Select name of "Account"

#|ru| Применение самоинкапсуляции позволяет выполнять рефакторинг более мелкими шагами. Это удобно, когда класс подвергается значительной переделке.
#|en| Self-encapsulating allows refactoring via baby steps. And when a class is undergoing major changes, that is a good thing.
#|uk| Застосування самоінкапсуляціі дозволяє виконувати рефакторинг дрібнішими кроками. Це зручно, коли клас зазнає значної переробки.

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|en| Let's run the final compile.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q Now refactoring is complete. If you like, you can compare the old and new code.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.