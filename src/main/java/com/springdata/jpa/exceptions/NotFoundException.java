package com.springdata.jpa.exceptions;

public class NotFoundException extends RuntimeException {

    private Class<?> className;

    public NotFoundException() {}

    public NotFoundException(Class<?> className) {
        super();
        this.className = className;
    }

    public Class<?> getClassName() {
        return className;
    }

    public void setClassName(Class<?> className) {
        this.className = className;
    }

}
