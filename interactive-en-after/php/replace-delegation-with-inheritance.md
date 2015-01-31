We have an <code>Employee</code> class that delegates certain work to the <code>Person</code> class.

Inheritance would be more appropriate here, since the employee class needs practically all data from <code>Person</code>.

Start refactoring by declaring inheritance in the <code>Employee</code> class.

Here you should run autotests to make sure there are no conflicting methods. These issues can arise if identically named methods return values of different types or generate different exceptions. For all such issues, use  <a href="/rename-method">Rename Method</a>.

Next force the delegation field to refer to the object itself.

We also should remove all simple delegate methods from <code>Employee</code>, such as <code>getName</code> and <code>setName</code>. If we forget to remove them, a stack overflow will occur due to infinite recursion.

Then get rid of references to the associated field, using own-class calls instead.

After all replacements, you can remove the field of the associated object and related initialization code, which are no longer necessary.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.