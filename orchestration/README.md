# Orchestration

This project shows a simple way to perform machine to machine (M2M) communication. This allows you to orchestrate different devices remotely through the platform.

To do this, you need to set up a connection between the external devices and the platform run in the cloud. One way to do this is using [ngrok](https://ngrok.com/).


### Our demo
In this demo, we show this remote control by simulating orchestration of software robots: WallE and Eve. To really showcase the power of the demo, you can run the platform normally in the cloud, while running the robots locally on your machine.
This is just a simple example, but the starting and stopping of robots can be substituted for any software you want.
Another component of the demo is the option of human intervention. The use case is simple: there are software robots performing tasks on a remote machine. 
The robots can fail at performing the tasks for various reasons. Should this happen, the platform should try to automatically restart them. If this fails, the platform can notify the person in charge that they should take a look what's going on.
If this person cannot do anything about it, their superior is notified as a final resort.


### Running the demo
To run, open the terminal in your coding environment and start the platform with `forge run`. Don't forget to set the environment variable to connect to your telegram bot.

Now open two more terminals. These will be used for port access with ngrok: 
in the first one write `ngrok http 8000`, and in the second `ngrok http 8765`. 
This starts two ngrok sessions, with status online.


You are now ready to move aheat to the second part of the setup. Download the project to your local machine. 
Now go to `robots/urls.py` and connect to the platform: from the terminal running ngrok for port 8000, copy the first link in `Forwarding` (starts with `http://`) to ```settings.FORGE_API_URL =```.
In the terminal running ngrok for 8765, copy the first link in `Forwarding` that starts with `http://`, but this time without the `http://`-part. Paste it here: ```FORGE_SOCKET_URL =```

Now you need to run the robots! For that open five terminals in project root and run the following (don't forget to activate your virtual environment if you need to!):
- ```python robots/runner.py Eve```:  establishes the connection between robot Eve and the Platform
- ```python robots/task_scheduler.py```: creates tasks for robot Eve to process
- ```python robots/Eve.py```: processes scheduled tasks from `tasks` directory and moves them to `done`
- ```python robots/runner.py WallE```: establishes the connection between robot WallE and the Platform 
- ```python robots/WallE.py```: removes the tasks that Eve completed from the `done` directory.

You can messsage your telegram bot. You can also connect two users and put one of them in the superior role by sending a message "super admin".

Congratulations, you've set up your first remote machine control! Have fun!
