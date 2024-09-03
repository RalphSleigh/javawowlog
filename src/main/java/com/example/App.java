/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.FileSystems;

import com.example.api.JerseyApplication;
import com.example.logitems.LogFile;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.SimpleFileServer;

import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.ext.RuntimeDelegate;


/**
 * Hello world application using only the standard JAX-RS API and lightweight
 * HTTP server bundled in JDK.
 *
 * @author Martin Matula
 */
public class App {
    public static final String REQUEST_ID_KEY = "requestId";
    static volatile LogFile logFile = new LogFile();

    /**
     * Starts the lightweight HTTP server serving the JAX-RS application.
     *
     * @return new instance of the lightweight HTTP server
     * @throws IOException
     */
    static HttpServer startServer() throws IOException {
        // create a new server listening at port 8080
        final HttpServer server = HttpServer.create(new InetSocketAddress(getBaseURI().getPort()), 0);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                server.stop(0);
            }
        }));

        // create a handler wrapping the JAX-RS application
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new JerseyApplication(logFile), HttpHandler.class);
        var p =  FileSystems.getDefault().getPath("web").toAbsolutePath();
        HttpHandler files = SimpleFileServer.createFileHandler(p);
        // map JAX-RS handler to the server root
        server.createContext(getBaseURI().getPath()+"api/", handler).getFilters().add(logging());
        server.createContext(getBaseURI().getPath(), files).getFilters().add(logging());

        // start the server
        server.start();

        return server;
    }

    private static Filter logging() {
        return new Filter() {
            @Override
            public void doFilter(HttpExchange http, Chain chain) throws IOException {
                try {
                    chain.doFilter(http);
                } finally {
                    Object possibleRequestId = http.getAttribute(REQUEST_ID_KEY);
                    String requestId = possibleRequestId instanceof String ? (String) possibleRequestId : "unknown";
                    if(http.getResponseCode() == 500) {
                        System.err.println(String.format("%s %s %s %s %s %s",
                                              requestId,
                                              http.getRequestMethod(),
                                              http.getRequestURI().getPath(),
                                              http.getRemoteAddress(),
                                              http.getRequestHeaders().getFirst("User-Agent"),
                                              http.getResponseCode()));
                    }
                    System.out.println(String.format("%s %s %s %s %s %s",
                                              requestId,
                                              http.getRequestMethod(),
                                              http.getRequestURI().getPath(),
                                              http.getRemoteAddress(),
                                              http.getRequestHeaders().getFirst("User-Agent"),
                                              http.getResponseCode()));
                }
            }

            @Override
            public String description() {
                return "logging";
            }
        };
    }

    public static void main(String[] args) throws IOException, InterruptedException {


        /* Logger.getLogger("").setLevel(Level.FINEST);
        Logger.getLogger("").getHandlers()[0].setLevel(Level.FINEST);
        Logger logger = Logger.getLogger("org.glassfish.jersey.tracing");
        logger.setLevel(Level.ALL);
        logger.addHandler(new StreamHandler(System.out, new SimpleFormatter()));
        logger.log(Level.FINEST, "test"); */

        System.out.println("Starting Log Parser");

        Runnable task = () -> {
            LogParser logParser = new LogParser(logFile, "WoWCombatLog-090324_135126.txt");
        };

        Thread thread = new Thread(task);
        thread.start();

        System.out.println("\"Hello World\" Jersey Example Application");

        startServer();

        System.out.println("Application started.\n"
                + "Try accessing " + getBaseURI() + "helloworld in the browser.\n"
                + "CTRL + C to stop the application...\n");

        Thread.currentThread().join();
    }

    private static int getPort(int defaultPort) {
        final String port = System.getProperty("jersey.config.test.container.port");
        if (null != port) {
            try {
                return Integer.parseInt(port);
            } catch (NumberFormatException e) {
                System.out.println("Value of jersey.config.test.container.port property"
                        + " is not a valid positive integer [" + port + "]."
                        + " Reverting to default [" + defaultPort + "].");
            }
        }
        return defaultPort;
    }

    /**
     * Gets base {@link URI}.
     *
     * @return base {@link URI}.
     */
    public static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(getPort(8080)).build();
    }
}