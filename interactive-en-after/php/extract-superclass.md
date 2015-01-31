Let's look at this refactoring technique using the example of employees and their department.

These classes have several traits in common. First, as with employees, departments also have names.

Second, for both classes there is an annual budget (<code>annualcost</code), although the calculation methods are slightly different.

Therefore it would be good to extract these aspects to a shared parent class.

To start, we create a new parent class and we define the existing classes as subclasses of it.

Now we can start pulling up functions to the parent class. Usually it is simpler to employ <a href="/pull-up-field">Pull Up Field</a> first.

Then use <a href="/pull-up-method">Pull Up Method</a> on the methods for accessing the field.

The fields should be protected from the public, but for this we must first do <a href="/pull-up-constructor-body">Pull Up Constructor Body</a> to initialize them.

In the subclasses you can go ahead and remove code initialization, placing parent constructor calls there instead.

The name has been moved, which leaves the annual budget for you to tackle.

The <code>getTotalAnnualCost</code> and <code>getAnnualCost</code> methods have the same purpose, so they should have the same name. Use <a href="/rename-method">Rename Method</a> to give them the same name.

The bodies of the methods are currently different, so we cannot use <a href="/pull-up-method">Pull Up Method</a>. On the other hand, we can declare an abstract method with the same name in the parent class.

Having made these changes, let's look at clients of both classes to determine whether we can make them use the new parent class.

One of the clients of the classes is the <code>Department</code> class itself, which contains a collection of employee classes. The <code>getAnnualCost</code> method uses only the annual budget calculation method, which is now declared in <code>Party</code>.

This behavior offers a new opportunity. We can look at using the <a href="http://sourcemaking.com/design_patterns/composite">Composite</a> pattern on <code>Department</code> and <code>Employee</code>. 

This allows including one department in another. The result is new functionality, so strictly speaking this goes beyond refactoring.

Be that as it may, if the Composite pattern were necessary, we would get it by changing the type of the <code>staff</code> field to make for a prettier picture.

This change would entail a corresponding change in the name of <code>addStaff</code> and change of the parameter to <code>Party</code>.

In the final version, the <code>headCount</code> method should be made recursive.

But for this method to work, we must declare an equivalent method in <code>Employee</code> that simply returns <code>1</code>.

This method should also be declared abstract in the parent class.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.