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


public class programa {
	public static void main(String[] args) {
		// Conectamos a MongoDB y seleccionamos BD
	    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
	    MongoDatabase database = mongoClient.getDatabase("tienda");

	    // Seleccionar la colección de usuarios
	    MongoCollection<Document> usuariosCollection = database.getCollection("usuarios");
	    MongoCollection<Document> productosCollection = database.getCollection("productos");

	    // 1. Consultas básicas utilizando distintos selectores de búsqueda
	    
		    // Buscamos usuarios PREMIUM
		    Bson filtroPremiums = Filters.eq("premium", true);
		    MongoCursor<Document> cursor = usuariosCollection.find(filtroPremiums).iterator();
		    
		    System.out.println("Mostrando los usuarios premium: ");
		    while (cursor.hasNext()) {
		      System.out.println(cursor.next().toJson());
		    }
		    
		    System.out.println("---------");
		    
		    // Buscamos productos con precio > 700
		    Bson filtroPrecioProductos = Filters.gt("precio", 1000);
		    MongoCursor<Document> cursor2 = productosCollection.find(filtroPrecioProductos).iterator();
		    
		    System.out.println("Mostrando los productos con precio > 1000: ");
		    while (cursor2.hasNext()) {
		      System.out.println(cursor2.next().toJson());
		    }
		    
		    
		    System.out.println("---------");
		    
		    
		 // Buscamos usuarios de ciudad distinta a Madrid
		    Bson filtroLugares = Filters.ne("direccion.poblacion", "Madrid");
		    MongoCursor<Document> cursor3 = usuariosCollection.find(filtroLugares).iterator();
		    
		    System.out.println("Mostrando los usuarios que no son de Madrid: ");
		    while (cursor3.hasNext()) {
		      System.out.println(cursor3.next().toJson());
		    }
	    
		    
		// 2. Modificaciones
		 // Actualizamos el nombre del usuario con el ID 1
		    Document updateOperation = new Document("$set", new Document("nombre", "Don Patricia"));
		    UpdateResult resultado = usuariosCollection.updateOne(Filters.eq("id", 1), updateOperation);
		    
		    if(resultado.getModifiedCount() != 0) {
		    	System.out.println("Actualizado correctamente");
		    }
	    
	   
	}
}
