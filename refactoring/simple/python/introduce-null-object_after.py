class NullCustomer(Customer):

    def isNull(self):
        return True
    
    def getPlan(self):
        return self.NullPlan()
        
    # Some other NULL functionality.

# Replace null values with Null-object.
customer = order.customer if order.customer != None else NullCustomer()

# Use Null-object as if it's normal subclass.
plan = customer.getPlan()