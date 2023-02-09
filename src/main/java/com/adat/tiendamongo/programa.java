package com.adat.tiendamongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Scanner;

public class programa {
	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		// Conectamos a MongoDB y seleccionamos BD
	    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	    MongoDatabase database = mongoClient.getDatabase("tienda");

	    // Seleccionar la coleccon de usuarios
	    MongoCollection<Document> usuariosCollection = database.getCollection("usuarios");
	    MongoCollection<Document> productosCollection = database.getCollection("productos");
	    
	    int opc = 22;
	    
	    do {
	    	mostrarMenu();
	    	opc = sc.nextInt();
	    	
	    	switch(opc) {
	    	
	    	// 1. Consultas basicas utilizando distintos selectores de busqueda ------------------
	    	case 1:
	    		// Buscamos usuarios PREMIUM
			    Bson filtroPremiums = Filters.eq("premium", true);
			    MongoCursor<Document> cursor = usuariosCollection.find(filtroPremiums).iterator();
			    
			    System.out.println("Mostrando los usuarios premium: ");
			    while (cursor.hasNext()) {
			      System.out.println(cursor.next().toJson());
			    }
			    
			    System.out.println("---------");
			    
			    // Buscamos productos con precio >1000
			    Bson filtroPrecioProductos = Filters.gt("precio", 1000);
			    MongoCursor<Document> cursor2 = productosCollection.find(filtroPrecioProductos).iterator();
			    
			    System.out.println("Mostrando los productos con precio > 1000: ");
			    while (cursor2.hasNext()) {
			      System.out.println(cursor2.next().toJson());
			    }
			    
			    
			    System.out.println("---------");
			    
			    
			 // Buscamos usuarios de ciudad distinta a Madrid
			    Bson filtroLugares = Filters.ne("direccion.poblacion", "Sevilla");
			    MongoCursor<Document> cursor3 = usuariosCollection.find(filtroLugares).iterator();
			    
			    System.out.println("Mostrando los usuarios que no son de Sevilla: ");
			    while (cursor3.hasNext()) {
			      System.out.println(cursor3.next().toJson());
			    }
	    		break;
	    		
	    	// 2. Modificaciones ----------------------------
	    	case 2:
	    		// Actualizamos el nombre del usuario con el ID 1
			    Document operacion = new Document("$set", new Document("nombre", "Don Patricia"));
			    System.out.println(operacion);
			    UpdateResult resultado = usuariosCollection.updateOne(Filters.eq("id", 1), operacion);
			    System.out.println(resultado);
			    System.out.println("hola");
			    if(resultado.getModifiedCount() != 0) {
			    	System.out.println("Actualizado correctamente");
			    }else {
			    	System.out.println("No se ha encontrado registro con dicho id");
			    }
	  
			   System.out.println("---------");
			 // Incrementamos el precio en 30 en cuyos productos valgan > 1000
			    Document filtro = new Document("precio", new Document("$gt",1000));
			    Document operacion2 = new Document("$inc", new Document("precio", 30));
			    UpdateResult resultado2 = productosCollection.updateMany(filtro, operacion);
			    
			    if(resultado.getModifiedCount() != 0) {
			    	System.out.println("Actualizado correctamente: " + resultado.getModifiedCount() + " registros");
			    }else {
			    	System.out.println("No se han encontrado registros");
			    }
			    
	    		break;
	    	}
	    }while(opc != 9);
	   
	    
		    
	    
		    
		
		 
		 
	}
	
	public static void mostrarMenu() {
		System.out.println("1. Consultas basicas");
		System.out.println("2. Actualizaciones");
		System.out.println("3. Consultas/Modificaciones sobre Arrays");
		System.out.println("4. Borrado");
		System.out.println("5. Consultas agregacion pipeline");
		System.out.println("6. Consultas con funciones para arrays");
		System.out.println("7. Consultas sobre docs enlazados");
	}

	public static int pedirNumero(String mensaje, int min, int max) {
    	int n = 0;
    	
    	// Si no hay maximo
    	if(max == 0) {
    		do {
        		System.out.println(mensaje);
        		n = sc.nextInt();
        	}while(n < min );
    	
    	}else {
    		do {
        		System.out.println(mensaje);
        		n = sc.nextInt();
        	}while(n < min || n > max);
    	}
    	return n;
    }
	
	 
}
