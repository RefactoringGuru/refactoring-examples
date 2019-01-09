form-template-method:csharp

###

1.ru. Разбейте алгоритмы в подклассах на составные части, описанные в отдельных методах. В этом может помочь <a href="/ru/extract-method">извлечение метода</a>.
1.en. Split algorithms in the subclasses into their constituent parts described in separate methods. <a href="/extract-method">Extract Method</a> can help with this.
1.uk. Розбийте алгоритми в підкласах на складові частини, описані в окремих методах. У цьому може допомогти <a href="/uk/extract-method">витягання методу</a>.

2.ru. Получившиеся методы, одинаковые для всех подклассов, можете смело перемещать в базовый класс, используя <a href="/ru/pull-up-method">подъём метода</a>.
2.en. The resulting methods that are identical for all subclasses can be moved to a base class via <a href="/pull-up-method">Pull Up Method</a>.
2.uk. Методи, що вийшли однаковимі для усіх підкласів, можете сміливо переміщати в базовий клас, використовуючи <a href="/uk/pull-up-method">підйом методу</a>.

3.ru. Отличающиеся методы приведите к единым названиям с помощью <a href="/ru/rename-method">переименования метода</a>.
3.en. The non-similar methods can be given consistent names via <a href="/rename-method">Rename Method</a>.
3.uk. Методи, що відрізняються, приведіть до єдиних назв за допомогою <a href="/uk/rename-method">перейменування методу</a>.

4.ru. Поместите сигнатуры отличающихся свойств в базовый класс как абстрактные с помощью <a href="/ru/pull-up-field">подъёма поля</a>. Их реализации оставьте в подклассах.
4.en. Move the signatures of non-similar properties to a base class as abstract ones by using <a href="/pull-up-field">Pull Up Field</a>. Leave their implementations in the subclasses.
4.uk. Помістіть сигнатури властивостей, що відрізняються, в базовий клас як абстрактні за допомогою <a href="/uk/pull-up-field">підйому поля</a>. Їх реалізації залиште в підкласах.

5.ru. И наконец, поднимите основной метод алгоритма в базовый класс. Он теперь должен работать со свойствами, описанными в базовом классе — реальными или абстрактными.
5.en. And finally, pull up the main method of the algorithm to the base class. Now it should work with the properties described in the base class, both real and abstract.
5.uk. І, нарешті, підніміть основний метод алгоритму в базовий клас. Він тепер повинен працювати з властивостями, описаними в базовому класі — реальними або абстрактними.



###

```
public class Article
{
  // ...
  public string Title { get; set; }
  public string Intro { get; set; }
  public string Body { get; set; }
  public string Author { get; set; }
  public string Date { get; set; }

  public string MarkdownView()
  {
    var output = new StringBuilder();
    output.Append("# ").Append(Title).AppendLine().AppendLine();
    output.Append("> ").Append(Intro).AppendLine().AppendLine();
    output.Append(Body).AppendLine().AppendLine();
    output.Append("_Written by ").Append(Author).Append(" on ").Append(Date).Append("_");
    return output.ToString();
  }
  public string HtmlView()
  {
    var output = new StringBuilder();
    output.Append("<h2>").Append(Title).Append("</h2>").AppendLine();
    output.Append("<blockquote>").Append(Intro).Append("</blockquote>").AppendLine();
    output.Append("<p>").Append(Body).Append("</p>").AppendLine();
    output.Append("<em>Written by ").Append(Author).Append(" on ").Append(Date).Append("</em>");
    return output.ToString();
  }
}
```

###

