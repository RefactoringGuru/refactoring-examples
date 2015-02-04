remove-control-flag:php

###

1.ru. Найдите присваивание значения управляющему флагу, которое приводит к выходу из цикла или текущей итерации.
1.en. Find the value assignment to the control flag that causes the exit from the loop or current iteration.
1.uk. Знайдіть, де керюючому флагу программа надае якесь значення, завдяки якому виконується виход з циклу або поточної ітерації.

2.ru. Замените его на <code>break</code>, если это выход из цикла, или <code>continue</code>, если это выход из итерации, или <code>return</code>, если нужно вернуть это значение из функции.
2.en. Replace it with <code>break</code> if exiting a loop, <code>continue</code> if exiting an iteration, or <code>return</code> if needing to return a value from the function.
2.uk. Замініть його на <code>break</code>, якщо це вихід з циклу або <code>continue</code>, якщо це вихід з ітерації.

3.ru. Уберите весь остальной код и проверки, связанные с управляющим флагом.
3.en. Remove the remaining code and checks associated with the control flag.
3.uk. Приберіть увесь інший код і перевірки, пов'язані з керуючим флагом.



###

```
function checkSecurity(array $people) {
  $found = false;
  for ($i = 0; $i < count($people); $i++) {
    if (!$found) {
      if ($people[$i] == "Don") {
        sendAlert();
        $found = true;
      }
      if ($people[$i] == "John") {
        sendAlert();
        $found = true;
      }
    }
  }
}
```

###

```
function checkSecurity(array $people) {
  for ($i = 0; $i < count($people); $i++) {
    if ($people[$i] == "Don") {
      sendAlert();
      break;
    }
    if ($people[$i] == "John") {
      sendAlert();
      break;
    }
  }
}
```

###

Set step 1

#|ru| Следующая функция проверяет, не содержится ли в списке лиц кто-либо из парочки подозрительных, имена которых жёстко закодированы (Don и John).
#|en| The following function checks whether the list of people contains anybody suspicious; these suspicious names (Don and John) are hard-coded.
#|uk| Наступна функція перевіряє, чи не міститься в списку осіб хто-небудь з парочки підозрілих, імена яких жорстко закодовані (Don і John).

Select "|||$found||| = false"

#|ru| В этой функции переменная <code>found</code> является управляющим флагом. Она инициализируется одним значением…
#|en| In this function, the variable <code>found</code> is a control flag. It is initialized by one value…
#|uk| У цій функції змінна <code>found</code> є керуючим прапором. Вона ініціалузується одним значенням…

Select "|||$found||| = true"

#|ru| …которое меняется по ходу выполнения функции…
#|en| …which changes as the function is run…
#|uk| …яке змінюється по ходу виконання функції…

Select "(!$found)"

#|ru| …после чего, код больше ничего не делает до конца выполнения цикла.
#|en| …after which the code does not do anything more until the loop is finished.
#|uk| …після чого, код більше нічого не робить до кінця виконання циклу.

Select "$found = true"

#|ru| Данный рефакторинг начинается с того, что мы ищем присваивания управляющей переменной, которые влияют на ход выполнения программы. В данном случае это присваивания значения <code>true</code>.
#|en| This refactoring starts with us looking for any assignments to the control variable that affect the execution flow of the program. In our case, this is assignments of the <code>true</code> value.
#|uk| Даний рефакторинг починається з того, що ми шукаємо присвоювання керуючої змінної, які впливають на хід виконання програми. В даному випадку це надання значення <code>true</code>.

Set step 2

#|ru| По логике нашего метода после этих присваиваний код внутри цикла уже ничего не должен делать, поэтому мы можем просто заменить их оператором <code>break</code>, сэкономив несколько холостых итераций цикла.
#|en| According to the logic of this method, we can simply replace assignments to control flags with <code>break</code> operator.
#|uk| За логікою нашого методу після цих присвоювань код всередині циклу вже нічого не повинен робити, тому ми можемо просто замінити їх оператором <code>break</code>, заощадивши кілька холостих ітерацій циклу.

Print "break"

Set step 3

#|ru| После этого можно убрать все упоминания управляющего флага.
#|en| Then we can remove all other mentions of the control flag.
#|uk| Після цього можна прибрати всі згадки керуючого прапора.


Select:
```
    if (!$found) {

```

+ Select:
```
      }
|||    }
|||  }
}
```

Remove selected

Select "      "

Replace "    "

Wait 500ms

Select:
```
  $found = false;

```

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