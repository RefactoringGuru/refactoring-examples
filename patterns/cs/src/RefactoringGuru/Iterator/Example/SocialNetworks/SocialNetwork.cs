using System;
using RefactoringGuru.Iterator.Example.Iterators;

namespace RefactoringGuru.Iterator.Example.SocialNetworks
{
    interface SocialNetwork
    {
        ProfileIterator GetFriendsIterator(String profileEmail);

        ProfileIterator GetCoworkersIterator(String profileEmail);
    }
}