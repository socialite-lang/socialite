#!/usr/bin/env python

import sys

# refer to http://bluesock.org/~willg/dev/ansi.html for following:
GREEN = "\033[0;32m"
WHITE = "\033[0;37m"
ORANGE= "\033[0;33m"
RED = "\033[0;31m"

BLUE = "\033[0;34m"
CYAN = "\033[0;36m"

WHITE_ON_RED = "\033[37;41m"


ENDC = "\033[0m"

global prefix
prefix=""
def main():
    while True:
        line=sys.stdin.readline()
        if len(line)==0: break
        line=line.strip("\n")
        line=prefix+line
        line = colorize(line)
        print line

def colorize(line):
    colorline=line
    lineCap = line.upper()
    if lineCap.find("EXCEPTION")>=0:
        colorline=RED+line  # no ENDC since we want the entire exception stack to be in red

    if lineCap.find("stopping")>=0:
        colorline=ORANGE+line+ENDC

    # colorize by the log-level
    if lineCap.find(" DEBUG ")>=0:
        colorline=GREEN+line+ENDC
    if lineCap.find(" INFO ")>=0:
        colorline=WHITE+line+ENDC
    if lineCap.find(" WARN ")>=0:
        colorline=ORANGE+line+ENDC
    if lineCap.find(" ERROR ")>=0:
        colorline=RED+line+ENDC
    if lineCap.find(" FATAL ")>=0:
        colorline=WHITE_ON_RED+line+ENDC

    if lineCap.find(" NOTICE ")>=0:
        colorline=CYAN+line+ENDC

    return colorline

if __name__=='__main__':
    if len(sys.argv)>1:
        prefix = sys.argv[1]
    main()
