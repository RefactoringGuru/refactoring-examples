A thorough example would require an entire chapter, so we will demonstrate this refactoring on a method that does not require it (for this reason, it's better not to question the logic of the method – it was devised without any grand plan in mind).

We see that one of the class methods has many sophisticated calculations and entanglement of local variables. All this makes it hard to refactor the class.

Let's convert this method to a separate class so that the local variables become fields of the class. That will isolate it and ease the further refactoring.

So, let's create a new class.

First, create an immutable field for storing the source object, in the <code>Gamma</code> class.

Move all variables from the method that we want to separate…

...and create fields for each of the method's parameters.

Create a constructor that will accept the method's parameters and store them in class fields for further use.

Let's compile and test to check for errors in your code.

Now you can move the original method.

Modify any calls to the <code>Account</code> methods so that they are run via the <code>account</code> field.

Then simply replace the body of the old method with a call to the method in the new class.

Let's compile and test to check for errors in your code.

The benefit of this refactoring is that you can now easily apply <a href="/extract-method">Extract Method</a> to the <code>compute()</code> method without worrying about passing correct arguments between sub-methods.

The refactoring is complete! You can compare the old and new code if you like.