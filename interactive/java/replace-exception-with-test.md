replace-exception-with-test:java

###

1.ru. Создайте условный оператор для граничного случая и поместите его перед <code>try</code>/<code>catch</code> блоком.

1.en. Create a conditional for an edge case and move it before the try/catch block.

1.uk. Створіть умовний оператор для граничного випадку і розташуйте його перед <code>try</code>/<code>catch</code> блоком.

2.ru. Переместите код из <code>catch</code>-секции внутрь этого условного оператора.

2.en. Move code from the <code>catch</code> section inside this conditional.

2.uk. Перемістіть код з <code>catch</code>-секції всередину цього умовного оператора.

3.ru. В <code>catch</code>-секции поставьте код выбрасывания обычного безымянного исключения и запустите все тесты.

3.en. In the <code>catch</code> section, place the code for throwing a usual unnamed exception and run all the tests.

3.uk. У <code>catch</code>-секції розташцуйте код викидання звичайного безіменного виключення і запустіть усі тести.

4.ru. Если никаких исключений не было выброшено во время тестов, избавьтесь от оператора <code>try</code>/<code>catch</code>.

4.en. If no exceptions were thrown during the tests, get rid of the <code>try</code>/<code>catch</code> operator.

4.uk. Якщо ніяких виключень не було викинуто під час тестів, позбавтеся від оператора <code>try</code>/<code>catch</code>.



###

```
class ResourcePool {
  // ...
  private Stack available;
  private Stack allocated;

  public Resource getResource() {
    Resource result;
    try {
      result = (Resource) available.pop();
      allocated.push(result);
      return result;
    } catch (EmptyStackException e) {
      result = new Resource();
      allocated.push(result);
      return result;
    }
  }
}
```

###

```
class ResourcePool {
  // ...
  private Stack available;
  private Stack allocated;

  public Resource getResource() {
    Resource result;
    if (available.isEmpty()) {
      result = new Resource();
    }
    else {
      result = (Resource) available.pop();
    }
    allocated.push(result);
    return result;
  }
}
```

###

Set step 1

#|ru| Для этого примера возьмём объект, управляющий ресурсами, создание которых обходится дорого, но возможно повторное их использование. Хороший пример такой ситуации дают соединения с базами данных.
#|en| For this example, we take an object that controls resources which are expensive to create but reusable. A good example of this situation is database connections.
#|uk| Для цього прикладу візьмемо об'єкт, керуючий ресурсами, створення яких обходиться дорого, але можливе їх повторне  використання. Хороший приклад такої ситуації дають з'єднання з базами даних.

Select "Stack |||available|||"
#|ru|+ У администратора соединений есть два пула, в одном из которых находятся ресурсы, доступные для использования, …
#|en|+ The administrator has two pools. One of them contains resources available for use…
#|uk|+ У адміністратора з'єднань є два пула, в одному з яких знаходяться ресурси, доступні для використання...

Select "Stack |||allocated|||"

#|ru|<= …а в другом – уже выделенные.
#|en|<= …and the other pool contains already allocated resources.
#|uk|<= ...а в іншому – вже виділені.

Select "(Resource) available.pop()"

#|ru|< Когда клиенту нужен ресурс, администратор предоставляет его из пула доступных и переводит в пул выделенных. Когда клиент высвобождает ресурс, администратор возвращает его обратно.
#|en|< When a client needs a resource, the administrator provides it from the pool of available resources and moves it to the allocated pool. When the client frees up the resource, the administrator returns it back.
#|uk|< Коли клієнту потрібен ресурс, адміністратор надає його з пулу з тих, що доступні, та переводить в пул виділених. Коли клієнт звільняє ресурс, адміністратор повертає його назад.

Select "result = new Resource();"

#|ru|< Если клиент запрашивает ресурс, когда свободных ресурсов нет, администратор создаёт новый ресурс.
#|en|< If a client requests a resource and no free resources remain, the administrator creates a new resource.
#|uk|< Якщо клієнт запитує ресурс, коли вільних ресурсів немає, адміністратор створює новий ресурс.

