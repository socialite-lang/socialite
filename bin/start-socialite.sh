#!/usr/bin/env bash

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

. "$bin/socialite-config.sh"

. "$bin/prepare-launch.sh"
export JVM_OPTS SOCIALITE_OPTS

jarfile="$bin/../socialite-all.jar"
if [ ! -f "$jarfile" ]; then
  echo Run "ant alljar" before starting Socialite AppMaster
  exit 1
fi

if [ ! -f "${HADOOP_PREFIX}/bin/yarn" ] ; then
  echo Edit conf/socialite-env.sh to setup HADOOP_HOME before starting Socialite AppMaster
  exit 1
fi

echo Starting SociaLite AppMaster on Yarn

exec hadoop jar socialite-all.jar socialite.yarn.Client start "$jarfile"
