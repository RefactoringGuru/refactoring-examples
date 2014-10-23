decompose-conditional:java

###

1.ru. Выделите условие в отдельный метод с помощью <a href="/extract-method">выделения метода</a>.
1.uk. Виділіть умову в окремий метод за допомогою <a href="/extract-method">відокремлення методу</a>.

2.ru. Повторите выделение для <code>then</code> и <code>else</code> части оператора.
2.uk. Повторіть видокремлення для частин оператора then і else.



###

```
class Stadium {
  // ...
  public double summerRate;
  public double winterRate;
  public double winterServiceCharge;

  public double getTicketPrice(Date date, int quantity) {
    double charge;
    if (date.before(SUMMER_START) || date.after(SUMMER_END)) {
      charge = quantity * winterRate + winterServiceCharge;
    }
    else {
      charge = quantity * summerRate;
    }
    return charge;
  }
}
```

###

```
class Stadium {
  // ...
  public double summerRate;
  public double winterRate;
  public double winterServiceCharge;

  public double getTicketPrice(Date date, int quantity) {
    double charge;
    if (notSummer(date)) {
      charge = winterCharge(quantity);
    }
    else {
      charge = summerCharge(quantity);
    }
    return charge;
  }

  private boolean notSummer(Date date) {
    return date.before(SUMMER_START) || date.after(SUMMER_END);
  }
  private double winterCharge(int quantity) {
    return quantity * winterRate + winterServiceCharge;
  }
  private double summerCharge(int quantity) {
    return quantity * summerRate;
  }
}
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Разбиение условного оператора</i> на примере вычисления стоимости входного билета на стадион.
#|uk| Давайте розглянемо <i>Розбиття умовного оператора<i> на прикладі обчислення вартості вхідного квитка на стадіон.

Select name of "getTicketPrice"
+ Select "SUMMER_START"
+ Select "SUMMER_END"

#|ru| Стоимость отличается в зависимости от сезона: есть отдельный зимний и летний тариф.
#|uk| Вартість відрізняється в залежності від сезону – є окремий зимовий та літній тариф.

#|ru| Наша задача — сделать этот условный оператор проще для понимания. Начнём с выделения условия в новый метод с более очевидным названием.
#|uk| Наше завдання – зробити цей умовний оператор простіше для розуміння. Почнемо з виділення умови в новий метод з більш очевидним назвою.

Go to the end of "Stadium"

Print:
```


  private boolean notSummer(Date date) {
  }
```

Select "date.before(SUMMER_START) || date.after(SUMMER_END)"

Wait 500ms

Go to the end of "notSummer"

Print:
```

    return date.before(SUMMER_START) || date.after(SUMMER_END);
```

Wait 500ms

Select "date.before(SUMMER_START) || date.after(SUMMER_END)" in "getTicketPrice"

Remove selected

Print "notSummer(date)"


#C|ru| Запускаем компиляцию и тестирование, чтобы убедиться в отсутствии ошибок.
#S Отлично, все работает!

#C|uk| Запускаємо компіляцію і тестування, щоб переконатися у відсутності помилок.
#S Супер, все працює.

Select "notSummer(date)" in "getTicketPrice"

#|ru| Условие стало очевидней, не правда ли? Многие программисты в таких ситуациях не выделяют части, образующие условие. Часто условия кажутся короткими и не стоящими такого труда.
#|uk| Умова стала очевиднішою , чи не так? Багато програмістів в таких ситуаціях не виділяють частини, що утворюють умову. Часто умови здаються короткими і не вартими такої праці.

#|ru| Тем не менее, несмотря на краткость условия, нередко существует большой разрыв между смыслом кода и его телом. Приходится смотреть в код и разбираться в том, что он делает. В данном случае это сделать нетрудно, но даже здесь выделенный метод более похож на комментарий.
#|uk| Тим не менш, незважаючи на стислість умови, нерідко існує великий розрив між сенсом коду і його тілом. Доводиться дивитися в код і розбиратися в тому, що він робить. В даному випадку це зробити неважко, але навіть тут виділений метод більш схожий на коментар.

Set step 2

Select "charge = quantity * winterRate + winterServiceCharge;"

#|ru| Теперь возьмёмся за тело условного оператора. Сначала выделим в новый метод всё, что находится внутри <code>then</code>
#|uk| Тепер візьмемося за тіло умовного оператора. Спочатку виділимо в новий метод все, що знаходиться всередині <code>then</code>

Go to the end of "Stadium"

Print:
```

  private double winterCharge(int quantity) {
    return quantity * winterRate + winterServiceCharge;
  }
```

Select "charge = quantity * winterRate + winterServiceCharge;"

Replace "charge = winterCharge(quantity);"


Select "charge = quantity * summerRate;"

#|ru| После этого возьмёмся за <code>else</code>
#|uk| Після цього візьмемося за <code>else</code>


Go to the end of "Stadium"

Print:
```

  private double summerCharge(int quantity) {
    return quantity * summerRate;
  }
```

Select "charge = quantity * summerRate;"

Replace "charge = summerCharge(quantity);"

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.