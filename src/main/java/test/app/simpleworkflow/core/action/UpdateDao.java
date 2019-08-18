package test.app.simpleworkflow.core.action;

import test.app.simpleworkflow.core.entity.Document;

public interface UpdateDao<D extends Document> {
    D update(D document);
}
