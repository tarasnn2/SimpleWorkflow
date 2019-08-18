package test.app.simpleworkflow.impl.validation;

import test.app.simpleworkflow.impl.entity.DocumentImpl;

public class CreateValidatorImpl extends AbstractValidation {

    @Override
    public boolean valid(DocumentImpl document) {
        isNotEmpty(document);

        if (null == document.getCreator())
            throw new ValidationException("Создатель не представлен");

        if (null == document.getAcceptor())
            throw new ValidationException("Акцептор не представлен");

        return true;
    }

}
