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

launch_consumers bmi_guidance-telegram_adapter-input telegram_adapter
launch_consumers bmi_guidance-rule_engine-input rule_engine
launch_consumers bmi_guidance-gpt3_adapter-input gpt3_adapter

sleep 10