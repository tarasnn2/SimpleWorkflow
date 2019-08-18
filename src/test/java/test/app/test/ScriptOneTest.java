package test.app.test;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.app.simpleworkflow.impl.entity.DocumentImpl;
import test.app.simpleworkflow.impl.service.SignSideImpl;

public class ScriptOneTest
        extends ScriptAbstractTest {

    @DisplayName("script #1")
    @Test
    public void run() {
        login(firstCompany);
        DocumentImpl documentNew = createFirstDocument();

        login(secondCompany);
        DocumentImpl filter = DocumentImpl.DocumentBuilder.getBuilder().setId(documentNew.getId()).build();
        DocumentImpl documentAcceptor = workflow.read(filter);
        workflow.sign(documentAcceptor, secondCompany, SignSideImpl.ACCEPTOR);
        documentAcceptor = workflow.read(filter);

        Assert.assertEquals("Подпись Создателя не совпадает", documentAcceptor.getCreatorSign(), documentNew.getCreatorSign());
        Assert.assertEquals("Подпись Ацептора не совпадает", documentAcceptor.getAcceptorSign(), documentNew.getAcceptorSign());
    }
}
