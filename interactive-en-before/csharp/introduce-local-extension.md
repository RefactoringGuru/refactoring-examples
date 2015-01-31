In the previous example <a href="/introduce-foreign-method">Introduce Foreign Method</a>, we used <i>extension methods</i> to add necessary functionality to the <code>DateTime</code> structure.

This choice is good if the task is limited to just adding methods. If you need to extend the functionality of a class by adding properties, or redefining any of its existing methods, then use <i>Introduce Local Extension</i>, which we will now examine in greater detail.

Start with the code from the previous example, in which we needed to extend the functionality of the  <code>DateTime</code> structure. <i>Introduce Local Extension</i> can be performed in two ways: by creating a child class or creating a wrapper class. <code>DateTime</code> is a structure and therefore does not support inheritance, so we will choose wrapping.

First, create a new wrapper class.

Add a private field to it for the type that we want to place in a wrapper.

Create duplicate constructors that will delegate calls for constructors of the field you created. There is no need to duplicate all field constructors – just the ones used in the client code. For example, let's implement a constructor without parameters.

Now add a converting constructor that accepts the original <code>DateTime</code> object as an argument.

Redirect the calls from duplicate constructors to it.

When the class constructors are ready, you can add new methods to it or move foreign methods form other classes. Let's move the <code>GetNearFirstDate()</code> method with the help of <a href="/move-method">Move Method</a>.

The method parameter is no longer needed since the method is inside the <code>DateTime</code> wrapper. Thus the required data can be taken from its own object.

The method is no longer static.

Now change the code that uses the foreign method so that it uses the new extension class instead.

After changes are complete, remove the external method from the client class.

Let's run the final compile.

Now refactoring is complete. If you like, you can compare the old and new code.