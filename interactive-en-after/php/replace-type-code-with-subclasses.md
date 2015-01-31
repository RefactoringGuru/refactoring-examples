Let's look at <i>Replace Type Code With Subclasses</i>, using an employee salary class as our example. We have several type of employees and the salary of an employee depends on his or her type.

Start by applying <a href="/self-encapsulate-field">Self-Encapsulate Field</a> to the employee type.

Since the <code>Employee</code> constructor uses type code as a parameter, replace it with a factory method.

Now start converting <code>Engineer</code> to a subclass. First create the subclass itself…

…then create the method to replace the type code.

Replace the factory method as well so that it creates the necessary object.

Continue these actions in order until all code has been replaced by subclasses.

Then we can eliminate the field with type code in <code>Employee</code>…

…and make <code>getType</code> an abstract method.

This will make the <code>Employee</code> class abstract as well.

After all these changes, we can no longer create <code>Employee</code> objects as the default implementation. So it is important to remember to get rid of the type field only after creating all subclasses.

Note that we ended up creating another big <code>switch</code> operator. Generally speaking this <a href="/smells/switch-statements">gives off a bad whiff</a> but once refactoring is done, it will be the only operator remaining in the code.

After creating the subclasses, use <a href="/push-down-method">Push Down Method</a> and <a href="/push-down-field">Push Down Field</a> on all methods and fields that relate to only specific types of employees.

In our case, we will create <code>payAmount</code> methods in each of the subclasses and move payroll calculations there for the relevant types of employees.

Do the same thing with the manager class.

After all the code has been moved to the subclasses, you can either declare the method in the superclass abstract or else leave the default implementation there (which is what we will do).

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.