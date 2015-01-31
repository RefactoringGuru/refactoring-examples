Let's look at <i>Remove Assignments to Parameters</i> using a small discount calculation method as an example.

Note the <code>inputVal</code> parameter.

The value of this parameter changes in the method body.

Replace use of the parameter with a new variable. We will change the value of the variable and then return it as the result of our method.

Initialize our variable with the parameter value.

In the method body, replace all references to the parameter with the variable that we have created.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.