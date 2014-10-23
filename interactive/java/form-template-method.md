form-template-method:java

###

1.ru. Разбейте алгоритмы в подклассах на составные части, описанные в отдельных методах. В этом может помочь <a href="/extract-method">извлечение метода</a>.
1.uk. Розбийте алгоритми в підкласах на складові частини, описані в окремих методах. У цьому може допомогти <a href="/extract-method">витягання методу</a>.

2.ru. Получившиеся методы, одинаковые для всех подклассов, можете смело перемещать в суперкласс, используя <a href="/pull-up-method">подъём метода</a>.
2.uk. Методи, що вийшли однаковимі для усіх підкласів, можете сміливо переміщати в суперклас, використовуючи <a href="/pull-up-method">підйом методу</a>.

3.ru. Отличающиеся методы приведите к единым названиям с помощью <a href="/rename-method">переименования метода</a>.
3.uk. Методи, що відрізняються, приведіть до єдиних назв за допомогою <a href="/rename-method">перейменування методу</a>.

4.ru. Поместите сигнатуры отличающихся методов в суперкласс как абстрактные с помощью <a href="/pull-up-method">подъёма метода</a>. Их реализации оставьте в подклассах.
4.uk. Помістіть сигнатури методів, що відрізняються, в суперклас як абстрактні за допомогою <a href="/pull-up-method">підйому методу</a>. Їх реалізації залиште в підкласах.

5.ru. И наконец, поднимите основной метод алгоритма в суперкласс. Он теперь должен работать с методами-шагами, описанными в суперклассе — реальными или абстрактными.
5.uk. І, нарешті, підніміть основний метод алгоритму в суперклас. Він тепер повинен працювати з методами-кроками, описаними в суперкласі — реальними або абстрактними.



###

```
class Article {
  // ...
  public String getTitle() { /* ... */ }
  public String getIntro() { /* ... */ }
  public String getBody() { /* ... */ }
  public String getAuthor() { /* ... */ }
  public String getDate() { /* ... */ }

  public String markdownView() {
    String output = "# " + getTitle() + "\n\n";
    output += "> " + getIntro() + "\n\n";
    output += getBody() + "\n\n";
    output += "_Written by " + getAuthor() + " on " + getDate() + "_";
    return output;
  }
  public String htmlView() {
    String output = "<h2>" . getTitle() + "</h2>" + "\n";
    output += "<blockquote>" . getIntro() + "</blockquote>" + "\n";
    output += "<p>" . getBody() . "</p>" + "\n";
    output += "<em>Written by " . getAuthor() + " on " + getDate() + "</em>";
    return output;
  }
}
```

###

```
class Article {
  // ...
  public String getTitle() { /* ... */ }
  public String getIntro() { /* ... */ }
  public String getBody() { /* ... */ }
  public String getAuthor() { /* ... */ }
  public String getDate() { /* ... */ }

  public String markdownView() {
    return new ArticleMarkdown(this).view();
  }
  public String htmlView() {
    return new ArticleHtml(this).view();
  }
}

abstract class ArticleView {
  private Article article;
  public ArticleView(Article article) {
    this.article = article;
  }
  protected abstract String title();
  protected abstract String intro();
  protected abstract String body();
  protected abstract String footer();
  public String view() {
    return title() + intro() + body() + footer();
  }
}
class ArticleMarkdown extends ArticleView {
  protected String title() {
    return "# " + article.getTitle() + "\n\n";
  }
  protected String intro() {
    return "> " + article.getIntro() + "\n\n";
  }
  protected String body() {
    return article.getBody() + "\n\n";
  }
  protected String footer() {
    return "_Written by " + article.getAuthor() + " on " + article.getDate() + "_";
  }
}
class ArticleHtml extends ArticleView {
  protected String title() {
    return "<h2>" . article.getTitle() + "</h2>" + "\n";
  }
  protected String intro() {
    return "<blockquote>" . article.getIntro() + "</blockquote>" + "\n";
  }
  protected String body() {
    return "<p>" . article.getBody() . "</p>" + "\n";
  }
  protected String footer() {
    return "<em>Written by " . article.getAuthor() + " on " + article.getDate() + "</em>";
  }
}
```

###

Set step 1

Select name of "Article"

