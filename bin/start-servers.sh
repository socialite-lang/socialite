#!/usr/bin/env bash

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

. "$bin/socialite-config.sh"

echo "Starting master node"
"$bin"/socialite-daemons.sh master start
sleep 0.1
echo "Starting worker nodes"
"$bin"/socialite-daemons.sh worker start

#"$bin/remote.sh" master bash -c 'tail -n +1 -F ../logs/socialite-master*.log | { sed "/Ready/ q" && kill $$ ;}'
