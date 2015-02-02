Let's look at <i>Extract Class</i> using the example of a simple class that describes a person.

In this example, we can isolate methods related to phone numbers to a separate class.

Let's start by defining the phone number class.

Easy as pie! Now we create a reference from the <code>Person</code> class to the phone number class.

Everything is ready to start moving fields and methods. We use <a href="/move-field">Move Field</a> to move the <code>officeAreaCode</code> field to the <code>TelephoneNumber</code> class.

Did you notice? We immediately renamed the field to be more neutral. That improves our chances of reusing the class.

Now we should change the methods, which used the moved field so that they access it through a <code>TelephoneNumber</code> object.

We can also turn all cases of direct field access to the proper getter/setter calls.

At last, we can remove the field from the original class.

The <code>areaCode</code> is all done. Similarly, we move the <code>officeNumber</code> field...

…and the method for getting the formatted phone number <code>getTelephoneNumber()</code>.

After that, we can delegate all phone functionality to the <code>TelephoneNumber</code> class.

Let's run the compiler to verify that the code is not broken anywhere.

Here we should decide how available we want this new field to be for a client code. We can hide it entirely using delegation methods for accessing all the fields (as is currently done)…

…or remove all these methods and make the field public.

To do this, we will need to create a public getter for the associated object so that clients can access it.

But if we want to make the field public, let's consider some of the dangers related to object references. What about the fact that the client can change the area code when opening a phone number? Any code that has access to a class instance via the public getter could perform such change.

The following options are possible: <ul><li>Any object can change any part of the phone number. In this case the phone number becomes a reference and you should look at <a href="/change-value-to-reference">Change Value to Reference</a>. Access to the phone number is implemented through an instance of <code>Person</code>.</li><li>We do not want anyone to be able to change a phone number except through the methods of an instance of the <code>Person</code> class. The phone number can be made read-only or access to it can be limited to an appropriate method.</li><li>We can also clone an instance of the <code>TelephoneNumber</code> class before providing access to it. But this can cause confusion because people will think that they can change this value. In addition, clients may have problems with references if the phone number is frequently passed .</li></ul>

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.