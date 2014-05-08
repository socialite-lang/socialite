#!/usr/bin/env bash

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

. "$bin/socialite-config.sh"

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

tail_logs() {
    masterOrWorker=$1
    if [ "$masterOrWorker" == "master" ]; then
        HOSTLIST="$SOCIALITE_CONF_DIR/master"
        kind="master"
    else
        HOSTLIST="$SOCIALITE_CONF_DIR/slaves"
        kind="worker"
    fi

    for remote in `cat "$HOSTLIST"|sed "s/#.*$//;/^$/d"`; do
        ssh $remote "tail ${bin}/../logs/socialite-${kind}-*.log" \
          2>&1 |${bin}/colorlog.py "${remote}:"

        if [ "$masterOrWorker" = "master" ]; then
            break
        fi
        echo
    done


}

echo
echo "* Master node logs:"
tail_logs "master"

echo
echo "* Worker nodes logs"
tail_logs "workers"
