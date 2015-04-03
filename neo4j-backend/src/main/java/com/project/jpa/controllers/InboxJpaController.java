package com.project.jpa.controllers;

import com.project.jpa.controllers.exceptions.NonexistentEntityException;
import com.project.jpa.entities.Inbox;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author ArmenArzumanyan
 */
@Service("inboxJpaController")
@Component
public class InboxJpaController implements Serializable {

    private static final Logger log = Logger.getLogger(InboxJpaController.class);

    private EntityManagerFactory emf = null;

    public InboxJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @PostConstruct
    public void init() {
        log.info("InboxJpaController init called");
        emf = Persistence.createEntityManagerFactory("mailDBJPA");
    }

    public void create(Inbox inbox) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(inbox);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inbox inbox) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            inbox = em.merge(inbox);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = inbox.getId();
                if (findInbox(id) == null) {
                    throw new NonexistentEntityException("The inbox with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inbox inbox;
            try {
                inbox = em.getReference(Inbox.class, id);
                inbox.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inbox with id " + id + " no longer exists.", enfe);
            }
            em.remove(inbox);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inbox> findInboxEntities() {
        return getAllInbox(true, -1, -1);
    }

    public List<Inbox> findInboxEntities(int maxResults, int firstResult) {
        return getAllInbox(false, maxResults, firstResult);
    }

    public List<Inbox> getAllInbox(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inbox.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Inbox findInbox(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inbox.class, id);
        } finally {
            em.close();
        }
    }

    public int getInboxCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inbox> rt = cq.from(Inbox.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
