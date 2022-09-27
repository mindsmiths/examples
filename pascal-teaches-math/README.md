# Hi, I'm Pascal!

This demo shows a simple learning mechanism you can use out-of-the-box in any project on the platform.
Here we'll use this learning feature to power up Pascal: an agent who supports the user in learning basic multiplication.

# Our Demo

In this demo we use a simple neuron-like structure to estimate the user's level of knowledge of the multiplication table.
We call this structure the neuro integrator: it collects and integrates different incoming signals into a single vaule,
that subsequently affects the system behaviour and adapts it to the needs of that particular user.


From the system side, we have an Instructor agent creating multiplication tasks and sending them to the user. 
Additionally, there is the Neuron - a model that integrates received signals and changes its output value using a simple integration function.


Looking at the implementation itself, each user gets 10 simple Neuron models that charge or discharge based on the user's performance on the multiplication tasks.
Every time the user submits an answer, the Neurons for the two numbers in the task are either charged (if the answer is correct) or discharged (if it's incorrect).
In addition to that, we track how long the user took to solve the task and integrate that as another signal when calculating the Neuron value.
The numbers whose Neurons have lower values are more likely to get picked for the next task, since these are the numbers the user should assumedly practice multiplying with.

Additionally, the Neurons gradually decay over time, so the numbers that haven't been practiced for a while can again come into play, 
while keeping the values more or less contained to a specific range.

This is the simplest form of learning, but it's relatively powerful and you'll see the effects quickly.
The approach can also be easily extended  with more complex logic for more elaborate use cases.

# Running the demo
Just run it with `forge run` and go to your armory link, and enjoy solving the multiplication tasks your Instructor gives you!

> NOTE: We're using Armory in this demo. After you start the platform run, you access Armory at `https://8000.workspace-ms-0000000000.sandbox.mindsmiths.io`, 
> with the `0000000000` replaced by the digits in your workspace link.
 
 
> NOTE: You can track the live Neuron values by setting log level to debug in the `config/config.yaml` file: 
> ```
> global:
>  ...
>  env:
>    LOG_LEVEL: DEBUG
> ```