Let's look at <i>Change Reference to Value</i> using a customer class as an example.

This class contains a customer's name and date of birth. This class gives rise to reference objects, meaning that only one instance of the <code>Customer</code> class is created for one real customer.

The code for getting the instance may look as follows:

The <code>Customer</code> class keeps a registry of its instances. We cannot simply access the constructor (because it is private).

Instead, we call a static factory method that looks for the customer among the objects already created. And only if such an object has not been created does the factory method run the real constructor and then add the newly created object to the registry.

Now let's say that we have multiple orders referring to the same client. Suddenly, the code of one of the orders changes the value of the client's date of birth. Since both orders refer to the same client object, the new date of birth will be available from the other order as well.

Would this be made impossible if each order had its own instance of the <code>Customer</code> class? Probably not. That is why the main requirement of this refactoring technique is making the class immutable. In some cases, this is simply not possible and the technique should not be used.

Following this logic, remove the setter for the date of birth field. Initialize the value of the field in the constructor. Use <a href="/remove-setting-method">Remove Setting Method</a>. 

Since the class no longer contains a setter, we need to remove use of it in the client code. We have nothing to replace the action of this setter yet – but don't worry, we will get to this a bit later.

One more problem. Value objects with identical data should be equal when compared. To do this in C#, define <code>Equals</code> and <code>GetHashCode</code> methods in the classes being compared.

This is how it will look in our case.

Now the comparison <code>new Customer("John").Equals(new Customer("John"))</code> will return <code>TRUE</code>.

And one last thing. Since we no longer need to keep a registry of created objects, we can remove the factory method and make the constructor public.

The client code will also change as the result of these changes.

Let's run the final compile.

Now refactoring is complete. If you like, you can compare the old and new code.