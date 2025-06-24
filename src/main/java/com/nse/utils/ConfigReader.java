package com.nse.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigReader {
    private final Map<String, Object> config;

    public ConfigReader() {
        Yaml yaml = new Yaml();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.yaml");
        config = yaml.load(inputStream);
    }

    public String getBaseUrl() {
        return (String) config.get("baseUrl");
    }

    public String getIndicesPageUrl() {
        return (String) config.get("indicesPageUrl");
    }

    public String getStocksPageUrl() {
        return (String) config.get("stocksPageUrl");
    }

    public String getEquityPageUrl() {
        return (String) config.get("equityPageUrl");
    }

    public int getTimeout() {
        return (Integer) config.get("timeout");
    }

}
