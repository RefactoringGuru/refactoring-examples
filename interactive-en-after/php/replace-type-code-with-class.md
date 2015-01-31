Let's look at <i>Replace Type Code With Class</i>, using the example of a person class that contains blood type fields.

Blood types are coded as four constants of this class.

We start refactoring by creating a new <code>BloodGroup</code> class, which will be responsible for blood types.

We place the blood type field from the <code>Person</code> class, constructor initializing the field value, and its getter in this class.

Now create static methods for each of the coded type values from the original class. These methods should return instances of the <code>BloodGroup</code> class.

Let's double-check the code by testing.

In the original class, change the type of the coded field to <code>BloodGroup</code>.

Change the code of the constructor and setter accordingly.

Then change the field getter so that it calls the getter of the <code>BloodGroup</code> class.

It is now time to replace all mentions of coded type values with calls to the corresponding static methods of the <i>type class</i>.

After performing the replacements, we should perform some testing.

After all the changes are completed, it is better to avoid using any numeric codes for <code>BloodGroup</code> and use objects instead. Let's try to do so in the <code>Person</code> class.

These changes will probably cause the client code to break, but this can be fixed by simply getting rid of the codes there as well.

And finally, remove the constants from the <code>Person</code> class.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.