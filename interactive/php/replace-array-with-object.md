replace-array-with-object:php

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
5.en. For each element of the array, create a private field in the class and then change the access methods so that they use this field instead of the array.
5.uk. Для кожного елементу масиву створіть приватне поле в класі, після чого зміните методи доступу так, щоб вони використовували це поле замість масиву.

6.ru. Когда с этим покончено, удалите массив.
6.en. When this is done, delete the array.
6.uk. Коли з цим покінчено, видаліть масив.



###

```
class Tournament {
  public $row = array();
  
  public function __construct() {
    $this->row[0] = "Liverpool";
    $this->row[1] = "15";
  }
  public function displayScore() {
    $name = $this->row[0];
    $score = intval($this->row[1]);
    // ...
  }
}
```

###

```
class Tournament {
  public row; // Performance
  
  public function __construct() {
    $this->row = new Performance();
    $this->row->setName("Liverpool");
    $this->row->setScore("15");
  }
  public function displayScore() {
    $name = $this->row->getName();
    $score = $this->row->getScore();
    // ...
  }
}

class Performance {
  private $name;
  private $score;

  public function getName() {
    return $this->name;
  }
  public void setName($arg) {
    $this->name = $arg;
  }
  public function getScore() {
    return $this->score;
  }
  public function setScore($arg) {
    $this->score = intval($arg);
  }
}
```

###

Set step 1

#|ru| Давайте рассмотрим рефакторинг <i>Замена массива объектом</i> на примере класса, хранящего название спортивной команды и количество заработанных ею очков.
#|en| Let's look at <i>Replace Array with Object</i>, using a class that stores the name of an athletic team, number of wins and losses as our example.
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
#|en| Then, we add the same array field to the new class as in the original. Don't worry, this is a temporary measure that we will take care of later.
#|uk| На першому етапі ми додаємо в новий клас таке ж поле масив, як і в оригіналі. Не турбуйтеся, ми позбудемося нього трохи пізніше.

Print:
```

  public $data = array();
```

Set step 2

Select name of "Tournament"

#|ru| Теперь нужно найти места, в которых идут обращения к этому массиву, и заменить их обращениями к вашему новому классу.
#|en| Now we should find all code, which works with the array and replace it with calls to your new class.
#|uk| Тепер потрібно знайти місця, в яких йдуть звернення до цього масиву, і замінити їх зверненнями до вашого нового класу.

Select "public $row = array();"

#|ru| В первую очередь создаём сам объект в том месте, где был инициирован массив данных.
#|en| Create the instance of our data class in the place where the array had been initialized.
#|uk| В першу чергу створюємо сам об'єкт в тому місці, де був ініційований масив даних.

Print:
```
public row; // Performance
```

Go to start of "__construct"

Print:
```

    $this->row = new Performance();
```

Select "|||row|||[" in "__construct"
+Select "|||row|||[" in "displayScore"

#|ru| Теперь нужно заменить код, использующий массив.
#|en| Now replace the code that uses the array.
#|uk| Тепер потрібно замінити код, що використовує масив.

Replace "row->data"

Set step 3

Go to the end of "Performance"

#|ru| После этого в класс данных добавляем геттеры и сеттеры с более содержательными именами. Начнём с поля названия команды.
#|en| Add getters and setters with more self-explanatory names to the data class. Start with the field containing the team name.
#|uk| Після цього в клас даних додаємо геттери і сеттери з більш змістовними іменами. Почнемо з поля назви команди.

Print:
```


  public function getName() {
    return $this->data[0];
  }
  public void setName($arg) {
    $this->data[0] = $arg;
  }
```

Select "$this->row->data[0] = "Liverpool""
+ Select "$name = $this->row->data[0]"

#|ru| Теперь нужно поочерёдно заменить код присваивания значениям элементов массива так, чтобы везде применялись методы класса <code>Perfomance</code>.
#|en| Now we need to replace the code of assignment values to array elements with appropriate setters of the <code>Performance</code> class.
#|uk| Тепер потрібно по черзі замінити код присвоювання значень елементів масиву так, щоб скрізь застосовувалися методи класу <code>Perfomance</code>.

Select "$this->row->data[0] = "Liverpool""

Replace "$this->row->setName("Liverpool")"

Wait 500ms

Select "$this->row->data[0]"

Replace "$this->row->getName()"

Go to the end of "Performance"

#|ru| То же самое можно проделать со вторым элементом.
#|en| Do the same with the second element.
#|uk| Те ж саме можна зробити з другим елементом.

Print:
```

  public function getScore() {
    return intval($this->data[1]);
  }
  public function setScore($arg) {
    $this->data[1] = $arg;
  }
```


Select "$this->row->data[1] = "15""

Replace "$this->row->setScore("15")"

Wait 500ms

Select "intval($this->row->data[1])"

Replace "$this->row->getScore()"

Set step 4

Select "|||public||| $data"

#|ru| Выполнив эти действия для всех элементов, можно объявить массив приватным.
#|en| Having done so for all elements, we can declare the array private.
#|uk| Виконавши ці дії для всіх елементів, можна оголосити масив приватним.

Replace "private"

Set step 5

#|ru| Теперь главная часть этого рефакторинга – замена интерфейса – завершена. Однако полезно будет также заменить массив внутри класса данных.
#|en| The main part of this refactoring – replacing the interface – is now complete. But it will also be useful to replace the array inside the data class.
#|uk| Тепер головна частина цього рефакторинга – заміна інтерфейсу – завершена. Однак корисно буде також замінити масив всередині класу даних.

Select name of "getName"
+ Select name of "setName"

#|ru| Это можно сделать, добавив поля для всех элементов массива и переориентировав методы доступа на их использование. Для начала преобразуем поле названия команды.
#|en| To do this, we add fields for all array elements and reorient the access methods to use them. First convert the team name field.
#|uk| Це можна зробити, додавши поля для всіх елементів масиву і переорієнтувавши методи доступу на їх використання. Для початку перетворимо поле назви команди.

Go to "array();|||"

Print:
```

  private $name;
```

Select "data[0]"

Replace "name"

Select name of "getScore"
+ Select name of "setScore"

#|ru| А затем – поле для хранения командного счёта.
#|en| Then convert the field that stores the team win/loss history.
#|uk| А потім – поле для зберігання командного рахунку.

Go to "$name;|||"

Print:
```

  private $score;
```

Select "intval($this->data[1])"

Replace "$this->score"

Select "$this->data[1] = $arg"

Replace "$this->score = intval($arg)"

Set step 6

Select:
```
  private $data = array();

```
#|ru| Выполнив замену для всех элементов массива, можно удалить и само объявление массива из класса.
#|en| After finishing replacements for all the elements of the array, we can remove the array declaration from the class.
#|uk| Виконавши заміну для всіх елементів масиву, можна видалити і саме оголошення масиву з класу.

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