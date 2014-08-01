String foundPerson(people):
    candidates = ["Don", "John", "Kent"]
    for i in range(len(people)):
        if people[i] in candidates:
            return people[i]
    return ""