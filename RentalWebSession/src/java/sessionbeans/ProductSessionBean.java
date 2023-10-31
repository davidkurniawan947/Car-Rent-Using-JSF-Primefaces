/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Products;

/**
 *
 * @author louiv
 */
@Stateless
public class ProductSessionBean {

    @PersistenceContext(unitName = "RentalPU")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public Products checkProduct(int id){
        try {
            Query q = em.createQuery("SELECT p FROM Products p WHERE p.id = :tempId");
            q.setParameter("tempId", id);
            
            List<Products> data = q.getResultList();
            
            if (data.isEmpty()){
                return null;
            } else {
                //product = data.get(0);
                return data.get(0);
            }
        } catch (Exception e){
            return null;
        }
    }
    
    public boolean updateCar(Products updateProduct){
        try {
            em.merge(updateProduct);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean addCar(Products addProduct){
        try {
            em.persist(addProduct);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean removeCar(Products searchProduct){
        try {
            Products deleteProd = em.find(Products.class, searchProduct.getId());
            em.remove(deleteProd);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public List<Products> listAllProd(){
        try {
            Query q = em.createQuery("SELECT p FROM Products p");
            return q.getResultList();
        } catch (Exception e){
            return null;
        }
    }
}
