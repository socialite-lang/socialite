#!/usr/bin/env bash

usage="Usage: socialite-daemons.sh (master|worker) (start|stop)"

# if no args specified, show usage
if [ $# -lt 2 ]; then
  echo $usage
  exit 1
fi

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`
. "$bin/socialite-config.sh"

masterOrWorker=$1
exec "$bin/remote.sh" $masterOrWorker bash -c '
SOCIALITE_HOME=$1; shift
bin=$1; shift
cd "$SOCIALITE_HOME"
"$bin/socialite-daemon.sh" "$@"
' socialite-daemons.sh "$SOCIALITE_HOME" "$bin" "$@"
