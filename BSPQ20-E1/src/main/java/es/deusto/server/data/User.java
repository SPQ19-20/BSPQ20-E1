package es.deusto.server.data;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import es.deusto.server.data.GenericUser;
// import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
@Inheritance(strategy=InheritanceStrategy.COMPLETE_TABLE)
public class User extends GenericUser {
	// TODO change the name of this class
	private String city;
	// public User(String code, String name) {
	// 	this.code = code;
	// 	this.name = name;
	// }

	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "User [name= " + getName() +" city=" + city + "]";
	}
	
}