#|ru|+ Рассмотрим этот рефакторинг на примере класса статьи, которая может выводиться в двух форматах...
#|uk|+ Розглянемо цей рефакторинг на прикладі класу статті, яка може виводитися в двох форматах...

Select name of "markdownView"

#|ru|<+ ...простом текстовом формате Markdown...
#|uk|<+ ...простому текстовому форматі Markdown...

Select name of "htmlView"

#|ru|<= ...и в разметке HTML.
#|uk|<= ...і в розмітці HTML.

#|ru| Прежде чем приступить непосредственно к рефакторингу, мы должны устроить так, чтобы эти два метода появились в подклассах некоторого общего родительского класса.
#|uk| Перш ніж приступити безпосередньо до рефакторингу, ми повинні влаштувати так, щоб ці два методи з'явилися в підкласах деякого загального батьківського класу.

Select whole "markdownView"
+Select whole "htmlView"

#|ru| Для этого можно создать <a href="/replace-method-with-method-object">простой объект методов</a>, переместив оба метода в него.
#|uk| Для цього можна створити <a href="/replace-method-with-method-object">простий об'єкт методів</a>, перемістивши обидва методи в нього.

Go to after "Article"

Print:
```


class ArticleView {
  private Article article;
  public ArticleView(Article article) {
    this.article = article;
  }
  public String markdownView() {
    String output = "# " + article.getTitle() + "\n\n";
    output += "> " + article.getIntro() + "\n\n";
    output += article.getBody() + "\n\n";
    output += "_Written by " + article.getAuthor() + " on " + article.getDate() + "_";
    return output;
  }
  public String htmlView() {
    String output = "<h2>" . article.getTitle() + "</h2>" + "\n";
    output += "<blockquote>" . article.getIntro() + "</blockquote>" + "\n";
    output += "<p>" . article.getBody() . "</p>" + "\n";
    output += "<em>Written by " . article.getAuthor() + " on " + article.getDate() + "</em>";
    return output;
  }
}
```

Select body of "markdownView"
+ Select body of "htmlView"

#|ru| Теперь тела оригинальных методов можно заменить вызовами методов <code>ArticleView</code>
#|uk| Тепер тіла оригінальних методів можна замінити викликами методів <code>ArticleView</code>

Select body of "markdownView"

Replace:
```
    return new ArticleView(this).markdownView();
```

Wait 500ms

Select body of "htmlView"

Replace:
```
    return new ArticleView(this).htmlView();
```

#|ru| Далее из <code>ArticleView</code> мы можем выделить два подкласса — <code>ArticleMarkdown</code> and <code>ArticleHtml</code>, переместив в них соответствующие методы.
#|uk| Далі з <code>ArticleView</code> ми можемо виділити два підкласи – <code>ArticleMarkdown</code> and <code>ArticleHtml</code>, перемістивши в них відповідні методи.

Go to after "ArticleView"

Print:
```

class ArticleMarkdown extends ArticleView {
  public String markdownView() {
    String output = "# " + article.getTitle() + "\n\n";
    output += "> " + article.getIntro() + "\n\n";
    output += article.getBody() + "\n\n";
    output += "_Written by " + article.getAuthor() + " on " + article.getDate() + "_";
    return output;
  }
}
```

Wait 500ms

Select whole of "markdownView" in "ArticleView"

Remove selected

Wait 500ms

Select "ArticleView" in "markdownView" of "Article"

Replace "ArticleMarkdown"

Wait 500ms

Go to after "ArticleMarkdown"

Print:
```

class ArticleHtml extends ArticleView {
  public String htmlView() {
    String output = "<h2>" . article.getTitle() + "</h2>" + "\n";
    output += "<blockquote>" . article.getIntro() + "</blockquote>" + "\n";
    output += "<p>" . article.getBody() . "</p>" + "\n";
    output += "<em>Written by " . article.getAuthor() + " on " + article.getDate() + "</em>";
    return output;
  }
}
```

Wait 500ms

Select whole of "htmlView" in "ArticleView"

Remove selected

Wait 500ms

Select "ArticleView" in "htmlView" of "Article"

Replace "ArticleHtml"

Select name of "markdownView" in "ArticleMarkdown"
+ Select name of "htmlView" in "ArticleHtml"

