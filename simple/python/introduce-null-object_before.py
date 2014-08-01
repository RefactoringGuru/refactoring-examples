if customer == None:
    plan = BillingPlan.basic()
else:
    plan = customer.getPlan()