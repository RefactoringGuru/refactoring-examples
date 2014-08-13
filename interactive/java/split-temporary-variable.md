split-temporary-variable:java

###

1. Найдите место в коде, где переменная первый раз заполняется каким-то значением. В этом месте, переименуйте переменную и дайте ей название, соответствующее присваиваемому значению.

2. Подставьте её новое название вместо старого в тех местах, где использовалось это значение переменной.

3. Повторите операцию для случаев, где переменной присваивается новое значение.



###

```
double getDistanceTravelled(int time) {
  double result;
  double acc = primaryForce / mass;
  int primaryTime = Math.min(time, delay);
  result = 0.5 * acc * primaryTime * primaryTime;

  int secondaryTime = time - delay;
  if (secondaryTime > 0) {
    double primaryVel = acc * delay;
    acc = (primaryForce + secondaryForce) / mass;
    result +=  primaryVel * secondaryTime + 0.5 * acc * secondaryTime * secondaryTime;
  }
  return result;
}
```

###

```
double getDistanceTravelled(int time) {
  double result;
  final double primaryAcceleration = primaryForce / mass;
  int primaryTime = Math.min(time, delay);
  result = 0.5 * primaryAcceleration * primaryTime * primaryTime;

  int secondaryTime = time - delay;
  if (secondaryTime > 0) {
    double primaryVel = primaryAcceleration * delay;
    final double secondaryAcceleration = (primaryForce + secondaryForce) / mass;
    result +=  primaryVel * secondaryTime + 0.5 * secondaryAcceleration * secondaryTime * secondaryTime;
  }
  return result;
}
```

###

Set step 1

# Давайте рассмотрим <i>Расщепление переменной</i> на примере небольшого метода рассчёта расстояния перемещения мяча в пространстве, в зависимости от времени и сил, действующих на него.

Select "|||acc||| ="

#^ Для нашего примера представляет интерес то, что переменная <code>acc</code> устанавливается в нём дважды.


#+ Она выполняет две задачи: содержит первоначальное ускорение, вызванное первой силой...

#^= ...и позднее ускорение, вызванное обеими силами.

#^ Следовательно, эту переменную лучше расщепить, дабы каждая ее часть отвечала только за одну задачу.

Select "double |||acc|||"

# Начнём с изменения имени переменной. Я выбрал имя, которое отражает ее первое применение.

Print "primaryAcceleration"

Go to "|||double primaryAcceleration"

# Кроме того, я объявляю ее как <code>final</code>, чтобы гарантировать однократное присваивание ей значения.

Print "final "

Set step 2

Select "result = 0.5 * |||acc|||"
+ Select "|||acc||| * delay"

# После этого нужно переименовать переменную во всех местах, где она использовалась, вплоть до того места, где ей присваивается новое значение.

Print "primaryAcceleration"

Go to "|||acc ="

# После всех замен можно объявить первоначальную переменную в месте второго присваивания ей некоторого значения.

Print "double "

#C После того как мы добрались до второго случая использования переменной, можно выполнить компиляцию и тестирование.

#S Все отлично, можно продолжать.

Set step 3

Select 1st "|||acc||| "

# Теперь можно повторить все действия со вторым присваиванием временной переменной. Я окончательно удаляю первоначальное имя переменной, и заменяю его новым, соответствующим второй задаче.

Print "secondaryAcceleration"

Wait 500ms

Go to "|||double secondaryAcceleration"

Print "final "

Wait 500ms

Select " |||acc||| "

Replace "secondaryAcceleration"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.