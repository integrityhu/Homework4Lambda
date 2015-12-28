package hu.infokristaly.devlearning.lambda;

import hu.infokristaly.devlearning.lambda.domain.Person;

public class CheckPersonEligibleForSelectiveService implements CheckPerson {
    public boolean test(Person p) {
        return p.getGender() == Person.Sex.MALE &&
            p.getAge() >= 18 &&
            p.getAge() <= 25;
    }
}
