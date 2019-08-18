package test.app.simpleworkflow.impl.validation;

import test.app.simpleworkflow.impl.entity.DocumentImpl;

public class SignValidatorImpl extends AbstractValidation {

    @Override
    public boolean valid(DocumentImpl document) {
        isNotEmpty(document);
        hasAccess(document);

        if (null != document.getAcceptorSign() &&
                null != document.getCreatorSign())
            throw new ValidationException("Подписанный документ подписывать нельзя");

        return true;
    }

}
