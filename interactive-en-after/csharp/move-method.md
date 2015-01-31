Let's look at <i>Move Method</i> using a bank account class as an example.

Say that there will be several new account types, and each account type will have different rules for how to calculate overdraft fees, when the bank's customer attempts to spend more money than is available.

We want to move the method for calculating this overdraft fee to inside the class that represents the account type.

First see which fields and methods are used by <code>OverdraftCharge()</code> and decide whether you can move only it – or else move it and everything related to it as well.

The <code>type</code> field stores the account type. There is no reason to move it anywhere else.

Moving the <code>daysOverdrawn</code> field would not make sense either, since it is different for different accounts.

So we will move only the <code>OverdraftCharge()</code> method.

Copy the <code>OverdraftCharge()</code> method to the <code>AccountType</code> class.

Now edit the method so that it will function correctly in its new location.

First remove the <code>type</code> field from the method, since we are now inside the class that implements the account type and all methods can be called from it directly.

Now go through the fields and methods of <code>Account</code> that you need. In our case, this would be the <code>daysOverdrawn</code> field.

In theory, there are four options for saving a method or field of the original class: <ol><li>Move the field or method to the target class.</li><li>Create a reference from the target class to the original one or restore the previously existing one.</li><li>Pass an instance of the original class as a parameter of the target class method.</li><li>Pass the field value as a parameter.</li></ol>

In this case, we will pass the field value as a parameter…

…and use this parameter in the method body.

Let's compile and test to check for errors in your code.

Now we can replace the body of the original method with simple delegation.

Let's compile again just to be safe.

The code is functioning, so we can proceed and entirely remove the <code>OverdraftCharge()</code> method from the original class.

But first, find all places where it is called and readdress these to the method in the <code>AccountType</code> class.

After redirecting all method calls to the new class, we can entirely remove the method declaration in the <code>Account</code> class.

If the moved method is not private, make sure that other classes are not using it. In a strongly typed language, after a method declaration in the original class is removed, compilation will uncover everything that may have been skipped. In other languages, autotests will assist.

So can we say that we are done moving the method?<br/><br/>Not quite…

Let's look at one nuance. In this case, the method referred to a single field, because of which we could pass its value in a parameter. If the method had called any other method of the <code>Account</code> class, we would not be able to do this. In such situations, you must pass an instance of the entire original class in the parameters. Here are all the details of how to implement this.

First, pass an instance of the original class to the method being moved.

In addition, all fields and methods of interest should now be taken directly from the instance received.

…and remember that if the fields are private (which they always should be), you need to create the relevant properties so that they can be accessed.

Then work with these properties from the instance of the class passed in the method parameters.

And third, you must add passing of the current instance of the <code>Account</code> class to all method calls.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.