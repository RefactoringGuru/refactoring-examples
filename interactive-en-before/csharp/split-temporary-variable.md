Let's look at <i>Split Temporary Variable</i>, by way of the example of a small method for calculating the movement of a ball in space as a function of time and the forces acting on it.

Start by changing the name of the variable. For this purpose, it is convenient to select a name that reflects the first use of the variable.

We will also declare it a constant to make sure that a value Is assigned only once.

Then rename the variable in all places where it is used, including the place where the new value is assigned.

After all replacements, you can declare the initial variable in the place of the second assignment of a value to it.

After getting to the second case of variable use, compile and test.

Now we can repeat all these actions with the second assignment of a temporary variable. We remove the initial name of the variable once and for all, and then replace it with a new name that fits the second task.

Let's run the final compile.

Now refactoring is complete. If you like, you can compare the old and new code.