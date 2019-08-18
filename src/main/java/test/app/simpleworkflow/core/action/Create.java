package test.app.simpleworkflow.core.action;

import test.app.simpleworkflow.core.entity.Document;

public interface Create<D extends Document> {
    D create(D document);
}
