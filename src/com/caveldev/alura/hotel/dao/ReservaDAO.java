package com.caveldev.alura.hotel.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

import com.caveldev.alura.hotel.modelo.Huesped;
import com.caveldev.alura.hotel.modelo.Reserva;
import com.caveldev.alura.hotel.utils.JPAUtils;

public class ReservaDAO {

	// Craendo acceso a esta clase
	private static EntityManager em;

	public ReservaDAO(EntityManager em) {
		this.em = em;
	}

	// Guarda o registra una reserva
	public void guardar(Reserva reserva) {
		this.em.persist(reserva);
	}

	public Reserva update(Reserva reserva) {
		this.em = JPAUtils.getEntityManager();
		
    	em.getTransaction().begin();
    	Reserva updatedReserva = em.merge(reserva);
    	em.getTransaction().commit();
    	em.close();
    	return updatedReserva;
	}
	
	public static List<Reserva> listar() {
		String jpql = "SELECT r FROM Reserva r";
        return em.createQuery(jpql, Reserva.class).getResultList();
    }

	public List<Reserva> viewTableReserva() {
		String jpql = "SELECT r FROM Reserva r";
		TypedQuery<Reserva> query = em.createQuery(jpql, Reserva.class);
		List<Reserva> reservas = query.getResultList();
		return reservas;
    }

	public void removeData(Long id) {

		EntityManager em = JPAUtils.getEntityManager();
    
		try {
        em.getTransaction().begin(); //Inicia la transaccion de datos

		String jpql ="SELECT r FROM Reserva r WHERE r.id = :id";
        Reserva reserva = em.createQuery(jpql, Reserva.class).setParameter("id", id).getSingleResult();

        em.remove(reserva); //Remueve la fila con el id
        em.getTransaction().commit();
		
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	public Reserva find(Long id) {
		EntityManager em = JPAUtils.getEntityManager();
	
		try {
			Reserva reserva = em.find(Reserva.class, id);
			return reserva;
		} finally {
			em.close();
		}
	}

}
