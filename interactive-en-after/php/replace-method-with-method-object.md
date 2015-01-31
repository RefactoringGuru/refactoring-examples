A thorough example would require an entire chapter, so we will demonstrate this technique on a method that does not require it (better not to question the logic of the method – it was devised without any grand plan in mind).

We see that one of the class methods has many opaque calculations and thickets of local variables. All this makes it hard to refactor the class.

Let's convert this method to a separate class so that the local variables become fields of the class. Then we can move the method to a new class.

First, create a new class.

Begin by creating an immutable field for storing the source object, in the <code>Gamma</code> class.

Move all variables from the method that we want to separate…

...and create fields for each of the method's parameters.

Create a constructor that will accept the method parameters and store them in class fields for further use.

Let's launch autotests to check for errors in code.

Move the original method, changing it so that fields are used instead of the variables and parameters of the old method.

Modify any calls to the <code>Account</code> methods so that they are run via the <code>account</code> field.

Then simply replace the body of the old method with a call to the method in the new class.

Let's launch autotests to check for errors in code.

The benefit of this refactoring technique is that you can now easily apply <a href="/extract-method">Extract Method</a> to the <code>compute()</code> method without worrying about passing arguments.

The refactoring is complete! You can compare the old and new code if you like.