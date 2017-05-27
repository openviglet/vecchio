package com.viglet.vecchio.db;
import javax.persistence.*;
public class DBTest {
	
	   public static void main(String[] args) throws Exception {
	      EntityManagerFactory factory = Persistence.createEntityManagerFactory("vecchio");
	      EntityManager manager = factory.createEntityManager( );
	      try {

	      } finally {
	         manager.close( );
	         factory.close( );
	      }
	   }
}
