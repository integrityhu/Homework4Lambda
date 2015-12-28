package hu.infokristaly.devlearning.lambda.domain;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"person"})
public class Persons {

	public Persons() {
	}

	private Person[] person;

	public Person[] getPerson() {
		return person;
	}

	public void setPerson(Person[] persons) {
		this.person = persons;
	}
	
}
