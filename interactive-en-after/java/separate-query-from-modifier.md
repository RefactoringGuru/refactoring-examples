Let's consider <i>Separate Query from Modifier</i> using a security system class as our example. The class has a method that tells us the name of a violator and sends a warning.

The main problem with this method is that it is used for two different purposes.

First, it finds and returns a list of names that are then used for different purposes.

An example of such use can be found in the <code>checkSecurity</code> method.

But that's not all. The method also sends a message about the people found.

With this approach, even if we only need to get a list of names we take the risk of accidentally sending messages. This refactoring technique will save us from that risk. In our case, searching for people will be the "query" and sending messages will be the "modifier".

To separate the query from the modifier, first create an appropriate query that returns the same value as the original method but does not lead to side effects.

Then, one by one, replace all cases of <code>return</code> in the original method with calls for the new query.

Now change all the methods from which reference is made so that two calls occur in them: first for the modifier, then for the query.

Once this has been completed for all calls, we remove the return code from the modifier.

We should also now change the name of the original method for consistency.

Of course, the result contains a great deal of duplicate code since the modifier still uses the body of the query. But now we can apply <a href="/substitute-algorithm">Substitute Algorithm</a> without the risk of breaking any other code.

Let's perform the final compilation and testing.

The refactoring is complete! You can compare the old and new code if you like.