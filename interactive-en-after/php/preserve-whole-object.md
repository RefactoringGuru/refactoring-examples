Let's look at a class that describes a room and logs the hottest/coldest temperatures in a 24-hour period.

It should compare this range with the range in the preset heating schedule and then, depending on the results of the comparison, perform certain actions (such as change temperature or, say, send an email message to the house owner).

Currently we are passing only the temperature for comparison but at any time we may need to check another room parameter, such as humidity.

In other words, we would have to create more and more new parameters. To avoid this, we can pass the entire room object instead of specific values. This allows using any room parameters without changing the signature of the methods.

So for the first step, add a parameter to the <code>withinRange</code> method.

One by one, start to remove parameters from the method that you can replace with calls to fields or methods of the object being passed.

Test and then repeat these actions for the remaining parameter.

Test again to make sure the code still works.

And finally, remove the now-unused variables from <code>withinPlan</code>.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.