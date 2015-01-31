Let's look at <i>Replace Subclass With Fields</i>, using the example of the same person class and its gender subclasses.

The only difference between the subclasses is that they have implementations of the abstract method that return hard-coded constants. It is preferable to get rid of such classes.

First use <a href="/replace-constructor-with-factory-method">Replace Constructor With Factory Method</a>. In our case, we need the factory method for each subclass.

Then replace all calls to subclass constructors with calls to the relevant factory methods.

After replacing all these calls, the code should not contain any more mentions of the subclasses.

Now, in the parent class, declare fields for each method that returns constants in subclasses.

Add a protected constructor to the parent class.

Add constructors that call this new constructor in subclasses.

After this, let's perform testing.

The fields are created and initialized, but are not yet used. Now we can get the fields "in the game" by placing access methods in the parent class and removing subclass methods.

All subclasses are empty at this point so we remove the "abstract" label from the Person class and, by using <a href="/inline-method">Inline Method</a>, place the subclass constructor inline inside the parent class.

The <code>Male</code> class should now be removed.

Test to ascertain whether any other code has been broken in the process.

Let's start the final testing.

Now refactoring is complete. If you like, you can compare the old and new code.