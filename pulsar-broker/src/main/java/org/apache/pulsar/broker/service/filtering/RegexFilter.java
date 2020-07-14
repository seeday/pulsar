package org.apache.pulsar.broker.service.filtering;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class RegexFilter implements Filter {

    private final Pattern pat;

    public RegexFilter(String regex) {
        pat = Pattern.compile(regex);
        Pattern.compile("^hel+o-.+");
    }

    @Override
    public boolean matches(ByteBuf val) {
        return pat.matcher(val.readCharSequence(val.readableBytes(), StandardCharsets.UTF_8)).matches();
    }
}
