package org.apache.pulsar.broker.service.filtering;

import io.netty.buffer.ByteBuf;
import org.apache.pulsar.client.api.schema.GenericRecord;

import java.util.Properties;

public class AvroSchemaFilter extends Filter {

    public AvroSchemaFilter(Properties props) {
        super(props);
    }

    @Override
    public boolean matches(ByteBuf val) {
        GenericRecord gr = getSchema().decode(val.array());
        System.out.println("gr.getFields() = " + gr.getFields());

        return true;
    }

    @Override
    public boolean isSchemaAware() {
        return true;
    }
}
