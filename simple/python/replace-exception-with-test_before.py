def getValueForPeriod(periodNumber):
    try:
        return values[periodNumber]
    except IndexError:
        return 0