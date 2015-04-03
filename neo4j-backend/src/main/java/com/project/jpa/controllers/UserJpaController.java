package com.project.jpa.controllers;

import com.project.jpa.controllers.exceptions.NonexistentEntityException;
import com.project.jpa.entities.User;
import com.project.neo4j.util.CommonUtils;
import java.io.Serializable;
import java.util.Date;
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
@Service("userJpaController")
@Component
public class UserJpaController implements Serializable {

    private static final Logger log = Logger.getLogger(UserJpaController.class);
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public UserJpaController() {
    }

    @PostConstruct
    public void init() {
        log.info("UserController init called");
        emf = Persistence.createEntityManagerFactory("mailDBJPA");
    }

    public User doLogin(String name, String password) {
        log.info("doLogin   " + name + " time " + new Date(System.currentTimeMillis()));
        EntityManager em = getEntityManager();
        User user = null;
        try {
            em.getTransaction().begin();
            user = (User) em.createQuery("SELECT o FROM User o WHERE o.email = ?1 AND o.password = ?2")
                    .setParameter(1, name)
                    .setParameter(2, CommonUtils.hashPassword(password)).getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
        }
        return user;
    }

    public User findUserById(Long id) {
        EntityManager em = getEntityManager();
        try {
            //  System.out.println("Return user again");

            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public User getAccountByEmail(String email) {
        EntityManager em = getEntityManager();

        User user = null;
        try {
            user = (User) em.createQuery("SELECT o FROM User o WHERE UPPER(o.email) LIKE ?1").setParameter(1, email.trim().toUpperCase()).getSingleResult();

        } catch (Exception e) {
        }
        return user;
    }

    public void updateUser(User user) throws NonexistentEntityException, Exception {
        if (user.getId() == null) {
            return;
        }

        EntityManager em = null;
        try {
            log.info("Called update user id =" + user.getId());
            em = getEntityManager();
            em.getTransaction().begin();

            em.merge(user);
            em.flush();
            em.getTransaction().commit();
            log.info("Update user finished with id = " + user.getId());
        } catch (Exception ex) {
            log.info("Error updateUser " + ex.getLocalizedMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void updatePassword(Long userId, String password) {
        log.info("Updating user password, id = " + userId);
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("UPDATE User o SET o.password=?1 WHERE o.id=?2").setParameter(1, CommonUtils.hashPassword(password)).setParameter(2, userId).executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            log.info("Error occured during update  password: id= " + userId);
            e.printStackTrace();
        }
    }

    public void create(User user) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.merge(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    public List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
