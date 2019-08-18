package test.app.simpleworkflow.impl.validation;

import test.app.simpleworkflow.impl.entity.DocumentImpl;

public class UpdateValidatorImpl extends AbstractValidation {

    @Override
    public boolean valid(DocumentImpl document) {
        return isNotEmpty(document) &&
                hasAccess(document);
    }
}
