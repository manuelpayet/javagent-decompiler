package com.github.manuelpayet.transformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.ProtectionDomain;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WriteClassToDiskTransformer implements ClassFileTransformer {

    private final String outputFolder;
    private final ExecutorService executorService;
    public WriteClassToDiskTransformer(final String outputFolder) {
        this.outputFolder = outputFolder;
        executorService = Executors.newFixedThreadPool(10);
    }

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        executorService.submit(()-> writeToDisk(className, classfileBuffer));
        return classfileBuffer;
    }

    private void writeToDisk(String className, byte[] classfileBuffer) {
        try {
            Path path = Paths.get(outputFolder, "classesoutput", className + ".class");
            Files.createDirectories(path.getParent());
            Files.write(path, classfileBuffer);
        } catch (Throwable ignored) {// ignored, don’t do this at home kids
        }
    }
}
