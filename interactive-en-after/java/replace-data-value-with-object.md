Let's look at the <i>Replace Data Value with Object</i> refactoring, using an order class as an example.

In this example, the customer in the order class is stored as a string. Alternatively, we could create a <code>Customer</code> class and move the other customer data and behaviors to this class.

The class is now ready. Let's move the customer name field to it, since the field is used in the rest of the order code.

We should also create a constructor that accepts the initial value of the name.

Now we can change the type of the <code>Customer</code> field. We should also change the associated methods so that they work with instances of the <code>Customer</code> class.

Let's start with changing the type of the customer field.

Now we will make the getter for the user name return the value from the associated object.

Then change the constructor and access setter so that they fill the customer field with a new <code>Customer</code> object.

Note that the setter creates a new instance of the customer class each time. That makes the customer is value object. In other words, if there are two orders made by single customer, the orders will still have two separate customer objects.

Value objects should be made immutable to avoid certain unpleasant errors related to the fact that objects are always passed via references. By the way, later we will still need to convert <code>Customer</code> to a reference object, but that's out of current refactoring scope.

Anyway, let's compile and test to make sure there are no errors.

All we have left now is to look at <code>Order</code> methods which work with <code>Customer</code> and make a few small changes in them.

FIrst, we apply <a href="/rename-method">Rename Method</a> to the getter to make clear that it returns a name, not an object.

It also is a good idea to change the names of the parameters in the constructor and setter.

Let's perform the final compilation and testing.

Before we finish, note that here and in many other cases, one more step is necessary. You may need to add a credit score, address, etc. to the <code>Customer</code>. You cannot do this yet, since <code>Customer</code> is used as a value object. That is, each order has its own instance of the <code>Customer</code> class.

To create the necessary attributes in the <code>Customer</code> class, use the <a href="/change-value-to-reference">Change Value to Reference</a> refactoring technique on it. After that refactoring, all orders for the same customer will refer to the same instance of the <code>Customer</code> class.

The refactoring is complete! You can compare the old and new code if you like.