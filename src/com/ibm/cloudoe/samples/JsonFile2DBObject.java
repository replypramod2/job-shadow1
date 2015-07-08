package com.rbc.cloud.cloudInnovationChallenge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class JsonFile2DBObject {
	
	private String readFile( String file ) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }

	    return stringBuilder.toString();
	}
	
	public void DBInsert(String fileName) {
	
    JsonFile2DBObject tempObj = new JsonFile2DBObject();
	String json = null;
	try {
		json = tempObj.readFile(fileName);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

    try {
    	 
	Mongo mongo = new Mongo("localhost", 27017);
	DB db = mongo.getDB("yourdb");
 
	DBCollection collection = db.getCollection("dummyColl");
 
	System.out.println("JSON insert to mongodb...");
  
	DBObject dbObject = (DBObject)JSON.parse(json);
 	collection.insert(dbObject);
 
	//Additional code for printing the DBObject
	DBCursor cursorDocJSON = collection.find();
	while (cursorDocJSON.hasNext()) {
		System.out.println(cursorDocJSON.next());
	}//Printing DBObject Ends here; this code can be discarded if we don't need o/p on stdout
 
	//collection.remove(new BasicDBObject());
 
    } catch (MongoException e) {
	e.printStackTrace();
    }
 
    }
	
	public static void main(String[] args) {
		String fileName = "E:\\file.json";
		JsonFile2DBObject tempObj = new JsonFile2DBObject();
		tempObj.DBInsert(fileName);
	}
	
}
