package test.app.simpleworkflow.core.validation;

import test.app.simpleworkflow.core.entity.Document;

public interface Validator<D extends Document> {
    boolean valid(D document);
}
