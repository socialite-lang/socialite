#!/usr/bin/env bash

echo "Building SociaLite"
ant -q

echo
echo

for s in test/*.py; do
    echo "=================================================="
    echo " Running " $s
    echo "=================================================="
    bin/socialite $s

done
