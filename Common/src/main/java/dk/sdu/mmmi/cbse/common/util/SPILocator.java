package dk.sdu.mmmi.cbse.common.util;

import java.util.*;

public class SPILocator {

    private static final Map<Class, ServiceLoader> loadermap = new HashMap<Class, ServiceLoader>();
    private static final SPILocator instance = new SPILocator();

    private SPILocator() {
    }

    public static SPILocator getInstance() {
        return instance;
    }

    public synchronized <T> List<T> locateAllSPIs(Class<T> service) {
        ServiceLoader<T> loader = getOrCreateLoader(service);
        List<T> list = loadImplementations(loader);
        return list;
    }

    private synchronized <T> ServiceLoader<T> getOrCreateLoader(Class<T> service) {
        ServiceLoader<T> loader = loadermap.get(service);
        if (loader == null) {
            loader = ServiceLoader.load(service);
            loadermap.put(service, loader);
        }
        return loader;
    }

    private <T> List<T> loadImplementations(ServiceLoader<T> loader) {
        List<T> list = new ArrayList<T>();
        if (loader != null) {
            try {
                for (T instance : loader) {
                    list.add(instance);
                }
            } catch (ServiceConfigurationError serviceError) {
                serviceError.printStackTrace();
            }
        }
        return list;
    }
}
