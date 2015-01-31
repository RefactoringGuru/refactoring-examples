Let's look at <i>Inline Class</i> using the example of a person class and the phone number used in it.

We want to include the <code>TelephoneNumber</code> class back in the <code>Person</code> class, since it is no longer necessary for us.

Start by declaring all visible properties of the phone number class in the <code>Person</code> class.

Initially all these properties will delegate to the phone number object.

Now find all cases where the phone number class is used in client code and replace it with use of the <code>Person</code> class.

Then we can proceed to <a href="/move-method">Move Method</a> and finish <a href="/move-field">Move Field</a>, configuring getters and setters to work inside the <code>Person</code> class. These moves can be performed one by one or all at once, if there are not too many of them.

First modify the getters and setters for the moved properties.

Then move the methods.

Once the functionality has been moved, remove the unused properties and methods of the old class.

This is a good time to compile and test, to make sure the code is still working correctly.

At this point, we need only to remove the <code>TelephoneNumber</code> class from the program.

Start by removing its field and property in the <code>Person</code> class.

Voila! Nothing is holding us back now from removing the class itself. Thank you for the good times, <code>TelephoneNumber</code>, they were good indeed!

Let's run the final compile.

Now refactoring is complete. If you like, you can compare the old and new code.