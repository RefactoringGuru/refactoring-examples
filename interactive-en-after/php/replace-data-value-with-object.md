Let's look at the <i>Replace Data Value with Object</i> refactoring technique, using an order class as an example.

In this example, the customer in the order class is stored as a string. But we could have created a class just for customers and moved all data and operations related to customers to this class.

The class is now ready. Let's move the customer name field to it, since the field is used in the rest of the order code.

Here is where we will create a constructor that accepts the initial value of the name.

Then you can change the type of the <code>Customer</code> field and also change the associated methods so that they now act on an instance of the <code>Customer</code> class.

Let's start with changing the type of the customer field.

Now we will make the getter for the user name return the value from the associated object.

Then change the constructor and access setter so that they fill the customer field with a new <code>Customer</code> object.

Note that the setter creates a new instance of the customer class each time. This means that the customer is a value and each order contains its own instance of <code>Customer</code>. In other words, one and the same customer will correspond in different orders to different instances of the <code>Customer</code> class.

Generally, value objects should be immutable, which allows avoiding certain unpleasant errors related to the fact that objects are always passed via references. Incidentally, later we will need for <code>customer</code> to become a reference object, but for that we will need to perform yet another refactoring.

Now we can perform testing.

All we have left now is to look at <code>Order</code> methods working with <code>Customer</code> and implement a few changes in order to clarify the new way of things.

Apply <a href="/rename-method">Rename Method</a> to the getter to make clear that it returns a name, not an object.

It also is a good idea to change the names of the parameters in the constructor and setter.

Let's perform the final testing.

Before we finish, note that here and in many other cases, one more step is necessary. You may need to add a credit score, address, etc. to the client. You cannot do this yet, since <code>Customer</code> is used as a value. That is, each order has its own instance of the <code>Customer</code> class.

To create the necessary attributes in the <code>Customer</code> class, use the <a href="/change-value-to-reference">Change Value to Reference</a> refactoring technique on it. Now all orders for the same customer will refer to the same instance of the <code>Customer</code> class.

The refactoring is complete! You can compare the old and new code, if you like.