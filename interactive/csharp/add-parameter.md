add-parameter:csharp

###

1.ru. Проверьте, не определён ли метод в суперклассе или подклассе. Если метод в них присутствует, нужно будет повторить все шаги также в этих классах.

1.en. See whether the method is defined in a superclass or subclass. If the method is present in them, you will need to repeat all the steps in these classes as well.

1.uk. Перевірте, чи не є метод вже визначеним в суперкласі або підкласі. Якщо метод в них вже присутній, треба буде повторити усі кроки також в цих класах.

2.ru. Следующий шаг важен, чтобы сохранить работоспособность программы во время рефакторинга. Итак, создайте новый метод, скопировав старый, и добавьте в него требуемый параметр. Замените код старого метода вызовом нового метода. Вы можете подставить любое значение в новый параметр (например <code>null</code> для объектов или ноль для чисел).

2.en. The following step is critical for keeping your program functional during the refactoring process. Create a new method by copying the old one and add the necessary parameter to it. Replace the code for the old method with a call to the new method. You can plug in any value to the new parameter (such as <code>null</code> for objects or a zero for numbers).

2.uk. Наступний крок важливий для того, щоб зберегти працездатність програми під час рефакторингу. Отже, створіть новий метод, скопіювавши старий, і додайте в нього необхідний параметр. Замініть код старого методу викликом нового методу. Ви можете підставити будь-яке значення в новий параметр (наприклад <code>null</code> для об'єктів або нуль для чисел).

3.ru. Найдите все обращения к старому методу и замените их обращениями к новому методу.

3.en. Find all references to the old method and replace them with references to the new method.

3.uk. Знайдіть усі звернення до старого методу і замініть їх зверненнями до нового методу.

4.ru. Удалите старый метод. Этот шаг неосуществим, если старый метод является частью публичного интерфейса. В этом случае старый метод нужно пометить как устаревший (<code>deprecated</code>).

4.en. Delete the old method. Deletion is not possible if the old method is part of the public interface. If that is the case, mark the old method as deprecated.

4.uk. Видаліть старий метод. Цей крок стає неможливим, якщо старий метод є частиною публічного інтерфейсу. В цьому випадку старий метод треба помітити як застарілий (<code>deprecated</code>).



###

```
class Calendar
{
  // ...
  private List<Appointment> appointments;

  public List<Appointment> FindAppointments(DateTime date)
  {
    List<Appointment> result = new List<Appointment>();

    foreach (Appointment item in kent.GetCourses())
    {
      if (date.Date == item.Date.Date)
      {
        result.Add(date);
      }
    }

    return result;
  }
}

// Somewhere in client code
DateTime today = DateTime.Now;
appointments = calendar.FindAppointments(today);
```

###

```
class Calendar
{
  // ...
  private List<Appointment> appointments;

  public List<Appointment> FindAppointments(DateTime date, string name)
  {
    List<Appointment> result = new List<Appointment>();

    foreach (Appointment item in kent.GetCourses())
    {
      if (date.Date == item.Date.Date)
      {
        if (string.IsNullOrEmpty(name) || name == item.Name)
        {
          result.Add(date);
        }
      }
    }

    return result;
  }
}

// Somewhere in client code
DateTime today = DateTime.Now;
appointments = calendar.FindAppointments(today, null);
```

###

Set step 1

#|ru| У нас есть класс Календарь, в котором хранятся записи о запланированных встречах.
#|en| We have a Calendar class that stores records about planned meetings.
#|uk| Ми маємо клас Календар, в якому зберігаються записи щодо запланованих зустрічей.

Select name of "FindAppointments"

#|ru| В нём есть метод, который возвращает значения встреч на определенную дату.
#|en| A method in this class returns the values of meetings for a particular date.
#|uk| У ньому є метод, який повертає значення зустрічей на певну дату.

#|ru| Было бы неплохо, если бы этот метод мог фильтровать посетителей ещё и по имени.
#|en| We would like for this method to be able to filter visitors by name as well.
#|uk| Було б непогано, якби цей метод міг фільтрувати відвідувачів ще й за ім'ям.

Set step 2

#|ru| Можно просто вписать новый параметр в описание метода, но в таком случае есть большая вероятность «сломать» вызовы этого метода в других фрагментах кода.
#|en| We could simply add a new parameter to the method description, but that would cause a large risk of breaking calls involving this method in other code fragments.
#|uk| Можна просто вписати новий параметр в опис методу, але в такому випадку є велика ймовірність «зламати» виклики цього методу в інших фрагментах коду.

Go to the end of "Calendar"

#|ru| Поэтому мы будем продвигаться очень осторожно и создадим новый метод с желаемым параметром, для начала скопировав туда тело существующего.
#|en| So we will proceed very carefully, creating a new method with the desired parameter. To start, we will copy the body of the existing method.
#|uk| Тому ми будемо просуватися дуже обережно і створимо новий метод з бажаним параметром, для початку скопіювавши туди тіло існуючого.

Print:
```

  public List<Appointment> FindAppointments(DateTime date, string name)
  {
    List<Appointment> result = new List<Appointment>();

    foreach (Appointment item in kent.GetCourses())
    {
      if (date.Date == item.Date.Date)
      {
        result.Add(date);
      }
    }

    return result;
  }
```

Select 2nd "        result.Add(date);"

#|ru| Тело метода изменяем под использование нового метода.
#|en| Then we change the method body as needed for the new method.
#|uk| Тіло методу змінюємо під використання нового методу.

Print:
```
        if (string.IsNullOrEmpty(name) || name == item.Name)
        {
          result.Add(date);
        }
```

Select body of "FindAppointments"

#|ru| Теперь тело старого метода можно заменить вызовом нового метода.
#|en| Now the body of the old method can be replaced with a call to the new method.
#|uk| Тепер тіло старого методу можна замінити викликом нового методу.

Print:
```
    FindAppointments(date, null);
```

Set step 3

Select name of "FindAppointments"

#|ru| Далее нужно найти все обращения к старому методу и заменить их обращениями к новому.
#|en| Then find all references to the old method and replace them with references to the new one.
#|uk| Далі потрібно знайти всі звернення до старого методу і замінити їх зверненнями до нового.

Select "calendar.FindAppointments(today);"

#|ru| Вот одно из них. Так как нам нечего подать в новый параметр, впишем значение <code>null</code>.
#|en| Here is one of them. Since we have nothing to “give” to the new parameter, we write in the value <code>null</code>.
#|uk| Ось одне з них. Так як нам нема чого подавати в новий параметр, використаємо значення <code>null</code>.

Go to "calendar.FindAppointments(today|||);"

Print ", null"

Set step 4

Select whole "FindAppointments"

#|ru| После проведения всех изменений старый метод можно удалить.
#|en| After all changes have been made, go ahead and delete the old method.
#|uk| Після проведення всіх змін старий метод можна видалити.

Remove selected

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