/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Orders;
import model.Products;

/**
 *
 * @author louiv
 */
@Stateless
public class OrderSessionBean {

    @PersistenceContext(unitName = "RentalPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public boolean addOrder(Orders order) {
        try {
            em.persist(order);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Orders searchOrder(int id) {
        try {
            Query q = em.createQuery("SELECT o FROM Orders o WHERE o.id = :tempId");
            q.setParameter("tempId", id);

            List<Orders> data = q.getResultList();

            if (data.isEmpty()) {
                return null;
            } else {
                return data.get(0);
            }
        } catch (Exception e) {
            System.out.println("Gagal search");
            return null;
        }
    }

    public boolean confirmOrder(Orders order) {
        try {
            em.merge(order);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteOrder(Orders order) {
        try {
            Orders orderDeletion = em.find(Orders.class, order.getId());
            em.remove(orderDeletion);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Orders> checkConfirmedStatus(int id) {
        try {
            Query q = em.createQuery("SELECT o FROM Orders o WHERE o.productId.id = :tempId AND o.status = 'CONFIRMED' ");
            q.setParameter("tempId", id);

            List<Orders> data = q.getResultList();

            if (data.isEmpty()) {
                return null;
            } else {
                return data;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List<Orders> listCompletedOrders() {
        try {
            Query q = em.createQuery("SELECT o FROM Orders o WHERE o.status = 'COMPLETED'");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Orders> completedOrderByMonth(int month) {
        try {
            /*
            String fetchData;
            
            if (month < 10){
                fetchData = ">= '2023-0" + month + "-01 AND o.tanggalSelesai <= '2023-0" + month + "-30 ";
                fetchData = ">= CONCAT('2023-0', :month, '01') AND o.tanggalSelesai <= CONCAT('2023-0', :month ,'-30')";
            } else {
                fetchData = ">= '2023-" + month + "-01 AND o.tanggalSelesai <= '2023-" + month + "-30 ";
                fetchData = ">= CONCAT('2023-', :month, '01') AND o.tanggalSelesai <= CONCAT('2023-', :month ,'-30')";
            }
             */

            //Query q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= CONCAT(' '', :date1, '' ') AND o.tanggalSelesai <= CONCAT(' '', :date2, '' ') ");
            //q.setParameter("date1", date1);
            //q.setParameter("date2", date2);
            
            // WORKING
            
            Query q;
            
            switch (month) {
                case 1:
                    q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= '2023-01-01' AND o.tanggalSelesai <= '2023-01-31' ");
                    break;
                case 2:
                    q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= '2023-02-01' AND o.tanggalSelesai <= '2023-02-30' ");
                    break;
                case 3:
                    q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= '2023-03-01' AND o.tanggalSelesai <= '2023-02-31' ");
                    break;
                case 4:
                    q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= '2023-04-01' AND o.tanggalSelesai <= '2023-02-30' ");
                    break;
                case 5:
                    q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= '2023-05-01' AND o.tanggalSelesai <= '2023-02-31' ");
                    break;
                case 6:
                    q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= '2023-06-01' AND o.tanggalSelesai <= '2023-02-30' ");
                    break;
                case 7:
                    q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= '2023-07-01' AND o.tanggalSelesai <= '2023-02-31' ");
                    break;
                case 8:
                    q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= '2023-08-01' AND o.tanggalSelesai <= '2023-02-31' ");
                    break;
                case 9:
                    q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= '2023-09-01' AND o.tanggalSelesai <= '2023-02-30' ");
                    break;
                case 10:
                    q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= '2023-10-01' AND o.tanggalSelesai <= '2023-02-31' ");
                    break;
                case 11:
                    q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= '2023-11-01' AND o.tanggalSelesai <= '2023-02-30' ");
                    break;
                case 12:
                    q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= '2023-12-01' AND o.tanggalSelesai <= '2023-02-31' ");
                    break;
                default:
                    q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= '2023-12-01' AND o.tanggalSelesai <= '2023-02-31' ");
                    break;
            }

            //q = em.createQuery("SELECT o FROM Orders o WHERE o.tanggalSelesai >= '2023-01-01' AND o.tanggalSelesai <= '2023-01-30' ");
            System.out.println(q.getResultList());
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
