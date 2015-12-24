package fr.sco.activitytracker.dao;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.kafka.common.config.ConfigException;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import fr.sco.activitytracker.config.EventStepTracker;
import fr.sco.activitytracker.exception.ExecutorException;
import fr.sco.activitytracker.metier.AbstractInserter;
import fr.sco.activitytracker.metier.InstanceProcessusBO;
import fr.sco.activitytracker.metier.Processus;
import fr.sco.activitytracker.metier.StepBO;

public class MongoInserter extends AbstractInserter {

	private static final Logger logger = LoggerFactory.getLogger(MongoInserter.class);

	private MongoClient mongoClient;
	private String connectionString;
	private DB db;

	DateFormat formatDate = new SimpleDateFormat("yyyyMMddHHmmss");

	public MongoInserter(String connectionString) throws ConfigException {
		super();
		this.connectionString = connectionString;
		// TODO Auto-generated constructor stub
		this.connect();
	}

	public void connect() throws ConfigException {
		try {
			MongoClientURI mgcURI = new MongoClientURI(connectionString);
			mongoClient = new MongoClient(mgcURI);
			db = mongoClient.getDB("activitytracker");
		} catch (UnknownHostException e) {
			throw new ConfigException("Impossible de connecter à la base de données mongoDB");
		}
	}

	@Override
	public void insertEvent(Map<String, String> mapBody) throws ExecutorException {

		DBCollection coll = db.getCollection("event");

		BasicDBObject doc = new BasicDBObject();

		doc.append("eventName", mapBody.get("eventName"));

		try {
			doc.append("eventDateCreation", formatDate.parse(mapBody.get("eventDate")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new ExecutorException("Erreur de parsing de la date de création de l'évènement");
		}

		doc.append("eventDateInsertion", new Date());

		appendKeyValue(doc, mapBody);

		coll.insert(doc);

	}

	public ArrayList<InstanceProcessusBO> getInstanceProcessus(Processus processus, Map<String, String> mapBody)
			throws ExecutorException {

		DBCollection coll = db.getCollection("processus");
		DBCollection collStep = db.getCollection("step");

		BasicDBObject doc = new BasicDBObject();
		doc.append("processus", new BasicDBObject().append("packageName", processus.getPackageName())
				.append("name", processus.getName()).append("version", processus.getVersion()));
		appendKeyValue(doc, mapBody);
		doc.append("actif", true);

		DBCursor cursor = coll.find(doc);

		ArrayList<InstanceProcessusBO> listIP = null;

		if (cursor.count() == 0) {
			logger.debug("Aucune instance processus trouvée pour le processus " + processus.getFullName());

		} else {
			logger.debug("" + cursor.count() + " instances de processus trouvées pour le processus "
					+ processus.getFullName());
			listIP = new ArrayList<InstanceProcessusBO>();

			DBObject obj;

			while (cursor.hasNext()) {
				obj = cursor.next();
				InstanceProcessusBO iP = new InstanceProcessusBO();
				ObjectId objId = (ObjectId) obj.get("_id");
				iP.setId(objId.toString());

				BasicDBObject docRequest = new BasicDBObject();
				docRequest.append("instanceProcessusId", objId);
				docRequest.append("actif", true);

				DBObject objStep;
				DBCursor cursorStep = collStep.find(docRequest);

				while (cursorStep.hasNext()) {
					objStep = cursorStep.next();
					iP.getListStep().add(new StepBO(objStep.get("_id").toString(), objStep.get("name").toString()));
				}
				listIP.add(iP);
			}
		}

		return listIP;
	}

	public InstanceProcessusBO createInstanceProcessus(Processus processus, Map<String, String> mapBody)
			throws ExecutorException {
		DBCollection coll = db.getCollection("processus");

		BasicDBObject doc = new BasicDBObject();
		doc.append("processus", new BasicDBObject().append("packageName", processus.getPackageName())
				.append("name", processus.getName()).append("version", processus.getVersion()));
		doc.append("instanceDateCreation", new Date());
		doc.append("instanceDateModification", new Date());
		doc.append("actif", true);
		appendKeyValue(doc, mapBody);

		InstanceProcessusBO iP = new InstanceProcessusBO();
		coll.insert(doc);
		iP.setId(doc.get("_id").toString());

		return iP;
	}

	private void updateDateModificationInstanceProcessus(InstanceProcessusBO iPBO) {
		DBCollection coll = db.getCollection("processus");

		BasicDBObject doc = new BasicDBObject();
		doc.append("_id", new ObjectId(iPBO.getId()));

		BasicDBObject docUpdate = new BasicDBObject();
		docUpdate.append("$set", new BasicDBObject("instanceDateModification", new Date()));

		coll.update(doc, docUpdate);
	}

	public StepBO addStepInstanceProcessus(InstanceProcessusBO iP, EventStepTracker est) throws ExecutorException {
		DBCollection coll = db.getCollection("step");

		BasicDBObject doc = new BasicDBObject();
		doc.append("name", est.getStep());
		doc.append("instanceProcessusId", new ObjectId(iP.getId()));
		doc.append("stepDateCreation", new Date());
		doc.append("stepDateModification", new Date());
		doc.append("actif", true);

		StepBO stepBO = new StepBO();
		stepBO.setName(est.getStep());
		coll.insert(doc);
		stepBO.setId(doc.get("_id").toString());

		updateDateModificationInstanceProcessus(iP);

		return stepBO;

	}

	public void desactivateStepInstanceProcessus(StepBO sBO) throws ExecutorException {
		DBCollection coll = db.getCollection("step");

		BasicDBObject doc = new BasicDBObject();
		doc.append("_id", new ObjectId(sBO.getId()));

		BasicDBObject docUpdate = new BasicDBObject();
		docUpdate.append("$set", new BasicDBObject("actif", false).append("stepDateModification", new Date()));

		coll.update(doc, docUpdate);
	}

	private void appendKeyValue(BasicDBObject doc, Map<String, String> mapBody) {
		int nbKey = Integer.valueOf(mapBody.get("nbKey"));

		for (int i = 1; i <= nbKey; i++) {
			doc.append(mapBody.get("key." + i), mapBody.get("value." + i));
		}

	}

}
