package es.deusto.serialization;

import es.deusto.server.data.Organizer;

public class OrganizerInfo {

    private String name, email;
    private String organization;

    public OrganizerInfo(Organizer organizer) {
        this.name = organizer.getName();
        this.email = organizer.getEmail();
        this.organization = organizer.getOrganization();
    }

    public String getName() {
        return this.name;
    }    

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

}