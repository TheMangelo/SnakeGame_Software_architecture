# SA Project
This is the frontend of the Party Snake Android game. It is created in the context of the group project of TDT4240 Software Architecture.

## State of the Project
Because of several reasons, the project was by no means completed. Several pieces of functionality are still missing, and multiple bugs persist. Currently, the master branch contains a somewhat functioning game based on the code that was completed till now.

## Compilation
As this is a Java project with Gradle, you should be able to easily compile and run the project by cloning the repo and compiling the project locally. By using Android Studio, you can clone the project and run the game with the desktop launcher. Note that though it is possible to run the project on Android or the Android emulator, it is not recommended to run the game this way as the results are very buggy given the code's current state.

## Structure
Firstly, the overall file structure looks as follows.
```
.
├── android
├── core
├── desktop
├── html
├── ios
└── Gradle Scripts
```

The interesting part of the repo, the code we have writted ourselves, can be found in core. The structure looks like this:
```
.
├── core
│   └── java
│       └── com.group9.partysnake
│           ├── gameElements
│           │   ├── (Class) Apple
│           │   ├── (Class) OnlineSnake
│           │   ├── (Class) Snake
│           │   └── (Abstract class) SuperEatable
│           ├── gamestate
│           │   ├── (Class) GameStateManager
│           │   ├── (Class) LoginState
│           │   ├── (Class) MenuState
│           │   ├── (Class) OnlineState
│           │   ├── (Class) ScoreState
│           │   ├── (Class) SettingsState
│           │   ├── (Class) SinglePlayerState
│           │   └── (Abstract class) State
│           ├── (Class) PartySnake
│           └── ...
└── ...
```

Additionally, it may be interesting to know that all of the project's graphical assets can be found in the assets folder under android.
```
.
├── android
│   ├── assets
│   │   └── ...
│   └── ...
└── ...
```

Lastly, to provide an overview of the logic, a class diagram is shown below.

![Class diagram](./classDiagram.jpg)
