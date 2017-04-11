using System;
using RefactoringGuru.Iterator.Example.Iterators;
using RefactoringGuru.Iterator.Example.Profile;
using RefactoringGuru.Iterator.Example.SocialNetworks;

namespace Iterator.Example.Spammer
{
    class SocialSpammer
    {
        public SocialNetwork network;
        public ProfileIterator iterator;

        public SocialSpammer(SocialNetwork network)
        {
            this.network = network;
        }

        public void SendSpamToFriends(String profileEmail, String message)
        {
            Console.WriteLine("\nIterating over friends...\n");
            iterator = network.GetFriendsIterator(profileEmail);
            while (iterator.HasNext())
            {
                Profile profile = iterator.GetNext();
                SendMessage(profile.GetEmail(), message);
            }
        }

        public void SendSpamToCoworkers(String profileEmail, String message)
        {
            Console.WriteLine("\nIterating over coworkers...\n");
            iterator = network.GetCoworkersIterator(profileEmail);
            while (iterator.HasNext())
            {
                Profile profile = iterator.GetNext();
                SendMessage(profile.GetEmail(), message);
            }
        }

        public void SendMessage(String email, String message)
        {
            Console.WriteLine("Sent message to: '" + email + "'. Message body: '" + message + "'");
        }
    }
}
