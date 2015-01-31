Let's take a look at this refactoring technique in the context of code for calculating salaries for different types of employees (see <a href="/replace-type-code-with-state-strategy">Replace Type Code with State/Strategy</a>).

We can try to get rid of the conditional inside the <code>payAmount()</code> method.

First extract the implementation of <code>payAmount</code> to a new method in a class like <code>EmployeeType</code>. This gives us a common access point for this method in the subclasses.

We need datа from the <code>Employee</code> object, so in the method we create the parameter to which the main <code>Employee</code> object will be passed.

After these actions, we can set up delegation from the <code>Employee</code> class.

Then start moving code to subclasses. Create <code>payAmount</code> methods in each of the subclasses and move payroll calculations there for the relevant employee types.

Now that the methods have been created, you can make abstract the <code>payAmount</code> method in <code>EmployeeType</code>.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.