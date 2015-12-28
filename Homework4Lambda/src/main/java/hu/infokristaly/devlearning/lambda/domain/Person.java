package hu.infokristaly.devlearning.lambda.domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import sun.util.calendar.CalendarDate;

@XmlType(propOrder = {"name","birthday","gender","emailAddress"})
public class Person {

    public enum Sex {
        MALE, FEMALE
    }

    private String name;
    
    private LocalDate birthday;
    private Sex gender;
    private String emailAddress;
    
    public int getAge() {
        Date now = new Date(); 
        Calendar newCDate = new GregorianCalendar();
        newCDate.setTime(now);
        Period period = birthday.until(LocalDate.of(newCDate.get(Calendar.YEAR), newCDate.get(Calendar.MONTH), newCDate.get(Calendar.DAY_OF_MONTH)));        
        return period.getYears();
    }
    
    public void printPerson() {
        System.out.println(this.name);
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getBirthday() {
        return birthday;
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public Sex getGender() {
        return gender;
    }
    public void setGender(Sex gender) {
        this.gender = gender;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    
}