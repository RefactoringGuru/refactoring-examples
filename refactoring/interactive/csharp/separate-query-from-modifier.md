separate-query-from-modifier:csharp

###

1.ru. Создайте новый <i>метод-запрос</i>, который бы возвращал то, что возвращает оригинальный метод.
1.en. Create a new <i>query method</i> to return what the original method did.
1.uk. Створіть новий <i>метод-запит</i>, який би повертав те, що повертав оригінальний метод.

2.ru. Сделайте так, чтобы оригинальный метод возвращал только результат вызова нового <i>метода-запроса</i>.
2.en. Change the original method so that it returns only the result of calling the new <i>query method</i>.
2.uk. Зробіть так, щоб оригінальний метод повертав тільки результат виклику нового <i>методу-запиту</i>.

3.ru. Замените все обращения к оригинальному методу вызовом <i>метода-запроса</i>, но сразу перед этой строкой вставьте вызов оригинального метода.
3.en. Replace all references to the original method with a call to the <i>query method</i> but immediately before this line, insert a call to the original method.
3.uk. Замініть усі звернення до оригінального методу викликом <i>метода-запита</i>, але відразу перед цим рядком розташуйте виклик оригінального методу.

4.ru. Избавьтесь от кода возврата значения в оригинальном методе. После этого он станет правильным <i>методом-модификатором</i>.
4.en. Get rid of the value-returning code in the original method, which now has become a proper <i>modifier method</i>.
4.uk. Позбавтеся від коду повернення значення в оригінальному методі. Після цього він стане правильним <i>методом-модифікатором</i>.



###

```
public class Guard
{
  // ...
  public void CheckSecurity(string[] people)
  {
    string found = FindCriminalAndAlert(people);
    SomeLaterCode(found);
  }
  public string FindCriminalAndAlert(string[] people)
  {
    for (int i = 0; i < people.Length; i++)
    {
      if (people[i].Equals("Don")) {
        SendAlert();
        return "Don";
      }
      if (people[i].Equals("John")) {
        SendAlert();
        return "John";
      }
    }
    return String.Empty;
  }
}
```

###

```
public class Guard
{
  // ...
  public void CheckSecurity(string[] people)
  {
    DoSendAlert(people);
    string found = FindCriminal(people);
    SomeLaterCode(found);
  }
  public void DoSendAlert(string[] people)
  {
    if (!String.IsNullOrEmpty(FindCriminal(people))) {
      SendAlert();
    }
  }
  public string FindCriminal(string[] people)
  {
    for (int i = 0; i < people.Length; i++)
    {
      if (people[i].Equals ("Don")) {
        return "Don";
      }
      if (people[i].Equals ("John")) {
        return "John";
      }
    }
    return String.Empty;
  }
}
```

###

Set step 1

Select name of "FindCriminalAndAlert"

#|ru| Рассмотрим рефакторинг <i>Разделение запроса и модификатора</i> на примере класса системы безопасности. В этом классе есть метод, который сообщает имя злоумышленника и посылает предупреждение.
#|en| Let's look at <i>Separate Query from Modifier</i> refactoring using a security system class as our example. The class has a method that tells us the name of a violator and sends a warning.
#|uk| Розглянемо рефакторинг <i>Поділ запиту та модифікатора<i> на прикладі класу системи безпеки. В цьому класі є метод, який повідомляє ім'я зловмисника і посилає попередження.

#|ru| Основной проблемой данного метода является то, что он используется для двух разных целей.
#|en| The main problem with this method is that it is used for two different purposes.
#|uk| Основною проблемою даного методу є те, що він використовується для двох різних цілей.

Select "return "Don""
+ Select "return "John""

#|ru| Во-первых, он находит и возвращает список нужных имён, которые в дальнейшем используются для других целей.
#|en| First, it finds and returns a list of names that are then used for different purposes.
#|uk| По-перше, він знаходить і повертає список потрібних імен, які в подальшому використовуються для інших цілей.

Select "found" in "CheckSecurity"

#|ru| Пример такого использования можно найти в методе <code>CheckSecurity</code>.
#|en| An example of such use can be found in the <code>CheckSecurity</code> method.
#|uk| Приклад такого використання можна знайти в методі <code>CheckSecurity</code>.

Select "SendAlert()" in "FindCriminalAndAlert"

#|ru| Во-вторых, он оповещает о найденных людях.
#|en| Secondly, it alerts about the people found.
#|uk| По-друге, він оповіщає про знайдені людях.

