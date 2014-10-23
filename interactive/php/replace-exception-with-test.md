replace-exception-with-test:php

###

1.ru. Создайте условный оператор для граничного случая и поместите его перед <code>try</code>/<code>catch</code> блоком.
1.uk. Створіть умовний оператор для граничного випадку і розташуйте його перед <code>try</code>/<code>catch</code> блоком.

2.ru. Переместите код из <code>catch</code>-секции внутрь этого условного оператора.
2.uk. Перемістіть код з <code>catch</code>-секції всередину цього умовного оператора.

3.ru. В <code>catch</code>-секции поставьте код выбрасывания обычного безымянного исключения и запустите все тесты.
3.uk. У <code>catch</code>-секції розташцуйте код викидання звичайного безіменного виключення і запустіть усі тести.

4.ru. Если никаких исключений не было выброшено во время тестов, избавьтесь от оператора <code>try</code>/<code>catch</code>.
4.uk. Якщо ніяких виключень не було викинуто під час тестів, позбавтеся від оператора <code>try</code>/<code>catch</code>.



###

```
class ResourcePool {
  // ...
  private $available; // SplStack 
  private $allocated; // SplStack 

  public function getResource() {
    try {
      $result = $this->available->pop();
      $this->available->push($this->allocated, $result);
      return $result;
    } catch (RuntimeException $e) {
      $result = new Resource();
      $this->available->push($this->allocated, $result);
      return $result;
    }
  }
}
```

###

```
class ResourcePool {
  // ...
  private $available; // SplStack 
  private $allocated; // SplStack 

  public function getResource() {
    if ($this->available->isEmpty()) {
      $result = new Resource();
    }
    else {
      $result = $this->available->pop();
    }
    $this->available->push($this->allocated, $result);
    return $result;
  }
}
```

###

Set step 1

#|ru| Для этого примера возьмём объект, управляющий ресурсами, создание которых обходится дорого, но возможно повторное их использование. Хороший пример такой ситуации дают соединения с базами данных.
#|uk| Для цього прикладу візьмемо об'єкт, керуючий ресурсами, створення яких обходиться дорого, але можливе їх повторне  використання. Хороший приклад такої ситуації дають з'єднання з базами даних.

Select "private |||$available|||"
#|ru|+ У администратора соединений есть два пула, в одном из которых находятся ресурсы, доступные для использования, …
#|uk|+ У адміністратора з'єднань є два пула, в одному з яких знаходяться ресурси, доступні для використання...

Select "private |||$allocated|||"

#|ru|<= …а в другом – уже выделенные.
#|uk|<= ...а в іншому – вже виділені.

Select "$this->available->pop()"

#|ru|< Когда клиенту нужен ресурс, администратор предоставляет его из пула доступных и переводит в пул выделенных. Когда клиент высвобождает ресурс, администратор возвращает его обратно.
#|uk|< Коли клієнту потрібен ресурс, адміністратор надає його з пулу з тих, що доступні, та переводить в пул виділених. Коли клієнт звільняє ресурс, адміністратор повертає його назад.

Select "$result = new Resource();"

#|ru|< Если клиент запрашивает ресурс, когда свободных ресурсов нет, администратор создаёт новый ресурс.
#|uk|< Якщо клієнт запитує ресурс, коли вільних ресурсів немає, адміністратор створює новий ресурс.

#|ru|< В данном случае нехватка ресурсов не является неожиданным происшествием, поэтому использование исключения не совсем оправдано.
#|uk|< В даному випадку нестача ресурсів не є несподіваною подією, тому використання винятку не зовсім виправдано.

Go to the start of "getResource"

#|ru| Итак, попытаемся избавиться от исключения. Первым делом в начале метода создадим условный оператор, условие в котором будет совпадать с условием выброса исключения. Весь остальной код поместим в <code>else</code>.
#|uk| Отже, спробуємо позбутися від виключення. Першим ділом на початку методу створимо умовний оператор, умова в якому буде збігатися з умовою виключення винятку. Весь інший код помістимо в <code>else</code>.

Print:
```

    if ($this->available->isEmpty()) {
    }
    else {
```

Go to:
```
    }|||
  }
```

Print:
```

    }
```

Select:
```
    try {
      $result = $this->available->pop();
      $this->available->push($this->allocated, $result);
      return $result;
    } catch (RuntimeException $e) {
      $result = new Resource();
      $this->available->push($this->allocated, $result);
      return $result;
    }

```

Indent

Set step 2

Select:
```
        $result = new Resource();
        $this->available->push($this->allocated, $result);
        return $result;

```

#|ru| Далее скопируем код из <code>catch</code> секции внутрь граничного условного оператора.
#|uk| Далі скопіюємо код з <code>catch</code> секції всередину граничного умовного оператора.

Go to "isEmpty()) {|||"

Print:
```

      $result = new Resource();
      $this->available->push($this->allocated, $result);
      return $result;
```

Set step 3

Go to "catch (RuntimeException $e) {|||"

#|ru| Полученный код никогда не должен достигать <code>catch</code> секции. Но чтобы убедиться в этом на 100%, вставим проверку внутрь секции и запустим все тесты.
#|uk| Отриманий код ніколи не повинен досягати <code>catch</code> секції. Але щоб переконатися в цьому на 100%, вставимо перевірку всередину секції і запустимо всі тести.

Print:
```

        throw new RuntimeException("Should not reach here.");
```

#C|ru| Посмотрим, что покажут авто-тесты.
#S Всё отлично, можем продолжать!

#C|uk| Подивимося, що покажуть авто-тести.
#S Все добре, можемо продовжувати.

Set step 4

#|ru| Теперь мы можем удалить try/catch секцию, не беспокоясь о возможных ошибках.
#|uk| Тепер ми можемо видалити try / catch секцію, не турбуючись про можливі помилки.

Select:
```
      try {

```

Remove selected

Select:
```
      } catch (RuntimeException $e) {
        throw new RuntimeException("Should not reach here.");
        $result = new Resource();
        $this->available->push($this->allocated, $result);
        return $result;
      }

```

Remove selected

Select:
```
        $result = $this->available->pop();
        $this->available->push($this->allocated, $result);
        return $result;
```

Deindent

Select:
```
      $this->available->push($this->allocated, $result);
      return $result;

```

#|ru| Обычно после этого оказывается возможным привести в порядок условный код. В данном случае мы можем применить <a href="/consolidate-duplicate-conditional-fragments">консолидацию дублирующихся условных фрагментов</a>.
#|uk| Зазвичай після цього виявляється можливим привести в порядок умовний код. В даному випадку ми можемо застосувати <a href="/consolidate-duplicate-conditional-fragments">консолідацію дубльованих умовних фрагментів</a>.

Go to:
```
    }|||
  }
```

Print:
```

    $this->available->push($this->allocated, $result);
    return $result;
```

Wait 500ms

Select:
```
      $this->available->push($this->allocated, $result);
      return $result;

```

Remove selected

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.