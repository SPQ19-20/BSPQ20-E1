package es.deusto.server.data;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
// import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
@Inheritance(strategy=InheritanceStrategy.COMPLETE_TABLE)
public abstract class GenericUser {

	long id;
	private String name, email,password;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	@Override
	public String toString() {
		return "GenericUser [id=" + id + ", name=" + name + "]";
	}
	
}
