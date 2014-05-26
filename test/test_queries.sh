#!/usr/bin/env bash

olddir=`pwd`
root="`dirname "$0"`/.."
root=`cd "$root"; pwd`
cd $root

echo "Building SociaLite"
ant -q

echo
echo

export JAVA_HEAP_MAX=-Xmx512m
export JAVA_HEAP_MIN=-Xmx64m

if [ $# -eq 1 ]; then
    file=$1
    if [[ "$file" != /* ]]; then
        file="$olddir/$1"
    fi
    echo "Running " $1
    bin/socialite $file
else
    for s in test/*.py; do
        echo "=================================================="
        echo " Running " $s
        echo "=================================================="
        bin/socialite $s
    done
fi
