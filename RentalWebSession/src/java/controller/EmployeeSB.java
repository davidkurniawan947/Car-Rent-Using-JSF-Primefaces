/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.SQLException;
import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Employees;
import util.PasswordHasher;

/**
 *
 * @author louiv
 */
@Singleton
//@Stateful
public class EmployeeSB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PersistenceContext(unitName = "RentalPU")
    private EntityManager em;
    
    private Employees employee;
    
    private Employees searchEmployee;
    
    private Employees addEmployee;
    
    private Employees changePassEmployee;

    // Database
    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public Employees getSearchEmployee() {
        return searchEmployee;
    }

    public void setSearchEmployee(Employees searchEmployee) {
        this.searchEmployee = searchEmployee;
    }

    public Employees getAddEmployee() {
        return addEmployee;
    }

    public void setAddEmployee(Employees addEmployee) {
        this.addEmployee = addEmployee;
    }

    public Employees getChangePassEmployee() {
        return changePassEmployee;
    }

    public void setChangePassEmployee(Employees changePassEmployee) {
        this.changePassEmployee = changePassEmployee;
    }
    
    
    
    
    // Login System
    public boolean validateLogin(String username, String password){
        try {
            // HASH THE LOGIN PASSWORD
            PasswordHasher loginPassHash = new PasswordHasher();
            String hashedLoginPass = loginPassHash.toHexString(loginPassHash.getSHA(password));
            
            Query q = em.createQuery("SELECT e fROM Employees e WHERE e.username = :tempUser AND e.password = :tempPass");
            q.setParameter("tempUser", username);
            q.setParameter("tempPass", hashedLoginPass);
            
            List<Employees> employeeList = q.getResultList();
            
            if (employeeList.isEmpty()){
                return false;
            } else {
                employee = employeeList.get(0);
                return true;
            }
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean nullUser(){
        try {
            employee = null;
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean nullSearchUser(){
        try {
            searchEmployee = null;
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean nullAddEmp(){
        try {
            addEmployee = null;
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public List<Employees> listAllEmp(){
        try {
            Query q = em.createQuery("SELECT e FROM Employees e");
            return q.getResultList();
        } catch (Exception e){
            return null;
        }
    }
    
    // Search emp
    public boolean searchEmp(int id){
        try {
            Query q = em.createQuery("SELECT e FROM Employees e WHERE e.id = :tempId");
            q.setParameter("tempId", id);
            
            List<Employees> data = q.getResultList();
            
            if (data.isEmpty()){
                return false;
            } else {
                //employee = data.get(0);
                searchEmployee = data.get(0);
                return true;
            }
        } catch (Exception e){
            return false;
        }
    }
    
    // Update Employee
    public boolean updateEmp(){
        try {
            em.merge(searchEmployee);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    //Remove Employee
    public boolean removeEmp(){
        try {
            Employees removeEmployee = em.find(Employees.class, searchEmployee.getId());
            em.remove(removeEmployee);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    //Add Employee
    public boolean addEmp(){
        try {
            
            Query q = em.createQuery("SELECT e FROM Employees e WHERE e.username = :tempUser");
            q.setParameter("tempUser", addEmployee.getUsername());
            
            List<Employees> data = q.getResultList();
            
            if (data.size() == 0){
                em.persist(addEmployee);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            return false;
        }
    }
    
    public void nullChangePassEmp(){
        changePassEmployee = null;
    }
    
    public boolean searchChangePassEmp(int changePassId){
        try {
            Query q = em.createQuery("SELECT e FROM Employees e WHERE e.id = :tempId");
            q.setParameter("tempId", changePassId);
            
            List<Employees> data = q.getResultList();
            
            if (data.isEmpty()){
                return false;
            } else {
                changePassEmployee = data.get(0);
                return true;
            }
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean updateEmpPass(){
        try {
            em.merge(changePassEmployee);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
