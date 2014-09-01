form-template-method:php

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
  public function getTitle() { /* ... */ }
  public function getIntro() { /* ... */ }
  public function getBody() { /* ... */ }
  public function getAuthor() { /* ... */ }
  public function getDate() { /* ... */ }

  public function markdownView() {
    $output = "# " . $this->getTitle() . "\n\n";
    $output .= "> " . $this->getIntro() . "\n\n";
    $output .= $this->getBody() . "\n\n";
    $output .= "_Written by " . $this->getAuthor() . " on " . date("m/d/Y", $this->getDate()) . "_";
    return $output;
  }
  public function htmlView() {
    $output = "<h2>" . $this->getTitle() . "</h2>" . "\n";
    $output .= "<blockquote>" . $this->getIntro() . "</blockquote>" . "\n";
    $output .= "<p>" . $this->getBody() . "</p>" . "\n";
    $output .= "<em>Written by " . $this->getAuthor() . " on " . date("m/d/Y", $this->getDate()) . "</em>";
    return $output;
  }
}
```

###

```
class Article {
  // ...
  public function getTitle() { /* ... */ }
  public function getIntro() { /* ... */ }
  public function getBody() { /* ... */ }
  public function getAuthor() { /* ... */ }
  public function getDate() { /* ... */ }

  public function markdownView() {
    $view = new ArticleMarkdown($this);
    return $view->view();
  }
  public function htmlView() {
    $view = new ArticleHtml($this);
    return $view->view();
  }
}

abstract class ArticleView {
  private $article;
  public function __construct(Article $article) {
    $this->article = $article;
  }
  protected abstract function title();
  protected abstract function intro();
  protected abstract function body();
  protected abstract function footer();
  public function view() {
    return $this->title()
      . $this->intro()
      . $this->body()
      . $this->footer();
  }
}
class ArticleMarkdown extends ArticleView {
  protected function title() {
    return "# " . $this->article->getTitle() . "\n\n";
  }
  protected function intro() {
    return "> " . $this->article->getIntro() . "\n\n";
  }
  protected function body() {
    return $this->article->getBody() . "\n\n";
  }
  protected function footer() {
    return "_Written by " . $this->article->getAuthor() . " on " . date("m/d/Y", $this->article->getDate()) . "_";
  }
}
class ArticleHtml extends ArticleView {
  protected function title() {
    return "<h2>" . $this->article->getTitle() . "</h2>" . "\n";
  }
  protected function intro() {
    return "<blockquote>" . $this->article->getIntro() . "</blockquote>" . "\n";
  }
  protected function body() {
    return "<p>" . $this->article->getBody() . "</p>" . "\n";
  }
  protected function footer() {
    return "<em>Written by " . $this->article->getAuthor() . " on " . date("m/d/Y", $this->article->getDate()) . "</em>";
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
  private $article;
  public function __construct(Article $article) {
    $this->article = $article;
  }
  public function markdownView() {
    $output = "# " . $this->article->getTitle() . "\n\n";
    $output .= "> " . $this->article->getIntro() . "\n\n";
    $output .= $this->article->getBody() . "\n\n";
    $output .= "_Written by " . $this->article->getAuthor() . " on " . date("m/d/Y", $this->article->getDate()) . "_";
    return $output;
  }
  public function htmlView() {
    $output = "<h2>" . $this->article->getTitle() . "</h2>" . "\n";
    $output .= "<blockquote>" . $this->article->getIntro() . "</blockquote>" . "\n";
    $output .= "<p>" . $this->article->getBody() . "</p>" . "\n";
    $output .= "<em>Written by " . $this->article->getAuthor() . " on " . date("m/d/Y", $this->article->getDate()) . "</em>";
    return $output;
  }
}
```

Select body of "markdownView"
+ Select body of "htmlView"

# Теперь тела оригинальных методов можно заменить вызовами методов <code>ArticleView</code>

Select body of "markdownView"

Replace:
```
    $view = new ArticleView($this);
    return $view->markdownView();
```

Wait 500ms

Select body of "htmlView"

Replace:
```
    $view = new ArticleView($this);
    return $view->htmlView();
```

# Далее из <code>ArticleView</code> мы можем выделить два подкласса — <code>ArticleMarkdown</code> and <code>ArticleHtml</code>, переместив в них соответствующие методы.

Go to after "ArticleView"

Print:
```

class ArticleMarkdown extends ArticleView {
  public function markdownView() {
    $output = "# " . $this->article->getTitle() . "\n\n";
    $output .= "> " . $this->article->getIntro() . "\n\n";
    $output .= $this->article->getBody() . "\n\n";
    $output .= "_Written by " . $this->article->getAuthor() . " on " . date("m/d/Y", $this->article->getDate()) . "_";
    return $output;
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
  public function htmlView() {
    $output = "<h2>" . $this->article->getTitle() . "</h2>" . "\n";
    $output .= "<blockquote>" . $this->article->getIntro() . "</blockquote>" . "\n";
    $output .= "<p>" . $this->article->getBody() . "</p>" . "\n";
    $output .= "<em>Written by " . $this->article->getAuthor() . " on " . date("m/d/Y", $this->article->getDate()) . "</em>";
    return $output;
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

  protected function title() {
    return "# " . $this->article->getTitle() . "\n\n";
  }
  protected function intro() {
    return "> " . $this->article->getIntro() . "\n\n";
  }
  protected function body() {
    return $this->article->getBody() . "\n\n";
  }
  protected function footer() {
    return "_Written by " . $this->article->getAuthor() . " on " . date("m/d/Y", $this->article->getDate()) . "_";
  }
```

Select body of "view" in "ArticleMarkdown"

# Теперь мы можем заменить части метода <code>view</code> вызовами новых методов.

Print:
```
    return $this->title()
      . $this->intro()
      . $this->body()
      . $this->footer();
```

Select name of "ArticleMarkdown"

# Проделаем все это для класса <code>ArticleHtml</code>.\

Go to start of "ArticleHtml"

Print:
```

  protected function title() {
    return "<h2>" . $this->article->getTitle() . "</h2>" . "\n";
  }
  protected function intro() {
    return "<blockquote>" . $this->article->getIntro() . "</blockquote>" . "\n";
  }
  protected function body() {
    return "<p>" . $this->article->getBody() . "</p>" . "\n";
  }
  protected function footer() {
    return "<em>Written by " . $this->article->getAuthor() . " on " . date("m/d/Y", $this->article->getDate()) . "</em>";
  }
```

Select body of "view" in "ArticleHtml"

# Теперь мы можем заменить части метода <code>view</code> вызовами новых методов.

Print:
```
    return $this->title()
      . $this->intro()
      . $this->body()
      . $this->footer();
```

Set step 4

# Как видите, у нас получилось много одинаковых шагов в обоих классах. Следует переместить одинаковые шаги в виде абстрактных методов в суперкласс.

Go to type "ArticleView"

Print "abstract "

Go to end of "ArticleView"

Print:
```

  protected abstract function title();
  protected abstract function intro();
  protected abstract function body();
  protected abstract function footer();
```

Select whole of "view" in "ArticleMarkdown"
+ Select whole of "view" in "ArticleHtml"

Set step 5

# Теперь мы можем свободно вынести ничем не отличающиеся методы <code>view</code> в суперкласс.

Remove selected

Go to end of "ArticleView"

Print:
```

  public function view() {
    return $this->title()
      . $this->intro()
      . $this->body()
      . $this->footer();
  }
```

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.