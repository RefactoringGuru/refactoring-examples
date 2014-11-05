replace-array-with-object:java

###

1.ru. Создайте новый класс, который будет содержать данные из массива. Поместите в него сам массив как публичное поле.
1.en. Create the new class that will contain the data from the array. Place the array itself in the class as a public field.
1.uk. Створіть новий клас, який міститиме дані з масиву. Помістіть в нього сам масив як публічне поле.

2.ru. Создайте поле для хранения объекта этого класса в исходном классе.
2.en. Create a field for storing the object of this class in the original class.
2.uk. Створіть поле для зберігання об'єкту цього класу в початковому класі.

3.ru. В новом классе, один за другим создайте методы доступа для всех элементов массива.
3.en. In the new class, create access methods one by one for all elements of the array.
3.uk. У новому класі, один за іншим створюйте методи доступу для всіх елементів масиву.

4.ru. Когда методы доступа будут созданы для всех ячеек, сделайте массив приватным.
4.en. When access methods have been created for all elements, make the array private.
4.uk. Коли методи доступу будуть створені для усіх елементів, зробіть масив приватним.

5.ru. Для каждого элемента массива, создайте приватное поле в классе и измените методы доступа так, чтобы они использовали это поле вместе массива.
5.en. For each element of the array, create a private method in the class and then change the access methods so that they use this field instead of the array.
5.uk. Для кожного елементу масиву створіть приватне поле в класі, після чого зміните методи доступу так, щоб вони використовували це поле замість масиву.

6.ru. Когда с этим покончено, удалите массив.
6.en. When this is done, delete the array.
6.uk. Коли з цим покінчено, видаліть масив.



###

```
class Tournament {
  String[] row = new String[2];

  public Tournament() {
    row[0] = "Liverpool";
    row[1] = "15";
  }
  public void displayScore() {
    String name = row[0];
    int score = Integer.parseInt(row[1]);
    // ...
  }
}
```

###

```
class Tournament {
  Performance row = new Performance();

  public Tournament() {
    row.setName("Liverpool");
    row.setScore("15");
  }
  public void displayScore() {
    String name = row.getName();
    int score = row.getScore();
    // ...
  }
}

class Performance {
  private String name;
  private int score;

  public String getName() {
    return name;
  }
  public void setName(String arg) {
    name = arg;
  }
  public int getScore() {
    return score;
  }
  public void setScore(String arg) {
    score = Integer.parseInt(arg);
  }
}
```

###

Set step 1

#|ru| Давайте рассмотрим рефакторинг <i>Замена массива объектом</i> на примере класса, хранящего название спортивной команды и количество заработанных ею очков.
#|en| Let's look at <i>Replace Array with Object</i>, using as our example a class that stores the name of an athletic team, number of wins, and number of losses.
#|uk| Давайте розглянемо рефакторинг <i>Заміна масиву об'єктом<i> на прикладі класу, який зберігає назву спортивної команди, кількість виграшів і кількість поразок.

Go to the end of file

#|ru| Превращение массива в объект начинается с создания класса.
#|en| Converting an array to an object starts with creating a class.
#|uk| Перетворення масиву в об'єкт починається зі створення класу.

Print:
```


class Performance {
}
```

Go to the start of "Performance"

#|ru| На первом этапе мы добавляем в новый класс такое же поле массив, как и в оригинале. Не беспокойтесь, мы избавимся от него чуть позже.
#|en| We start by adding the same array field to the new class as in the original. Don't worry, this is a temporary measure that we will dispense with later.
#|uk| На першому етапі ми додаємо в новий клас таке ж поле масив, як і в оригіналі. Не турбуйтеся, ми позбудемося нього трохи пізніше.

Print:
```

  public String[] data = new String[2];
```

Set step 2

Select name of "Tournament"

#|ru| Теперь нужно найти места, в которых идут обращения к этому массиву, и заменить их обращениями к вашему новому классу.
#|en| Now find places with references to the array and replace them with references to your new class.
#|uk| Тепер потрібно знайти місця, в яких йдуть звернення до цього масиву, і замінити їх зверненнями до вашого нового класу.

Select "String[] row = new String[2]"

