Let's look at <i>Extract Class</i> using the example of a simple class that describes a person.

In this example, you can isolate methods related to phone numbers to a separate class.

Start with defining the phone number class.

Easy as pie! Now we create a reference from the <code>Person</code> class to the phone number class.

Everything is ready for you to start moving fields and methods. Use <a href="/move-field">Move Field</a> to move the <code>officeAreaCode</code> field to the <code>TelephoneNumber</code> class.

Did you notice? We immediately renamed the field to be more neutral. This improves our chances of reusing the class.

Now that we have successfully moved the field to the <code>TelephoneNumber</code> class, the methods that were used by the moved field should be changed so that they reference an instance of the created class.

As for the methods that used direct access to the field, change them so that they reference the field getter.

Then you can remove the field from the original class.

<code>areaCode</code> is all done. Similarly, we move the <code>officeNumber</code> field...

…and the method for getting the formatted phone number <code>getTelephoneNumber()</code>.

After that, we can delegate all phone functionality to the <code>TelephoneNumber</code> class.

Let's run the compiler to verify that the code is not broken anywhere.

Here we should decide how available we want this new class to be for clients. We can hide it entirely, using delegate methods for access (as is currently done)…

…or remove all these methods and make the class public.

We will need to create a public getter for the associated object so that clients can access it.

But if we make the class public, take into account the dangers related to references. What about the fact that the client can change the area code when opening a phone number? This kind of change can be performed by any code that has access to a class instance via the public getter.

The following options are possible: <ul><li>Any object can change any part of the phone number. In this case the phone number becomes a reference and you should look at <a href="/change-value-to-reference">Change Value to Reference</a>. Access to the phone number is implemented through an instance of <code>Person</code>.</li><li>You do not want anyone to be able to change a phone number except through the methods of an instance of the <code>Person</code> class. The phone number can be made read-only or access to it can be limited to an appropriate method.</li><li>You can also clone an instance of the <code>TelephoneNumber</code> class before providing access to it. But this can cause confusion because people will think that they can change this value. In addition, clients may have problems with references if the phone number is passed frequently.</li></ul>

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code, if you like.