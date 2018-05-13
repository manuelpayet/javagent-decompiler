package com.github.manuelpayet.transformer.decompiler;

import com.github.manuelpayet.transformer.decompiler.utils.DecompilationLogger;
import com.github.manuelpayet.transformer.decompiler.utils.DecompilationProvider;
import org.jetbrains.java.decompiler.main.DecompilerContext;
import org.jetbrains.java.decompiler.main.Fernflower;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WriteDecompiledCodeToDiskTransformer implements ClassFileTransformer {

    private final Path outputFolderPath;
    private final DecompilationProvider decompilationProvider;
    private final ExecutorService executorService;
    public WriteDecompiledCodeToDiskTransformer(final String outputFolder) {

        this.outputFolderPath = Paths.get(outputFolder);
        this.decompilationProvider = new DecompilationProvider(outputFolderPath);


        executorService = Executors.newFixedThreadPool(10);

    }

    private final Map<String, Object> defaultParameters = new HashMap<>();
    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        executorService.submit(() -> decompile(className, classfileBuffer));
        return classfileBuffer;
    }

    private void decompile(String className, byte[] classfileBuffer) {
        if(className.startsWith("java/") || className.startsWith("sun/") || className.startsWith("jdk/") || className.startsWith("javax/")) {
            return;
        }
        final Fernflower fernflower = new Fernflower(decompilationProvider, decompilationProvider, defaultParameters, new DecompilationLogger());
        try {
            decompilationProvider.setClassBytecode(classfileBuffer);
            fernflower.getStructContext().addSpace(Paths.get(outputFolderPath.toString(), className+".class").toFile(), true);
            fernflower.decompileContext();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            fernflower.clearContext();
        }
    }


}
