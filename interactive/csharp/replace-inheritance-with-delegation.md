replace-inheritance-with-delegation:csharp

###

1.ru. Создайте поле в подклассе для содержания экземпляра базового класса. На первом этапе поместите в него текущий объект.
1.en. Create a field in the subclass for holding the base class. During the initial stage, place the current object in it.
1.uk. Створіть поле в підкласі для утримання примірника базового класу. На першому етапі додайте в нього поточний об'єкт.

2.ru. Измените методы подкласса так, чтобы они использовали объект базового класса, вместо <code>this</code>.
2.en. Change the subclass methods so that they use the base class object instead of <code>this</code>.
2.uk. Змініть методи підкласу так, щоб вони використовували об'єкт базового класу, замість <code>this</code>.

3.ru. Уберите объявление наследования из подкласса.
3.en. Remove the inheritance declaration from the subclass.
3.uk. Приберіть оголошення спадкоємства з підкласу.

4.ru. Измените код инициализации поля, в котором хранится бывший базовый класс, созданием нового экземпляра класса.
4.en. Change the initialization code of the field in which the former base class is stored by creating a new class instance.
4.uk. Змініть код ініціалізації поля новим екземпляром базового класу.



###

```
public class Engine
{
  //…
  public double Fuel
  { get; set; }
  public double CV
  { get; set; }
}

public class Car: Engine
{
  // ...
  public string Brand
  { get; set; }
  public string Model
  { get; set; }
  public string Name
  {
    get{ return Brand + " " + Model + " (" + CV + "CV)"; }
  }
}
```

###

```
public class Engine
{
  //…
  public double Fuel
  { get; set; }
  public double CV
  { get; set; }
}

public class Car
{
  // ...
  protected Engine engine;

  public string Brand
  { get; set; }
  public string Model
  { get; set; }
  public string Name
  {
    get{ return Brand + " " + Model + " (" + engine.CV + "CV)"; }
  }

  public Car()
  {
    this.engine = new Engine();
  }
}
```

###

Set step 1

#|ru| Рассмотрим рефакторинг на примере класса автомобилей <code>Car</code>, который наследуется от класса двигателей <code>Engine</code>.
#|en| Let's try out one more refactoring using a <code>Car</code> class that is inherited from the <code>Engine</code> as our example.
#|uk| Розглянемо рефакторинг на прикладі класу автомобілів <code>Car</code>, який успадковується від класу двигунів <code>Engine</code>.

Select "|||CV||| +" in "Car"

#|ru| Сначала идея наследования казалась хорошей и оправданной, но в итоге выяснилось, что автомобили используют только одно свойство двигателя (а именно, объем).
#|en| At first, inheritance seemed a good and noble idea… But later we found that cars use only one engine's property (volume, to be precise).
#|uk| Спочатку ідея успадкування здавалася гарною і виправданою, але в підсумку з'ясувалося, що автомобілі використовують тільки одну властивість двигуна (а саме, об'єм).

Go to the start of "Car"

#|ru| Таким образом, было бы эффективней использовать делегирование к классу <code>Engine</code> для получения нужных свойств или методов.
#|en| So it would have been more efficient to delegate to the <code>Engine</code> class for getting the necessary properties or methods.
#|uk| Таким чином, було б ефективніше використовувати делегування до класу <code>Engine</code> для отримання потрібних властивостей або методів.

#|ru| Начнём рефакторинг с создания поля для хранения ссылки на объект двигателя.
#|en| Let's start refactoring by creating a field for storing a reference to an engine object.
#|uk| Почнемо рефакторинг зі створення поля для зберігання посилання на об'єкт двигуна.

Go to "// ...|||" in "Car"

Print:
```

  protected Engine engine;

```

Select "Engine |||engine|||"

#|ru| Пока что будем заполнять это поле текущим объектом (это можно сделать в конструкторе).
#|en| For now we will fill this field with the current object (this can be done in the constructor).
#|uk| Поки що будемо заповнювати це поле поточним об'єктом (це можна зробити в конструкторі).

Go to end of "Car"

Print:
```


  public Car()
  {
    this.engine = this;
  }
```

Set step 2

Select "|||CV||| +" in "Car"

#|ru| Теперь следует изменить все обращения к свойствам и методам базового класса так, чтобы они обращались к созданному полю. В нашем случае, это происходит только в одном месте.
#|en| Then we should change all access points to the Engine's properties and methods so that they go through the newly created field. In our case, this happens in only one place. 
#|uk| Тепер слід змінити всі звернення до властивостей і методів базового класу так, щоб вони зверталися до створеного поля. У нашому випадку, це відбувається тільки в одному місці.

Print "engine.CV"

Set step 3

Select ": Engine"

#|ru| Теперь можно убрать объявление наследование из класса <code>Car</code>.
#|en| Now we can remove the inheritance declaration from the <code>Car</code> class.
#|uk| Тепер можна прибрати оголошення успадкування з класу <code>Car</code>.

Remove selected

Set step 4

Select "engine = |||this|||"

#|ru| После этого остаётся только создать новый объект двигателей для заполнения поля связанного объекта.
#|en| All that's left to do is create a new engine object for filling the field of the associated object.
#|uk| Після цього залишається тільки створити новий об'єкт двигунів для заповнення поля пов'язаного об'єкта.

Replace "new Engine()"

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