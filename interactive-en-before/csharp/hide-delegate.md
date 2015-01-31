Let's look at <i>Hide Delegate</i> using the classes representing an employee and the employee's department as an example.

If the client needs to know the manager of a certain employee, the client must first know in which department that person works.

So the <code>Department</code> class and storage in it of manager data is opened to the client code.

This association can be reduced by hiding the <code>Department</code> class from the client, by creating a simple delegate method in <code>Person</code>.

Now modify the code so that it uses the new method.

After all the necessary methods have been delegated, you can remove the property in the <code>Person</code> class that provided access to the <code>Department</code> instance.

Let's run the final compile.

Now refactoring is complete. If you like, you can compare the old and new code.