package org.example.ShortUriWebService.repo.impl;

import com.sun.istack.NotNull;
import org.example.ShortUriWebService.domain.UrlEntity;
import org.example.ShortUriWebService.domain.UrlEntityWithRank;
import org.example.ShortUriWebService.repo.UrlEntityDAO;
import org.example.ShortUriWebService.util.SessionUtil;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Orlov Diga
 */
public class UrlEntityDAOImpl implements UrlEntityDAO {

    public UrlEntity saveUrlEntity(String origUrl, Function<Integer, String> fn) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        UrlEntity entity = new UrlEntity();
        entity.setOriginalUrl(origUrl);

        try {
            tx = session.beginTransaction();

            Long entityId = (Long) session.save(entity);
            entity.setId(entityId);
            entity.setShortUrl(fn.apply(Math.toIntExact(entityId)));
            session.update(entity);

            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return entity;
    }

    public List<UrlEntity> findAll() {
        Session session = SessionUtil.getSession();
        List<UrlEntity> entities = null;

        try {
            entities = session.createQuery("FROM UrlEntity").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return entities;
    }

    public void removeUrlEntity(@NotNull final Long id) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            UrlEntity entity = session.get(UrlEntity.class, id);
            session.delete(entity);

            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateEntitiesCount(@NotNull final Map<String, Integer> callCount) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UrlEntity> cr = cb.createQuery(UrlEntity.class);
        Root<UrlEntity> from = cr.from(UrlEntity.class);
        cr.select(from);

        try {
            tx = session.beginTransaction();

            for (Map.Entry<String, Integer> entry: callCount.entrySet()) {
                cr.where(cb.equal(from.get("shortUrl"), entry.getKey()));
                TypedQuery<UrlEntity> typedQuery = session.createQuery(cr);
                UrlEntity entity = typedQuery.getSingleResult();

                System.out.println("Before update " + entity.toString());

                entity.setCallCount(entity.getCallCount() + entry.getValue());
                System.out.println("After update" + entity.toString());

                session.update(entity);
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public UrlEntity findByShortUrl(@NotNull final String shortUrl) {
        Session session = SessionUtil.getSession();
        UrlEntity entity = null;

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UrlEntity> cr = cb.createQuery(UrlEntity.class);
        Root<UrlEntity> from = cr.from(UrlEntity.class);
        cr.select(from);
        cr.where(cb.equal(from.get("shortUrl"), shortUrl));

        try {
            TypedQuery<UrlEntity> typed = session.createQuery(cr);
            entity = typed.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (NoResultException e) {
            System.out.println("not found entity");
        }
        finally {
            session.close();
        }

        return entity;
    }

    public UrlEntity findById(Long id) {
        Session session = SessionUtil.getSession();
        UrlEntity entity = null;

        try {
            entity = session.load(UrlEntity.class, id);
            Hibernate.initialize(entity);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return entity;
    }

    public UrlEntityWithRank findByShortUrlWithRank(String shortUrl) {
        Session session = SessionUtil.getSession();
        UrlEntityWithRank entity = null;

        try {
            Object[] params =
                    (Object[]) session.createSQLQuery
                            ("SELECT shorturl, originalurl, callcount, rank FROM ( SELECT\n" +
                                    "\t shorturl, originalurl, callcount,\n" +
                                    "\tDENSE_RANK () OVER ( \n" +
                                    "\t\tORDER BY callcount DESC\n" +
                                    "\t) rank \n" +
                                    "FROM\n" +
                                    "\turlentity ) AS foo where shorturl = :param1")
                            .setParameter("param1", shortUrl).getSingleResult();

            entity = new UrlEntityWithRank();
            entity.setShortUrl(params[0].toString());
            entity.setOriginalUrl(params[1].toString());
            entity.setCallCount(params[2].toString());
            entity.setRank(params[3].toString());
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return entity;
    }

    public List<UrlEntityWithRank> findAllByPageAndCount(int page, int count) {
        int end = page * count;
        int begin = end - count;

        Session session = SessionUtil.getSession();
        List<UrlEntityWithRank> entityWithRankList = new ArrayList<>();

        try {
            List<Object[]> paramsList = session.createSQLQuery("SELECT shorturl, originalurl, callcount, rank FROM ( SELECT\n" +
                    "\tshorturl, originalurl, callcount,\n" +
                    "\tDENSE_RANK () OVER ( \n" +
                    "\t\tORDER BY callcount DESC\n" +
                    "\t) rank \n" +
                    "FROM\n" +
                    "\turlentity ) AS ss LIMIT :end OFFSET :begin")
                    .setParameter("end", end)
                    .setParameter("begin", begin)
                    .getResultList();

            for (Object[] param : paramsList) {
                UrlEntityWithRank entity = new UrlEntityWithRank();
                entity.setShortUrl(param[0].toString());
                entity.setOriginalUrl(param[1].toString());
                entity.setCallCount(param[2].toString());
                entity.setRank(param[3].toString());

                entityWithRankList.add(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return entityWithRankList;
    }
}
