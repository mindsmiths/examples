# Hi, I'm Pascal!

This project demonstrates an easy way how you can process and learn from different signals in the platform.
We'll use this feature to power up Pascal, who supports the user in learning basic multiplication.

# Our Demo

In this demo we use a simple single neuron-like structure to estimate the performance of the user trying to practice multiplication.
From the system side, we have an Instructor agent creating multiplication tasks and sending them to the user. 
Additionally, there is the Neuron - a model that integrates certain signals 
(e.g. whether the user answered correctly, how long they took with the answer etc.) and changes its output value by charging and discharging accordingly using some simple function.

What this means is that the agent tracks a bunch of signals and modifies its behaviour based on the user's performance on the tasks. 
This is the simplest form of learning, but you can easily extend it with more complex logic.

# Running the demo
Set the `ARMORY_SITE_URL=` variable in your `.env` file.

> NOTE: Your Armory link is `http://workspace-ms-0000000000.sandbox.mindsmiths.io`, with the `0000000000` replaced by the digits in your workspace link.

Just run it with `forge run` and go to your armory link, and enjoy solving the multiplication tasks your Instructor gives you!
