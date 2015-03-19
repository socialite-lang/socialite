#!/usr/bin/env bash

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

. "$bin/socialite-config.sh"

run_at_master() {
    local script=$1; shift
    "$bin/remote.sh" master bash -c '
    SOCIALITE_HOME=$1; shift
    cd "$SOCIALITE_HOME"
    '"$script"'
    ' socialite-daemons.sh "$SOCIALITE_HOME" "$@"
}

# move previous master log on the side to make the later waiting work
run_at_master '
for log in logs/socialite-master*.log; do
    mv -f $log $log.0
done
'

echo "Starting master node"
"$bin"/socialite-daemons.sh master start &
sleep 0.1
echo "Starting worker nodes"
"$bin"/socialite-daemons.sh worker start &

# wait for master log to print the Ready line
run_at_master '
tail -n +1 -F logs/socialite-master-$HOSTNAME.log 2>/dev/null |
{
    sed "/MasterNode:.* Ready/q"
    # XXX need to kill the process group of this shell, or tail/sed pair will hang
    kill -TERM $(ps -o pgid= $$ | sed s/^/-/)
}
'
