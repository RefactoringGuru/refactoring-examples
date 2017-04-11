using System;
using System.Collections.Generic;

namespace RefactoringGuru.Iterator.Example.Profile
{
    class Profile
    {
        private String name;
        private String email;
        private Dictionary<String, List<String>> contacts = new Dictionary<string, List<string>>();

        public Profile(String email, String name, String[] contacts)
        {
            this.email = email;
            this.name = name;

            // Parse contact list from a set of "friend:email@gmail.com" pairs.
            foreach (var contact in contacts)
            {
                String[] parts = contact.Split(':');
                String contactType = "friend", contactEmail;
                if (parts.Length == 1)
                {
                    contactEmail = parts[0];
                }
                else
                {
                    contactType = parts[0];
                    contactEmail = parts[1];
                }
                if (!this.contacts.ContainsKey(contactType))
                {
                    this.contacts.Add(contactType, new List<String>());
                }
                this.contacts[contactType].Add(contactEmail);
            }
        }

        public String GetEmail()
        {
            return email;
        }

        public String GetName()
        {
            return name;
        }

        public List<String> GetContacts(String contactType)
        {
            if (!contacts.ContainsKey(contactType))
            {
                contacts.Add(contactType, new List<String>());
            }
            return contacts[contactType];
        }
    }
}
