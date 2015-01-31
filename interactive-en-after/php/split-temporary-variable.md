Let's look at <i>Split Temporary Variable</i>, by way of the example of a small method for calculating the movement of a ball in space as a function of time and the forces acting on it.

Start by changing the name of the variable. For this purpose, it is convenient to select a name that reflects the first use of the variable.

Then rename the variable in all places where it is used, including the place where the new value is assigned.

This was the last replacement. All that's left now is the second case of variable use.

Let's launch autotests.

Now we can repeat all these actions with the second assignment of a temporary variable. We remove the initial name of the variable once and for all, and then replace it with a new name that fits the second task.

Let's start the final testing.

Now refactoring is complete. If you like, you can compare the old and new code.