Let's look at <i>Move Method</i> using a bank account class as an example.

Say, there will be several new account types, and each account type will have different rules for how to calculate overdraft fees, when the bank's customer attempts to spend more money than is available.

We want to move the method for calculating this overdraft fee to inside the class that represents the account type.

First, we need to decide whether to move only <code>overdraftCharge()</code> method or, perhaps, some other related code, fields or methods. 

The <code>type</code> field stores the account type. There is no reason to move it anywhere else.

Moving the <code>daysOverdrawn</code> field would not make sense either, since its value will be different in every other account.

So, it looks like we going to move only the <code>OverdraftCharge()</code> method.

Awesome, let's copy the <code>overdraftCharge()</code> method to the <code>AccountType</code> class.

Now, we should edit the method so that it will work correctly in the new location.

First remove the <code>type</code> field from the method, since the method is inside the class that implements the account type and, therefore, all methods could be called from it directly.

Now we should go through the fields and methods  that left in <code>Account</code> class but still needed inside the method we move. In our case, this is the <code>daysOverdrawn</code> field.

In theory, there are four options for saving a method or field of the original class: <ol><li>Move the field or method to the target class.</li><li>Create a reference from the target class to the original one or restore the previously existing one.</li><li>Pass an instance of the original class as a parameter of the target class method.</li><li>Pass the field value as a parameter.</li></ol>

In our case, let's pass the field value as a parameter…

…and use this parameter in the method body.

Let's compile and test to check for errors in your code.

Now we can replace the body of the original method in <code>Account</code> class with simple delegation.

Let's compile again just to be safe.

The code works fine, so we can continue and remove the <code>overdraftCharge()</code> method from the original class entirely.

But first, find all places where it is called and readdress these to the method in the <code>AccountType</code> class.

In case, when the moved being method is not private, make sure that other classes are not using it. It's easy in strongly-typed programming languages (Java, C#) – compilation will uncover everything that may have been missed. In other languages, autotest is your friend.

After redirecting all method calls to the new class, we can entirely remove the method declaration in the <code>Account</code> class.

We can compile and test after each removal or perform everything in one fell swoop.

So can we say that we are done moving the method?<br/><br/>Not quite…

Let's look at one nuance. In this case, the method referred to a single field, which allowed us to pass its value to the parameter. If the method had called any other method of the <code>Account</code> class, we would not be able to do this. In such situations, you must pass the entire object in the parameters and retrieve everything you need from it. Let's see how it's done.

First, pass an instance of the original class to the method being moved.

In addition, all fields and methods of interest should now be taken directly from the instance received.

And third, you must add passing of the current instance of the <code>Account</code> class to all method calls.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.