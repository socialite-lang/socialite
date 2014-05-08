#!/usr/bin/env bash

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

. "$bin/socialite-config.sh"

"$bin"/socialite-daemons.sh master kill
"$bin"/socialite-daemons.sh worker kill
