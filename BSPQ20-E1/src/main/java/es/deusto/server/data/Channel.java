package es.deusto.server.data;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
// import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
@Inheritance(strategy=InheritanceStrategy.COMPLETE_TABLE)
public class Channel {

	//a ID is created automatically
	private String name;	
	// public User(String code, String name) {
	// 	this.code = code;
	// 	this.name = name;
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Channel [name=" + name + "]";
	}
	
}
