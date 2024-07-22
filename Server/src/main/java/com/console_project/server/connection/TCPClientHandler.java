package com.console_project.server.connection;

import com.console_project.server.command.CommandInvoker;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import com.console_project.shared.serialization.ObjectSerializer;
import com.console_project.shared.serialization.ObjectSerializerImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.Level;

import static com.console_project.server.Server.log;

@RequiredArgsConstructor
public class TCPClientHandler<T> implements ClientHandler<T> {

    private final int MAX_BUFFER_SIZE = 100000;
    private final int SOCKET_TIMEOUT = 600000;
    private final String CLIENT_CONNECTION_ERROR_MESSAGE = "Возникла проблема соединения с клиентом ";
    private final String CONNECTION_ERROR_MESSAGE = "Невозможно открыть соединение для клиентов ";
    private final String SELECTOR_ERROR_MESSAGE = "Возникла ошибка в работе селектора ";
    private final String TIMEOUT_ERROR_MESSAGE = "Время ожидания подключения клиентов истекло ";

    private final int port;
    private final CommandInvoker<T> commandInvoker;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private final ObjectSerializer<UserRequest<T>> requestSerializer = new ObjectSerializerImpl<>();
    private final ObjectSerializer<CommandResponse> responseSerializer = new ObjectSerializerImpl<>();

    public void handleClients() throws SocketException, SocketTimeoutException {
        initConnection();
        try {
            while (true) {
                try {
                    selector.select();
                    Iterator it = selector.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey skey = (SelectionKey) it.next();
                        it.remove();
                        if (skey.isAcceptable()) {
                            handleNewConnection();
                        } else if (skey.isReadable()) {
                            handleRequest(skey);
                        }
                    }
                } catch (SocketTimeoutException e) {
                    log.log(Level.SEVERE, TIMEOUT_ERROR_MESSAGE + e.getMessage());
                    throw e;
                } catch (IOException e) {
                    log.log(Level.SEVERE, SELECTOR_ERROR_MESSAGE + e.getMessage());
                }
            }
        } finally {
            closeAll();
        }
    }

    private final void initConnection() throws SocketException {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().setSoTimeout(SOCKET_TIMEOUT);
            selector = Selector.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            closeAll();
            log.log(Level.SEVERE, CONNECTION_ERROR_MESSAGE + e.getMessage());
            SocketException exc = new SocketException();
            exc.addSuppressed(e);
            throw exc;
        }
    }

    private void handleNewConnection() {
        SocketChannel ch = null;
        try {
            ch = serverSocketChannel.accept();
            log.log(Level.INFO, "Новое соединение: " + ch.socket());
            ch.configureBlocking(false);
            ch.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            log.log(Level.SEVERE, CLIENT_CONNECTION_ERROR_MESSAGE + e.getMessage());
            closeClientConnection(ch);
        }
    }

    private void handleRequest(SelectionKey skey) throws IOException {
        SocketChannel ch = null;
        try {
            log.log(Level.INFO, "Чтение команды");
            ch = (SocketChannel) skey.channel();
            ByteBuffer buffer = ByteBuffer.wrap(new byte[MAX_BUFFER_SIZE]);
            ch.read(buffer);
            UserRequest<T> request = requestSerializer.deserializeObject(buffer.array());
            CommandResponse commandResponse = commandInvoker.processRequest(request);
            ch.write(ByteBuffer.wrap(responseSerializer.serializeObject(commandResponse)));
            ch.register(selector, SelectionKey.OP_READ);
            log.log(Level.INFO, "Обработка команды " + request.command() + " завершена");
        } catch (IOException e) {
            log.log(Level.SEVERE, CLIENT_CONNECTION_ERROR_MESSAGE + e.getMessage());
            closeClientConnection(ch);
        }
    }

    @SneakyThrows
    private void closeClientConnection(SocketChannel sc) {
        if (sc != null && sc.isOpen()) {
            sc.close();
        }
    }

    @SneakyThrows
    private void closeAll() {
        if (serverSocketChannel != null && serverSocketChannel.isOpen()) {
            serverSocketChannel.close();
        }
        if (selector != null && selector.isOpen()) {
            selector.close();
        }
    }

}
