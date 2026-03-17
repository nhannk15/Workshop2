/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.exceptions.NonexistentEntityException;
import model.exceptions.PreexistingEntityException;

/**
 *
 * @author nhann
 */
public class OrderDetailsJpaController implements Serializable {

    public OrderDetailsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrderDetails orderDetails) throws PreexistingEntityException, Exception {
        if (orderDetails.getOrderDetailsPK() == null) {
            orderDetails.setOrderDetailsPK(new OrderDetailsPK());
        }
        orderDetails.getOrderDetailsPK().setProductId(orderDetails.getProducts().getProductId());
        orderDetails.getOrderDetailsPK().setOrderId(orderDetails.getOrders().getOrderId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orders orders = orderDetails.getOrders();
            if (orders != null) {
                orders = em.getReference(orders.getClass(), orders.getOrderId());
                orderDetails.setOrders(orders);
            }
            Products products = orderDetails.getProducts();
            if (products != null) {
                products = em.getReference(products.getClass(), products.getProductId());
                orderDetails.setProducts(products);
            }
            em.persist(orderDetails);
            if (orders != null) {
                orders.getOrderDetailsCollection().add(orderDetails);
                orders = em.merge(orders);
            }
            if (products != null) {
                products.getOrderDetailsCollection().add(orderDetails);
                products = em.merge(products);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrderDetails(orderDetails.getOrderDetailsPK()) != null) {
                throw new PreexistingEntityException("OrderDetails " + orderDetails + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrderDetails orderDetails) throws NonexistentEntityException, Exception {
        orderDetails.getOrderDetailsPK().setProductId(orderDetails.getProducts().getProductId());
        orderDetails.getOrderDetailsPK().setOrderId(orderDetails.getOrders().getOrderId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrderDetails persistentOrderDetails = em.find(OrderDetails.class, orderDetails.getOrderDetailsPK());
            Orders ordersOld = persistentOrderDetails.getOrders();
            Orders ordersNew = orderDetails.getOrders();
            Products productsOld = persistentOrderDetails.getProducts();
            Products productsNew = orderDetails.getProducts();
            if (ordersNew != null) {
                ordersNew = em.getReference(ordersNew.getClass(), ordersNew.getOrderId());
                orderDetails.setOrders(ordersNew);
            }
            if (productsNew != null) {
                productsNew = em.getReference(productsNew.getClass(), productsNew.getProductId());
                orderDetails.setProducts(productsNew);
            }
            orderDetails = em.merge(orderDetails);
            if (ordersOld != null && !ordersOld.equals(ordersNew)) {
                ordersOld.getOrderDetailsCollection().remove(orderDetails);
                ordersOld = em.merge(ordersOld);
            }
            if (ordersNew != null && !ordersNew.equals(ordersOld)) {
                ordersNew.getOrderDetailsCollection().add(orderDetails);
                ordersNew = em.merge(ordersNew);
            }
            if (productsOld != null && !productsOld.equals(productsNew)) {
                productsOld.getOrderDetailsCollection().remove(orderDetails);
                productsOld = em.merge(productsOld);
            }
            if (productsNew != null && !productsNew.equals(productsOld)) {
                productsNew.getOrderDetailsCollection().add(orderDetails);
                productsNew = em.merge(productsNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                OrderDetailsPK id = orderDetails.getOrderDetailsPK();
                if (findOrderDetails(id) == null) {
                    throw new NonexistentEntityException("The orderDetails with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(OrderDetailsPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrderDetails orderDetails;
            try {
                orderDetails = em.getReference(OrderDetails.class, id);
                orderDetails.getOrderDetailsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderDetails with id " + id + " no longer exists.", enfe);
            }
            Orders orders = orderDetails.getOrders();
            if (orders != null) {
                orders.getOrderDetailsCollection().remove(orderDetails);
                orders = em.merge(orders);
            }
            Products products = orderDetails.getProducts();
            if (products != null) {
                products.getOrderDetailsCollection().remove(orderDetails);
                products = em.merge(products);
            }
            em.remove(orderDetails);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrderDetails> findOrderDetailsEntities() {
        return findOrderDetailsEntities(true, -1, -1);
    }

    public List<OrderDetails> findOrderDetailsEntities(int maxResults, int firstResult) {
        return findOrderDetailsEntities(false, maxResults, firstResult);
    }

    private List<OrderDetails> findOrderDetailsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrderDetails.class));
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

    public OrderDetails findOrderDetails(OrderDetailsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrderDetails.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderDetailsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrderDetails> rt = cq.from(OrderDetails.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
