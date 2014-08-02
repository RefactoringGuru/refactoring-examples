int low = DaysTempRange().GetLow();
int high = DaysTempRange().GetHigh();
bool withinPlan = plan.WithinRange(low, high);