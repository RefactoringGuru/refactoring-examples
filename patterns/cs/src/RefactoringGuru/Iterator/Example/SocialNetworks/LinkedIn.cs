using System;
using System.Collections.Generic;
using System.Threading;
using RefactoringGuru.Iterator.Example.Iterators;
using RefactoringGuru.Iterator.Example.Profiles;

namespace RefactoringGuru.Iterator.Example.SocialNetworks
{
    class LinkedIn : SocialNetwork
    {
        private List<Profile> contacts;

        public LinkedIn(List<Profile> cache)
        {
            if (cache != null)
            {
                this.contacts = cache;
            }
            else
            {
                this.contacts = new List<Profile>();
            }
        }

        public Profile RequestContactInfoFromLinkedInAPI(String profileEmail)
        {
            // EN: Here would be a POST request to one of the LinkedIn API
            // endpoints. Instead, we emulates long network connection, which
            // you would expect in the real life...
            // 
            // RU: Здесь бы был POST запрос к одному из адресов API LinkedIn. Но
            // вместо этого мы эмулируем долгое сетевое соединение, прямо как в
            // реальной жизни...
            SimulateNetworkLatency();
            Console.WriteLine("LinkedIn: Loading profile '" + profileEmail + "' over the network...");

            // EN: ...and return test data.
            // 
            // RU: ...и возвращаем тестовые данные.
            return FindContact(profileEmail);
        }

        public List<String> RequestRelatedContactsFromLinkedInAPI(String profileEmail, String contactType)
        {
            // EN: Here would be a POST request to one of the LinkedIn API
            // endpoints. Instead, we emulates long network connection, which
            // you would expect in the real life.
            // 
            // RU: Здесь бы был POST запрос к одному из адресов API LinkedIn. Но
            // вместо этого мы эмулируем долгое сетевое соединение, прямо как в
            // реальной жизни...
            SimulateNetworkLatency();
            Console.WriteLine("LinkedIn: Loading '" + contactType + "' list of '" + profileEmail + "' over the network...");

            // EN: ...and return test data.
            // 
            // RU: ...и возвращаем тестовые данные.
            Profile profile = FindContact(profileEmail);
            if (profile != null)
            {
                return profile.GetContacts(contactType);
            }
            return null;
        }

        private Profile FindContact(String profileEmail)
        {
            foreach (var profile in contacts)
            {
                if (profile.GetEmail().Equals(profileEmail))
                {
                    return profile;
                }
            }
            return null;
        }

        private void SimulateNetworkLatency()
        {
            Thread.Sleep(2500);
        }

        public ProfileIterator GetFriendsIterator(string profileEmail)
        {
            return new LinkedInIterator(this, "friends", profileEmail);
        }

        public ProfileIterator GetCoworkersIterator(string profileEmail)
        {
            return new LinkedInIterator(this, "coworkers", profileEmail);
        }
    }
}
