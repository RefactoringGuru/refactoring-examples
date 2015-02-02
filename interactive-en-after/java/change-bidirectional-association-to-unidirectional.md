We will start <i>Change Bidirectional Association to Unidirectional</i> from the place where we have stopped in the inverse refactoring.

In other words, we have <code>Customer</code> and <code>Order</code> classes with a bidirectional association.

Two new methods have been added to the code since completion of the previous refactoring.

First, the method for getting order price in the customer object, and...

The method for getting price with discount in the order class.

Few days ago a new requirement was received, which says that orders must only be created only for existing customers. This lets us eliminate the bidirectional association between orders and customer, and only keeping customers aware of their orders.

The hardest part of this refactoring technique is making sure that it is possible. Refactoring itself is easy, but we must make sure that it is safe. The problem comes down to whether any of order class' code needs a customer field. If that is the case, removing the field requires you to provide an alternative method for getting the customer object.

First, we review all usages of the customer field and it's getter. Is there another way to provide the customer object or it's data? Often thу best solution means passing the customer as an argument to the methods, which use the field.

This works particularly well for methods that called by client code already containing a customer object. In this case, you just pass it as a method's argument.

Another alternative is to change the customer field getter so that it would get the right object by looking it in some object repository like this:

The previous introduction of a parameter into the method can now be removed since the customer getter will return the correct object.

Slow… But it works. In the context of a database, things may even become a little faster if a database query is used.

Now we can prepare for the <code>setCustomer</code> method removal. Instead calling it in customer class, we should add orders to the order collection directly.

Then we can freely remove the method in the order class.

At last, we can remove the field itself, fully eliminating the bidirectional association between the classes.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.