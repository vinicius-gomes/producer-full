package com.b2wdigital.lets.juvenal.producer.shared;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;
import java.util.List;

@RegisterForReflection
public class EntityFieldError implements Serializable {

    private List<String> fields;
    private String namespace;
    private String message;

    public EntityFieldError() {
    }

    public EntityFieldError(List<String> fields, String message) {
        this.fields = fields;
        this.message = message;
    }

    public EntityFieldError(List<String> fields, String namespace, String message) {
        this.fields = fields;
        this.namespace = namespace;
        this.message = message;
    }

    public EntityFieldError(String message) {
        this.message = message;
    }

    public List<String> getField() {
        return fields;
    }

    public void setField(List<String> field) {
        this.fields = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String nameSpace) {
        this.namespace = nameSpace;
    }
}