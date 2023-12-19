# Java Media Transport Controls (JMTC)
## Overview
JMTC is a lightweight Java library that integrates a Java application with the Media Transport Controls and Now Playing elements of the operating System. The properties of the currently playing Media can easily be set and the library defines callbacks for the Media Keys(global) and the UI elements.
## Features
- Title, Artist, Album Title, Album Artist, Playtime and Cover Art fields supported
- Seeking, Play/Pause, Stop, Volume, Shuffle, Rate and Looping supported
- simple callback syntax via Lambda Functions
- easily integrable into projects using Compose Multiplatform, JavaFX, Swing, TornadoFX, etc. 
### Planned Features
- Logging
- graceful Error Handling
- Support for different Media Types (Video, Photo) ???
## Supported Platforms
- [x] Windows (via [SystemMediaTransportControls](https://learn.microsoft.com/de-de/uwp/api/windows.media.systemmediatransportcontrols?view=winrt-22621))
- [x] Linux (via [MPRIS 2](https://specifications.freedesktop.org/mpris-spec/2.2/) on D-Bus)
- [ ] Mac (TODO currently I have no test system)
## Getting Started
### Prerequisites
- Java 8 or later
### Installation
#### Maven Repaository
**TODO** This Library is not yet available on a Maven repository.
#### Maven Local
Download this repository via git with:
```bash
git clone https://github.com/Selemba1000/JavaMediaTransportControls.git
```
or download as zip and extract it.
Then load the project into your IDE of choice and execute Task publishToMavenLocal.
Then add the following to your build.gradle:
```groovy
repositories {
    mavenLocal()
}
dependencies {
    implementation 'io.github.selemba1000:jmtc:1.0.0'
}
```
### Usage
To start with the Library first get an instance of JMTC:
```java
JMTC jmtc = JMTC.getInstance(
        new JMTCSettings("example-player-name","example-player-desktop-file")
);
```
Then you can set all parameters via this instance, for example:
```java
JMTCCallbacks callbacks = new JMTCCallbacks();
callbacks.onPlay = () -> {
    control.setPlayingState(JMTCPlayingState.PLAYING);
};
callbacks.onPause = () -> control.setPlayingState(JMTCPlayingState.PAUSED);
jmtc.setEnabledButtons(
    new JMTCEnabledButtons(
        true,
        true,
        true,
        true,
        true
    )
);
jmtc.setCallbacks(callbacks);
jmtc.setEnabled(true);
jmtc.setMediaType(JMTCMediaType.Music);
jmtc.setPlayingState(JMTCPlayingState.PAUSED);
control.setMediaProperties(
    new JMTCMusicProperties(
        "TestTitle",
        "TestArtist",
        "test",
        "tset",
        new String[]{},
        0,
        1,
        null
    )
);
jmtc.setTimelineProperties(
    new JMTCTimelineProperties(
        0L,
        100000L,
        0L,
        100000L
    )
);
jmtc.setPlayingState(JMTCPlayingState.PLAYING);
jmtc.updateDisplay();
jmtc.setPosition(100L);
```
## License
**TODO**
