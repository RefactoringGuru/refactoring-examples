split-temporary-variable:php

###

1. Найдите место в коде, где переменная первый раз заполняется каким-то значением. В этом месте переименуйте переменную и дайте ей название, соответствующее присваиваемому значению.

2. Подставьте её новое название вместо старого в тех местах, где использовалось это значение переменной.

3. Повторите операцию для случаев, где переменной присваивается новое значение.



###

```
public function getDistanceTravelled($time) {
  $result = 0;;
  $acc = $this->primaryForce / $this->mass;
  $primaryTime = Math.min($time, $this->delay);
  $result = 0.5 * $acc * $primaryTime * $primaryTime;

  $secondaryTime = $time - $this->delay;
  if ($secondaryTime > 0) {
    $primaryVel = $acc * $this->delay;
    $acc = ($this->primaryForce + $this->secondaryForce) / $this->mass;
    $result += $primaryVel * $secondaryTime + 0.5 * $acc * $secondaryTime * $secondaryTime;
  }
  return $result;
}
```

###

```
public function getDistanceTravelled($time) {
  $result = 0;;
  $primaryAcceleration = $this->primaryForce / $this->mass;
  $primaryTime = Math.min($time, $this->delay);
  $result = 0.5 * $primaryAcceleration * $primaryTime * $primaryTime;

  $secondaryTime = $time - $this->delay;
  if ($secondaryTime > 0) {
    $primaryVel = $primaryAcceleration * $this->delay;
    $secondaryAcceleration = ($this->primaryForce + $this->secondaryForce) / $this->mass;
    $result += $primaryVel * $secondaryTime + 0.5 * $secondaryAcceleration * $secondaryTime * $secondaryTime;
  }
  return $result;
}
```

###

Set step 1

# Давайте рассмотрим <i>Расщепление переменной</i> на примере небольшого метода расчёта расстояния перемещения мяча в пространстве в зависимости от времени и сил, действующих на него.

Select "|||$acc||| ="

#^ Для нашего примера представляет интерес то, что переменная <code>acc</code> устанавливается в нём дважды.

#+ Она выполняет две задачи: содержит первоначальное ускорение, вызванное первой силой...

#^= ...и позднее ускорение, вызванное обеими силами.

#^ Следовательно, эту переменную лучше расщепить, чтобы каждая её часть отвечала только за одну задачу.

Select 1st "$acc"

# Начнём с изменения имени переменной. Для этого очень удобно выбрать имя, которое будет отражать её первое применение.

Print "$primaryAcceleration"

Set step 2

Select "$result = 0.5 * |||$acc|||"
+ Select "|||$acc||| * $this->delay"

# После этого нужно переименовать переменную во всех местах, где она использовалась, вплоть до того места, где ей присваивается новое значение.

Print "$primaryAcceleration"

Select "$acc"

# Это была последняя замена, после которой остался только второй случай использования переменной.

#C Запустим компиляцию и тестирование.

#S Все отлично, можно продолжать.

Set step 3

Select 1st "|||$acc||| "

# Теперь можно повторить все действия со вторым присваиванием временной переменной. Окончательно удаляем первоначальное имя переменной, и заменяем его новым, соответствующим второй задаче.

Print "$secondaryAcceleration"

Wait 500ms

Select " |||$acc||| "

Replace "$secondaryAcceleration"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.