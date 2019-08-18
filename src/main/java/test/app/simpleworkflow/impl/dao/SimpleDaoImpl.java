package test.app.simpleworkflow.impl.dao;

import test.app.simpleworkflow.core.dao.SimpleDao;
import test.app.simpleworkflow.impl.entity.DocumentImpl;
import test.app.simpleworkflow.impl.validation.ValidationException;
import test.app.simpleworkflow.impl.logger.LoggerHelper;
import test.app.simpleworkflow.impl.restriction.RestrictionExecutor;
import test.app.simpleworkflow.impl.restriction.Restrictions;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//эмуляция персистент слоя
public class SimpleDaoImpl implements SimpleDao<DocumentImpl, ConcurrentHashMap> {

    private Map<UUID, DocumentImpl> store = new ConcurrentHashMap<>();

    public SimpleDaoImpl(Restrictions restrictionsCreate) {
        this.restrictionsCreate = restrictionsCreate;
    }

    private Restrictions restrictionsCreate;

    @Override
    public DocumentImpl create(DocumentImpl document) {
        //TODO эти проверки в реале я бы сделал на уровне констрейнов БД
        if (getStore().entrySet().
                stream().
                filter(entry -> {
                    DocumentImpl d = entry.getValue();
                    return (d.getId().equals(document.getId()) ||
                            d.getName().equals(document.getName())) &&
                            null != d.getAcceptorSign() &&
                            null != d.getCreatorSign();
                })
                .count() > 1)
            throw new ValidationException("Нелья создать документ с " + document.getName() +
                    " и " + document.getId() + " тк он уже подписан");

        LoggerHelper.info("Проверка ограничений");
        RestrictionExecutor.accept(document, this, restrictionsCreate);
        getStore().put(document.getId(), document);
        LoggerHelper.info("Зафиксирован документ" + document);
        return document;
    }

    @Override
    public boolean delete(DocumentImpl filter) {
        getStore().remove(filter.getId());
        LoggerHelper.info("Удален документ по параметрам: " + filter);
        return true;
    }

    @Override
    public DocumentImpl read(DocumentImpl filter) {
        return getStore().get(filter.getId());
    }

    @Override
    public DocumentImpl update(DocumentImpl document) {
        return create(document);
    }

    @Override
    public ConcurrentHashMap<UUID, DocumentImpl> getStore() {
        return (ConcurrentHashMap) store;
    }
}
