package configuration.factory;

import configuration.models.TestData;
import configuration.models.UrlData;
import configuration.reader.YamlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class PropertyStore {

    private static final Logger logger = LoggerFactory.getLogger("PropertyStore.class");
    YamlReader yamlReader = new YamlReader();
    protected UrlData urlData;
    protected TestData testData;

    private PropertyStore() {
        setUrlProperties();
        setTestDataProperties();
    }

    public static PropertyStore getInstance() {
        return PropertyStoreSingleton.INSTANCE;
    }

    private static class PropertyStoreSingleton {
        private static final PropertyStore INSTANCE = new PropertyStore();
    }

    private void setUrlProperties() {
        logger.info(">> YAMLREADER -- urlProperties loaded");
        urlData = yamlReader.getConfig().getUrlData();
        Map<String, Object> urlDataProperties = urlData.getUrlProperties();
        for (Map.Entry entry : urlDataProperties.entrySet()) {
            System.setProperty(entry.getKey().toString(), entry.getValue().toString());
        }
    }
    private void setTestDataProperties() {
        logger.info(">> YAMLREADER -- testDataProperties loaded");
        testData = yamlReader.getConfig().getTestData();
        Map<String, Object> testDataProperties = testData.getDataProperties();
        for (Map.Entry entry : testDataProperties.entrySet()) {
            System.setProperty(entry.getKey().toString(), entry.getValue().toString());
        }
    }
}
