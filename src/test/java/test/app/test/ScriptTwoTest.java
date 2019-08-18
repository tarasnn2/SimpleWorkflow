package test.app.test;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.app.simpleworkflow.impl.entity.DocumentImpl;
import test.app.simpleworkflow.impl.security.SignHelper;
import test.app.simpleworkflow.impl.service.SignSideImpl;

public class ScriptTwoTest extends ScriptAbstractTest {

    @DisplayName("script #2")
    @Test
    public void run() {
        login(firstCompany);
        DocumentImpl documentFirst = createFirstDocument();

        login(secondCompany);
        //перечитал документ по другим пользователем
        documentFirst = workflow.read(DocumentImpl.DocumentBuilder.getBuilder().setId(documentFirst.getId()).build());
        //новая редакция (создал)
        DocumentImpl documentSecond = DocumentImpl.DocumentBuilder.getBuilder()
                .setName("Счет за услуги по заготовке рогов и копыт #1")
                .build();

        //сохранил (новая версия)
        documentSecond = workflow.update(documentFirst, documentSecond);
        // подписал новую версию
        documentSecond = workflow.sign(documentSecond, secondCompany, SignSideImpl.CREATOR);
        //снова под первым пользователем
        login(firstCompany);
        //перечитал документ
        DocumentImpl document = workflow.read(DocumentImpl.DocumentBuilder.getBuilder().setId(documentSecond.getId()).build());
        //подписал первым пользоваетелем
        document = workflow.sign(document, firstCompany, SignSideImpl.ACCEPTOR);
        document = workflow.read(document);

        Assert.assertEquals("Подпись Создателя не совпадает", documentFirst.getCreatorSign(), SignHelper.sign(firstCompany.getId()));
        Assert.assertNull("Подпись не пустая", documentFirst.getAcceptorSign());

        Assert.assertEquals("Связь документов не корректна", documentFirst.getId(), document.getIdParent());

        Assert.assertEquals("Подпись Создателя не совпадает", document.getCreatorSign(), SignHelper.sign(secondCompany.getId()));
        Assert.assertEquals("Подпись Аццептора не совпадает", document.getAcceptorSign(), SignHelper.sign(firstCompany.getId()));
    }

}
