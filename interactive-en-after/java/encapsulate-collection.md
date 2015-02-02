Let's look at <i>Encapsulate Collection</i>, using a catalog of training courses as our example.

The class of courses is rather simple.

Plus there is also a class of course students.

With this interface, clients add courses via the following code.

Thus, first we need proper modification methods for this collection.

We can also make life easier for ourselves by initializing the field:

Now look at the uses of the <code>setCourses</code> setter. If there are many clients and the setter is used intensively, replace the method body so that it uses add/remove operations.

The complexity of this procedure depends on the way in which the collection setter is used. In the most simple case, the client initializes values by using the setter, meaning that courses do not exist prior to use of the method.

If this is the case, change the setter body so that it uses collection's <code>add</code> method.

After this modification, use <a href="/rename-method">Rename Method</a> to make your intentions obvious.

But in a general case, this method should first remove all existing collection items, and then add new ones.

If there are no additional behaviors or checks during initialization, we can make the code even simpler by getting rid of the loop and using collection's <code>addAll</code> method.

Remember that we cannot simply assign a value to a set even if the previous set was empty. If the client plans to modify the set after passing it, this will violate encapsulation. So we always have to create copies.

If client code simply creates a set and uses its setter, we should force it to use add/remove methods instead...

...and get rid of the call to the initialization method completely.

Now let's see who is using the collection's getter. Our foremost interest should go to cases when it is used to modify the collection.

We need to replace such calls with add/remove calls.

As the finishing touch, let's change the getter's body so that it returns a read-only value (an immutable representation of the collection).

Let's compile to make sure there are no errors.

At this point, we can consider the collection fully encapsulated. Nobody can change its elements other than by using the <code>Person</code> class' method.

Now that we have a proper interface for the <code>Person</code> class, we can start moving the relevant code to this class. Here is an example of the code.

Apply <a href="/extract-method">Extract Method</a> to the code to move it to <code>Person</code>.

Take a loot at this code. It's quite frequently encountered.

It can be replaced with a more readable version.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.