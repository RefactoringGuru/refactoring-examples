Let's look at <i>Extract Class</i> using the example of a simple class that describes a person.

In this example, you can extract the properties and methods relating to phone numbers to a separate class.

Start with defining the phone number class.

Easy as pie! Now we create a reference from the <code>Person</code> class to the phone number class.

Everything is ready to start moving properties and methods. We use the <a href="/move-field">Move Field</a> technique to move the <code>OfficeAreaCode</code> property to the <code>TelephoneNumber</code> class.

Did you notice? Right away I renamed the property to give it a neutral name. This improves our changes of reusing this class.

After the method has been successfully moved to the <code>TelephoneNumber</code> class, the methods that used the moved property must be changed so that they reference an instance of the created class.

In addition, if you need to store a property in the source class, you need to rewrite its setter and getter so that they delegate the property of the created class.

…and redirect all references made to it in favor of the class that we have created.

Now we need only to move the method for getting the formatted number <code>GetTelephoneNumber()</code>.

After that, we can delegate all phone functionality to the <code>TelephoneNumber</code> class.

Let's run the compiler to verify that the code is not broken anywhere.

If so, we must create a public property for the associated object so that the clients can access it.

However, we should also consider the reference-related dangers inherent with making a class public. When opening a phone number, the client can change the area code. What do about this, a change that could be made by any code that has access to a class instance via the public property?

The following options are possible: <ul><li>Any object can change any part of the phone number. In this case the phone number becomes a reference and you should look at <a href="/change-value-to-reference">Change Value to Reference</a>. Access to the phone number is implemented through an instance of <code>Person</code>.</li><li>You do not want anyone to be able to change a phone number except through the methods of an instance of the <code>Person</code> class. String properties of the phone number can be made read-only or access to it can be limited to an appropriate method.</li><li>You can also clone an instance of the <code>TelephoneNumber</code> class before providing access to it. But this can cause confusion because people will think that they can change this value. In addition, clients may have problems with references if the phone number is passed frequently.</li></ul>

Let's run the final compile.

Now refactoring is complete. If you like, you can compare the old and new code.