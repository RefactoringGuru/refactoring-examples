rename-method:java

###

1. Проверьте, не определён ли метод в суперклассе или подклассе. Если так, нужно будет повторить все шаги и в этих классах.

2. Следующий шаг важен, чтобы сохранить работоспособность программы во время рефакторинга. Итак, создайте новый метод с новыми именем. Скопируйте туда код старого метода. Удалите весь код в старом методе и вставьте вместо него вызов нового метода.

3. Найдите все обращения к старому методу и замените их обращениями к новому.

4. Удалите старый метод. Этот шаг неосуществим, если старый метод является частью публичного интерфейса. В этом случае, старый метод нужно пометить как устаревший (`deprecated`).



###

```
class Person {
  //...
  public String getTelephoneNumber() {
    return ("(" + officeAreaCode + ") " + officeNumber);
  }
}

// Client code
phone = employee.getTelephoneNumber();
```

###

```
class Person {
  //...
  public String getOfficeTelephoneNumber() {
    return ("(" + officeAreaCode + ") " + officeNumber);
  }
}

// Client code
phone = employee.getOfficeTelephoneNumber();
```

###

Select name of "getTelephoneNumber"

# Имеется метод для получения номера телефона лица.

# Мы решили переименовать метод в <code>getOfficeTelephoneNumber</code>, дабы он лучше отражал то, что делает.

Go to the end of "Person"

# Начинаю с создания нового метода и копирования тела в новый метод.

Print:
```

  public String getOfficeTelephoneNumber() {
    return ("(" + officeAreaCode + ") " + officeNumber);
  }
```

# Старый метод изменяется так, чтобы вызывать новый.

Select body of "getTelephoneNumber"

Print "    getOfficeTelephoneNumber();"

# Теперь находим места вызова прежнего метода и изменяем их так, чтобы в них вызывался новый метод.

Select "employee.|||getTelephoneNumber|||()"

Print "getOfficeTelephoneNumber"

Select whole "getTelephoneNumber"

# После всех изменений прежний метод можно удалить.

Remove selected

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.