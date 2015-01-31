We will continue <i>Change Bidirectional Association to Unidirectional</i> from the place where we stopped in the inverse refactoring.

We have <code>Customer</code> and <code>Order</code> classes with a bidirectional association.

Since completion of the previous refactoring technique, two new methods have appeared in the code:

Method for getting order price inside the customer class, and

Method for getting price with discount in the order class

Recently we received new requirements: orders must appear only if the customer has already been created. This lets us forego bidirectional association between the classes and get rid of the association between the order and customer.

The hardest part of this refactoring technique is making sure that it is possible. Refactoring is easy, but you must make sure that it is safe to do so. The problem comes down to whether the order code depends on the presence of the customer field. If that is the case, removing the field requires that you provide an alternative method for getting the customer object.

First review all read operations involving the field and all methods that use these operations. Is there another way to provide the customer object? Often this means passing the customer as an operation argument.

This works particularly well when behavior is called by client code containing a customer object that can be passed as an argument.

Another alternative to consider is changing the getter of the property that allows it to get the customer without using the field. Then you can apply <a href="/substitute-algorithm">Replace Algorithm</a> to the body of <code>Order.Customer.get</code> and do something similar to the actions outlined below.

The previous insertion of the parameter in the method can now be removed, since the getter of the <code>this.Customer</code> property will return the correct object.

Slow… but it works. In the context of a database, things may even become a little faster if a database query is used.

Now you can prepare to remove use of the <code>order.Customer</code> property, replacing its assignment in the code of the customer class with direct addition of order objects to the collection.

Then remove the property setter in the order class where assignment of the new customer value had taken place.

Now just remove the field itself, fully eliminating the bidirectional association between the classes.

Let's run the final compile and test.

Now refactoring is complete. If you like, you can compare the old and new code.