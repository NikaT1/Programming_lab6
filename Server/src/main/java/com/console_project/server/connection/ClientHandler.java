package com.console_project.server.connection;

import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public interface ClientHandler<T> {
    void handleClients() throws SocketException, SocketTimeoutException;
}
