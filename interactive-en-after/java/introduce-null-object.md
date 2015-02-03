Let's look at this refactoring, using a commercial company class as an example.

Every business has customers (<code>Customer</code>).

Customers in turn have their own properties and behaviors.

The client code operates on these access methods in order to do its work. For example, this is code for getting the name of a current customer:

Note the conditional that verifies whether the business has the customer in question. This situation may occur if the business is new or an old customer has decided to change vendors.

The code may contain many such repetitive <code>null</code> verifications, which indicates the need to introduce a null-object.

First create a <code>null</code>-object class for <code>Customer</code> and modify the <code>Customer</code> class so that it supports a query for <code>null</code> verification.

To create "null" clients, let's introduce a factory method. Thanks to it, client code will not need to know about the existence of the null-object

Now we should handle all code that returns <code>Customer</code> objects. We should add the checks, which will return our <code>null</code> object instead of <code>null</code> value.

Then, in the remaining code, replace all checks of the type <code>Customer == null</code> with calls of <code>Customer.isNull()</code>.

This is the most complex part of the refactoring. For each source of <code>null</code> that you are replacing, you must find all <code>null</code> checks and change them. If an object is passed back and forth between methods, doing so consistently can be difficult.

After all replacements are done, compile and test carefully.

We do not yet gain any benefit from using <code>isNull</code> instead of plain <code>== null</code>  checks. The benefit will be visible when the code, which used to work in null cases will be moved straight to the null-object class.

Let's start moving behaviors. The first thing to do is move the default customer name to the null-object class.

Then remove the check for <code>null</code> from the corresponding part of the client code.

Do the same with the remaining methods for which you can think of a default behavior.

Careful review of the last bit of code could show a potential access error. It will occur when somebody will access a payment object while user object won't have any payment history.

To solve the problem, you can create a null-object class for the payment history class as well.

Once the null-object has been defined, you can move default behavior to it.

Now we can rest easy about any potential problem accessing the null-object of the payment history. But there are still other things to take care of.

We can return the null-object of the payment history from the null-object of customers, fully ridding the client code of checks for <code>null</code>.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.