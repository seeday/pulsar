package org.apache.pulsar.broker.service.filtering;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class BytesPrefixFilter implements Filter {

    private final byte[] prefix;

    public BytesPrefixFilter(String prefix) {
        this.prefix = prefix.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public boolean matches(ByteBuf val) {
        for (int i = 0; i < prefix.length; i++) {
            if (val.readByte() != prefix[i]) {
                return false;
            }
        }
        return true;
    }
}
