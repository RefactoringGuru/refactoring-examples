using System;
using System.Collections.Generic;
using RefactoringGuru.Iterator.Example.Profiles;
using RefactoringGuru.Iterator.Example.SocialNetworks;
using RefactoringGuru.Iterator.Example.Spammer;

namespace RefactoringGuru.Iterator
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Please specify social network to target spam tool (default:Facebook):");
            Console.WriteLine("1. Facebook");
            Console.WriteLine("2. LinkedIn");
            String choice = Console.ReadLine();

            SocialNetwork network;
            if (choice.Equals("2"))
            {
                network = new LinkedIn(CreateTestProfiles());
            }
            else
            {
                network = new Facebook(CreateTestProfiles());
            }

            SocialSpammer spammer = new SocialSpammer(network);
            spammer.SendSpamToFriends("anna.smith@bing.com",
                    "Hey! This is Anna's friend Josh. Can you do me a favor and like this post [link]?");
            spammer.SendSpamToCoworkers("anna.smith@bing.com",
                    "Hey! This is Anna's boss Jason. Anna told me you would ne interested in [link].");

            Console.ReadKey();
        }

        public static List<Profile> CreateTestProfiles()
        {
            List<Profile> data = new List<Profile>();
            String[] args = new String[] { "anna.smith@bing.com", "Anna Smith", "friends:mad_max@ya.com", "friends:catwoman@yahoo.com", "coworkers:sam@amazon.com" };
            data.Add(new Profile("anna.smith@bing.com", "Anna Smith", args));
            args = new String[] { "friends:anna.smith@bing.com", "coworkers:sam@amazon.com" };
            data.Add(new Profile("mad_max@ya.com", "Maximilian", args));
            args = new String[] { "coworkers:avanger@ukr.net" };
            data.Add(new Profile("bill@microsoft.eu", "Billie", args));
            args = new String[] { "coworkers:bill@microsoft.eu" };
            data.Add(new Profile("avanger@ukr.net", "John Day", args));
            args = new String[] { "coworkers:anna.smith@bing.com", "coworkers:mad_max@ya.com", "friends:catwoman@yahoo.com" };
            data.Add(new Profile("sam@amazon.com", "Sam Kitting", args));
            args = new String[] { "friends:anna.smith@bing.com", "friends:sam@amazon.com" };
            data.Add(new Profile("catwoman@yahoo.com", "Liza", args));
            return data;
        }
    }
}
