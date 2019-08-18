package test.app.simpleworkflow.impl.validation;

import test.app.simpleworkflow.impl.entity.DocumentImpl;

public class DeleteValidatorImpl extends AbstractValidation {

    @Override
    public boolean valid(DocumentImpl document) {
        isNotEmpty(document);
        hasAccess(document);

        if (null != document.getCreator()
                || null != document.getCreator())
            throw new ValidationException("Нельзя удалить подписанный документ");

        return true;
    }

}
