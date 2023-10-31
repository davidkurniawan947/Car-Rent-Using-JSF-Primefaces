/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Products;
import model.Orders;

/**
 *
 * @author louiv
 */
@Singleton
//@Stateful
public class ProductSB implements Serializable{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PersistenceContext(unitName = "RentalPU")
    private EntityManager em;
    
    private Products product;
    
    private Products searchProduct;
    
    private List<Products> dataProducts;

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Products getSearchProduct() {
        return searchProduct;
    }

    public void setSearchProduct(Products searchProduct) {
        this.searchProduct = searchProduct;
    }
    
    

    public List<Products> allDataProducts(String orderByPrice) {
        try {
            //Query q = em.createNamedQuery("Products.findAll");
            Query q = em.createQuery("SELECT p FROM Products p "+ orderByPrice);
            return q.getResultList();
        } catch (Exception e){
            return null;
        }
    }
    
    // SEARCH CAR BY NAME
    public List<Products> listProductsByName(String carName, String orderByPrice){
        try {
            Query q = em.createQuery("SELECT p FROM Products p WHERE p.name LIKE CONCAT('%', :tempCarName, '%')" + orderByPrice);
            q.setParameter("tempCarName", carName);
            return q.getResultList();
        } catch (Exception e){
            return null;
        }
    }
    

    public List<Products> cekHargaMurah() {
        try {
            Query q = em.createQuery("SELECT p FROM Products p WHERE p.price < 200000");
            return q.getResultList();
        } catch (Exception e){
            return null;
        }
    }
    
    public boolean nullCar(){
        try {
            product = null;
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean checkProduct(int id){
        try {
            Query q = em.createQuery("SELECT p FROM Products p WHERE p.id = :tempId");
            q.setParameter("tempId", id);
            
            List<Products> data = q.getResultList();
            
            if (data.isEmpty()){
                return false;
            } else {
                //product = data.get(0);
                searchProduct = data.get(0);
                return true;
            }
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean updateCar(){
        try {
            if (searchProduct != null){
                em.merge(searchProduct);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean addCar(){
        try {
            em.persist(product);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean removeCar(){
        try {
            Products deleteProd = em.find(Products.class, searchProduct.getId());
            em.remove(deleteProd);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    
}