#|ru| В первую очередь создаём сам объект в том месте, где был инициирован массив данных.
#|en| Create the object itself in the place where the array had been initialized.
#|uk| В першу чергу створюємо сам об'єкт в тому місці, де був ініційований масив даних.

Print:
```
Performance row = new Performance()
```

Select "row" in "public Tournament"
+Select "row" in "displayScore"

#|ru| Теперь нужно заменить код, использующий массив.
#|en| Now replace the code that uses the array.
#|uk| Тепер потрібно замінити код, що використовує масив.

Replace "row.data"

Set step 3

Go to the end of "Performance"

#|ru| После этого в класс данных добавляем геттеры и сеттеры с более содержательными именами. Начнём с поля названия команды.
#|en| Add getters and setters with more self-explanatory names to the data class. Start with the field containing the team name.
#|uk| Після цього в клас даних додаємо геттери і сеттери з більш змістовними іменами. Почнемо з поля назви команди.

Print:
```


  public String getName() {
    return data[0];
  }
  public void setName(String arg) {
    data[0] = arg;
  }
```

Select "row.data[0] = "Liverpool""
+ Select "String name = row.data[0]"

#|ru| Теперь нужно поочерёдно заменить код присваивания значениям элементов массива так, чтобы везде применялись методы класса <code>Perfomance</code>.
#|en| One by one, replace the code for assignment to array element values. The methods of the <code>Performance</code> class will now be used everywhere instead.
#|uk| Тепер потрібно по черзі замінити код присвоювання значень елементів масиву так, щоб скрізь застосовувалися методи класу <code>Perfomance</code>.

Select "row.data[0] = "Liverpool""

Replace "row.setName("Liverpool")"

Wait 500ms

Select "row.data[0]"

Replace "row.getName()"

Go to the end of "Performance"

#|ru| То же самое можно проделать со вторым элементом.
#|en| Do the same with the second element.
#|uk| Те ж саме можна зробити з другим елементом.

Print:
```

  public int getScore() {
    return Integer.parseInt(data[1]);
  }
  public void setScore(String arg) {
    data[1] = arg;
  }
```


Select "row.data[1] = "15""

Replace "row.setScore("15")"

Wait 500ms

Select "Integer.parseInt(row.data[1])"

Replace "row.getScore()"

Set step 4

Select "|||public||| String[] data"

#|ru| Выполнив эти действия для всех элементов, можно объявить массив приватным.
#|en| Having done so for all elements, we can declare the array private.
#|uk| Виконавши ці дії для всіх елементів, можна оголосити масив приватним.

Replace "private"

Set step 5

#|ru| Теперь главная часть этого рефакторинга – замена интерфейса – завершена. Однако полезно будет также заменить массив внутри класса данных.
#|en| The main part of this refactoring technique – replacing the interface – is now complete. But it will also be useful to replace the array inside the data class.
#|uk| Тепер головна частина цього рефакторинга – заміна інтерфейсу – завершена. Однак корисно буде також замінити масив всередині класу даних.

Select name of "getName"
+ Select name of "setName"

#|ru| Это можно сделать, добавив поля для всех элементов массива и переориентировав методы доступа на их использование. Для начала преобразуем поле названия команды.
#|en| To do this, add fields for all array elements and reorient the access methods to use them. First convert the team name field.
#|uk| Це можна зробити, додавши поля для всіх елементів масиву і переорієнтувавши методи доступу на їх використання. Для початку перетворимо поле назви команди.

Go to "new String[2];|||"

Print:
```

  private String name;
```

Select "data[0]"

Replace "name"

Select name of "getScore"
+ Select name of "setScore"

#|ru| А затем – поле для хранения командного счёта.
#|en| Then convert the field that stores the team win/loss history.
#|uk| А потім – поле для зберігання командного рахунку.

Go to "String name;|||"

Print:
```

  private int score;
```

Select "Integer.parseInt(data[1])"

Replace "score"

Select "data[1] = arg"

Replace "score = Integer.parseInt(arg)"

Set step 6

Select:
```
  private String[] data = new String[2];

```
#|ru| Выполнив замену для всех элементов массива, можно удалить и само объявление массива из класса.
#|en| After finishing replacement for all the elements of the array, you can remove the array declaration from the class.
#|uk| Виконавши заміну для всіх елементів масиву, можна видалити і саме оголошення масиву з класу.

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