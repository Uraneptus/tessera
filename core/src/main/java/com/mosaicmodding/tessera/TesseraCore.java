package com.mosaicmodding.tessera;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

public class TesseraCore {
    public static final String ID = "tessera";
    public static final String NAME = "Tessera";
    public static final Logger LOG = LoggerFactory.getLogger(NAME);

    public static void init() {

    }

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
