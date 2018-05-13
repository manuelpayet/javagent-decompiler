package com.github.manuelpayet.transformer.decompiler.utils;

import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;

public class DecompilationLogger extends IFernflowerLogger {

    @Override
    public void writeMessage(String message, Severity severity) {
       // System.out.println(String.format("%s %s", message, severity));
    }

    @Override
    public void writeMessage(String message, Severity severity, Throwable t) {
        // System.out.println(String.format("E %s %s %s", message, severity, t));
    }
}
