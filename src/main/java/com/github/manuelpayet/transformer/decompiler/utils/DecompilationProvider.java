package com.github.manuelpayet.transformer.decompiler.utils;

import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.Manifest;

public class DecompilationProvider implements IBytecodeProvider, IResultSaver {
    private byte[] classBytecode;

    final Path outputFolder;
    public DecompilationProvider(final Path outputFolder) {
        this.outputFolder = outputFolder;
    }

    public void setClassBytecode(byte[] classBytecode) {
        this.classBytecode = classBytecode;
    }


    @Override
    public byte[] getBytecode(String externalPath, String internalPath) throws IOException {
        return classBytecode;
    }

    @Override
    public void saveFolder(String path) {

    }

    @Override
    public void copyFile(String source, String path, String entryName) {

    }

    @Override
    public void saveClassFile(String path, String qualifiedName, String entryName, String content, int[] mapping) {
        final Path classPath = Paths.get(outputFolder.toString(), "decompiled_sources", qualifiedName + ".java");
        try {
            Files.createDirectories(classPath.getParent());
            Files.write(classPath, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createArchive(String path, String archiveName, Manifest manifest) {

    }

    @Override
    public void saveDirEntry(String path, String archiveName, String entryName) {

    }

    @Override
    public void copyEntry(String source, String path, String archiveName, String entry) {

    }

    @Override
    public void saveClassEntry(String path, String archiveName, String qualifiedName, String entryName, String content) {

    }

    @Override
    public void closeArchive(String path, String archiveName) {

    }
}
