package de.xailabs.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;





import de.xailabs.client.*;
import de.xailabs.interfaces.IAddress;
import de.xailabs.interfaces.IContact;


public class DatabaseConnection {
	
	EntityManagerFactory emfactory;
	EntityManager em;
	
	public DatabaseConnection() {
		emfactory = Persistence.createEntityManagerFactory("Contact_JPA");
		
	}
	
	/**
	 * Adds a new contact to the database.
	 * @param contact The contact to be added
	 * @return The contact's ID in the database
	 */
	public Integer addNewContact(IContact contact) {
		em = emfactory.createEntityManager();
		IAddress foundAddress = checkForExistingAddress(contact.getAddress());
		contact.setAddress(foundAddress);
		em.getTransaction().begin();
		em.persist(contact);
		em.getTransaction().commit();
		em.refresh(contact);
		em.close();
		return contact.getId();
	}
	
	/**
	 * Checks whether an address already exists in MySQL
	 * @param address The address being checked for
	 * @return either the same address or the already existing address with the reference to its position in MySQL
	 */
	public IAddress checkForExistingAddress(IAddress address) {
		IAddress foundAddress = address;
		Query query = em.createNamedQuery("address.findStreet", Address.class);
		query.setParameter(1, address.getStreet());
		query.setParameter(2, address.getHousenumber());
		try {
			if (query.getSingleResult() != null) {
				foundAddress = (Address) query.getSingleResult();
			}
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return foundAddress;
	}
	
	/**
	 * Deletes a contact in the database.
	 * 
	 * @param contact
	 */
	public void deleteContact(IContact contact) {
		em = emfactory.createEntityManager();
		IContact jpaContact = em.find(Contact.class, contact.getId());
		int checkAddressID = jpaContact.getAddress().getId();
		em.getTransaction().begin();
		em.remove(jpaContact);
		em.getTransaction().commit();
		cleanAddresses(checkAddressID);
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
		int id = -1;
		Query query = em.createNamedQuery("getMaxID", Contact.class);
		try {
			id = (int) query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
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
	
	public void cleanAddresses(int addressID) {
		Query query = em.createNativeQuery("SELECT * FROM contact_address ca WHERE A_ID = " + addressID);
		if(query.getResultList().isEmpty()) {
			em.getTransaction().begin();
			IAddress jpaAddress = em.find(Address.class, addressID);
			em.remove(jpaAddress);
			em.getTransaction().commit();
		}
	}
}