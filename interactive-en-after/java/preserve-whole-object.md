Let's look at this refactoring using the class that describes hotel rooms and logs their daily temperature.

The class should analyze room's micro climate and react with certain actions. For now, only temperature is compared with a predefined temperature plan. Then, depending on the results of the comparison, class could issue a heat or cool command, or even send an email to the house owner if the temperature is dangerously high.

Currently, we are passing only the temperature for analysis but at any time we may need to check another room parameter, such as humidity.

With current implementation, we would have to add more and more parameters to the method. To avoid this, we can pass the entire room object instead of specific values. That will allow to take any room data straight from the room object, without changing signature of the method.

So for the first step, we add a parameter to the <code>withinRange</code> method.

One by one, we should remove parameters with data, that could be retrieved from the object we pass into the method.

Compile and test, and then repeat the actions for the remaining parameter.

Compile and test one more time, to be sure that the code still works.

And finally, let's remove the unused variables from <code>withinPlan</code>.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.