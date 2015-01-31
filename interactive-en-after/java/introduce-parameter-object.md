Let's look at this refactoring technique, using as our example a bank account class and transactions.

We are interested in the method for getting the total transactions during an indicated period of time.

As you can see, the method takes a range of two dates as its parameters. This is a common situation and instead of passing two dates, it would be nice to pass an object consisting of a range of dates.

In the future, operations for checking whether a date falls in a range, etc., can be moved to this object.

Create a simple range class.

The class is immutable: the dates of the range cannot be changed after it is created, since the date fields are declared as private and we did not create setters for them.

This step allows avoiding many errors related with passing objects in parameters via references.

Now we can add a range parameter to the method for getting the transaction total.

Find all places where the method is called. In these calls, add a new parameter – specifically, an object created from the dates already given to the method.

The new parameter is in place, so we return to the method description and try to get rid of the old date parameters there.

First take care of the <code>start</code> parameter.

After replacements in the method body, the parameter can be removed from the signature and calls of the method.

Now for the remaining parameter.

After performing all these moves, run and test.

After all the necessary parameters are removed, we can think about moving appropriate behaviors to the parameter object.

In our case, we can move a check to see if a date is within a range. This gets rid of the unwieldy code inside <code>getFlowBetween</code>.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code, if you like.