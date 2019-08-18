package test.app.simpleworkflow.impl.restriction;

import test.app.simpleworkflow.impl.dao.SimpleDaoImpl;
import test.app.simpleworkflow.impl.entity.DocumentImpl;

public class RestrictionExecutor {
    public static void accept(DocumentImpl document, SimpleDaoImpl dao, Restrictions restrictions) {
        restrictions.forEach(restriction -> restriction.getConsumer().accept(document, dao, restriction.getProperties()));
    }
}
