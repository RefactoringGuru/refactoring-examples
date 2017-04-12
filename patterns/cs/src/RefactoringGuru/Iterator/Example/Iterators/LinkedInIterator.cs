using System;
using System.Collections.Generic;
using RefactoringGuru.Iterator.Example.Profiles;
using RefactoringGuru.Iterator.Example.SocialNetworks;

namespace RefactoringGuru.Iterator.Example.Iterators
{
    class LinkedInIterator : ProfileIterator
    {
        private LinkedIn linkedIn;
        private String type;
        private String email;
        private int currentPosition = 0;
        private List<String> emails = new List<string>();
        private List<Profile> contacts = new List<Profile>();

        public LinkedInIterator(LinkedIn linkedIn, String type, String email)
        {
            this.linkedIn = linkedIn;
            this.type = type;
            this.email = email;
        }

        private void LazyLoad()
        {
            if (emails.Count == 0)
            {
                List<String> profiles = linkedIn.RequestRelatedContactsFromLinkedInAPI(this.email, this.type);
                foreach (var profile in profiles)
                {
                    this.emails.Add(profile);
                    this.contacts.Add(null);
                }
            }
        }

        public bool HasNext()
        {
            LazyLoad();
            return currentPosition < emails.Count;
        }

        public Profile GetNext()
        {
            if (!HasNext())
            {
                return null;
            }

            String friendEmail = emails[currentPosition];
            Profile friendContact = contacts[currentPosition];
            if (friendContact == null)
            {
                friendContact = linkedIn.RequestContactInfoFromLinkedInAPI(friendEmail);
                contacts.Insert(currentPosition, friendContact);
            }
            currentPosition++;
            return friendContact;
        }

        public void Reset()
        {
            currentPosition = 0;
        }
    }
}
