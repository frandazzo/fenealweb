package applica.feneal.data.hibernate.utils;


import applica.feneal.domain.model.User;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Country;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.geo.Region;
import applica.framework.*;
import applica.framework.data.hibernate.HibernateCrudStrategy;
import applica.framework.data.hibernate.HibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/9/15
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomHibernateCrudStrategy extends HibernateCrudStrategy {

    @Override
    public <T extends Entity> T get(Object id, Repository<T> repository) {
        HibernateRepository hibernateRepository = (HibernateRepository)repository;
        Assert.notNull(hibernateRepository, "Specified repository is not HibernateRepository");
        Session session = hibernateRepository.getSession();
        T entity = null;

        try {
            if(id != null) {
                if (id instanceof Integer){
                    int iid = IEntity.checkedId(id);
                    if(iid > 0) {
                        id = Integer.valueOf(iid);
                    }
                }
                else{
                    long iid = LEntity.checkedId(id);
                    if(iid > 0L) {
                        id = Long.valueOf(iid);
                    }
                }


                entity = (T) session.get(repository.getEntityType(), (Serializable)id);
            }
        } finally {
            session.close();
        }

        return entity;
    }



    @Override
    public <T extends Entity> LoadResponse<T> find(LoadRequest loadRequest, Repository<T> repository) {
        HibernateRepository<T> hibernateRepository = ((HibernateRepository<T>) repository);
        Assert.notNull(hibernateRepository, "Specified repository is not HibernateRepository");

        LoadResponse<T> loadResponse = new LoadResponse<T>();

        Session session = hibernateRepository.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Criteria countCriteria = hibernateRepository.createCriteria(session, loadRequest);
            Criteria criteria = hibernateRepository.createCriteria(session, loadRequest);

            Object countObject = countCriteria.setProjection(Projections.rowCount()).uniqueResult();
            long count = countObject != null ? (Long) countObject : 0;
            int limit = loadRequest.getRowsPerPage();
            int skip = loadRequest.getRowsPerPage() * (loadRequest.getPage() - 1);

            if (limit != 0) criteria.setMaxResults(limit);
            if (skip != 0) criteria.setFirstResult(skip);

            List<Sort> sorts = loadRequest.getSorts();
            if (sorts == null) {
                sorts = hibernateRepository.getDefaultSorts();
            }
            // if there is a restriction sort are superfluous
            if( loadRequest.getRestriction() != null){
                criteria.add(Restrictions.sqlRestriction(loadRequest.getRestriction().getSqlResriction()));
            }else {
                if (sorts != null) {
                    for (Sort sort : sorts) {
                        if (sort.isDescending()) {
                            criteria.addOrder(Order.desc(sort.getProperty()));
                        } else {
                            criteria.addOrder(Order.asc(sort.getProperty()));
                        }
                    }
                }
            }
            loadResponse.setRows(criteria.list());
            loadResponse.setTotalRows(count);

            transaction.commit();
        } catch(Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        return loadResponse;
    }


    @Override
    public <T extends Entity> void save(T entity, Repository<T> repository) {
        HibernateRepository<T> hibernateRepository = ((HibernateRepository<T>) repository);
        Assert.notNull(hibernateRepository, "Specified repository is not HibernateRepository");

        if (entity != null) {
            Session session = hibernateRepository.getSession();
            Transaction tx = session.beginTransaction();


            try {
                Object oldId = entity.getId();




                    if (entity instanceof Country ||
                            entity instanceof City ||
                            entity instanceof Province ||
                            entity instanceof Region){

                        //per queste entità si tratta di id di tipo intero
                        entity.setId(IEntity.checkedId(oldId));
                        session.save(entity);

                    }else{
                        //convert id to long if possible;
                        long iid = LEntity.checkedId(oldId);
                        if (iid > 0) {
                            entity.setId(iid);
                        }
                        session.saveOrUpdate(entity);
                    }



                tx.commit();
            } catch(Exception ex) {

                tx.rollback();
                throw ex;
            } finally {
                session.close();
            }
        }
    }

    @Override
    public <T extends Entity> void delete(Object id, Repository<T> repository) {
        HibernateRepository<T> hibernateRepository = ((HibernateRepository<T>) repository);
        Assert.notNull(hibernateRepository, "Specified repository is not HibernateRepository");

        if (id != null) {
            Session session = hibernateRepository.getSession();
            Transaction tx = session.beginTransaction();
            try {

                if (id instanceof Integer){
                    int iid = IEntity.checkedId(id);
                    if(iid > 0) {
                        id = Integer.valueOf(iid);
                    }
                }
                else{
                    long iid = LEntity.checkedId(id);
                    if(iid > 0L) {
                        id = Long.valueOf(iid);
                    }
                }


//                long iid = LEntity.checkedId(id);
//                if (iid > 0) {
//                    id = iid;
//                }
                T entity = (T) session.get(hibernateRepository.getEntityType(), (java.io.Serializable) id);
                if (entity != null) {
                    session.delete(entity);
                }
                tx.commit();
            } catch(Exception ex) {
                tx.rollback();
                throw ex;
            } finally {
                session.close();
            }
        }
    }
}
