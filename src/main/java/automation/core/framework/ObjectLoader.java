package automation.core.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import automation.core.AutomationException;

public class ObjectLoader {

    private static final Logger logger = LoggerFactory.getLogger(ObjectLoader.class);

    public static Properties loadYaml(String resourceName) {
        logger.info("Loading: {}", resourceName);

        final Properties props = new Properties();
        try (InputStream configFileStream = ClassLoader.getSystemResourceAsStream(resourceName)) {
            Yaml yamlConfig = new Yaml();
            List<Map<String, String>> ol = new ArrayList<>();
            yamlConfig.loadAll(configFileStream).forEach(o -> ol.add((Map<String, String>) o));

            /* load common properties */
            ol.stream().findFirst().ifPresent(a -> {
                a.values().removeIf(Objects::isNull);
                props.putAll(a);
            });
        } catch (IOException ex) {
            throw new AutomationException("Could not load automation properties", ex);
        }
        return props;
    }
}
