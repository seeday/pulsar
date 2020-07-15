package org.apache.pulsar.client.api;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class MessageFilterPolicy {

    protected final String className;
    protected final Map<String, String> properties;

    protected MessageFilterPolicy(String className) {
        this.className = className;
        this.properties = new HashMap<>();
    }

    public String getFilterClassName() {
        return this.className;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public static BytesPrefixFilterPolicy bytesPrefixPolicy(byte[] prefix) {
        return new BytesPrefixFilterPolicy(prefix);
    }

    public static RegexFilterPolicy regexFilterPolicy(Pattern pattern) {
        return new RegexFilterPolicy(pattern);
    }

    public static class BytesPrefixFilterPolicy extends MessageFilterPolicy {
        public BytesPrefixFilterPolicy(byte[] prefix) {
            super("org.apache.pulsar.broker.service.filtering.BytesPrefixFilter");
            properties.put("bytes_prefix_filter_prefix", new String(prefix, StandardCharsets.UTF_8));
        }
    }

    public static class RegexFilterPolicy extends MessageFilterPolicy {
        public RegexFilterPolicy(Pattern pattern) {
            super("org.apache.pulsar.broker.service.filtering.RegexFilter");
            properties.put("regex_filter_pattern_key", pattern.pattern());
        }
    }
}
