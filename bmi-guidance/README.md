# BMI Health Check

This project shows how you can easily train machine learning models from human input to extend human capabilities and reduce the workload of experts handling a high rate of incoming requests.

### Use case
In this demo, we implemented a health assistant that helps you identify potential obesity issues in children and adolescents. 
Apart from being a real-life problem, it's interesting because we modelled two different personas whose relationship we're trying to mediate: the patients sending the requests, and the doctor answering them.


We approach this problem by introducing two types of agents handling the specific needs of these two respective types of users: the Patient Agent makes sure the patient checks their child's weight regularly, and gets expert opinion on it as soon as possible after reporting it.
The Doctor Agent communicates the incoming patients' requests to the doctor, but also wants to control his/her workload and make sure he/she is not overloaded with work. That's why we add an extra machine learning component that gradually learns from the doctor's responses.


With time, the model itself will take over most of the workload, leaving only the "trickier" edge cases for the doctor to handle. 
Ideally, this is how we want to use ML models in general: not to substitute humans, but to extend their capabilities by automating the straightfowrard, repetitive work, and allowing them to focus more on the creative, more demanding tasks.


### Running the demo
To run, first set your [TELEGRAM_BOT_TOKEN](https://core.telegram.org/bots) and [OPENAI_API_KEY](https://beta.openai.com/account/api-keys) in `.env`. Now just start up the demo with `forge run`.

The communication with all users goes on via Telegram Messenger, and we use GPT-3 for creativity in proactive prompts for patients. There are just a couple of things to keep in mind when running this demo: 
for the sake of convenience, the first user to register automatically becomes the doctor, and every subsequent user that sends a message on Telegram is welcomed as a patient. 
There is no limit to the number of patients that can join. You can see this logic in `Runner.java`.

In the beginning, the patients receive a story about the child whose weight they want to check. They send weight values to the doctor, and the doctor receives them and answers whether the child of that age, height and weight is obese or not.
All the while, these data are sent as training input to the AutoML component, until the trained model is confident enough to start taking over the patient requests.

To simulate the doctor's workload (and speed up the training of the model), you can also simulate patient activity: in a separate terminal, run `forge run-service robo-patient`. This creates fake patients that constantly send requests to the doctor, creating a heavy workload.
Don't forget to stop this run as well with `CTRL+C` when stopping the demo.


Another thing you can try out is loading in synthetic training data, to simulate the passing of time and help your model learn faster. 
In another terminal, run `forge import-data`, optionally adding an integer parameter for the number of data points to load (maximum is 7424, default is 115). Apart from that, you can also change the file the data are loaded from (the default file is `services/model_trainer/bmi_data.csv`). Notice that these are positional arguments.

That's everything you need to know to run this demo. Enjoy!

