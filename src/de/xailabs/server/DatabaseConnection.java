package de.xailabs.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.xailabs.client.Contact;
import de.xailabs.interfaces.IContact;


public class DatabaseConnection {
	
	EntityManagerFactory emfactory;
	EntityManager em;
	
	public DatabaseConnection() {
		emfactory = Persistence.createEntityManagerFactory("Contact_JPA");
		em = emfactory.createEntityManager();
	}
	
	public Integer addNewContact(IContact contact) {
		em.getTransaction().begin();
		em.persist(contact);
		em.getTransaction().commit();
		em.refresh(contact);
		return contact.getId();
	}
	
	public void deleteContact(IContact contact) {
		IContact jpaContact = em.find(Contact.class, contact.getId());
		em.getTransaction().begin();
		em.remove(jpaContact);
		em.getTransaction().commit();
	}
	
	public IContact getContact(int id) {
		IContact jpaContact = em.find(Contact.class, id);
		return jpaContact;
	}
	
	public List<IContact> getContacts() {
		Query query = em.createNamedQuery("getAllContacts", Contact.class);
		@SuppressWarnings("unchecked")
		List<IContact> contacts = query.getResultList();
		return contacts;
	}
	
	public int getMaxID() {
		int id;
		Query query = em.createNamedQuery("getMaxID", Contact.class);
		id = (int) query.getSingleResult();
		return id;
	}
	
	public int getVersion(int id) {
		IContact jpaContact = em.find(Contact.class, id);
		return jpaContact.getVersion();
	}
	
	public void updateContact(IContact contact) {
		IContact jpaContact = em.find(Contact.class, contact.getId());
		em.getTransaction().begin();
		jpaContact.setName(contact.getName());
		jpaContact.setPhonenumber(contact.getPhonenumber());
		jpaContact.setNotes(contact.getNotes());
		jpaContact.setVersion(jpaContact.getVersion() + 1);
		em.getTransaction().commit();
	}
	
	public List<IContact> searchForContact(String parameter) {
		Query query = em.createNamedQuery("searchForContact", Contact.class).setParameter("parameter", parameter);		
		@SuppressWarnings("unchecked")
		List<IContact> foundContacts = query.getResultList();
		return foundContacts;
	}
}