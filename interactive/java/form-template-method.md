form-template-method:java

###

1. Разбейте алгоритмы в подклассах на составные части, описанные в отдельных методах. В этом может помочь <a href="/extract-method">извлечение метода</a>.

2. Получившиеся методы, одинаковые для всех подклассов, можете смело перемещать в суперкласс, используя <a href="/pull-up-method">подъём метода</a>.

3. Отличающиеся методы приведите к единым названиям с помощью <a href="/rename-method">переименования метода</a>.

4. Поместите сигнатуры отличающихся методов в суперкласс как абстрактные с помощью <a href="/pull-up-method">подъёма метода</a>. Их реализации оставьте в подклассах.

5. И наконец, поднимите основной метод алгоритма в суперкласс. Он теперь должен работать с методами-шагами, описанными в суперклассе — реальными или абстрактными.



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

#+ Рассмотрим этот рефакторинг на примере класса статьи, которая может выводиться в двух форматах...

Select name of "markdownView"

#<+ ...простом текстовом формате Markdown...

Select name of "htmlView"

#<= ...и в разметке HTML.

# Прежде чем приступить непосредственно к рефакторингу, мы должны устроить так, чтобы эти два метода появились в подклассах некоторого общего родительского класса.

Select whole "markdownView"
+Select whole "htmlView"

# Для этого можно создать <a href="/replace-method-with-method-object">простой объект методов</a>, переместив оба метода в него.

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

# Теперь тела оригинальных методов можно заменить вызовами методов <code>ArticleView</code>

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

# Далее из <code>ArticleView</code> мы можем выделить два подкласса — <code>ArticleMarkdown</code> and <code>ArticleHtml</code>, переместив в них соответствующие методы.

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

# Так как методы теперь находятся в разных классах, мы можем сделать их более похожими друг на друга, дав им одинаковые имена.

Replace "view"

Wait 500ms

Select "markdownView" in body of "markdownView" of "Article"
+ Select "htmlView" in body of "htmlView" of "Article"

Replace "view"

Select name of "ArticleHtml"
+ Select name of "ArticleMarkdown"

#C Запустим тестирование, чтобы убедиться в отсутствии ошибок.

#S Всё работает, можем продолжать.

# Итак, все готово чтобы, наконец, приступить к самому рефакторингу.

# Первым делом разобьём методы <code>view</code> в обоих классах на составные шаги. Определить шаги в нашем случае довольно просто — это части напечатанной статьи.

Select name of "ArticleMarkdown"

# Начнём с класса <code>ArticleMarkdown</code>.\

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

# Теперь мы можем заменить части метода <code>view</code> вызовами новых методов.

Print:
```
    return title() + intro() + body() + footer();
```

Select name of "ArticleMarkdown"

# Проделаем все это для класса <code>ArticleHtml</code>.\

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

# Теперь мы можем заменить части метода <code>view</code> вызовами новых методов.

Print:
```
    return title() + intro() + body() + footer();
```

Set step 4

# Как видите, у нас получилось много одинаковых шагов в обоих классах. Следует переместить одинаковые шаги в виде абстрактных методов в суперкласс.

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

# Теперь мы можем свободно вынести ничем не отличающиеся методы <code>view</code> в суперкласс.

Remove selected

Go to end of "ArticleView"

Print:
```

  public String view() {
    return title() + intro() + body() + footer();
  }
```

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.