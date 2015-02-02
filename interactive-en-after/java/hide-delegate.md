Let's look at <i>Hide Delegate</i> using the classes representing an employee and the employee's department as an example.

If the client needs to know the manager of a certain employee, the client must first know in which department that person works.

That way, along with the manager value, client code gets full access to the <code>Department</code> object and its other fields. If that doesn't look very safe to you... you're right.

This association can be reduced by hiding the <code>Department</code> class from the client, by creating a simple delegate method in <code>Person</code>.

Now let's modify the code so that it uses the new method.

Once all necessary methods have been delegated, we can remove the method in the <code>Person</code> class that provided access to the <code>Department</code> instance.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.