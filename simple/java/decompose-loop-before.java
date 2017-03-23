void printProperties(List users) {
    for (int i = 0; i < users.size(); i++) {
        String result = null;
        result += users.get(i).getName();
        result += users.get(i).getage();
        System.out.println(result);

        // ...
    }
}
