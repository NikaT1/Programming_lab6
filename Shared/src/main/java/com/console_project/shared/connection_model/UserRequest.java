package com.console_project.shared.connection_model;

import java.io.Serializable;

public record UserRequest<T>(
        String params,
        String command,
        T object
) implements Serializable {
}
