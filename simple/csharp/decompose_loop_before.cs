    void printProperties(IList users) {
        for (int i = 0; i < users.size(); i++) {
            string result = null;
            result += users.get(i).getName();
            result += users.get(i).getage();
            Console.WriteLine(result);

            // ...
        }
    }
