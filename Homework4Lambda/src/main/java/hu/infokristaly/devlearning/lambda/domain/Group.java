package hu.infokristaly.devlearning.lambda.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "name", "persons" })
public class Group {

	private String name;
	private Persons persons;

	public Persons getPersons() {
		return persons;
	}

	public void setPersons(Persons persons) {
		this.persons = persons;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
