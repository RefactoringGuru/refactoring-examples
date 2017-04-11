using System;
using System.Collections.Generic;
using RefactoringGuru.Iterator.Example.SocialNetworks;

namespace RefactoringGuru.Iterator.Example.Iterators
{
    class FacebookIterator : ProfileIterator
    {
        private Facebook facebook;
        private String type;
        private String email;
        private int currentPosition = 0;
        private List<String> emails = new List<String>();
        private List<Profile.Profile> profiles = new List<Profile.Profile>();

        public FacebookIterator(Facebook facebook, String type, String email)
        {
            this.facebook = facebook;
            this.type = type;
            this.email = email;
        }

        private void LazyLoad()
        {
            if (emails.Count == 0)
            {
                List<String> profiles = facebook.RequestProfileFriendsFromFacebook(this.email, this.type);
                foreach (var profile in profiles)
                {
                    this.emails.Add(profile);
                    this.profiles.Add(null);
                }
            }
        }

        public bool HasNext()
        {
            LazyLoad();
            return currentPosition < emails.Count;
        }

        public Profile.Profile GetNext()
        {
            if (!HasNext())
            {
                return null;
            }

            String friendEmail = emails[currentPosition];
            Profile.Profile friendProfile = profiles[currentPosition];
            if (friendProfile == null)
            {
                friendProfile = facebook.RequestProfileFromFacebook(friendEmail);
                profiles.Insert(currentPosition, friendProfile);
            }
            currentPosition++;
            return friendProfile;
        }

        public void Reset()
        {
            currentPosition = 0;
        }
    }
}
