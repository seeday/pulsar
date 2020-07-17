package org.apache.pulsar.broker.service.filtering;

import io.netty.buffer.ByteBuf;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.babel.SqlBabelParserImpl;
import org.apache.calcite.sql.util.SqlVisitor;
import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.apache.commons.lang.NotImplementedException;

import java.util.Properties;


public class AvroSqlFilter extends Filter {

    public AvroSqlFilter(Properties props) throws SqlParseException {
        super(props);
        SqlParser.Config c = SqlParser.configBuilder()
                .setParserFactory(SqlBabelParserImpl.FACTORY)
                .setConformance(SqlConformanceEnum.BABEL)
                .build();

        SqlNode ast = SqlParser.create(props.getProperty("query"), c).parseExpression();
        // TODO: validate expression against available schema fields
        // TODO: we've also gotta walk the ast every time and see if it matches the record?
        // god this might be slow
        // oh wait it won't be as long as we build a list of Functions of GenericRecord to Boolean
        // then evalutate that list as acceptance checks
        ast.accept(new SqlVisitor<Object>() {
            @Override
            public Object visit(SqlLiteral sqlLiteral) {
                return null;
            }

            @Override
            public Object visit(SqlCall sqlCall) {
                switch (sqlCall.getKind()) {
                    case EQUALS:
                        break;
                    default:
                        throw new NotImplementedException("sqlcall of " + sqlCall.getKind());
                }
                return null;
            }

            @Override
            public Object visit(SqlNodeList sqlNodeList) {
                return null;
            }

            @Override
            public Object visit(SqlIdentifier sqlIdentifier) {
                return null;
            }

            @Override
            public Object visit(SqlDataTypeSpec sqlDataTypeSpec) {
                throw new NotImplementedException("sqldatatype");
            }

            @Override
            public Object visit(SqlDynamicParam sqlDynamicParam) {
                return null;
            }

            @Override
            public Object visit(SqlIntervalQualifier sqlIntervalQualifier) {
                return null;
            }
        });
    }

    @Override
    public boolean matches(ByteBuf val) {
        return true;
    }

    @Override
    public boolean isSchemaAware() {
        return true;
    }
}
