
def toPyObj(scalarVal):
    if scalarVal.num != None:
        return scalarVal.num
    elif scalarVal.real != None:
        return scalarVal.real
    else:
        return scalarVal.str
    
def translate(ttuple):
    values = []
    if ttuple.colValMap:
        map = ttuple.colValMap
        for i in xrange(len(map.keys())):
            values.append(toPyObj(map[i]))
    else:
        values.append(toPyObj(ttuple.col0))
        if ttuple.col1:
            values.append(toPyObj(ttuple.col1))
        if ttuple.col2:
            values.append(toPyObj(ttuple.col2))

    if len(values) == 1:
        return values[0]
    else:
        return tuple(values)


