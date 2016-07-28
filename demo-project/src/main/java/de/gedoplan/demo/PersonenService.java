/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.demo;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author hjungnitsch
 */
@Stateless
public class PersonenService {

    @PersistenceContext
    private EntityManager em;

    public List<Person> findAll() {
        return em.createQuery("select p from Person p", Person.class).getResultList();
    }

    public Integer save(Person p) {
        em.persist(p);
        return p.getId();
    }

    public Person find(Integer id) {
        return em.find(Person.class, id);
    }
}
