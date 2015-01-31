Let's look at <i>Inline Class</i> using the example of a person class and the phone number used in it.

We want to include the <code>TelephoneNumber</code> class back in the <code>Person</code> class, since it is no longer necessary for us.

Start by declaring all visible methods of the phone number class in the <code>Person</code> class.

For the first step, all these methods will delegate to the phone number object.

Now find all cases where the phone number class is used in client code and replace it with use of the <code>Person</code> class.

We can then proceed to <a href="/move-method">Move Method</a> and <a href="/move-field">Move Field</a> for moving all fields and methods to the <code>Person</code> class. These changes can be done one by one or, if there are not too many, all at once.

First move the fields.

Then move each method…

…one by one…

…move all the methods…

…each and every one…

…and finally the last getter of the phone number itself.

This is a good time to compile and test, to make sure the code is still working correctly.

At this point, we need only to remove the <code>TelephoneNumber</code> class from the program.

Start by removing its field and getter in the <code>Person</code> class.

Voila! Nothing is holding us back now from removing the class itself. Thank you for the good times, <code>TelephoneNumber</code>, they were good indeed!

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.