replace-array-with-object:java

###

1. Создайте новый класс, который будет содержать данные их массива. Поместите в него сам массив как публичное поле.

2. Создайте поле для хранения объекта этого класса в исходном классе. Не забудьте создавать сам объект в том месте, где вы инициировали массив данных.

3. В новом классе, один за другим создавайте методы доступа для всех элементов массива.

4. Когда методы доступа созданы для всех ячеек, сделайте массив приватным.

5. Для каждого элемента массива, создайте приватное поле в классе и измените методы доступа так, чтобы они использовали это поле вместе массива.

6. Когда с этим покончено, удалите массив.



###

```
class Tournament {
  String[] row = new String[3];

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
  Performance row = new Performance();;

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

# Давайте рассмотрим рефакторинг <i>Замена массива объектом</i> на примере класса, хранящего название спортивной команды, количество выигрышей и количество поражений.

Go to the end of file

# Превращение массива в объект начинается с создания класса.

Print:
```


class Performance {
}
```

Go to the start of "Performance"

# На первом этапе мы добавляем в новый класс такое же поле массив, как и в оригинале. Не беспокойтесь, мы избавимся от него чуть позже.

Print:
```

  public String[] data = new String[3];
```

Set step 2

Select name of "Tournament"

# Теперь нужно найти места, в которых идут обращения к этому массиву, и заменить их обращениями к вашему новому классу.

Select "String[] row = new String[3]"

# В первую очередь создаём сам объект в том месте, где был инициирован массив данных.

Print:
```
Performance row = new Performance();
```

# Теперь нужно заменить код, использующий массив.

Select "row" in "public Tournament"
+Select "row" in "displayScore"

Replace "row.data"

Set step 3

Go to the end of "Performance"

# После этого в класс данных добавляем геттеры и сеттеры с более содержательными именами. Начнём с поля названия команды.

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

# Теперь нужно поочерёдно заменить код присваивания значениям элементов массива так, чтобы везде применялись методы класса <code>Perfomance</code>.

Select "row.data[0] = "Liverpool""

Replace "row.setName("Liverpool")"

Wait 500ms

Select "row.data[0]"

Replace "row.getName()"

Go to the end of "Performance"

# То же самое можно проделать со вторым элементом.

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

# Выполнив эти действия для всех элементов, можно объявить массив приватным.

Select "|||public||| String[] data"

Replace "private"

Set step 5

# Теперь главная часть этого рефакторинга – замена интерфейса – завершена. Однако полезно будет также заменить массив внутри класса данных.

Select name of "getName"
+ Select name of "setName"

# Это можно сделать, добавив поля для всех элементов массива и переориентировав методы доступа на их использование. Для начала преобразуем поле названия команды.

Go to "new String[3];|||"

Print:
```

  private String name;
```

Select "data[0]"

Replace "name"

Select name of "getScore"
+ Select name of "setScore"

# А затем – поле для хранения командного счёта.

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
  private String[] data = new String[3];

```
# Выполнив замену для всех элементов массива, можно удалить и само объявление массива из класса.

Remove selected

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.