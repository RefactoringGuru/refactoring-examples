As you see, our method has a conditional with repeating code in different branches.

Try to move this code outside the conditional. Start with <code>sendEmail()</code>.

The call to this function is at the end of both branches of the conditional operator. This means that we can move the call after the condition.

Let's run the autotests.

Now try to move <code>validateEmailAddress()</code>. These calls are in different places so it is worth thinking about whether to move them at all. In our case, validation can be performed anywhere but preferably closer to the beginning of the method. So let's move it there.

Let's start the final testing.

Now refactoring is complete. If you like, you can compare the old and new code.