```
public class Article
{
  // ...
  public string Title { get; set; }
  public string Intro { get; set; }
  public string Body { get; set; }
  public string Author { get; set; }
  public string Date { get; set; }

  public string MarkdownView()
  {
    return new ArticleMarkdown(this).View();
  }
  public string HtmlView()
  {
    return new ArticleHtml(this).View();
  }
}

public abstract class ArticleView
{
  protected Article article;

  protected abstract string Title { get; }
  protected abstract string Intro { get; }
  protected abstract string Body { get; }
  protected abstract string Footer { get; }

  protected ArticleView(Article article)
  {
    this.article = article;
  }

  public string View()
  {
    return Title + Intro + Body + Footer;
  }
}

public class ArticleMarkdown: ArticleView
{
  protected override string Title
  {
    get{ return "# " + article.Title + Environment.NewLine + Environment.NewLine; }
  }
  protected override string Intro
  {
    get{ return "> " + article.Intro + Environment.NewLine + Environment.NewLine; }
  }
  protected override string Body
  {
    get{ return article.Body + Environment.NewLine + Environment.NewLine; }
  }
  protected override string Footer
  {
    get{ return "_Written by " + article.Author + " on " + article.Date + "_"; }
  }

  public ArticleMarkdown(Article article): base(article)
  {
  }
}

public class ArticleHtml: ArticleView
{
  protected override string Title
  {
    get{ return "<h2>" + article.Title + "</h2>" + Environment.NewLine; }
  }
  protected override string Intro
  {
    get{ return "<blockquote>" + article.Intro + "</blockquote>" + Environment.NewLine; }
  }
  protected override string Body
  {
    get{ return "<p>" + article.Body + "</p>" + Environment.NewLine; }
  }
  protected override string Footer
  {
    get{ return "<em>Written by " + article.Author + " on " + article.Date + "</em>"; }
  }

  public ArticleHtml(Article article): base(article)
  {
  }
}
```

###

Set step 1

Select name of "Article"

#|ru|+ Рассмотрим этот рефакторинг на примере класса статьи, которая может выводиться в двух форматах…
#|en|+ Let's look at this refactoring using the example of an article that can be displayed in two formats…
#|uk|+ Розглянемо цей рефакторинг на прикладі класу статті, яка може виводитися в двох форматах…

Select name of "MarkdownView"

#|ru|<+ …простом текстовом формате Markdown…
#|en|<+ …in Markdown plain text…
#|uk|<+ …простому текстовому форматі Markdown…

Select name of "HtmlView"

#|ru|<= …и в разметке HTML.
#|en|<= …and in HTML.
#|uk|<= …і в розмітці HTML.

#|ru| Прежде чем приступить непосредственно к рефакторингу, мы должны сделать так, чтобы эти два метода появились в подклассах некоторого общего родительского класса.
#|en| Before starting the refactoring per se, we should arrange things so that these two methods appear in the subclasses of some shared parent class.
#|uk| Перш ніж приступити безпосередньо до рефакторингу, ми повинні зробити так, щоб ці два методи з'явилися в підкласах деякого загального батьківського класу.

Select whole "MarkdownView"
+Select whole "HtmlView"

#|ru| Для этого можно создать <a href="/ru/replace-method-with-method-object">простой объект методов</a>, переместив оба метода в него.
#|en| To do this, we create a <a href="/replace-method-with-method-object">simple method object</a> by moving both methods to it.
#|uk| Для цього можна створити <a href="/uk/replace-method-with-method-object">простий об'єкт методів</a>, перемістивши обидва методи в нього.

Go to after "Article"

Print:
```


public class ArticleView
{
  protected Article article;

  public ArticleView(Article article)
  {
    this.article = article;
  }

  public string MarkdownView()
  {
    var output = new StringBuilder();
    output.Append("# ").Append(article.Title).AppendLine().AppendLine();
    output.Append("> ").Append(article.Intro).AppendLine().AppendLine();
    output.Append(article.Body).AppendLine().AppendLine();
    output.Append("_Written by ").Append(article.Author).Append(" on ").Append(article.Date).Append("_");
    return output.ToString();
  }
  public string HtmlView()
  {
    var output = new StringBuilder();
    output.Append("<h2>").Append(article.Title).Append("</h2>").AppendLine();
    output.Append("<blockquote>").Append(article.Intro).Append("</blockquote>").AppendLine();
    output.Append("<p>").Append(article.Body).Append("</p>").AppendLine();
    output.Append("<em>Written by ").Append(article.Author).Append(" on ").Append(article.Date).Append("</em>");
    return output.ToString();
  }
}
```

Wait 500ms

Select body of "MarkdownView" in "Article"
+ Select body of "HtmlView" in "Article"

#|ru| Теперь тела оригинальных методов можно заменить вызовами методов <code>ArticleView</code>
#|en| Now the bodies of the original methods can be replaced with calls to the <code>ArticleView</code> methods.
#|uk| Тепер тіла оригінальних методів можна замінити викликами методів <code>ArticleView</code>

Select body of "MarkdownView" in "Article"

Replace:
```
    return new ArticleView(this).MarkdownView();
```

Wait 500ms

