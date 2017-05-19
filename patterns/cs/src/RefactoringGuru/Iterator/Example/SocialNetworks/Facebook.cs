using System;
using System.Collections.Generic;
using System.Threading;
using RefactoringGuru.Iterator.Example.Iterators;
using RefactoringGuru.Iterator.Example.Profiles;

namespace RefactoringGuru.Iterator.Example.SocialNetworks
{
    class Facebook : SocialNetwork
    {
        private List<Profile> profiles;

        public Facebook(List<Profile> cache)
        {
            if (cache != null)
            {
                this.profiles = cache;
            }
            else
            {
                this.profiles = new List<Profile>();
            }
        }

        public Profile RequestProfileFromFacebook(String profileEmail)
        {
            // EN: Here would be a POST request to one of the Facebook API
            // endpoints. Instead, we emulates long network connection, which
            // you would expect in the real life...
            // 
            // RU: Здесь бы был POST запрос к одному из адресов API Facebook. Но
            // вместо этого мы эмулируем долгое сетевое соединение, прямо как в
            // реальной жизни...
            SimulateNetworkLatency();
            Console.WriteLine("Facebook: Loading profile '" + profileEmail + "' over the network...");

            // EN: ...and return test data.
            // 
            // RU: ...и возвращаем тестовые данные.
            return FindProfile(profileEmail);
        }

        public List<String> RequestProfileFriendsFromFacebook(String profileEmail, String contactType)
        {
            // EN: Here would be a POST request to one of the Facebook API
            // endpoints. Instead, we emulates long network connection, which
            // you would expect in the real life...
            // 
            // RU: Здесь бы был POST запрос к одному из адресов API Facebook. Но
            // вместо этого мы эмулируем долгое сетевое соединение, прямо как в
            // реальной жизни...
            SimulateNetworkLatency();
            Console.WriteLine("Facebook: Loading '" + contactType + "' list of '" + profileEmail + "' over the network...");

            // EN: ...and return test data.
            // 
            // RU: ...и возвращаем тестовые данные.
            Profile profile = FindProfile(profileEmail);
            if (profile != null)
            {
                return profile.GetContacts(contactType);
            }
            return null;
        }

        private Profile FindProfile(String profileEmail)
        {
            foreach (var profile in profiles)
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
            return new FacebookIterator(this, "friends", profileEmail);
        }

        public ProfileIterator GetCoworkersIterator(string profileEmail)
        {
            return new FacebookIterator(this, "coworkers", profileEmail);
        }
    }
}