#|ru| Если при таком подходе нам потребуется лишь получение списка имён, то мы рискуем по ошибке разослать оповещения. Тут на помощь и приходит данный рефакторинг. В нашем случае поиск людей выступает в роли «запроса», а отсылка сообщений – в роли «модификатора».
#|en| With this approach, even if we only need to get a list of names we take the risk of accidentally sending alerts. This refactoring technique will save us from that risk. In our case, searching for people will be the "query" and sending alerts will be the "modifier".
#|uk| Якщо при такому підході нам буде потрібно лише отримання списку імен, то ми ризикуємо помилково розіслати оповіщення. Тут на допомогу і приходить даний рефакторинг. У нашому випадку пошук людей виступає в ролі «запиту», а відсилання повідомлень – в ролі «модифікатора».

Go to the end of "Guard"

#|ru| Итак, чтобы отделить запрос от модификатора, мы создадим метод-запрос, который возвращает те же значения, что и исходный метод, но при этом не содержит лишних модификаций.
#|en| To separate the query from the modifier, first create an appropriate query that returns the same value as the original method, but does not lead to side effects.
#|uk| Отже, щоб відокремити запит від модифікатора, ми створимо метод-запит, який повертає ті ж значення, що й вихідний метод, але при цьому не містить зайвих модифікацій.

Print:
```

  public string FindCriminal(string[] people)
  {
    for (int i = 0; i < people.Length; i++)
    {
      if (people[i].Equals ("Don")) {
        return "Don";
      }
      if (people[i].Equals ("John")) {
        return "John";
      }
    }
    return String.Empty;
  }
```

Set step 2

Select "return" in "FindCriminalAndAlert"

#|ru| После этого поочерёдно заменим все <code>return</code> в исходном методе вызовами нового запроса.
#|en| Then, one by one, replace all cases of <code>return</code> in the original method with calls for the new query.
#|uk| Після цього по черзі замінимо всі <code>return</code> в вихідному методі викликами нового запиту.

Select "return |||"Don"|||" in "FindCriminalAndAlert"

Replace "FindCriminal(people)"

Select "return |||"John"|||" in "FindCriminalAndAlert"

Replace "FindCriminal(people)"

Select "return |||String.Empty|||" in "FindCriminalAndAlert"

Replace "FindCriminal(people)"

Set step 3

Select:
```
    string found = FindCriminalAndAlert(people);
```

#|ru| Теперь изменим все методы, из которых происходит обращение, так, чтобы в них происходило два вызова: сначала – модификатора, а потом – запроса.
#|en| Now change all the methods from which reference is made so that two calls occur in them: first for the modifier, then for the query.
#|uk| Тепер змінимо всі методи, з яких відбувається обіг, так, щоб в них відбувалося два виклики: спочатку – модифікатора, а потім – запиту.

Select:
```
    |||string found = |||FindCriminalAndAlert(people);
```

Remove selected

Go to "FindCriminalAndAlert(people);|||"

Print:
```

    string found = FindCriminal(people);
```

Set step 4

Select type of "FindCriminalAndAlert"

#|ru| Проделав это для всех вызовов, убираем код возврата из модификатора.
#|en| Once this has been completed for all calls, we remove the return code from the modifier.
#|uk| Проробивши це для всіх викликів, прибираємо код повернення з модифікатора.

Print "void"

Wait 250ms

Select in "FindCriminalAndAlert":
```
        return FindCriminal(people);

```
+Select in "FindCriminalAndAlert":
```
    return FindCriminal(people);

```

Wait 250ms

Remove selected

Wait 500ms

Select name of "FindCriminalAndAlert"

#|ru| Теперь, пожалуй, самое время изменить имя оригинального метода.
#|en| We should also now change the name of the original method for consistency.
#|uk| Тепер, мабуть, саме час змінити ім'я оригінального методу.

Print "DoSendAlert"

Select "FindCriminalAndAlert"

Replace "DoSendAlert"

Select body of "DoSendAlert"

#|ru| Сейчас дублируется много кода, потому что модификатор всё ещё используется для своей работы телом запроса. Но мы можем применить к модификатору <a href="/ru/substitute-algorithm">Замещение алгоритма</a>, не боясь сломать какой-то другой код.
#|en| Of course, the result contains a great deal of duplicate code since the modifier still uses the body of the query. But now we can apply <a href="/substitute-algorithm">Substitute Algorithm</a> without the risk of breaking any other code.
#|uk| Зараз дублюється багато коду, тому що модифікатор все ще використовується для своєї роботи тілом запиту. Але ми можемо застосувати до модифікатору <a href="/uk/substitute-algorithm">Заміщення алгоритму</a>, не боячись зламати якийсь інший код.

Print:
```
    if (!String.IsNullOrEmpty(FindCriminal(people))) {
      SendAlert();
    }
```

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