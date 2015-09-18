/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entity.*;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Bobbie
 */
public class Facade {
     EntityManagerFactory emf = Persistence.createEntityManagerFactory("classicmodelsPU");
     EntityManager em = emf.createEntityManager();
    
    public Employee createEmploye(){
        Employee e = new Employee();
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();    
        
        return e;
    }
    
    public Customer updateCustomer(Customer cust){
        em.getTransaction().begin();
        em.refresh(cust);
        em.getTransaction().commit();
        Customer newCus = em.find(Customer.class, cust.getCustomerNumber());
        return newCus;
    }
    
    public Object getEmployeCount(){
        return em.createQuery("SELECT COUNT(e) FROM Employee e").getSingleResult();
        
    }
    
    public Collection getCustomerInCity(String city){
        return em.createNamedQuery("Customer.findByCity").setParameter("city", city).getResultList();
    }
    
    public Object getEmployeMaxCustomers(){
        em.createQuery("SELECT COUNT(CustomerNumber), salesRepEmployeeNumber FROM classicmodels.customers WHERE salesRepEmployeeNumber > 0 GROUP BY salesRepEmployeeNumber  ORDER BY COUNT(CustomerNumber) DESC LIMIT 1");
        return 0;
    }
    public static void main(String[] args) {
        Facade f = new Facade();
        f.getEmployeMaxCustomers();
        System.out.println(f.getCustomerInCity("Boston"));
    }
}
