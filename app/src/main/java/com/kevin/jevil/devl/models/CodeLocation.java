package com.kevin.jevil.devl.models;

public class CodeLocation {
    private String fileName;
    private String thread;
    private String className;
    private String method;
    private String lineNumber;

    public CodeLocation(StackTraceElement[] stackTrace) {
        StackTraceElement root = stackTrace[0];
        thread = Thread.currentThread().getName();
        fileName = root.getFileName();
        String classname = root.getClassName();
        className = classname.substring(classname.lastIndexOf('.') + 1);
        method = root.getMethodName();
        lineNumber = "" + root.getLineNumber();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getclassName() {
        return className;
    }

    public void setclassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        builder.append(thread);
        builder.append('.');
        builder.append(className);
        builder.append('.');
        builder.append(method);
        builder.append('(');
        builder.append(fileName);
        builder.append(':');
        builder.append(lineNumber);
        builder.append(')');
        builder.append("] ");
        return builder.toString();
    }
}
