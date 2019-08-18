package test.app.simpleworkflow.core.action;

import test.app.simpleworkflow.core.entity.Document;

public interface Delete<D extends Document> {
    boolean delete(D filter);
}
