package de.xailabs.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.xailabs.client.Address;
import de.xailabs.client.Contact;
import de.xailabs.interfaces.IContact;


public class DatabaseConnection {
	
	EntityManagerFactory emfactory;
	EntityManager em;
	
	public DatabaseConnection() {
		emfactory = Persistence.createEntityManagerFactory("Contact_JPA");
		
	}
	
	public Integer addNewContact(IContact contact) {
		em = emfactory.createEntityManager();
		em.getTransaction().begin();
		Address address = contact.getAddress();
		em.persist(address);
		System.out.println(address.getId());
		em.persist(contact);
		em.getTransaction().commit();
		em.refresh(contact);
		em.close();
		return contact.getId();
	}
	
	public void deleteContact(IContact contact) {
		em = emfactory.createEntityManager();
		IContact jpaContact = em.find(Contact.class, contact.getId());
		em.getTransaction().begin();
		em.remove(jpaContact);
		em.getTransaction().commit();
		em.close();
	}
	
	public IContact getContact(int id) {
		em = emfactory.createEntityManager();
		IContact jpaContact = em.find(Contact.class, id);
		em.close();
		return jpaContact;
	}
	
	public List<IContact> getContacts() {
		em = emfactory.createEntityManager();
		Query query = em.createNamedQuery("getAllContacts", Contact.class);
		@SuppressWarnings("unchecked")
		List<IContact> contacts = query.getResultList();
		em.close();
		return contacts;
	}
	
	public int getMaxID() {
		em = emfactory.createEntityManager();		
		int id;
		Query query = em.createNamedQuery("getMaxID", Contact.class);
		id = (int) query.getSingleResult();
		em.close();
		return id;
	}
	
	public int getVersion(int id) {
		em = emfactory.createEntityManager();
		IContact jpaContact = em.find(Contact.class, id);
		em.close();
		return jpaContact.getVersion();
	}
	
	public void updateContact(IContact contact) {
		em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(contact);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<IContact> searchForContact(String parameter) {
		em = emfactory.createEntityManager();
		Query query = em.createNamedQuery("searchForContact", Contact.class);
		query.setParameter(1, "%" + parameter + "%");
		@SuppressWarnings("unchecked")
		List<IContact> foundContacts = query.getResultList();
		em.close();
		return foundContacts;
	}
}