Let's look at <i>Move Field</i> using a bank account class as our example.

We want to move the <code>interestRate</code> field to the <code>AccountType</code> class.

Several methods refer to this field. One of them is the <code>interestForAmount_days()</code> method.

Create the same field and same access methods in the target class.

To stay on the safe side, let's compile.

In our example, the <code>Account</code> class contains a field for accessing the account type object. Therefore we can access the moved field through it.

Replace all references to the old field with appropriate calls to methods in the target class.

Once changes are complete, remove the original field.

Here you should compile and test for potential errors.

Remember that if a class has many methods that use the moved field, you may want to self-encapsulate it to simplify later refactoring. Let's look at a quick example.

In this case, you will not need to make changes in all methods right away…

…only in the access methods (getter and setter).

Then the original field can be removed.

Later, if desired, you can redirect access methods for clients so that they use the new object.

Self-encapsulating allows refactoring via baby steps. And when a class is undergoing major changes, that is a good thing.

Let's run the final compile.

Now refactoring is complete. If you like, you can compare the old and new code.