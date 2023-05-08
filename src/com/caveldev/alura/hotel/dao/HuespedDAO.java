package com.caveldev.alura.hotel.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.caveldev.alura.hotel.modelo.Huesped;
import com.caveldev.alura.hotel.modelo.Reserva;
import com.caveldev.alura.hotel.utils.JPAUtils;

public class HuespedDAO {
	
	//Craendo acceso a esta clase
	private EntityManager em;

	public HuespedDAO(EntityManager em) {
		this.em = em;
	}
	
	//Guarda o registra un huesped
		public void guardar(Huesped huesped) {
			this.em.persist(huesped);
		}

		public void actualizar(Huesped huesped) {
			em.getTransaction().begin();
			em.merge(huesped);
			em.getTransaction().commit();
		}


		public List<Huesped> viewTableHuesped() {
			String jpql = "SELECT h FROM Huesped h";
			TypedQuery<Huesped> query = em.createQuery(jpql, Huesped.class);
			List<Huesped> huespedes = query.getResultList();
			return huespedes;
		}

		public void eliminar(Long id) {

			EntityManager em = JPAUtils.getEntityManager();
		
			try {
			em.getTransaction().begin(); //Inicia la transaccion de datos
	
			String jpql ="SELECT h FROM Huesped h WHERE h.id = :id";
			Huesped huesped = em.createQuery(jpql, Huesped.class).setParameter("id", id).getSingleResult();
	
			em.remove(huesped); //Remueve la fila con el id
			em.getTransaction().commit();
			
			} catch (Exception e) {
				em.getTransaction().rollback();
				throw e;
			} finally {
				em.close();
			}
	
		}
}
