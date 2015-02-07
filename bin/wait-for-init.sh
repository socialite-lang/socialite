#!/usr/bin/env bash

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

. "$bin/socialite-config.sh"

sh -c 'tail -n +1 -F ../logs/socialite-master*.log | { sed "/Ready/ q" && kill $$ ;}'
