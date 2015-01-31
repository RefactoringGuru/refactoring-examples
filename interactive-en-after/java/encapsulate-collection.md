Let's look at <i>Encapsulate Collection</i>, using a catalog of training courses as our example.

The class of courses is rather simple.

Plus there is also a class of course students.

With this interface, clients add courses via the following code.

Thus first we need proper modification methods for this collection.

We can also make life easier for ourselves by initializing the field:

Now look at the users of the <code>setCourses</code> setter. If there are many clients and the setter is used intensively, replace the method body so that it uses add/remove operations.

The complexity of this procedure depends on the way in which the collection setter is used. In the most simple case, the client initializes values by using the setter, meaning that courses do not exist prior to use of the method.

If this is the case, change the setter body so that it uses an addition method.

After this modification, use <a href="/rename-method">Rename Method</a> to make your intentions obvious.

As a general rule, we should use the removal method and get rid of all elements, and then add new ones. However, this happens rarely (as is true of general rules).

If we know that there is no other behavior when elements are added during initialization, we can get rid of the cycle and use <code>addAll</code>.

Remember than we cannot simply assign a value to a set even if the previous set was empty. If the client plans to modify the set after passing it, this will violate encapsulation. So we should create a copy.

If clients simply create a set and use a setter, we can force them to use add/remove methods directly...

...and get rid of the call to the initialization method completely.

Now see who is using the collection getter. Our foremost interest should go to cases when it is used to modify the collection.

These calls should be replaced with calls to the method for adding or removing courses.

As the finishing touch, change the getter body so that it returns a read-only value (an immutable representation of the collection).

Let's compile to make sure there are no errors.

Then the collection can be considered fully encapsulated. Nobody can change its elements other than by using the <code>Person</code> method.

After the correct interface is created for the <code>Person</code> class, we can start moving the relevant code to this class. Here is an example of the code.

Apply <a href="/extract-method">Extract Method</a> to the code to move it to <code>Person</code>.

Such code is frequently encountered.

It can be replaced with a more readable version.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.