Select body of "HtmlView" in "Article"

Replace:
```
    return new ArticleView(this).HtmlView();
```

Wait 500ms

Select name of "ArticleView"

#|ru| Далее из <code>ArticleView</code> мы можем выделить два подкласса — <code>ArticleMarkdown</code> и <code>ArticleHtml</code>, переместив в них соответствующие методы.
#|en| Then from <code>ArticleView</code> we can extract two subclasses, <code>ArticleMarkdown</code> and <code>ArticleHtml</code>, by moving the corresponding methods to them.
#|uk| Далі з <code>ArticleView</code> ми можемо виділити два підкласи – <code>ArticleMarkdown</code> і <code>ArticleHtml</code>, перемістивши в них відповідні методи.

Go to after "ArticleView"

#|ru| Создаём класс <code>ArticleMarkdown</code>.
#|en| Let's create a <code>ArticleMarkdown</code> class.
#|uk| Створюємо клас <code>ArticleMarkdown</code>.

Print:
```


public class ArticleMarkdown: ArticleView
{
  public ArticleMarkdown(Article article): base(article)
  {
  }
}
```

Wait 500ms

Select whole of "MarkdownView" in "ArticleView"

#|ru| Переносим в него соответствующий метод.
#|en| Move corresponding method to it.
#|uk| Переносимо в нього відповідний метод.

Remove selected

Wait 500ms

Go to end of "ArticleMarkdown"

Print:
```


  public string MarkdownView()
  {
    var output = new StringBuilder();
    output.Append("# ").Append(article.Title).AppendLine().AppendLine();
    output.Append("> ").Append(article.Intro).AppendLine().AppendLine();
    output.Append(article.Body).AppendLine().AppendLine();
    output.Append("_Written by ").Append(article.Author).Append(" on ").Append(article.Date).Append("_");
    return output.ToString();
  }
```

Wait 500ms

Select "ArticleView" in "MarkdownView" of "Article"

Replace "ArticleMarkdown"

Wait 500ms

Go to after "ArticleMarkdown"

#|ru| Далее создаём класс <code>ArticleHtml</code>.
#|en| Now let's create a <code>ArticleHtml</code> class.
#|uk| Далі створюємо клас <code>ArticleHtml</code>.

Print:
```


public class ArticleHtml: ArticleView
{
  public ArticleHtml(Article article): base(article)
  {
  }
}
```

Wait 500ms

Select whole of "HtmlView" in "ArticleView"
+Select in "ArticleView":
```
|||
|||  public string HtmlView()
```

#|ru| И аналогичным образом переносим в него оставшийся метод.
#|en| And similarly move the remaining method to it.
#|uk| І аналогічним чином переносимо в нього залишився метод.

Remove selected

Wait 500ms

Go to end of "ArticleHtml"

Print:
```


  public string HtmlView()
  {
    var output = new StringBuilder();
    output.Append("<h2>").Append(article.Title).Append("</h2>").AppendLine();
    output.Append("<blockquote>").Append(article.Intro).Append("</blockquote>").AppendLine();
    output.Append("<p>").Append(article.Body).Append("</p>").AppendLine();
    output.Append("<em>Written by ").Append(article.Author).Append(" on ").Append(article.Date).Append("</em>");
    return output.ToString();
  }
```

Wait 500ms

Select "ArticleView" in "HtmlView" of "Article"

Replace "ArticleHtml"

Wait 500ms

Select name of "MarkdownView" in "ArticleMarkdown"
+ Select name of "HtmlView" in "ArticleHtml"

#|ru| Так как методы теперь находятся в разных классах, мы можем дать им одинаковые имена.
#|en| Since the methods are now located in different classes, we can give them identical names.
#|uk| Оскільки методи тепер знаходяться в різних класах, ми можемо дати їм однакові імена.

Replace "View"

Wait 500ms

Select "MarkdownView" in body of "MarkdownView" of "Article"
+ Select "HtmlView" in body of "HtmlView" of "Article"

Replace "View"

Select name of "ArticleHtml"
+ Select name of "ArticleMarkdown"

#C|ru| Запустим тестирование, чтобы убедиться в отсутствии ошибок.
#S Всё работает, можем продолжать.

#C|en| Let's launch autotests to check for errors in the code.
#S Everything is OK! We can keep going.

#C|uk| Запустимо тестування, щоб переконатися у відсутності помилок.
#S Все працює, можемо продовжувати.

