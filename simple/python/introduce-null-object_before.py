if customer is None:
    plan = BillingPlan.basic()
else:
    plan = customer.getPlan()