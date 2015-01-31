Let's consider <i>Replace Unidirectional Association with Bidirectional</i> using the example of two classes: <code>Customer</code> and <code>Order</code>.

The order class contains a reference to the customer object.

However, the customer class does not have references to order objects. Thus if you need to get an order object from the method of a customer object, you will have to usу some roundabout ways that are slow and inconvenient.

Let's start refactoring by adding order fields to the <code>Customer</code> class. Since a customer can have multiple orders, we should make the field a collection.

Now we need to decide which class will be responsible for managing the association. It is better to place responsibility with a single class because this allows keeping the entire logic in a single place.

Anyway, here's how to decide this:<ul><li>If both objects are reference objects and the association is one-to-many, the “control” object will be the one that contains one reference. So if one client has many orders, the association is controlled by the order.</li><li>If one object is a component of the other (whole–part association), the “whole” object should control the association.</li><li>If both objects are reference objects and the association is many-to-many, either the order class or client class can be selected as the control class at your discretion.</li></ul>

In our case, the order class will be responsible for the association, therefore we add a helper method to the customer class, which will provide access to the order collection.

Now we can change the field's setter in the <code>Order</code> class so that it would add the current order object to the list of customer orders.

The exact code in the control modifier depends on the degree of association. If <code>Customer</code> cannot be <code>null</code>, you can get by without checking it for <code>null</code> but in that case you should check the argument for <code>null</code>.

Nonetheless, the basic plan is always the same: first you "un-associate" the currently associated object so that it removes the reference to your object, then the association is replaced with a new object, and then a reverse reference to your object is added in the new object.

If you want to modify a reference through the customer class, the class should call the control method in the associated order object:

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.