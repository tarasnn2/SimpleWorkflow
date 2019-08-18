package test.app.simpleworkflow.core.action;

import test.app.simpleworkflow.core.entity.Document;

public interface Read<D extends Document> {
    D read(D filter);
}
