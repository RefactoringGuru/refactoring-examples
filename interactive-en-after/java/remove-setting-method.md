Let's look at <b>Remove Setter Method</b> using a simple example of a bank account class. The class has an ID field that should be created once and never change again.

However, the class currently has a setter for that field, which we want to eliminate.

The simplest solution would be to integrate the setter's code into the constructor.

In effect, we have already done everything for a case as simple as this one. But there are other, more difficult cases.

For example, what if the setter performs calculations on an argument:

If the change is simple, as it is here, it can also be moved to the constructor.

However, if the change is complex and consists of calls to several methods, it is better to create a new method for initializing the value.

Excellent. Now let's review one more case.

Another unpleasant situation arises when there are subclasses initializing private variables of a parent class.

Then, instead of calling a setter, we should call the parent constructor.

If that is impossible, we must call the proper initialization method. By the way, if it's private, you should make it protected first.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.