### This sample usage some of feature available on ffmpeg library

This is usage of ffmpeg libray some of capabilities like Mixing multiple files, changes the tempo,asetrate,aecho ,corus options to make differnt voice effects.
This repo contains sample app and ffmpec wrapper library named Audioeffects

## How to use Audio effects library

Here we are following library to use ffmpeg capability
```
implementation 'com.arthenica:mobile-ffmpeg-full:4.2.2.LTS'
```
This is created with Java android library.

FFMpegMediaConverter.java is class which has following functions

#public void mergeFiles(String voiceFile, String effect, String outFile) 
This will mix two files two files and create a file on the outFile location

#public void mergeFiles(String source1, String effect1, int startOffset, String effect2, int startOffset2,String destination)
This will mix two files with specific start poition in sourcefile, and create output file in Outfile location

#public void mergeFiles(AudioFile sourceRecording,String destination,AudioFile... effects) 
This will give array of files to mix together with specific off set and create a file in out location

##Effects.java file has the effect applying on the audio files to change the effects.
this has option querry string to apply effects on audio , like Radio voice,Robotic voice, cave echo effects ect


## How to use The Effects in Audioeffects library
This is way you can use effects with options in library
```
  Options options = new Options.Builder(Effects.ROBOT, fileName, fileNameNew).build();
  mediaConverter.execute(options);
```

### License Copyright 2020 -binsdreams@gmail.com

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
