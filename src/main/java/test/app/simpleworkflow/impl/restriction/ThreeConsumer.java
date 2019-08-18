package test.app.simpleworkflow.impl.restriction;

import test.app.simpleworkflow.impl.dao.SimpleDaoImpl;
import test.app.simpleworkflow.impl.entity.DocumentImpl;

import java.util.Properties;

@FunctionalInterface
public interface ThreeConsumer<Doc extends DocumentImpl, Dao extends SimpleDaoImpl, P extends Properties> {
    void accept(Doc doc, Dao dao, P p);
}
