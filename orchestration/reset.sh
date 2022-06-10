#!/bin/bash

ids=()

launch_consumers() {
    for i in {0..10}
    do
        forge consume_message -t $1 -g $2 &
        ids+=($!)
    done
}

kill_consumers() {
    for id_ in "${ids[@]}"
    do
        kill $id_
    done
    exit
}

trap kill_consumers EXIT

launch_consumers orchestration-telegram_adapter-input telegram_adapter
launch_consumers orchestration-rule_engine-input rule_engine

sleep 10