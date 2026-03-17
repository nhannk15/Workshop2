/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.exceptions.IllegalOrphanException;
import model.exceptions.NonexistentEntityException;
import model.exceptions.PreexistingEntityException;

/**
 *
 * @author nhann
 */
public class OrdersJpaController implements Serializable {

    public OrdersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orders orders) throws PreexistingEntityException, Exception {
        if (orders.getOrderDetailsCollection() == null) {
            orders.setOrderDetailsCollection(new ArrayList<OrderDetails>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<OrderDetails> attachedOrderDetailsCollection = new ArrayList<OrderDetails>();
            for (OrderDetails orderDetailsCollectionOrderDetailsToAttach : orders.getOrderDetailsCollection()) {
                orderDetailsCollectionOrderDetailsToAttach = em.getReference(orderDetailsCollectionOrderDetailsToAttach.getClass(), orderDetailsCollectionOrderDetailsToAttach.getOrderDetailsPK());
                attachedOrderDetailsCollection.add(orderDetailsCollectionOrderDetailsToAttach);
            }
            orders.setOrderDetailsCollection(attachedOrderDetailsCollection);
            em.persist(orders);
            for (OrderDetails orderDetailsCollectionOrderDetails : orders.getOrderDetailsCollection()) {
                Orders oldOrdersOfOrderDetailsCollectionOrderDetails = orderDetailsCollectionOrderDetails.getOrders();
                orderDetailsCollectionOrderDetails.setOrders(orders);
                orderDetailsCollectionOrderDetails = em.merge(orderDetailsCollectionOrderDetails);
                if (oldOrdersOfOrderDetailsCollectionOrderDetails != null) {
                    oldOrdersOfOrderDetailsCollectionOrderDetails.getOrderDetailsCollection().remove(orderDetailsCollectionOrderDetails);
                    oldOrdersOfOrderDetailsCollectionOrderDetails = em.merge(oldOrdersOfOrderDetailsCollectionOrderDetails);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrders(orders.getOrderId()) != null) {
                throw new PreexistingEntityException("Orders " + orders + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orders orders) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orders persistentOrders = em.find(Orders.class, orders.getOrderId());
            Collection<OrderDetails> orderDetailsCollectionOld = persistentOrders.getOrderDetailsCollection();
            Collection<OrderDetails> orderDetailsCollectionNew = orders.getOrderDetailsCollection();
            List<String> illegalOrphanMessages = null;
            for (OrderDetails orderDetailsCollectionOldOrderDetails : orderDetailsCollectionOld) {
                if (!orderDetailsCollectionNew.contains(orderDetailsCollectionOldOrderDetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderDetails " + orderDetailsCollectionOldOrderDetails + " since its orders field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<OrderDetails> attachedOrderDetailsCollectionNew = new ArrayList<OrderDetails>();
            for (OrderDetails orderDetailsCollectionNewOrderDetailsToAttach : orderDetailsCollectionNew) {
                orderDetailsCollectionNewOrderDetailsToAttach = em.getReference(orderDetailsCollectionNewOrderDetailsToAttach.getClass(), orderDetailsCollectionNewOrderDetailsToAttach.getOrderDetailsPK());
                attachedOrderDetailsCollectionNew.add(orderDetailsCollectionNewOrderDetailsToAttach);
            }
            orderDetailsCollectionNew = attachedOrderDetailsCollectionNew;
            orders.setOrderDetailsCollection(orderDetailsCollectionNew);
            orders = em.merge(orders);
            for (OrderDetails orderDetailsCollectionNewOrderDetails : orderDetailsCollectionNew) {
                if (!orderDetailsCollectionOld.contains(orderDetailsCollectionNewOrderDetails)) {
                    Orders oldOrdersOfOrderDetailsCollectionNewOrderDetails = orderDetailsCollectionNewOrderDetails.getOrders();
                    orderDetailsCollectionNewOrderDetails.setOrders(orders);
                    orderDetailsCollectionNewOrderDetails = em.merge(orderDetailsCollectionNewOrderDetails);
                    if (oldOrdersOfOrderDetailsCollectionNewOrderDetails != null && !oldOrdersOfOrderDetailsCollectionNewOrderDetails.equals(orders)) {
                        oldOrdersOfOrderDetailsCollectionNewOrderDetails.getOrderDetailsCollection().remove(orderDetailsCollectionNewOrderDetails);
                        oldOrdersOfOrderDetailsCollectionNewOrderDetails = em.merge(oldOrdersOfOrderDetailsCollectionNewOrderDetails);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orders.getOrderId();
                if (findOrders(id) == null) {
                    throw new NonexistentEntityException("The orders with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orders orders;
            try {
                orders = em.getReference(Orders.class, id);
                orders.getOrderId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orders with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<OrderDetails> orderDetailsCollectionOrphanCheck = orders.getOrderDetailsCollection();
            for (OrderDetails orderDetailsCollectionOrphanCheckOrderDetails : orderDetailsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orders (" + orders + ") cannot be destroyed since the OrderDetails " + orderDetailsCollectionOrphanCheckOrderDetails + " in its orderDetailsCollection field has a non-nullable orders field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(orders);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orders> findOrdersEntities() {
        return findOrdersEntities(true, -1, -1);
    }

    public List<Orders> findOrdersEntities(int maxResults, int firstResult) {
        return findOrdersEntities(false, maxResults, firstResult);
    }

    private List<Orders> findOrdersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orders.class));
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

    public Orders findOrders(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orders.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orders> rt = cq.from(Orders.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
