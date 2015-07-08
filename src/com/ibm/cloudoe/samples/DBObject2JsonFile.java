package com.rbc.cloud.cloudInnovationChallenge;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class DBObject2JsonFile {
	
	public static void insertDummyDocuments(DBCollection collection) {
		 
		List<DBObject> list = new ArrayList<DBObject>();
 
		Calendar cal = Calendar.getInstance();
 
		for (int i = 1; i <= 5; i++) {
 
			BasicDBObject data = new BasicDBObject();
			data.append("number", i);
			data.append("name", "mkyong-" + i);
			// data.append("date", cal.getTime());
 
			// +1 day
			cal.add(Calendar.DATE, 1);
 
			list.add(data);
 
		}
 
		collection.insert(list);
 
	}
	
	
	
	
    public void DBRetrieve(BasicDBObject query) {
 
	Gson gson = new Gson();
 
	try {
		
			  Mongo mongo = new Mongo("localhost", 27017);
			  DB db = mongo.getDB("yourdb");
		 
			  // get a single collection
			  DBCollection collection = db.getCollection("dummyColl");
		 
			  //insertDummyDocuments(collection);
			  
				//Additional code for printing the DBObject
				DBCursor cursorDocJSON = collection.find();
				while (cursorDocJSON.hasNext()) {
					System.out.println(cursorDocJSON.next());
				}//Printing DBObject Ends here; this code can be discarded if we don't need o/p on stdout
			 
		 
			  //StringBuilder sw = new StringBuilder();
			  
			  
			  /*System.out.println("1. Find first matched document");
			  DBObject dbObject = collection.findOne();
			  System.out.println(dbObject);
			  DataObject obj = gson.fromJson(dbObject.toString(), DataObject.class);
			  System.out.println(obj); */
		 
			 /* System.out.println("\n1. Find all matched documents");
			  DBCursor cursor = collection.find();
			  while (cursor.hasNext()) {
				//System.out.println(cursor.next());
				  DBObject object = cursor.next();
				  System.out.println(object);
				  DataObject obj = gson.fromJson(object.toString(), DataObject.class);
				  System.out.println(obj);
				//sw.append(cursor.next());
				//StringReader sr= new StringReader(sw.toString());
				//BufferedReader br = new BufferedReader(sr);
				//DataObject obj = gson.fromJson(br, DataObject.class);
				//DBObject obj = gson.fromJson(br, DBObject.class);
			  } */
		 
			  /*System.out.println("\n1. Get 'name' field only");
			  BasicDBObject allQuery = new BasicDBObject();
			  BasicDBObject fields = new BasicDBObject();
			  fields.put("name", 1);
		   
			  DBCursor cursor2 = collection.find(allQuery, fields);
			  while (cursor2.hasNext()) {
				System.out.println(cursor2.next());
			  } */
			  
			  
		 
			  //System.out.println("\n2. Find where number = 5");
			  //BasicDBObject whereQuery = new BasicDBObject();
			  //whereQuery.put("number", 5);
			  DBCursor cursor3 = collection.find(query);
			  while (cursor3.hasNext()) {
				DBObject object = cursor3.next();
				String json = object.toString();
				try {
					FileWriter writer = new FileWriter("E:\\output.json");
					writer.write(json);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.out.println(json);
			  }

			  collection.drop();
		 
			  System.out.println("Done");
		 
			 } catch (MongoException e) {
				e.printStackTrace();
			 }

    } 
    
    public static void main(String[] args) {
    	String fileName = "E:\\file.json";
		JsonFile2DBObject tempObj = new JsonFile2DBObject();
		tempObj.DBInsert(fileName);
    	
    	
    	DBObject2JsonFile tempObj1 = new DBObject2JsonFile();
    	BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("interests", "programmer");
		//whereQuery.put("subject", "candidates");
    	//whereQuery.put("number", 5);
    	tempObj1.DBRetrieve(whereQuery);
    	

    }
    
}
