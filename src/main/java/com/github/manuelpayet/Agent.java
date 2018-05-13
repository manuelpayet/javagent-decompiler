package com.github.manuelpayet;

import com.github.manuelpayet.transformer.WriteClassToDiskTransformer;
import com.github.manuelpayet.transformer.decompiler.WriteDecompiledCodeToDiskTransformer;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class Agent {

    public static void premain(String args, Instrumentation instrumentation) throws IOException {
        final String loadedClassesOutputFolder= System.getProperty("LOADED_CLASSES_OUTPUT_FOLDER");
        if(null == loadedClassesOutputFolder || loadedClassesOutputFolder.isEmpty()) {

            String missingParameter = "could not load javaagent-decompiler: missing -DLOADED_CLASSES_OUTPUT_FOLDER=... at the JVM launch";
            System.err.print(missingParameter);
            System.in.read();
            throw new IllegalArgumentException(missingParameter);

        }
        WriteClassToDiskTransformer classToDiskTransformer = new WriteClassToDiskTransformer(loadedClassesOutputFolder);
        WriteDecompiledCodeToDiskTransformer decompiledCodeToDiskTransformer = new WriteDecompiledCodeToDiskTransformer(loadedClassesOutputFolder);
        instrumentation.addTransformer(classToDiskTransformer);
        instrumentation.addTransformer(decompiledCodeToDiskTransformer);
    }
}
