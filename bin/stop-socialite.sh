#!/usr/bin/env bash

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

. "$bin/socialite-config.sh"

. "$bin/prepare-launch.sh"
export JVM_OPTS SOCIALITE_OPTS

echo Stopping SociaLite AppMaster 

exec hadoop jar socialite-all.jar socialite.yarn.Client stop
