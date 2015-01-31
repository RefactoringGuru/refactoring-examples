Let's look at <i>Move Field</i> using a bank account class as our example.

We want to move the <code>interestRate</code> field to the <code>AccountType</code> class.

Several methods refer to this field. One of them is the <code>InterestForAmountDays()</code> method.

Create a copy of the field in the target class…

…and also create a public property for accessing this field externally.

Incidentally, this is a good time to mention auto-implemented properties, which have been with us ever since C# 3.0. Replace our field/property relationship with an auto-implemented property.

To stay on the safe side, let's compile.

In our example, the <code>Account</code> class contains a field for accessing the account type object. Therefore we can access the moved field through it.

Replace all references to the old <code>interestRate</code> field with the appropriate references to the property in the target class.

Once changes are complete, remove the original field.

Here you should compile and test for potential errors.

Remember that if a class has many methods that use the moved field, you may want to self-encapsulate it into a private property to simplify future refactoring tasks. Here is one example.

Bring our field back and self-encapsulate it.

Bring back use of the field as well in the methods that now access it via the property we created.

And finally, for the full effect, add more methods.

If we now needed to move the field, we would not need to change the content of the methods.

Simply adjust the getter and setter of the property into which our field has been encapsulated.

Then you can (again) remove the original field.

Self-encapsulating allows refactoring via baby steps. And when a class is undergoing major changes, that is a good thing.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.