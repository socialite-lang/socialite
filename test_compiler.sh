#!/usr/bin/env bash

oldpwd=`pwd`
bin="`dirname "$0"`/bin"
bin=`cd "$bin"; pwd`
cd $bin
. "$bin/socialite-config.sh"

testit() {
    testSource=$1
    test=${testSource//src/}
    test=${test//.java/}
    test=${test/\//}

    test=${test//\//.}

    echo "=================================================="
    echo " Running... " $test
    echo "=================================================="

    JAVA_HEAP_MIN=-Xms256m
    JAVA_HEAP_MAX=-Xmx1024m
    cd $oldpwd
    "$JAVA" -server -ea $JAVA_HEAP_MAX $JAVA_HEAP_MIN $SOCIALITE_OPTS  -classpath "$CLASSPATH" $test
}
export -f testit

echo "Building SociaLite"
ant -q

echo
echo " Testing SociaLite compiler"
echo " Some tests may take a few minutes"
echo

cd $bin/../
for testSrc in $(find src/ -name "*Test.java"); do
   testit $testSrc
done
    
   # -exec bash -c 'testit "$0"' {} \;
