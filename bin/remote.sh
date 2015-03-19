#!/usr/bin/env bash

usage="Usage: remote.sh (master|worker) command..."

# if no args specified, show usage
if [ $# -lt 2 ]; then
  echo $usage
  exit 1
fi

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

. "$bin/socialite-config.sh"

masterOrWorker=$1
shift
if [ "$masterOrWorker" = "master" ]; then
    HOSTLIST="$SOCIALITE_CONF_DIR/master"
elif [ "$masterOrWorker" = "worker" ]; then
    HOSTLIST="$SOCIALITE_CONF_DIR/slaves"
else
    echo $usage
    exit 1
fi

#set -x
esc() {
    local s="" a=
    for a; do
        s+=" '"
        s+=${a//\'/\'\\\'\'}
        s+="'"
    done
    echo "$s"
}

for remote in `cat "$HOSTLIST"|sed "s/#.*$//;/^$/d"`; do
    ssh $remote "$(esc env SOCIALITE_HOSTNAME="$remote" "$@")" \
      2>&1 |${bin}/colorlog.py "${remote}:" &
    sleep 0.001

    if [ "$masterOrWorker" = "master" ]; then
        break
    fi
done

wait
