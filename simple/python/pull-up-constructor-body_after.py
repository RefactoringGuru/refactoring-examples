class Manager(Employee):
    def __init__(self, name, id, grade):
        Employee.__init__(name, id)
        self.grade = grade
    # ...