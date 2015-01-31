Let's look at <i>Replace Data Value with Object</i> using the customer/order class example. We will pick up where we finished the example involving <a href="/replace-data-value-with-object">Replace Data Value with Object</a>.

Here we have a customer class…

…that is used in the order class…

…and client code that is used by both classes.

Currently the customer in the order class is used as a data value. In other words, each order has its own instance of <code>Customer</code> even if the actual customer is the same. We want to change the code so that multiple orders for the same customer use the same instance of the <code>Customer</code> class.

In our case, this means that for each customer name, there must exist one and only one instance of the customer class.

We start with <a href="/replace-constructor-with-factory-method">Replace Constructor with Factory Method</a>. This lets us keep an eye on the process of creation of customer objects, which is extremely important for what we are going to do. We create the factory method in the customer class.

Then we replace the call to the <code>Customer</code> class constructor with a reference to the factory method.

We can now make the customer constructor private.

A decision must be made: Which object will be responsible for providing access to instances of the customer class? Generally it would be good to have a registry object for this purpose, containing a pool of all reference objects and retrieving the necessary instances from it. For example, if you need to put several products in an order, each product can be stored inside the order object.

But in this case, no such object exists for customers. To not create a new class for storing a customer registry, you can set up storage by using a static field in the <code>Customer</code> class.

Then decide how to create customers: in advance or dynamically (as needed). We will use the first way. When launching the application, we will load the clients that are currently "in use". We can take this information from a database or file, for example.

For simplicity, we will use explicit code for loading customers. This makes it possible to change it by using <a href="/substitute-algorithm">Substitute Algorithm</a>.

Now we modify the factory method of the <code>Customer</code> class so that it returns the previously created customer.

And since the <code>Create()</code> method now always returns an existing customer, this should be clarified with the help of <a href="/rename-method">Rename Method</a>.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.