package com.console_project.server.connection;

import com.console_project.shared.connection_model.UserRequest;

import java.net.InetAddress;

public record Request<T>(UserRequest<T> userRequest, int port, InetAddress addr) {
}
