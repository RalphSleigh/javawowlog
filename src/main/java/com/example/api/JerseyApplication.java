package com.example.api;

import java.util.Collections;

import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.example.logitems.LogFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

class Binder extends AbstractBinder {
    LogFile logFile;
    Binder(LogFile logFile) {
        this.logFile = logFile;
    }
    @Override
    protected void configure() {
        bind(this.logFile).to(this.logFile.getClass());
    }
}

@ApplicationPath("api")
public class JerseyApplication extends ResourceConfig {
    public JerseyApplication(LogFile logFile) {
        register(new Binder(logFile));
        register(EncounterResource.class);
        register(ObjectMapperContextResolver.class);
        addProperties(Collections.singletonMap(CommonProperties.PROVIDER_DEFAULT_DISABLE, "DATASOURCE"));
        addProperties(Collections.singletonMap("jersey.config.server.wadl.disableWadl", "true"));
        property("jersey.config.server.tracing.type", "ALL");
        property("jersey.config.server.tracing.threshold", "VERBOSE");
    }
}
@Provider
class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public ObjectMapperContextResolver() {
        this.mapper = createObjectMapper();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
