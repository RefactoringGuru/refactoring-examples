Let's look at <i>Introduce Foreign Method</i> using the example of a bank account class.

The class contains code that opens a new billing period only on the first day of the upcoming month.

Ideally, the <code>DateTime</code> class would have a method for getting the next relevant date (for example, <code>previousDate.GetNearDate()</code>). However, it does not have one.

What we can do though is create a "foreign" method in its own class.

To make the method more universal, add a <code>DateTime</code> class parameter. In essence, we will be broadening the functionality of the object sent in this parameter.

You should also declare the method static to make it accessible from other code not associated with <code>Account</code>.

The method can now be used in the remaining code.

Let's add a comment to clarify the "foreign method". This will avoid potential confusion in the future. In addition, if a new class is created in the program for storing additional functions with dates, this method can be easily found and moved to a better place.

Generally, such a "better place" might be a place that expands the features of <code>DateTime</code>. We can implement it by using <i>extension methods</i>, a tool available in our arsenal since C# 3.0.

Move our method to it…

…remove it from the original class…

…as the first parameter, the extension method should accept the instance of the class for which it will be called.

Excellent. Now just put a call to our extension method in the client code.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.