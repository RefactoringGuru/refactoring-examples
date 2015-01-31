Let's look at <i>Decompose Conditional</i> by looking at how to calculate the cost of a stadium ticket.

The cost depends on the season (winter or summer).

Our task is to make this conditional easier to understand. We can start by extracting the condition to a new method with a more obvious name.

Let's launch autotests to check for errors in code.

The condition is clearer now, yes? Many programmers in such situations do not extract the components of the conditional. Often the conditions seem short and not worth the effort.

But no matter how short the condition is, there is often a big difference between the purpose of the code and its body. Figuring this out requires looking at the code in detail. In this case, doing so is easy but even here the extracted method looks more like a comment.

Now we turn to the body of the conditional. First we extract everything inside <code>then</code> to a new method.

Then we turn our attention to <code>else</code>.

Let's perform the final testing.

The refactoring is complete! You can compare the old and new code, if you like.