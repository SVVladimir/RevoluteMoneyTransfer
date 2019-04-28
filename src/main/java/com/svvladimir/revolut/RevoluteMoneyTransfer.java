package com.svvladimir.revolut;

import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS;
import static org.glassfish.jersey.servlet.ServletProperties.JAXRS_APPLICATION_CLASS;

public class RevoluteMoneyTransfer {

    private static final int DEFAULT_PORT = 8080;
    private static final String SERVLET_CONTAINER_PATH = "/*";
    private static final String CONFIG_CLASS = "com.svvladimir.revolut.config.JerseyConfig";
    private static final String PACKAGES_PARAM = "jersey.config.server.provider.packages";
    private static final String CONTROLLER_PATH = "com.svvladimir.revolut.controller";

    private static final Logger LOGGER = getLogger();

    public static void main(String[] args) {
        Server server = initServer();
        runServer(server);
    }

    private static void runServer(Server server) {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            server.destroy();
        }
    }

    private static Server initServer() {
        ServletContextHandler contextHandler = new ServletContextHandler(SESSIONS);
        Server server = new Server(DEFAULT_PORT);
        server.setHandler(contextHandler);
        ServletHolder servletHolder = contextHandler.addServlet(ServletContainer.class, SERVLET_CONTAINER_PATH);
        servletHolder.setInitParameter(JAXRS_APPLICATION_CLASS, CONFIG_CLASS);
        servletHolder.setInitParameter(PACKAGES_PARAM, CONTROLLER_PATH);
        return server;
    }
}
