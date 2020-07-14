package org.apache.pulsar.broker.service.filtering;

import io.netty.buffer.ByteBuf;

public interface Filter {
    boolean matches(ByteBuf val);
}
