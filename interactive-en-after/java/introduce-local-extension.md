<i>Introduction of a local extension</i> can be performed in two ways: by creating an inheritor class or a wrapper class. In this example, we will use inheritance.

First create a new class of dates as a subclass of the original <code>Date</code> class.

Then repeat the original's constructors via simple delegation.

Now add a converting constructor that accepts the original as an argument.

When the class constructors are ready, you can add new methods to it or move foreign methods form other classes. Let's move the <code>nextWeek()</code> method with the help of <a href="/move-method">Move Method</a>.

The method parameter is no longer needed since the method is inside the <code>Date</code> inheritor. Thus the needed data can be taken from its own object.

In addition, the method stops being static and private – after all, we need to be able to call it from other classes.

Now change the code that uses the external code so that it uses the new extension class instead.

After changes are complete, remove the external method from the client class.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code, if you like.