#|ru|< В данном случае нехватка ресурсов не является неожиданным происшествием, поэтому использование исключения не совсем оправдано.
#|en|< Insufficient resources are not an unexpected event, so using an exception is not truly justified.
#|uk|< В даному випадку нестача ресурсів не є несподіваною подією, тому використання винятку не зовсім виправдано.

Go to "Resource result;|||"

#|ru| Итак, попытаемся избавиться от исключения. Первым делом в начале метода создадим условный оператор, условие в котором будет совпадать с условием выброса исключения. Весь остальной код поместим в <code>else</code>.
#|en| So we can get rid of the exception. First, in the beginning of the method, create a conditional whose condition coincides with the condition for throwing an exception. Place all the remaining code in <code>else</code>.
#|uk| Отже, спробуємо позбутися від виключення. Першим ділом на початку методу створимо умовний оператор, умова в якому буде збігатися з умовою виключення винятку. Весь інший код помістимо в <code>else</code>.

Print:
```

    if (available.isEmpty()) {
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
      result = (Resource) available.pop();
      allocated.push(result);
      return result;
    } catch (EmptyStackException e) {
      result = new Resource();
      allocated.push(result);
      return result;
    }

```

Indent

Set step 2

Select:
```
        result = new Resource();
        allocated.push(result);
        return result;

```

#|ru| Далее скопируем код из <code>catch</code> секции внутрь граничного условного оператора.
#|en| Then copy the code from the <code>catch</code> section to inside the borderline conditional.
#|uk| Далі скопіюємо код з <code>catch</code> секції всередину граничного умовного оператора.

Go to "isEmpty()) {|||"

Print:
```

      result = new Resource();
      allocated.push(result);
      return result;
```

Set step 3

Go to "catch (EmptyStackException e) {|||"

#|ru| Полученный код никогда не должен достигать <code>catch</code> секции. Но чтобы убедиться в этом на 100%, вставим проверку внутрь секции и запустим все тесты.
#|en| The code so obtained should not reach the <code>catch</code> section. But to be 100% sure, insert a check inside the section and run all the tests.
#|uk| Отриманий код ніколи не повинен досягати <code>catch</code> секції. Але щоб переконатися в цьому на 100%, вставимо перевірку всередину секції і запустимо всі тести.

Print:
```

        throw new RuntimeException("Should not reach here.");
```

#C|ru| Посмотрим, что покажет компиляция и авто-тесты.
#S Всё отлично, можем продолжать!

#C|en| Let's compile and test.
#S Everything is OK! We can keep going.

#C|uk| Подивимося, що покаже компіляція і авто-тести.
#S Все добре, можемо продовжувати.

Set step 4

#|ru| Теперь мы можем удалить try/catch секцию, не беспокоясь о возможных ошибках.
#|en| Now we can remove the try/catch section without worrying about possible errors.
#|uk| Тепер ми можемо видалити try / catch секцію, не турбуючись про можливі помилки.

Select:
```
      try {

```

Remove selected

Select:
```
      } catch (EmptyStackException e) {
        throw new RuntimeException("Should not reach here.");
        result = new Resource();
        allocated.push(result);
        return result;
      }

```

Remove selected

Select:
```
        result = (Resource) available.pop();
        allocated.push(result);
        return result;
```

Deindent

Select:
```
      allocated.push(result);
      return result;

```

#|ru| Обычно после этого оказывается возможным привести в порядок условный код. В данном случае мы можем применить <a href="/consolidate-duplicate-conditional-fragments">консолидацию дублирующихся условных фрагментов</a>.
#|en| After this, it is usually possible to tidy up the conditional code. In this case, we can apply <a href="/consolidate-duplicate-conditional-fragments">Consolidate Duplicate Conditional Fragments</a>.
#|uk| Зазвичай після цього виявляється можливим привести в порядок умовний код. В даному випадку ми можемо застосувати <a href="/consolidate-duplicate-conditional-fragments">консолідацію дубльованих умовних фрагментів</a>.

Go to:
```
    }|||
  }
```

Print:
```

    allocated.push(result);
    return result;
```

Wait 500ms

Select:
```
      allocated.push(result);
      return result;

```

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