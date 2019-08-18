package test.app.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import test.app.simpleworkflow.core.service.SimpleWorkflow;
import test.app.simpleworkflow.impl.dao.SimpleDaoImpl;
import test.app.simpleworkflow.impl.entity.ActorImpl;
import test.app.simpleworkflow.impl.entity.DocumentImpl;
import test.app.simpleworkflow.impl.restriction.Restriction;
import test.app.simpleworkflow.impl.restriction.RestrictionException;
import test.app.simpleworkflow.impl.restriction.Restrictions;
import test.app.simpleworkflow.impl.security.SessionHelper;
import test.app.simpleworkflow.impl.service.SignSideImpl;
import test.app.simpleworkflow.impl.service.SimpleWorkflowImpl;
import test.app.simpleworkflow.impl.validation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Properties;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ScriptAbstractTest {

    public static SimpleWorkflow<DocumentImpl, SignSideImpl, ActorImpl> workflow;
    public static ActorImpl firstCompany;
    public static ActorImpl secondCompany;

    @BeforeAll
    public void setup() {
        firstCompany = new ActorImpl(UUID.randomUUID(), "Остап и Ко");
        secondCompany = new ActorImpl(UUID.randomUUID(), "Рога и Копыта ООО");

        SimpleDaoImpl simpleDao = new SimpleDaoImpl(Restrictions.RestrictionsBuilder
                .getBuilder()
                .add(
                        Restriction.RestrictionBuilder.getBuilder()
                                .addProperties(getProperty1())
                                .addConsumer((doc, dao, property) -> {
                                    LocalTime durationAllowStop = LocalTime.parse(property.getProperty("durationAllowStop"));
                                    LocalTime durationAllowStart = LocalTime.parse(property.getProperty("durationAllowStart"));
                                    if (doc.getCreate().isBefore(LocalDateTime.of(LocalDate.now(), durationAllowStop)) &&
                                            doc.getCreate().isAfter(LocalDateTime.of(LocalDate.now(), durationAllowStart))
                                    ) {
                                        return;
                                    } else if (null == doc.getAcceptorSign() && null == doc.getCreatorSign()) {
                                        return;
                                    }
                                    throw new RestrictionException("В этот период времени запрещено подписывать или вносить изменения в документ");
                                }).build()
                )
                .add(
                        Restriction.RestrictionBuilder.getBuilder()
                                .addProperties(getProperty2())
                                .addConsumer((doc, dao, property) -> {
                                    Integer allowCountWorkflow = Integer.valueOf(property.getProperty("allowCountWorkflow"));
                                    boolean result = dao.getStore().entrySet().stream()
                                            .filter(
                                                    entry -> entry.getValue().getCreator() == doc.getCreator() &&
                                                            null == entry.getValue().getAcceptorSign()
                                            ).count() <= allowCountWorkflow;
                                    if (result)
                                        return;
                                    throw new RestrictionException("Компаниям запрещено участвовать более чем в " + allowCountWorkflow + " документооборотах");
                                }).build()
                )
                .add(
                        Restriction.RestrictionBuilder.getBuilder()
                                .addProperties(getProperty3())
                                .addConsumer((doc, dao, property) -> {

                                    Integer allowCountWorkflow = Integer.valueOf(property.getProperty("allowCountWorkflow"));
                                    Integer durationAllow = Integer.valueOf(property.getProperty("durationAllow"));

                                    LocalDateTime timeBorder = doc.getCreate().minusSeconds(durationAllow);

                                    boolean result = dao.getStore().entrySet().stream()
                                            .filter(
                                                    entry -> timeBorder.isAfter(entry.getValue().getCreate()) &&
                                                            null == entry.getValue().getIdParent()
                                            )
                                            .count() < allowCountWorkflow;
                                    if (result)
                                        return;
                                    throw new RestrictionException("Компании запрещено создавать более " + allowCountWorkflow + " документов в течении " + durationAllow / 360 + " часа");
                                }).build()
                ).add(
                        Restriction.RestrictionBuilder.getBuilder()
                                .addProperties(getProperty4())
                                .addConsumer((doc, dao, property) -> {

                                    Integer allowCountWorkflow = Integer.valueOf(property.getProperty("allowCountWorkflow"));
                                    UUID idCreator = doc.getCreator().getId();
                                    UUID idAcceptor = null == doc.getAcceptor() ? null : doc.getAcceptor().getId();

                                    boolean result = dao.getStore().entrySet().stream()
                                            .filter(
                                                    entry -> entry.getValue().getCreator().getId()==idCreator ||
                                                            entry.getValue().getCreator().getId()==idAcceptor
                                            )
                                            .count() <= allowCountWorkflow;
                                    if (result)
                                        return;
                                    throw new RestrictionException("Запрещено ведение более " + allowCountWorkflow + " документооборотов");
                                }).build()
                )
                .build());

        workflow = SimpleWorkflowImpl.SimpleWorkflowImplBuilder
                .getBuilder()
                .setDao(simpleDao)
                .setCreateValidator(new CreateValidatorImpl())
                .setReadValidator(new ReadValidatorImpl())
                .setUpdateValidator(new UpdateValidatorImpl())
                .setDeleteValidator(new DeleteValidatorImpl())
                .setSignValidator(new SignValidatorImpl())
                .build();
    }


    protected void login(ActorImpl actor) {
        System.out.println("Текущий пользователь: " + actor.getName());
        SessionHelper.setCurrentUser(actor);
    }

    protected DocumentImpl createFirstDocument() {
        DocumentImpl documentNew = DocumentImpl.DocumentBuilder.getBuilder()
                .setCreator(firstCompany)
                .setAcceptor(secondCompany)
                .setName(UUID.randomUUID().toString())
                .setId(UUID.randomUUID())
                .build();
        documentNew = workflow.create(documentNew);
        documentNew = workflow.read(documentNew);
        documentNew = workflow.sign(documentNew, firstCompany, SignSideImpl.CREATOR);
        return workflow.read(documentNew);
    }

    protected Properties getProperty1() {
        return new Properties() {
            {
                setProperty("durationAllowStart", "00:00");
                setProperty("durationAllowStop", "23:59");
            }
        };
    }

    protected Properties getProperty2() {
        return new Properties() {
            {
                setProperty("allowCountWorkflow", "1000");
            }
        };
    }

    protected Properties getProperty3() {
        return new Properties() {
            {
                setProperty("allowCountWorkflow", "1000");
                setProperty("durationAllow", "36000"); //в секундах
            }
        };
    }

    protected Properties getProperty4() {
        return new Properties() {
            {
                setProperty("allowCountWorkflow", "1000");
            }
        };
    }
}
