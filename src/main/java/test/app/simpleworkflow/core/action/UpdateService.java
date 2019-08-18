package test.app.simpleworkflow.core.action;

import test.app.simpleworkflow.core.entity.Document;

public interface UpdateService<D extends Document> {
    D update(D documenOld, D documenNew);
}
