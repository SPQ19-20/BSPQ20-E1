package es.deusto.server.data;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
// import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
@Inheritance(strategy=InheritanceStrategy.COMPLETE_TABLE)
public class NormalUser extends User {
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
		return "NormalUser [city=" + city + "]";
	}
	
}
