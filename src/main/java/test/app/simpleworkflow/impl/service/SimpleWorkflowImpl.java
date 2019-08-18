package test.app.simpleworkflow.impl.service;

import test.app.simpleworkflow.core.dao.SimpleDao;
import test.app.simpleworkflow.core.entity.Actor;
import test.app.simpleworkflow.core.entity.Document;
import test.app.simpleworkflow.core.service.SimpleWorkflow;
import test.app.simpleworkflow.core.validation.Validator;
import test.app.simpleworkflow.impl.entity.ActorImpl;
import test.app.simpleworkflow.impl.entity.DocumentImpl;
import test.app.simpleworkflow.impl.logger.LoggerHelper;
import test.app.simpleworkflow.impl.security.SessionHelper;
import test.app.simpleworkflow.impl.security.SignHelper;

import java.util.UUID;

public class SimpleWorkflowImpl
        implements SimpleWorkflow<DocumentImpl, SignSideImpl, ActorImpl> {

    final private SimpleDao dao;
    final private Validator createValidator;
    final private Validator deleteValidator;
    final private Validator updateValidator;
    final private Validator readValidator;
    final private Validator signValidator;

    private SimpleWorkflowImpl(SimpleWorkflowImplBuilder builder) {
        this.dao = builder.dao;
        this.createValidator = builder.createValidator;
        this.deleteValidator = builder.deleteValidator;
        this.updateValidator = builder.updateValidator;
        this.readValidator = builder.readValidator;
        this.signValidator = builder.signValidator;
    }

    @Override
    public DocumentImpl create(DocumentImpl document) {
        createValidator.valid(document);
        LoggerHelper.info("Попытка создать документ " + document);
        return (DocumentImpl) dao.create(document);
    }

    @Override
    public boolean delete(DocumentImpl document) {
        deleteValidator.valid(document);
        LoggerHelper.info("Попытка удалить документ " + document);
        return dao.delete(document);
    }

    @Override
    public DocumentImpl update(DocumentImpl documentOld, DocumentImpl documentNew) {
        updateValidator.valid(documentOld);
        Actor creatorOld = documentOld.getCreator();
        Actor acceptorOld = documentOld.getAcceptor();
        Actor creatorNew = SessionHelper.getCurrentUser();

        Actor acceptorNew = creatorNew.getId() != creatorOld.getId() ? creatorOld : acceptorOld;

        UUID id = null != documentNew.getId() ? documentNew.getId() : UUID.randomUUID();

        DocumentImpl documentDto = DocumentImpl.DocumentBuilder.getBuilder()
                .setCreator(creatorNew)
                .setAcceptor(acceptorNew)
                .setName(documentNew.getName()) //по сути, это единственное поле для изменения, и для него вводим documentNew, но будут поля и другие
                .setId(id)
                .setIdParent(documentOld.getId())
                .build();
        LoggerHelper.info("Попытка создать документ " + documentDto);
        return (DocumentImpl) dao.create(documentDto);
    }

    @Override
    public DocumentImpl sign(DocumentImpl document, ActorImpl actor, SignSideImpl side) {
        signValidator.valid(document);

        if (SignSideImpl.CREATOR.equals(side))
            document.setCreatorSign(SignHelper.sign(actor.getId()));

        if (SignSideImpl.ACCEPTOR.equals(side))
            document.setAcceptorSign(SignHelper.sign(actor.getId()));
        LoggerHelper.info("Попытка создать документ " + document);
        return (DocumentImpl) dao.update(document);
    }

    @Override
    public DocumentImpl read(DocumentImpl filter) {
        Document document = dao.read(filter);
        readValidator.valid(document);
        LoggerHelper.info("Прочитан документ " + document);
        return (DocumentImpl) document;
    }

    public static class SimpleWorkflowImplBuilder {

        private SimpleDao dao;
        private Validator createValidator;
        private Validator deleteValidator;
        private Validator updateValidator;
        private Validator readValidator;
        private Validator signValidator;

        public static SimpleWorkflowImplBuilder getBuilder() {
            return new SimpleWorkflowImplBuilder();
        }

        public SimpleWorkflowImplBuilder setDao(SimpleDao dao) {
            this.dao = dao;
            return this;
        }

        public SimpleWorkflowImplBuilder setCreateValidator(Validator createValidator) {
            this.createValidator = createValidator;
            return this;
        }

        public SimpleWorkflowImplBuilder setDeleteValidator(Validator deleteValidator) {
            this.deleteValidator = deleteValidator;
            return this;
        }

        public SimpleWorkflowImplBuilder setUpdateValidator(Validator updateValidator) {
            this.updateValidator = updateValidator;
            return this;
        }

        public SimpleWorkflowImplBuilder setReadValidator(Validator readValidator) {
            this.readValidator = readValidator;
            return this;
        }

        public SimpleWorkflowImplBuilder setSignValidator(Validator signValidator) {
            this.signValidator = signValidator;
            return this;
        }

        public SimpleWorkflow build() {
            return new SimpleWorkflowImpl(this);
        }

    }

}
