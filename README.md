# javagent-decompiler
A very simple java agent which will decompile any loaded class

usage: <br/>
<code>java -javaagent:<b>[folder containing the java agent]</b>/javagent-decompiler-1.0-SNAPSHOT.jar 
-DLOADED_CLASSES_OUTPUT_FOLDER=<b>[folder to output the uncompiled classes]</b></b></code>

compilation: 
<code>gradle jar</code>