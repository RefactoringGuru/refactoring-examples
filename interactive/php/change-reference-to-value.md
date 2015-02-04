change-reference-to-value:php

###

1.ru. Обеспечьте неизменяемость объекта. Объект не должен иметь сеттеров или других методов, меняющих его состояние и данные (в этом может помочь <a href="/remove-setting-method">удаление сеттера</a>). Единственное место, где полям объекта-значения присваиваются какие-то данные, должен быть конструктор.
1.en. Make the object unchangeable. The object should not have any setters or other methods that change its state and data (<a href="/remove-setting-method">Remove Setting Method</a> may help here). The only place where data should be assigned to the fields of a value object is a constructor.
1.uk. Забезпечте незмінність об'єкту. Об'єкт не повинен мати сетерів або інших методів, що міняють його стан і дані (у цьому може допомогти <a href="/remove-setting-method">видалення сетера</a>). Єдиним місцем, де полям об'єкту-значення привласнюються якісь дані, має бути конструктор.

2.ru. Проверьте, возможно ли удалить фабричный метод и сделать конструктор объекта публичным.
2.en. Check whether you can delete the factory method and make the object constructor public.
2.uk. Перевірте, чи можливо видалити фабричний метод і зробити конструктор об'єкту публічним.



###

```
class Customer {
  private $name;
  private $birthDate;

  public function getName() {
    return $this->name;
  }
  public function getBirthDate() {
    return $this->birthday;
  }
  public function setBirthDate(DateTime $birthDate) {
    $this->birthDate = $birthDate;
  }
  private function __construct($name) {
    $this->name = $name;
  }

  private static $instances = array();

  public static function get($name) {
    if (!isset($this->instances[$name])) {
      $value = new Customer($name);
      $this->instances[$name] = $value;
    }
    else {
      $value = $this->instances[$name]
    }
    return value;
  }
}

// Somewhere in client code
$john = Customer::get("John Smith");
$john->setBirthDate(new DateTime("1985-01-01"));
```

###

