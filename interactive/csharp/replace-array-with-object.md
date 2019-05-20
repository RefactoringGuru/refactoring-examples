replace-array-with-object:csharp

###

1.ru. Создайте новый класс, который будет содержать данные из массива. Поместите в него сам массив как публичное поле.
1.en. Create the new class that will contain the data from the array. Place the array itself in the class as a public field.
1.uk. Створіть новий клас, який міститиме дані з масиву. Помістіть в нього сам масив як публічне поле.

2.ru. Создайте поле для хранения объекта этого класса в исходном классе.
2.en. Create a field for storing the object of this class in the original class.
2.uk. Створіть поле для зберігання об'єкту цього класу в початковому класі.

3.ru. В новом классе, один за другим создайте публичные свойства для всех элементов массива.
3.en. In the new class, create public properties one by one for all elements of the array.
3.uk. У новому класі, одна за одною, створіть публічні властивості для всіх елементів масиву.

4.ru. Когда все элементы продублированы, удалите массив.
4.en. When all data has been moved, delete the array.
4.uk. Коли всі елементи будуть продубльовані, видаліть масив.



###

```
public class Tournament
{
  string[] row = new string[2];

  public Tournament()
  {
    row[0] = "Liverpool";
    row[1] = "15";
  }
  public void DisplayScore()
  {
    string name = row[0];
    int score = Convert.ToInt32(row[1]);
    // ...
  }
}
```

###

```
public class Tournament
{
  Performance row = new Performance();

  public Tournament()
  {
    row.Name = "Liverpool";
    row.Score = 15;
  }
  public void DisplayScore()
  {
    string name = row.Name;
    int score = row.Score;
    // ...
  }
}

public class Performance
{
  public string Name
  {
    get;
    set;
  }
  public int Score
  {
    get;
    set;
  }
}
```

###

Set step 1

#|ru| Давайте рассмотрим рефакторинг <i>Замена массива объектом</i> на примере класса, хранящего название спортивной команды и количество заработанных ею очков.
#|en| Let's look at <i>Replace Array with Object</i>, using a class that stores the name of an athletic team, number of wins and losses as our example.
#|uk| Давайте розглянемо рефакторинг <i>Заміна масиву об'єктом</i> на прикладі класу, який зберігає назву спортивної команди, кількість виграшів і кількість поразок.

Go to the end of file

#|ru| Превращение массива в объект начинается с создания класса.
#|en| Converting an array to an object starts with creating a class.
#|uk| Перетворення масиву в об'єкт починається зі створення класу.

Print:
```


public class Performance
{
}
```

Go to the start of "Performance"

#|ru| На первом этапе мы добавляем в новый класс такое же поле массив, как и в оригинале. Не беспокойтесь, мы избавимся от него чуть позже.
#|en| Then, we add the same array field to the new class as in the original. Don't worry, this is a temporary measure that we will take care of later.
#|uk| На першому етапі ми додаємо в новий клас таке ж поле масив, як і в оригіналі. Не турбуйтеся, ми позбудемося нього трохи пізніше.

Print:
```

  public string[] data = new string[2];
```

Set step 2

Select name of "Tournament"

#|ru| Теперь нужно найти места, в которых идут обращения к этому массиву, и заменить их обращениями к вашему новому классу.
#|en| Now we should find all code, which works with the array and replace it with calls to your new class.
#|uk| Тепер потрібно знайти місця, в яких йдуть звернення до цього масиву, і замінити їх зверненнями до вашого нового класу.

Select "string[] row = new string[2]"

#|ru| В первую очередь создаём сам объект в том месте, где был инициирован массив данных.
#|en| Create the instance of our data class in the place where the array had been initialized.
#|uk| В першу чергу створюємо сам об'єкт в тому місці, де був ініційований масив даних.

Print:
```
Performance row = new Performance()
```

Select "row" in "public Tournament"
+Select "row" in "DisplayScore"

#|ru| Теперь нужно заменить код, использующий массив.
#|en| Now replace the code that uses the array.
#|uk| Тепер потрібно замінити код, що використовує масив.

Replace "row.data"

Set step 3

Go to the end of "Performance"

#|ru| После этого в класс данных добавляем публичные свойства с более содержательными именами. Начнём с поля названия команды.
#|en| Add public properties with more self-explanatory names to the data class. Start with the field containing the team name.
#|uk| Після цього в клас даних додаємо публічні властивості з більш змістовними іменами. Почнемо з поля назви команди.

Print:
```

  public string Name
  {
    get;
    set;
  }
```

Select "row.data[0] = "Liverpool""
+ Select "string name = row.data[0]"

#|ru| Теперь нужно поочерёдно заменить код присваивания значениям элементов массива так, чтобы везде применялись методы класса <code>Perfomance</code>.
#|en| Now we need to replace the code of assignment values to array elements with appropriate setters of the <code>Performance</code> class.
#|uk| Тепер потрібно по черзі замінити код присвоювання значень елементів масиву так, щоб скрізь застосовувалися методи класу <code>Perfomance</code>.

Select "row.data[0] = "Liverpool""

Replace "row.Name = "Liverpool""

Wait 500ms

Select "row.data[0]"

Replace "row.Name"

Go to the end of "Performance"

#|ru| То же самое можно проделать со вторым элементом (обратите внимание, что мы сразу создаем свойство с желаемым типом <code>int</code>).
#|en| The same can be done with the second element (note that we are immediately creating a property with the desired <code>int</code> type).
#|uk| Те ж саме можна зробити з другим елементом (зверніть увагу, що ми відразу створюємо властивість з бажаним типом <code>int</code>).

Print:
```

  public int Score
  {
    get;
    set;
  }
```


Select "row.data[1] = "15""

Replace "row.Score = 15"

Wait 500ms

Select "Convert.ToInt32(row.data[1])"

Replace "row.Score"

Set step 4

Select:
```
  public string[] data = new string[2];

```

#|ru| Выполнив замену для всех элементов массива, можно удалить и само объявление массива из класса.
#|en| After finishing replacements for all the elements of the array, we can remove the array declaration from the class.
#|uk| Виконавши заміну для всіх елементів масиву, можна видалити і саме оголошення масиву з класу.

Remove selected

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