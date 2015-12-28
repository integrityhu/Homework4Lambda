package hu.infokristaly.devlearning.lambda;

import hu.infokristaly.devlearning.lambda.domain.Group;
import hu.infokristaly.devlearning.lambda.domain.Person;
import hu.infokristaly.devlearning.lambda.domain.Persons;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import javafx.util.Pair;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Hello world!
 * 
 */
interface CheckPerson {
	boolean test(Person p);
}

public class App {
	public static void main(String[] args) throws JAXBException, IOException {

		System.out.println("Persist to XML...");
		System.out.println("==============");
		System.out.println();
		persistGroup();

		System.out.println("Backload list ...");
		System.out.println("==============");
		System.out.println();
		Person[] personArray = readbackGroup();
		ArrayList<Person> persons = new ArrayList<Person>();

		persons.addAll(Arrays.asList(personArray));

		System.out.println();
		System.out.println("Static method:");
		System.out.println("==============");
		printPersonsOlderThan(persons, 38);
		System.out.println();

		System.out.println("Checker classes:");
		System.out.println("================");
		printPersons(persons, new CheckPersonEligibleForSelectiveService());

		printPersons(persons, new CheckPerson() {
			public boolean test(Person p) {
				return p.getGender() == Person.Sex.MALE && p.getAge() >= 18
						&& p.getAge() <= 40;
			}
		});
		System.out.println();

		System.out.println("Lambda 1:");
		System.out.println("=========");
		printPersons(persons, (Person p) -> p.getGender() == Person.Sex.FEMALE
				&& p.getAge() >= 13 && p.getAge() <= 25);
		System.out.println();

		System.out.println("Lambda 2:");
		System.out.println("=========");
		processElements(
				persons,
				p -> p.getGender() == Person.Sex.MALE && p.getAge() >= 13
						&& p.getAge() <= 40, p -> p.getEmailAddress(),
				email -> System.out.println(email));
		System.out.println();
		
		System.out.println("Lambda 3:");
		System.out.println("=========");
		persons
	    .stream()
	    .filter(
	        p -> p.getGender() == Person.Sex.MALE
	            && p.getAge() >= 13
	            && p.getAge() <= 40)
	    .map(p -> p.getEmailAddress())
	    .forEach(email -> System.out.println(email));
		System.out.println();
		
		System.out.println("Lambda 4:");
		System.out.println("=========");

		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.stream().forEach(i -> System.out.println(i));
		arrayList.add("first element");
		arrayList.add("second element");
		
		IntStream.range(0, arrayList.size())
        .mapToObj(i -> new Pair<Integer,String>(i, arrayList.get(i)))
        .forEach(System.out::println);
	}

	private static void persistGroup() throws JAXBException, IOException {
		Class<?>[] classes = { Person.class, Group.class, Persons.class };
		JAXBContext jaxbContext = JAXBContext.newInstance(classes, null);		

		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

		FileWriter writer = new FileWriter(new File("c:/temp/persons.xml"));
		Group group = getGroup();
		marshaller.marshal(group, writer);
		writer.close();
	}

	private static Group getGroup() {
		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();
		person.setBirthday(LocalDate.of(1977, 2, 8));
		person.setName("Zoltán");
		person.setGender(Person.Sex.MALE);
		person.setEmailAddress("papp.zoltan@integrity.hu");
		persons.add(person);
		Person person2 = new Person();
		person2.setBirthday(LocalDate.of(1953, 7, 9));
		person2.setName("Lajos");
		person2.setGender(Person.Sex.MALE);
		persons.add(person2);
		Person person3 = new Person();
		person3.setBirthday(LocalDate.of(1953, 7, 19));
		person3.setName("Anikó");
		person3.setGender(Person.Sex.FEMALE);
		persons.add(person3);
		Person person4 = new Person();
		person4.setBirthday(LocalDate.of(1953, 7, 29));
		person4.setName("Mónika");
		person4.setGender(Person.Sex.FEMALE);
		persons.add(person4);
		Person person5 = new Person();
		person5.setBirthday(LocalDate.of(1999, 5, 20));
		person5.setName("Szilvia");
		person5.setGender(Person.Sex.FEMALE);
		persons.add(person5);

		Group group = new Group();
		group.setName("teszt");
		Persons personsObj = new Persons();
		Person[] personArray = {};
		personsObj.setPerson(persons.toArray(personArray));

		group.setPersons(personsObj);

		return group;
	}

	public static Person[] readbackGroup() throws JAXBException, IOException {
		Class<?>[] classes = { Person.class, Persons.class, Group.class };
		JAXBContext jaxbContext = JAXBContext.newInstance(classes, null);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		FileReader reader = new FileReader(new File("c:/temp/persons.xml"));
		Group inGroup = (Group) unmarshaller.unmarshal(reader);
		reader.close();
		return inGroup.getPersons().getPerson();
	}

	public static void printPersonsOlderThan(List<Person> roster, int age) {
		for (Person p : roster) {
			if (p.getAge() >= age) {
				p.printPerson();
			}
		}
	}

	public static void printPersonsWithinAgeRange(List<Person> roster, int low,
			int high) {
		for (Person p : roster) {
			if (low <= p.getAge() && p.getAge() < high) {
				p.printPerson();
			}
		}
	}

	public static void printPersons(List<Person> roster, CheckPerson tester) {
		for (Person p : roster) {
			if (tester.test(p)) {
				p.printPerson();
			}
		}
	}

	public static <X, Y> void processElements(Iterable<X> source,
			Predicate<X> tester, Function<X, Y> mapper, Consumer<Y> block) {
		for (X p : source) {
			if (tester.test(p)) {
				Y data = mapper.apply(p);
				block.accept(data);
			}
		}
	}

}
