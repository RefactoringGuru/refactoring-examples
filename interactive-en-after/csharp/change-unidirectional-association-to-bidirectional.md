Let's consider <i>Replace Unidirectional Association with Bidirectional</i> using the example of two classes: <code>Customer</code> and <code>Order</code>.

Originally, the order class contains a reference to the customer object.

Access to the object is implemented through the relevant property.

On the other hand, the customer class does not contain references to order objects. Thus if you need to get an order object for this customer in the method of the customer object, you will have to do this using roundabout ways that are slow and inconvenient.

Start refactoring by adding order fields to the <code>Customer</code> class. Since a customer can have multiple orders, make the field a collection.

Now decide which class will be responsible for managing the association. It is better to place responsibility with a single class since this allows keeping the entire logic in a single place.

Here is how to decide:<ul><li>If both objects are reference objects and the association is one-to-many, the “control” object will be the one that contains one reference. (So if one client has many orders, the association is controlled by the order.)</li><li>If one object is a component of the other (whole–part association), the “whole” object should control the association.</li><li>If both objects are reference objects and the association is many-to-many, either the order class or client class can be selected as the control class at your discretion.</li></ul>

Since the order will be responsible for the association, add a helper property to the customer in order to provide access to the order collection.

Now you can change the property setter in the <code>Order</code> class so that it adds the current order object to the list of user orders.

The exact code in the control modifier depends on the degree of association. If <code>Customer</code> cannot be <code>null</code>, you can get by without checking it for <code>null</code> but in that case you should check the argument for <code>null</code>.

Nonetheless, the basic scheme is always the same: first you "un-associate" the currently associated object so that it removes the reference to your object, then the association is replaced with a new object, and then a reverse reference to your object is added in the new object.

If you want to modify the reference via the customer class, the class must set the property value in the referenced order object:

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code, if you like.