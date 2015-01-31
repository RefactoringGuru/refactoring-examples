Let's look at the <i>Replace Data Value with Object</i> refactoring technique, using an order class as an example.

In this example, the customer in the order class is stored as a string. But we could have created a class just for customers and moved all data and operations related to customers to this class.

The class is now ready. Let's move the customer name field to it, since the field is used in the rest of the order code.

Here is where we will create a constructor that accepts the initial value of the name.

Then we change the type of the <code>customer</code> field, as well as change the related methods and properties so that they now work with the instance of the <code>Customer</code> class.

Let's start with changing the type of the customer field.

Then we should decide what to do with the public property. There are two paths here:<br>1. Change the property type to <code>Customer</code> so that it allows working with the class instance directly. Thus we give the client code full access to the customer instance.<br>2. Leave the property type unchanged. This limits the context of its use (in this case, to working with the customer name).

In the example considered here, we will take the second path. The property will work only with the customer name. So we will convert its getter and setter.

Now change the constructor so that it fills the customer field with a new instance of the <code>Customer</code> class.

Note that the constructor creates a new instance of the customer class each time; we do not have any other setters for the field. This means that the customer is a value and each order has its own instance of <code>Customer</code>. In other words, the same customer will correspond to different instances of the <code>Customer</code> class in different orders.

Generally, values should be immutable, which allows avoiding unpleasant errors related to the fact that objects are always passed via references. Incidentally, later we will need for the <code>Customer</code> string to become a reference object but for this we will need to use an additional refactoring technique.

Let's compile and test.

At this point we work out how to name the areas of the <code>Order</code> class in which the code was adjusted to work with the new data type.

The <code>customer</code> field is an instance of the <code>Customer</code> class, therefore renaming it is not necessary.

Meanwhile the <code>Customer</code> property now allows working only with the customer name, so it would be logical to rename it to <code>CustomerName</code>.

You should also replace the name of the property in the remaining code (in most development environments, this is automatic).

To finish off the process, replace the parameter name in the constructor to make clear that the customer name is passed to it.

Let's run the final compile.

Before we finish, note that here and in many other cases, one more step is necessary. You may need to add a credit score, address, etc. to the client. You cannot do this yet, since <code>Customer</code> is used as a value. That is, each order has its own instance of the <code>Customer</code> class.

To create the necessary attributes in the <code>Customer</code> class, use the <a href="/change-value-to-reference">Change Value to Reference</a> refactoring technique on it. Now all orders for the same customer will refer to the same instance of the <code>Customer</code> class.

Now refactoring is complete. If you like, you can compare the old and new code.