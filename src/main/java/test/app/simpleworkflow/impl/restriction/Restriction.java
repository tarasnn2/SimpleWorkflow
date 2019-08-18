package test.app.simpleworkflow.impl.restriction;

import test.app.simpleworkflow.impl.dao.SimpleDaoImpl;
import test.app.simpleworkflow.impl.entity.DocumentImpl;

import java.util.Properties;

public class Restriction {
    private Properties properties;
    private ThreeConsumer<DocumentImpl, SimpleDaoImpl, Properties> consumer;

    private Restriction(Properties properties, ThreeConsumer<DocumentImpl, SimpleDaoImpl, Properties> consumer) {
        this.properties = properties;
        this.consumer = consumer;
    }

    public Properties getProperties() {
        return properties;
    }

    public ThreeConsumer<DocumentImpl, SimpleDaoImpl, Properties> getConsumer() {
        return consumer;
    }

    public static class RestrictionBuilder {

        private Properties properties;
        private ThreeConsumer<DocumentImpl, SimpleDaoImpl, Properties> consumer;

        public static RestrictionBuilder getBuilder() {
            return new RestrictionBuilder();
        }

        public RestrictionBuilder addProperties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public RestrictionBuilder addConsumer(ThreeConsumer consumer) {
            this.consumer = consumer;
            return this;
        }

        public Restriction build() {
            return new Restriction(this.properties, this.consumer);
        }
    }
}
