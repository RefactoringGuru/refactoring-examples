Let's look at <i>Remove Middle Man</i>, using the example of classes that represent an employee and the employee's department (the reverse of the situation in the <i>Hide Delegate</i> example).

To learn who a person's manager is, the client makes a query in the <code>Person</code> class itself.

This is a simple structure that encapsulates an instance of the <code>Department</code> class. But if there are many such methods, the <code>Person</code> class will have too many simple delegates. In this case, we should get rid of the middle men.

To start, create a method for delegate access.

Then review each delegate method of <code>Person</code> and find the code that uses it. Modify the code so that it first gets the delegate class (<code>Department</code>), and then directly calls the necessary method through the delegate method.

Here is how it will look for the method for getting the manager.

First find the places where it is used.

We proceed to change the code so that it calls delegate methods directly.

After all replacements are done, the <code>GetManager()</code> delegate method can be removed from the <code>Person</code> class.

And finally: removing all delegate methods is not necessary. It may be more convenient to maintain some delegation, so see what is best for your particular situation.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.