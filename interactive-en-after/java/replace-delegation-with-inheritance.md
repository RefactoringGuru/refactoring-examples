We have an <code>Employee</code> class that delegates certain work to the <code>Person</code> class.

Inheritance would be more appropriate here since the employee class needs practically all data from <code>Person</code>.

Let's start refactoring by declaring the <code>Employee</code> as a subclass of <code>Person</code>.

Right after that we should compile and run autotests to make sure there are no conflicting methods. These issues can arise if identically named methods return values of different types or generate different exceptions. For all such issues, use  <a href="/rename-method">Rename Method</a>.

Next, force the field, which contained reference to a <code>Person</code> object, to reference its own object. We will get rid of it later, but for now it will keep the code working.

We also should remove all simple delegate methods from <code>Employee</code>, such as <code>getName</code> and <code>setName</code>. If we forget to remove them, a stack overflow will occur due to infinite recursion.

Then we get rid of delegation calls by calling our object's methods.

After all replacements, we can finally remove the field of the associated object and it's initialization code, which are no longer necessary.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.