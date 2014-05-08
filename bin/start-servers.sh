#!/usr/bin/env bash

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

. "$bin/socialite-config.sh"

echo "Starting master node"
"$bin"/socialite-daemons.sh master start
sleep 0.1
echo "Starting worker nodes"
"$bin"/socialite-daemons.sh worker start
