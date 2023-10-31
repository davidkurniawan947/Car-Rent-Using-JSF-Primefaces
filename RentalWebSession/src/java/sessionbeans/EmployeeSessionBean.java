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
import model.Employees;
import util.PasswordHasher;

/**
 *
 * @author louiv
 */
@Stateless
public class EmployeeSessionBean {

    @PersistenceContext(unitName = "RentalPU")
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public Employees validateLogin(String username, String password){
        try {
            PasswordHasher hasher = new PasswordHasher();
            String hashedPassword = hasher.toHexString(hasher.getSHA(password));
            
            Query q = em.createQuery("SELECT e FROM Employees e WHERE e.username = :tempUser AND e.password = :tempPass");
            q.setParameter("tempUser", username);
            q.setParameter("tempPass", hashedPassword);
            
            List<Employees> data = q.getResultList();
            
            if (data.isEmpty()){
                return null;
            } else {
                return data.get(0);
            }
        } catch (Exception e){
            return null;
        }
    }
    
    public Employees searchEmp(int id){
        try {
            Query q = em.createQuery("SELECT e FROM Employees e WHERE e.id = :tempId");
            q.setParameter("tempId", id);
            
            List<Employees> data = q.getResultList();
            
            if (data.isEmpty()){
                return null;
            } else {
                //employee = data.get(0);
                return data.get(0);
            }
        } catch (Exception e){
            return null;
        }
    }
    
    public boolean updateEmp(Employees updateEmp){
        try {
            em.merge(updateEmp);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean removeEmp(Employees removeEmp){
        try {
            Employees removeEmployee = em.find(Employees.class, removeEmp.getId());
            em.remove(removeEmployee);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean addEmp(Employees addEmp){
        try {
            Query q = em.createQuery("SELECT e FROM Employees e WHERE e.username = :tempUser");
            q.setParameter("tempUser", addEmp.getUsername());
            
            List<Employees> data = q.getResultList();
            
            if (data.size() == 0){
                em.persist(addEmp);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            return false;
        }
    }
    
    public Employees searchChangePassEmp(int changePassId){
        try {
            Query q = em.createQuery("SELECT e FROM Employees e WHERE e.id = :tempId");
            q.setParameter("tempId", changePassId);
            
            List<Employees> data = q.getResultList();
            
            if (data.isEmpty()){
                return null;
            } else {
                return data.get(0);
            }
        } catch (Exception e){
            return null;
        }
    }
    
    public boolean updateEmpPass(Employees changePassEmp){
        try {
            em.merge(changePassEmp);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
