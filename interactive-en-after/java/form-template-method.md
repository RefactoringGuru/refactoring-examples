Let's look at this refactoring approach using the example of an article that can be displayed in two formats…

…in Markdown plain text…

…and in HTML.

Before starting the refactoring per se, we should arrange things so that these two methods appear in the subclasses of some shared parent class.

To do this, create a <a href="/replace-method-with-method-object">simple method object</a> by moving both methods to it.

Now the bodies of the original methods can be replaced with calls to the <code>ArticleView</code> methods.

Then from <code>ArticleView</code> we can extract two subclasses, <code>ArticleMarkdown</code> and <code>ArticleHtml</code>, by moving the corresponding methods to them.

Since the methods are now located in different classes, we can make them more similar to each other by giving them identical names.

Let's launch autotests to check for errors in code.

Now everything is ready to finally start the refactoring itself.

First split the <code>view</code> methods in both steps to their constituent steps. Defining the steps is rather easy in our case – these are parts of the printed article.

Start with the <code>ArticleMarkdown</code> class.

Now we can replace parts of the <code>view</code> method with calls to the new methods.

Do all of this for the <code>ArticleHtml</code> class.

Now we can replace parts of the <code>view</code> method with calls to the new methods.

As you can see, the two classes have many identical steps. We should move the identical steps as abstract methods to a superclass.

Now we can freely extract the identical <code>view</code> methods to the superclass.

Let's start the final testing.

The refactoring is complete! You can compare the old and new code, if you like.