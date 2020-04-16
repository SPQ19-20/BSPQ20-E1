package es.deusto.server.data;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
// import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.PrimaryKey;

import es.deusto.serialization.TopicInfo;

@PersistenceCapable(detachable="true")
public class Topic {

	//a ID is created automatically

	// @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	// private long _id;
	// @PrimaryKey
	private String name;	
	// public User(String code, String name) {
	// 	this.code = code;
	// 	this.name = name;
	// }
	public Topic() {
		
	}

	public Topic(TopicInfo info) {
		this.name = info.getName();
	}

	// public long getID() {
	// 	return _id;
	// }
	
	// public void setID(long id) {
	// 	this._id = id;
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