```
class Customer {
  private $name;
  private $birthDate;

  public function getName() {
    return $this->name;
  }
  public function getBirthDate() {
    return $this->birthday;
  }
  public function __construct($name, DateTime birthDate) {
    $this->name = $name;
    $this->birthDate = $birthDate;
  }
}

// Somewhere in client code
$john = new Customer("John Smith", new Date(1985, 1, 1));
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Замену ссылки значением</i> на примере класса покупателя.
#|en| Let's look at <i>Change Reference to Value</i> using a customer class as an example.
#|uk| Давайте розглянемо <i>Заміну посилання значенням<i> на прикладі класу покупця.

Select "private |||$name|||"
+ Select "private |||$birthDate|||"

#|ru| Этот класс содержит имя и день рождения покупателя. Такой класс порождает объекты-ссылки, другими словами для одного реального покупателя создаётся только один экземпляр класса <code>Customer</code>.
#|en| This class contains a customer's name and date of birth. This class gives rise to reference objects, meaning that only one instance of the <code>Customer</code> class is created for one real customer.
#|uk| Цей клас містить ім'я і день народження покупця. Такий клас породжує об'єкти-посилання, іншими словами для одного реального покупця створюється тільки один екземпляр класу <code>Customer</code>.

Select "Customer::get("John Smith")"

#|ru| Таким образом, для получения экземпляра может использоваться следующий код.
#|en| The code for getting the instance may look as follows:
#|uk| Таким чином, для отримання примірника може використовуватися наступний код.

Select visibility of "__construct"

#|ru| Класс <code>Customer</code> ведёт реестр своих экземпляров. Мы не можем просто обратиться к конструктору (потому он и является закрытым).
#|en| The <code>Customer</code> class keeps a registry of its instances. We cannot simply access the constructor (because it is private).
#|uk| Клас <code>Customer</code> веде реєстр своїх примірників. Ми не можемо просто звернутися до конструктора (тому він і є закритим).

Select name of "get"

#|ru| Вместо этого мы вызываем статический фабричный метод, который ищет покупателя среди уже созданных объектов. И только в случае, если такой объект ещё не создан, фабричный метод запускает реальный конструктор, а затем добавляет созданный объект в реестр.
#|en| Instead, we call a static factory method that looks for the customer among the objects already created. And only if such an object has not been created does the factory method run the real constructor and then add the newly created object to the registry.
#|uk| Замість цього ми викликаємо статичний фабричний метод, який шукає покупця серед вже створених об'єктів. І тільки в разі, якщо такий об'єкт ще не створений, фабричний метод запускає реальний конструктор, а потім додає створений об'єкт в реєстр.

#|ru| Теперь, допустим, у вас есть несколько заказов, ссылающихся на одного и того же клиента. Внезапно код одного из заказов меняет значение даты рождения клиента. Так как оба заказа ссылаются на один тот же объект клиента, новая дата рождения будет доступна также из другого заказа.
#|en| Now let's say that we have multiple orders referring to the same client. Suddenly, the code of one of the orders changes the value of the client's date of birth. Since both orders refer to the same client object, the new date of birth will be available from the other order as well.
#|uk| Тепер, припустимо, у вас є кілька замовлень, які посилаються на одного і того ж клієнта. Раптово код одного з замовлень міняє значення дати народження клієнта. Так як обидва замовлення посилаються на один той самий об'єкт клієнта, нова дата народження буде доступна також з іншого замовлення.

#|ru| Было бы такое возможно, если б каждый заказ имел разные экземпляры класса <code>Customer</code>? Пожалуй, нет. Вот почему главным требованием этого рефакторинга является приведение класса к неизменяемому виду. В некоторых случаях это вообще невозможно и от рефакторинга нужно отказаться.
#|en| Would this be made impossible if each order had own instance of the <code>Customer</code> class? Probably not. That is why the main requirement of this refactoring is making the class immutable. In some cases, this is simply not possible, and the refactoring should not be executed.
#|uk| Було б таке можливим, якщо б кожне замовлення мало різні екземпляри класу <code>Customer</code>? Мабуть, ні. Ось чому головною вимогою цього рефакторинга є приведення класу до незмінного виляду. В деяких випадках це взагалі неможливо і від рефакторинга потрібно відмовитися.

Select whole "setBirthDate"

#|ru| Следуя этой логике, необходимо убрать сеттер поля даты рождения и инициализировать значение этого поля в конструкторе. Применим рефакторинг <a href="/remove-setting-method">удаление сеттера</a>.
#|en| Following this logic, we should remove the setter of the date of birth field. Then, initialize the value of the field in the constructor. This could be done with <a href="/remove-setting-method">Remove Setting Method</a>.
#|uk| Слідуючи цій логіці, необхідно прибрати сетер поля дати народження і ініціалізувати значення цього поля в конструкторі. Застосуємо рефакторинг <a href="/remove-setting-method">видалення сетера</a>.

Remove selected

Go to the end of parameters of "__construct"

Print ", DateTime birthDate"

Go to the end of "__construct"

Print:
```

    $this->birthDate = $birthDate;
```

Select:
```

$john->setBirthDate(new DateTime("1985-01-01"));
```

#|ru| Так как сеттера в классе теперь нет, нам нужно удалить его использование в клиентском коде. Действие этого сеттера пока нечем компенсировать, но не волнуйтесь, мы рассмотрим это чуточку позже.
#|en| Since the class no longer contains a setter, we need to remove its use in the client code. Note that we don't have anything to compensate that setter yet. But don't worry, we will get to this a bit later.
#|uk| Так як сетера в класі тепер немає, нам потрібно видалити його використання в клієнтському коді. Дію цього сетера поки нічим компенсувати, але не хвилюйтеся, ми розглянемо це трішки пізніше.

Remove selected

Set step 2

Select:
```

  private static $instances = array();


```
+ Select whole "get"

#|ru| Остался последний штрих. Так как у нас теперь нет нужды хранить реестр созданных объектов, фабричный метод можно удалить, а конструктор сделать публичным.
#|en| And one last thing. Since we no longer need to keep a registry of created objects, we can remove the factory method and make the constructor public.
#|uk| Залишився останній штрих. Так як у нас тепер немає потреби зберігати реєстр створених об'єктів, фабричний метод можна видалити, а конструктор зробити публічним.

Remove selected

Select visibility of "__construct"

Replace "public"

Select "Customer::get("John Smith")"

#|ru| После всех этих изменений, клиентский код тоже изменится.
#|en| The client code will also change as the result of these changes.
#|uk| Після всіх цих змін, клієнтський код теж зміниться.

Print "new Customer("John Smith", new Date(1985, 1, 1))"

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