Let's look at <i>Replace Subclass With Fields</i>, using the example of the same person class and its gender subclasses.

The only difference between the subclasses is that they have implementations of the abstract method that return hard-coded constants. It is preferable to get rid of such classes.

First use <a href="/replace-constructor-with-factory-method">Replace Constructor With Factory Method</a>. In our case, we need the factory method for each subclass.

Then replace all calls to subclass constructors with calls to the relevant factory methods.

After replacing all these calls, the code should not contain any more mentions of the subclasses.

Now, in the parent class, we should declare fields for each method that returns constants in subclasses.

Add a protected constructor to the parent class.

Add constructors that call this new constructor in subclasses.

Then we can compile and test.

The fields are created and initialized, but are not yet used. Now we can get the fields "in the game" by placing access methods in the parent class and removing subclass methods.

All subclasses are empty at this point. That allows us to remove the "abstract" keyword from the Person class and use its constructor instead the ones from subclasses (that we could simply remove).

The <code>Male</code> class should now be removed.

Compile and test to make sure nothing has been broken by mistake.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.