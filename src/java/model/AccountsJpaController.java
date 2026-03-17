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
public class AccountsJpaController implements Serializable {

    public AccountsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Accounts accounts) throws PreexistingEntityException, Exception {
        if (accounts.getProductsCollection() == null) {
            accounts.setProductsCollection(new ArrayList<Products>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Products> attachedProductsCollection = new ArrayList<Products>();
            for (Products productsCollectionProductsToAttach : accounts.getProductsCollection()) {
                productsCollectionProductsToAttach = em.getReference(productsCollectionProductsToAttach.getClass(), productsCollectionProductsToAttach.getProductId());
                attachedProductsCollection.add(productsCollectionProductsToAttach);
            }
            accounts.setProductsCollection(attachedProductsCollection);
            em.persist(accounts);
            for (Products productsCollectionProducts : accounts.getProductsCollection()) {
                Accounts oldAccountOfProductsCollectionProducts = productsCollectionProducts.getAccount();
                productsCollectionProducts.setAccount(accounts);
                productsCollectionProducts = em.merge(productsCollectionProducts);
                if (oldAccountOfProductsCollectionProducts != null) {
                    oldAccountOfProductsCollectionProducts.getProductsCollection().remove(productsCollectionProducts);
                    oldAccountOfProductsCollectionProducts = em.merge(oldAccountOfProductsCollectionProducts);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAccounts(accounts.getAccount()) != null) {
                throw new PreexistingEntityException("Accounts " + accounts + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Accounts accounts) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accounts persistentAccounts = em.find(Accounts.class, accounts.getAccount());
            Collection<Products> productsCollectionOld = persistentAccounts.getProductsCollection();
            Collection<Products> productsCollectionNew = accounts.getProductsCollection();
            List<String> illegalOrphanMessages = null;
            for (Products productsCollectionOldProducts : productsCollectionOld) {
                if (!productsCollectionNew.contains(productsCollectionOldProducts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Products " + productsCollectionOldProducts + " since its account field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Products> attachedProductsCollectionNew = new ArrayList<Products>();
            for (Products productsCollectionNewProductsToAttach : productsCollectionNew) {
                productsCollectionNewProductsToAttach = em.getReference(productsCollectionNewProductsToAttach.getClass(), productsCollectionNewProductsToAttach.getProductId());
                attachedProductsCollectionNew.add(productsCollectionNewProductsToAttach);
            }
            productsCollectionNew = attachedProductsCollectionNew;
            accounts.setProductsCollection(productsCollectionNew);
            accounts = em.merge(accounts);
            for (Products productsCollectionNewProducts : productsCollectionNew) {
                if (!productsCollectionOld.contains(productsCollectionNewProducts)) {
                    Accounts oldAccountOfProductsCollectionNewProducts = productsCollectionNewProducts.getAccount();
                    productsCollectionNewProducts.setAccount(accounts);
                    productsCollectionNewProducts = em.merge(productsCollectionNewProducts);
                    if (oldAccountOfProductsCollectionNewProducts != null && !oldAccountOfProductsCollectionNewProducts.equals(accounts)) {
                        oldAccountOfProductsCollectionNewProducts.getProductsCollection().remove(productsCollectionNewProducts);
                        oldAccountOfProductsCollectionNewProducts = em.merge(oldAccountOfProductsCollectionNewProducts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = accounts.getAccount();
                if (findAccounts(id) == null) {
                    throw new NonexistentEntityException("The accounts with id " + id + " no longer exists.");
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
            Accounts accounts;
            try {
                accounts = em.getReference(Accounts.class, id);
                accounts.getAccount();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accounts with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Products> productsCollectionOrphanCheck = accounts.getProductsCollection();
            for (Products productsCollectionOrphanCheckProducts : productsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Accounts (" + accounts + ") cannot be destroyed since the Products " + productsCollectionOrphanCheckProducts + " in its productsCollection field has a non-nullable account field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(accounts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Accounts> findAccountsEntities() {
        return findAccountsEntities(true, -1, -1);
    }

    public List<Accounts> findAccountsEntities(int maxResults, int firstResult) {
        return findAccountsEntities(false, maxResults, firstResult);
    }

    private List<Accounts> findAccountsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Accounts.class));
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

    public Accounts findAccounts(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Accounts.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccountsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Accounts> rt = cq.from(Accounts.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
