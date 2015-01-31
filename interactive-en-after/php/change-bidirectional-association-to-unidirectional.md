We will continue <i>Change Bidirectional Association to Unidirectional</i> from the place where we stopped in the inverse refactoring.

We have <code>Customer</code> and <code>Order</code> classes with a bidirectional association.

Since completion of the previous refactoring technique, two new methods have appeared in the code:

Method for getting order price inside the customer class, and

Method for getting price with discount in the order class

Recently we received new requirements: orders must appear only if the customer has already been created. This lets us forego bidirectional association between the classes and get rid of the association between the order and customer.

The hardest part of this refactoring technique is making sure that it is possible. Refactoring is easy, but you must make sure that it is safe to do so. The problem comes down to whether the order code depends on the presence of the customer field. If that is the case, removing the field requires that you provide an alternative method for getting the customer object.

First review all read operations involving the field and all methods that use these operations. Is there another way to provide the customer object? Often this means passing the customer as an operation argument.

This works particularly well when behavior is called by client code containing a customer object that can be passed as an argument.

Another alternative is to change the method for getting data so as to get the customer while bypassing use of any field. Then you can use <a href="/substitute-algorithm">Substitute Algorithm</a> on the body of <code>Order.getCustomer</code> and do something similar to the following.

The previous introduction of a parameter into the method can now be removed, since the customer getter will return the correct object.

Slow… but it works. In the context of a database, things may even become a little faster if a database query is used.

Now prepare to remove the <code>setCustomer</code> method by replacing their calls in the code of the customer class with direct addition of order objects to the collection.

Then remove the method that assigns a new customer value in the order class.

Now just remove the field itself, fully eliminating the bidirectional association between the classes.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code if you like.