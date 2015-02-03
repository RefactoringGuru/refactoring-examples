Let's look at this refactoring, using the bank account and transactions classes.

We are interested in the method for getting the total for all transactions during an indicated period of time.

As you can see, the method takes a range of two dates as its parameters. Pretty common situation? But instead of passing two dates, it would be better to pass a single date range object.

In future, that will allow us to move date range behaviors into their own class.

Let's begin with creating a simple range class.

The class will be immutable: the dates of the range cannot be changed after it is created, since the date fields are declared as private and we did not create setters for them.

This way you could avoid many errors related to passing objects in method parameters via references.

Now we can add a range parameter to the method for getting the transaction total.

Now, let's find all places where the method is called. In these calls, add a new parameter – specifically, an object created from the dates already given to the method.

The new parameter is in place, so we return to the method description and try to get rid of the old date parameters there.

First take care of the <code>start</code> parameter.

After replacements in the method body, the parameter can be removed from the signature and calls of the method.

Now for the remaining parameter.

Compile, and test after performing all these moves.

After all the necessary parameters were removed, we can start thinking about moving appropriate behaviors to the parameter object.

In our case, we can move a check to see if a date is within a range. This gets rid of the unwieldy code inside <code>getFlowBetween</code>.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.