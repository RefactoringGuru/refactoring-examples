Let's look at <i>Split Temporary Variable</i>, by way of the example of a small method for calculating the movement of a ball in space as a function of time and the forces acting on it.

Notably for our example, the <code>acc</code> variable is set in it twice.

It performs two tasks: it contains the initial acceleration caused by the first force…

…and later acceleration caused by both forces.

So it is better to split up this variable, with each part responsible for only one task.

Start by changing the name of the variable. For this purpose, it is convenient to select a name that reflects the first use of the variable.

Then rename the variable in all places where it is used, including the place where the new value is assigned.

This was the last replacement. All that's left now is the second case of variable use.

Let's launch autotests.

Now we can repeat all these actions with the second assignment of a temporary variable. We remove the initial name of the variable once and for all, and then replace it with a new name that fits the second task.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code if you like.