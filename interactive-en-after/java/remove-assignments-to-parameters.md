Let's look at <i>Remove Assignments to Parameters</i> using a small discount calculation method as an example.

Take a look at the <code>inputVal</code> parameter.

The initial value of this parameter changes inside the method body.

Let's replace usage of the parameter with a new variable. We will be changing that variable's value instead of the parameter and then return it as the result of our method.

First, we initialize our variable with the parameter value.

Then, we should replace all references to the parameter in method's body with the variable that we have created.

And finally, to explicitly say that no assignments can be made to the parameters, we add the <code>final</code> keyword to each of them.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.