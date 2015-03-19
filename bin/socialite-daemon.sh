#!/usr/bin/env bash

usage="Usage: socialite-daemon.sh (master|worker) (start|stop)"

# if no args specified, show usage
if [ $# -lt 2 ]; then
  echo $usage
  exit 1
fi

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

masterOrWorker=$1
shift
startOrStop=$1
shift

. "$bin/socialite-config.sh"

socialite_rotate_log() {
    log=$1;
    num=10;
    # XXX either $log or $log.0 can exist, not both
    if [ -f "$log" -o -f "$log".0 ]; then # do rotate
        while [ $num -ge 1 ] ; do
            prev=`expr $num - 1`
            [ -f "$log.$prev" ] && mv "$log.$prev" "$log.$num"
            num=$prev
        done
        ! [ -f "$log" ] ||
            mv "$log" "$log.1"
    fi
}


# XXX: for debugging!  HACK!!
if [ "x$masterOrWorker" == "xmaster" ]; then
export SOCIALITE_OUTPUT_DIR="${SOCIALITE_PREFIX}/gen_master"
else
export SOCIALITE_OUTPUT_DIR="${SOCIALITE_PREFIX}/gen_worker"
fi


if [ "$masterOrWorker" != "master" ] && [ "$masterOrWorker" != "worker" ]; then
    echo $usage
    exit 1
fi

if [ "${SOCIALITE_PID_DIR}" = "" ]; then
    SOCIALITE_PID_DIR=/tmp
fi

pid="${SOCIALITE_PID_DIR}/socialite-$masterOrWorker.pid"
logfile="socialite-$masterOrWorker-$HOSTNAME.log"
log="${SOCIALITE_LOG_DIR}/$logfile"
SOCIALITE_OPTS="$SOCIALITE_OPTS -Dsocialite.log.dir=$SOCIALITE_LOG_DIR"
SOCIALITE_OPTS="$SOCIALITE_OPTS -Dsocialite.log.file=$logfile"
export SOCIALITE_OPTS

case $startOrStop in
  (start)
    mkdir -p "$SOCIALITE_PID_DIR"

    if [ -f $pid ]; then
      if kill -0 `cat $pid` > /dev/null 2>&1; then
        echo $masterOrWorker running as process `cat $pid`. Stop it first.
        exit 1
      fi
    fi

    socialite_rotate_log $log
    nohup "${SOCIALITE_HOME}/bin/launcher" $masterOrWorker > "$log" 2>&1 < /dev/null &
    echo $! > $pid
    sleep 1.5
    head "$log"
     
    ;;

  (stop)
    if [ -f $pid ]; then
      if kill -0 `cat $pid` > /dev/null 2>&1; then
        echo stopping $masterOrWorker
        kill `cat $pid`
      else
        echo no $masterOrWorker to stop
      fi
    else
      echo no $masterOrWorker to stop
    fi
    ;;
  
  (kill)
    if [ -f $pid ]; then
      if kill -0 `cat $pid` > /dev/null 2>&1; then
        echo killing $masterOrWorker
        kill -9 `cat $pid`
      else
        echo no $masterOrWorker to stop
      fi
    else
      echo no $masterOrWorker to stop
    fi
    ;;
 
  (*)
    echo $usage
    exit 1
    ;;
esac
