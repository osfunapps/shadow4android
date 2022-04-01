Introduction
------------

This repo main purpose is to design android shadow on runtime, on a running device, using the forked library.

Summary
-------
Shadows in Android are pretty annoying: they consume lots of memory and aren't modifiable much.

That's why the best option is to create a 9patch shadow file for each view which stretches as the view bounds, without
a loss of memory. 

To create the right 9patch file, there is alot of design trial and error and that's why I've decided to do this dynamically 
(in runtime).

How this repo works
--------------------
This library relies on the forked repo of inloop. 

- Using the html, the user can design 9patch shadows and download them it's computer
- Each time a user will download a new shadow, the shadow will be auto streamed to the Android device replacing the current
shadow, in the running app
  
To use
-------
1) Run the [index.html](index.html) file from the repo
2) If you're using Chrome, Open "Settings", search for "Downloads" and disable the "Ask where to save each file before downloading"
3) Download one of the shadows to a directory of your choice
4) Use the python module: [os_android_shadow_generator](https://github.com/osfunapps/os_android_shadow_generator-py) to stream the shadow to your device
5) When the Python module is running and looking for new shadow files, each time you'll download a new shadow 
   file it will auto stream to your device
6) Add the  [ShadowGenerator.kt](srcs/ShadowGenerator.kt) file to your Android project and add the views to it (read it's instructions)
7) Remember the shadow files you've used (write them down) to download them later on to your project. These are the 9patch shadow files you desired