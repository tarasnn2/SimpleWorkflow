package test.app.simpleworkflow.impl.validation;

import test.app.simpleworkflow.core.validation.Validator;
import test.app.simpleworkflow.impl.entity.DocumentImpl;
import test.app.simpleworkflow.impl.security.SessionHelper;

public abstract class AbstractValidation implements Validator<DocumentImpl> {

    protected boolean isNotEmpty(DocumentImpl document) {
        if (null == document)
            throw new ValidationException("Документ не представлен");
        return true;
    }

    protected boolean hasAccess(DocumentImpl document) {
        if (SessionHelper.getCurrentUser().getId().equals(document.getCreator().getId()) ||
                SessionHelper.getCurrentUser().getId().equals(document.getAcceptor().getId()))
            return true;
        throw new SecurityException("Нет доступа");
    }

}
