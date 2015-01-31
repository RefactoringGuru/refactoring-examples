Let's look at this refactoring technique, using a business as an example.

Every business has customers (<code>Customer</code>).

Customers in turn have their own properties and behaviors.

The client code operates on these access methods in order to do its work. For example, this is code for getting the name of a current customer:

Note the conditional that verifies whether the business has the customer in question. This situation may occur if the business is new or an old customer has decided to change vendors.

The code may contain many such repetitive <code>null</code> verifications, which indicates the need to introduce a Null object.

First create a <code>null</code>-object class for <code>Customer</code> and modify the <code>Customer</code> class so that it supports a query for <code>null</code> verification.

To create null clients, introduce a factory method. Thanks to it, clients will not need to know about the existence of the null class.

Now we should modify all code that requests <code>Customer</code> objects, modifying them so that they return the null user instead of <code>null</code>.

In the remaining code, we can now replace all checks resembling <code>Customer == null </code> with calls to <code>Customer->isNull()</code>.

This is the most complex part of the refactoring technique, since for each source of <code>null</code> that you are replacing, you must find all <code>null</code> checks and change them. If an object is passed intensively, doing so consistently can be difficult.

After all the changes, test everything carefully.

We do not yet gain any benefit from using <code>isNull</code> instead of <code>== null</code>. The benefit will appear when behavior code in a null situation is moved to a null class and conditionals are removed entirely.

Start moving behaviors to the null class. The first thing to do is move the default customer name to the null class.

Then remove the check for <code>null</code> from the part of the client code.

Do the same with the remaining methods for which you can think of a default behavior.

Careful review of the last bit of code shows that a potential access error is present, when attempting to access a payment object if the user object has no payment history.

To solve the problem, you can create a null class for the payment history class as well.

Once the null object has been created, you can add default behavior to it.

Now we can rest easy about any potential problem accessing the null object of the payment history. But there are still other things to take care of.

We can return the null object of the payment history from the null object of customers, fully ridding the client code of checks for <code>null</code>.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.