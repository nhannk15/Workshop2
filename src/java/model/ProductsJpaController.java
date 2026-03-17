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
public class ProductsJpaController implements Serializable {

    public ProductsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Products products) throws PreexistingEntityException, Exception {
        if (products.getOrderDetailsCollection() == null) {
            products.setOrderDetailsCollection(new ArrayList<OrderDetails>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accounts account = products.getAccount();
            if (account != null) {
                account = em.getReference(account.getClass(), account.getAccount());
                products.setAccount(account);
            }
            Categories typeId = products.getTypeId();
            if (typeId != null) {
                typeId = em.getReference(typeId.getClass(), typeId.getTypeId());
                products.setTypeId(typeId);
            }
            Collection<OrderDetails> attachedOrderDetailsCollection = new ArrayList<OrderDetails>();
            for (OrderDetails orderDetailsCollectionOrderDetailsToAttach : products.getOrderDetailsCollection()) {
                orderDetailsCollectionOrderDetailsToAttach = em.getReference(orderDetailsCollectionOrderDetailsToAttach.getClass(), orderDetailsCollectionOrderDetailsToAttach.getOrderDetailsPK());
                attachedOrderDetailsCollection.add(orderDetailsCollectionOrderDetailsToAttach);
            }
            products.setOrderDetailsCollection(attachedOrderDetailsCollection);
            em.persist(products);
            if (account != null) {
                account.getProductsCollection().add(products);
                account = em.merge(account);
            }
            if (typeId != null) {
                typeId.getProductsCollection().add(products);
                typeId = em.merge(typeId);
            }
            for (OrderDetails orderDetailsCollectionOrderDetails : products.getOrderDetailsCollection()) {
                Products oldProductsOfOrderDetailsCollectionOrderDetails = orderDetailsCollectionOrderDetails.getProducts();
                orderDetailsCollectionOrderDetails.setProducts(products);
                orderDetailsCollectionOrderDetails = em.merge(orderDetailsCollectionOrderDetails);
                if (oldProductsOfOrderDetailsCollectionOrderDetails != null) {
                    oldProductsOfOrderDetailsCollectionOrderDetails.getOrderDetailsCollection().remove(orderDetailsCollectionOrderDetails);
                    oldProductsOfOrderDetailsCollectionOrderDetails = em.merge(oldProductsOfOrderDetailsCollectionOrderDetails);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProducts(products.getProductId()) != null) {
                throw new PreexistingEntityException("Products " + products + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Products products) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Products persistentProducts = em.find(Products.class, products.getProductId());
            Accounts accountOld = persistentProducts.getAccount();
            Accounts accountNew = products.getAccount();
            Categories typeIdOld = persistentProducts.getTypeId();
            Categories typeIdNew = products.getTypeId();
            Collection<OrderDetails> orderDetailsCollectionOld = persistentProducts.getOrderDetailsCollection();
            Collection<OrderDetails> orderDetailsCollectionNew = products.getOrderDetailsCollection();
            List<String> illegalOrphanMessages = null;
            for (OrderDetails orderDetailsCollectionOldOrderDetails : orderDetailsCollectionOld) {
                if (!orderDetailsCollectionNew.contains(orderDetailsCollectionOldOrderDetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderDetails " + orderDetailsCollectionOldOrderDetails + " since its products field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (accountNew != null) {
                accountNew = em.getReference(accountNew.getClass(), accountNew.getAccount());
                products.setAccount(accountNew);
            }
            if (typeIdNew != null) {
                typeIdNew = em.getReference(typeIdNew.getClass(), typeIdNew.getTypeId());
                products.setTypeId(typeIdNew);
            }
            Collection<OrderDetails> attachedOrderDetailsCollectionNew = new ArrayList<OrderDetails>();
            for (OrderDetails orderDetailsCollectionNewOrderDetailsToAttach : orderDetailsCollectionNew) {
                orderDetailsCollectionNewOrderDetailsToAttach = em.getReference(orderDetailsCollectionNewOrderDetailsToAttach.getClass(), orderDetailsCollectionNewOrderDetailsToAttach.getOrderDetailsPK());
                attachedOrderDetailsCollectionNew.add(orderDetailsCollectionNewOrderDetailsToAttach);
            }
            orderDetailsCollectionNew = attachedOrderDetailsCollectionNew;
            products.setOrderDetailsCollection(orderDetailsCollectionNew);
            products = em.merge(products);
            if (accountOld != null && !accountOld.equals(accountNew)) {
                accountOld.getProductsCollection().remove(products);
                accountOld = em.merge(accountOld);
            }
            if (accountNew != null && !accountNew.equals(accountOld)) {
                accountNew.getProductsCollection().add(products);
                accountNew = em.merge(accountNew);
            }
            if (typeIdOld != null && !typeIdOld.equals(typeIdNew)) {
                typeIdOld.getProductsCollection().remove(products);
                typeIdOld = em.merge(typeIdOld);
            }
            if (typeIdNew != null && !typeIdNew.equals(typeIdOld)) {
                typeIdNew.getProductsCollection().add(products);
                typeIdNew = em.merge(typeIdNew);
            }
            for (OrderDetails orderDetailsCollectionNewOrderDetails : orderDetailsCollectionNew) {
                if (!orderDetailsCollectionOld.contains(orderDetailsCollectionNewOrderDetails)) {
                    Products oldProductsOfOrderDetailsCollectionNewOrderDetails = orderDetailsCollectionNewOrderDetails.getProducts();
                    orderDetailsCollectionNewOrderDetails.setProducts(products);
                    orderDetailsCollectionNewOrderDetails = em.merge(orderDetailsCollectionNewOrderDetails);
                    if (oldProductsOfOrderDetailsCollectionNewOrderDetails != null && !oldProductsOfOrderDetailsCollectionNewOrderDetails.equals(products)) {
                        oldProductsOfOrderDetailsCollectionNewOrderDetails.getOrderDetailsCollection().remove(orderDetailsCollectionNewOrderDetails);
                        oldProductsOfOrderDetailsCollectionNewOrderDetails = em.merge(oldProductsOfOrderDetailsCollectionNewOrderDetails);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = products.getProductId();
                if (findProducts(id) == null) {
                    throw new NonexistentEntityException("The products with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Products products;
            try {
                products = em.getReference(Products.class, id);
                products.getProductId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The products with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<OrderDetails> orderDetailsCollectionOrphanCheck = products.getOrderDetailsCollection();
            for (OrderDetails orderDetailsCollectionOrphanCheckOrderDetails : orderDetailsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Products (" + products + ") cannot be destroyed since the OrderDetails " + orderDetailsCollectionOrphanCheckOrderDetails + " in its orderDetailsCollection field has a non-nullable products field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Accounts account = products.getAccount();
            if (account != null) {
                account.getProductsCollection().remove(products);
                account = em.merge(account);
            }
            Categories typeId = products.getTypeId();
            if (typeId != null) {
                typeId.getProductsCollection().remove(products);
                typeId = em.merge(typeId);
            }
            em.remove(products);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Products> findProductsEntities() {
        return findProductsEntities(true, -1, -1);
    }

    public List<Products> findProductsEntities(int maxResults, int firstResult) {
        return findProductsEntities(false, maxResults, firstResult);
    }

    private List<Products> findProductsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Products.class));
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

    public Products findProducts(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Products.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Products> rt = cq.from(Products.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
