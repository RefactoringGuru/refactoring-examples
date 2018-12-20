<?php
if ($customer === null)
  $plan = BillingPlan::basic();
else
  $plan = $customer->getPlan();