#|ru| Так как методы теперь находятся в разных классах, мы можем сделать их более похожими друг на друга, дав им одинаковые имена.
#|uk| Так як методи тепер знаходяться в різних класах, ми можемо зробити їх більш схожими один на одного, давши їм однакові імена.

Replace "view"

Wait 500ms

Select "markdownView" in body of "markdownView" of "Article"
+ Select "htmlView" in body of "htmlView" of "Article"

Replace "view"

Select name of "ArticleHtml"
+ Select name of "ArticleMarkdown"

#C|ru| Запустим тестирование, чтобы убедиться в отсутствии ошибок.
#S Всё работает, можем продолжать.

#C|uk| Запустимо тестування, щоб переконатися у відсутності помилок.
#S Все працює, можемо продовжувати.

#|ru| Итак, все готово чтобы, наконец, приступить к самому рефакторингу.
#|uk| Отже, все готово щоб, нарешті, приступити до самого рефакторингу.

#|ru| Первым делом разобьём методы <code>view</code> в обоих классах на составные шаги. Определить шаги в нашем случае довольно просто — это части напечатанной статьи.
#|uk| Першим ділом розіб'ємо методи <code>view</code> в обох класах на складові кроки. Визначити кроки в нашому випадку досить просто – це частини надрукованої статті.

Select name of "ArticleMarkdown"

#|ru| Начнём с класса <code>ArticleMarkdown</code>.
#|uk| Почнемо з класу <code>ArticleMarkdown</code>.

Go to start of "ArticleMarkdown"

Print:
```

  protected String title() {
    return "# " + article.getTitle() + "\n\n";
  }
  protected String intro() {
    return "> " + article.getIntro() + "\n\n";
  }
  protected String body() {
    return article.getBody() + "\n\n";
  }
  protected String footer() {
    return "_Written by " + article.getAuthor() + " on " + article.getDate() + "_";
  }
```

Select body of "view" in "ArticleMarkdown"

#|ru| Теперь мы можем заменить части метода <code>view</code> вызовами новых методов.
#|uk| Тепер ми можемо замінити частини методу <code>view</code> викликами нових методів.

Print:
```
    return title() + intro() + body() + footer();
```

Select name of "ArticleMarkdown"

#|ru| Проделаем все это для класса <code>ArticleHtml</code>.
#|uk| Виконаємо все це для класу <code>ArticleHtml</code>.

Go to start of "ArticleHtml"

Print:
```

  protected String title() {
    return "<h2>" . article.getTitle() + "</h2>" + "\n";
  }
  protected String intro() {
    return "<blockquote>" . article.getIntro() + "</blockquote>" + "\n";
  }
  protected String body() {
    return "<p>" . article.getBody() . "</p>" + "\n";
  }
  protected String footer() {
    return "<em>Written by " . article.getAuthor() + " on " + article.getDate() + "</em>";
  }
```

Select body of "view" in "ArticleHtml"

#|ru| Теперь мы можем заменить части метода <code>view</code> вызовами новых методов.
#|uk| Тепер ми можемо замінити частини методу <code>view</code> викликами нових методів.

Print:
```
    return title() + intro() + body() + footer();
```

Set step 4

#|ru| Как видите, у нас получилось много одинаковых шагов в обоих классах. Следует переместить одинаковые шаги в виде абстрактных методов в суперкласс.
#|uk| Як бачите, у нас вийшло багато однакових кроків в обох класах. Слід перемістити однакові кроки у вигляді абстрактних методів в суперклас.

Go to type "ArticleView"

Print "abstract "

Go to end of "ArticleView"

Print:
```

  protected abstract String title();
  protected abstract String intro();
  protected abstract String body();
  protected abstract String footer();
```

Select whole of "view" in "ArticleMarkdown"
+ Select whole of "view" in "ArticleHtml"

Set step 5

#|ru| Теперь мы можем свободно вынести ничем не отличающиеся методы <code>view</code> в суперкласс.
#|uk| Тепер ми можемо вільно винести методи <code>view</code>, які нічим не відрізняються, в суперклас.

Remove selected

Go to end of "ArticleView"

Print:
```

  public String view() {
    return title() + intro() + body() + footer();
  }
```

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.