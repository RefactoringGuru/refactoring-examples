separate-query-from-modifier:java

###

1. Создайте новый <i>метод-запрос</i>, который бы возвращал то, что возвращал оригинальный метод.

2. Сделайте так, чтобы оригинальный метод возвращал только результат вызова нового <i>метода-запроса</i>.

3. Замените все обращения к оригинальному методу вызовом <i>метода-запроса</i>, но сразу перед этой строкой вставьте вызов оригинального метода.

4. Избавьтесь от кода возврата значения в оригинальном методе. После этого он станет правильным <i>методом-модификатором</i>.



###

```
class Guard {
  // ...
  public void checkSecurity(String[] people) {
    String found = findCriminalAndAlert(people);
    someLaterCode(found);
  }
  public String findCriminalAndAlert(String[] people) {
    for (int i = 0; i < people.length; i++) {
      if (people[i].equals("Don")) {
        sendAlert();
        return "Don";
      }
      if (people[i].equals("John")) {
        sendAlert();
        return "John";
      }
    }
    return "";
  }
}
```

###

```
class Guard {
  // ...
  public void checkSecurity(String[] people) {
    doSendAlert(people);
    String found = findCriminal(people);
    someLaterCode(found);
  }
  public void doSendAlert(String[] people) {
    if (findCriminal(people) != "")
      sendAlert();
    }
  }
  public String findCriminal(String[] people) {
    for (int i = 0; i < people.length; i++) {
      if (people[i].equals ("Don")) {
        return "Don";
      }
      if (people[i].equals ("John")) {
        return "John";
      }
    }
    return "";
  }
}
```

###

Set step 1

Select name of "findCriminalAndAlert"

# Рассмотрим рефакторинг <i>Разделение запроса и модификатора</i> на примере класса системы безопасности. В этом классе есть метод, которая сообщает имя злоумышленника и посылает предупреждение.

# Основной проблемой данного метода является то, что он используется для двух разных целей.

Select "return "Don""
+ Select "return "John""

# Во-первых, он находит и возвращает список нужных имён, которые в дальнейшем используются для других целей.

Select "found" in "checkSecurity"

# Пример такого использования можно найти в методе <code>checkSecurity</code>

Select "sendAlert()" in "findCriminalAndAlert"

# Но это ещё не всё. Метод также отсылает сообщения о найденных людях.

# С данным подходом, если нам всего лишь нужно получить список имён, мы рискую по ошибке разослать сообщения. Тут на помощь и приходит данный рефакторинг. В нашем случае, поиск людей выступает в роли «запроса», а отсылка сообщений — в роли «модификатора».

Go to the end of "Guard"

# Итак, чтобы отделить запрос от модификатора, мы должны сначала создать подходящий запрос, который возвращает то же значение, что и исходный метод, но не создаёт побочных эффектов.

Print:
```

  public String findCriminal(String[] people) {
    for (int i = 0; i < people.length; i++) {
      if (people[i].equals ("Don")) {
        return "Don";
      }
      if (people[i].equals ("John")) {
        return "John";
      }
    }
    return "";
  }
```

Set step 2

# После этого поочерёдно заменяем все <code>return</code> в исходном методе вызовами нового запроса.

Select "return |||"Don"|||" in "findCriminalAndAlert"

Replace "findCriminal(people)"

Select "return |||"John"|||" in "findCriminalAndAlert"

Replace "findCriminal(people)"

Select "return |||""|||" in "findCriminalAndAlert"

Replace "findCriminal(people)"

Set step 3

Select:
```
    String found = findCriminalAndAlert(people);
```

# Теперь изменим все методы, из которых происходит обращение, так чтобы в них происходило два вызова — сначала модификатора, а потом запроса.

Select:
```
    |||String found = |||findCriminalAndAlert(people);
```

Remove selected

Go to "findCriminalAndAlert(people);|||"

Print:
```

    String found = findCriminal(people);
```

Set step 4

Select type of "findCriminalAndAlert"

# Проделав это для всех вызовов, убираем код возврата из модификатора.

Print "void"

Select in "findCriminalAndAlert":
```
        return findCriminal(people);

```
+Select in "findCriminalAndAlert":
```
    return findCriminal(people);

```

Remove selected

Select name of "findCriminalAndAlert"

# Кроме того, теперь, пожалуй, лучше изменить имя оригинального метода.

Print "doSendAlert"

Select "findCriminalAndAlert"

Replace "doSendAlert"


Select body of "doSendAlert"

# Конечно, в этом случае дублируется много кода, потому что модификатор всё ещё пользуется для своей работы телом запроса. Но теперь мы можем применить к модификатору <a href="/substitute-algorithm">Замещение алгоритма</a>, не боясь сломать какой-то другой код.

Print:
```
    if (findCriminal(people) != "")
      sendAlert();
    }
```

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.