#|ru| Итак, все готово чтобы, наконец, приступить к самому рефакторингу.
#|en| Finally, everything is ready to start the refactoring itself.
#|uk| Отже, все готово щоб, нарешті, приступити до самого рефакторингу.

#|ru| Первым делом разобьём методы <code>View()</code> в обоих классах на составные части. Определить которые в нашем случае довольно просто — это будут части напечатанной статьи.
#|en| First split the <code>View()</code> methods in both steps to their constituent steps. Defining the steps is rather easy in our case – these are parts of the printed article.
#|uk| Першим ділом розіб'ємо методи <code>View()</code> в обох класах на складові частини. Визначити які в нашому випадку досить просто – це будуть частини надрукованої статті.

Select name of "ArticleMarkdown"

#|ru| Начнём с класса <code>ArticleMarkdown</code>.
#|en| Start with the <code>ArticleMarkdown</code> class.
#|uk| Почнемо з класу <code>ArticleMarkdown</code>.

Go to start of "ArticleMarkdown"

Print:
```

  private string Title
  {
    get{ return "# " + article.Title + Environment.NewLine + Environment.NewLine; }
  }
  private string Intro
  {
    get{ return "> " + article.Intro + Environment.NewLine + Environment.NewLine; }
  }
  private string Body
  {
    get{ return article.Body + Environment.NewLine + Environment.NewLine; }
  }
  private string Footer
  {
    get{ return "_Written by " + article.Author + " on " + article.Date + "_"; }
  }

```

Select body of "View" in "ArticleMarkdown"

#|ru| Теперь мы можем использовать созданные свойства в методе <code>View()</code>.
#|en| Now we can use the new properties in the <code>View()</code> method.
#|uk| Тепер ми можемо використовувати створені властивості в методі <code>View()</code>.

Print:
```
    return Title + Intro + Body + Footer;
```

Select name of "ArticleHtml"

#|ru| Проделаем все то же самое для класса <code>ArticleHtml</code>.
#|en| Do all of this for the <code>ArticleHtml</code> class.
#|uk| Виконаємо все те ж саме для класу <code>ArticleHtml</code>.

Go to start of "ArticleHtml"

Print:
```

  private string Title
  {
    get{ return "<h2>" + article.Title + "</h2>" + Environment.NewLine; }
  }
  private string Intro
  {
    get{ return "<blockquote>" + article.Intro + "</blockquote>" + Environment.NewLine; }
  }
  private string Body
  {
    get{ return "<p>" + article.Body + "</p>" + Environment.NewLine; }
  }
  private string Footer
  {
    get{ return "<em>Written by " + article.Author + " on " + article.Date + "</em>"; }
  }

```

Wait 500ms

Select body of "View" in "ArticleHtml"

Replace:
```
    return Title + Intro + Body + Footer;
```

Wait 500ms

Set step 4

Go to "public||| class ArticleView"

#|ru| Как видите, у нас получилось много одинаковых частей в обоих классах. Следует вынести их в базовый класс в виде абстрактных свойств.
#|en| As you can see, the two classes have many identical steps. We should move the identical steps as abstract properties to the base class.
#|uk| Як бачите, у нас вийшло багато однакових частин в обох класах. Слід винести їх в базовий клас у вигляді абстрактних методів.

Print " abstract"

Wait 500ms

Select visibility of "public ArticleView"

Replace "protected"

Wait 500ms

Go to:
```
article;
|||
```

Wait 500ms

Print:
```

  protected abstract string Title { get; }
  protected abstract string Intro { get; }
  protected abstract string Body { get; }
  protected abstract string Footer { get; }

```

Wait 500ms

Select "|||private||| string"

Replace "protected override"

Wait 500ms

Select whole of "View" in "ArticleMarkdown"
+ Select whole of "View" in "ArticleHtml"
+ Select:
```
|||
|||  public string View
```

Set step 5

#|ru| Теперь мы можем свободно вынести в базовый класс ничем не отличающиеся методы <code>View()</code>.
#|en| Now we can freely extract the identical <code>View()</code> methods to the base class.
#|uk| Тепер ми можемо вільно винести в базовий клас методи <code>View()</code>, які нічим не відрізняються.

Remove selected

Go to end of "ArticleView"

Print:
```


  public string View()
  {
    return Title + Intro + Body + Footer;
  }
```

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