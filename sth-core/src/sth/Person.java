package sth;

import java.io.Serializable;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.NoSuchSurveyException;
import java.util.List;
import java.util.LinkedList;

abstract class Person implements Observer, Comparable<Person>, Serializable {
    private String _name;
    private String _phone;
    private int _id;
    private List<String> _notifs = new LinkedList<String>();

    private static final long serialVersionUID = 201810051538L;

    /**
     * Constructor for class Person
     * 
     * @param name  is the person's name
     * @param id    is the person's id
     * @param phone is the String containing the person's phone number
     */
    public Person(int id, String phone, String name) {
        _id = id;
        _phone = phone;
        _name = name;
    }

    /**
     * Changes a person's phone number
     * 
     * @param number is the String containing the person's phone number
     */
    public void setPhoneNumber(String number) {
        _phone = number;
    }

    /**
     * @return the person's phone number (String)
     */
    public String getPhoneNumber() {
        return _phone;
    }

    /**
     * @return the person's id (int)
     */
    public int getID() {
        return _id;
    }

    /**
     * @return the person's name (String)
     */
    public String getName() {
        return _name;
    }

    /**
     * Implementation of the 'compareTo' method from the Comparable interface.
     * Compares persons by their name using Natural Language as reference.
     * 
     * @param p is the person to compare to.
     * @return a negative integer, zero, or a positive integer as this person's name
     *         is less than, equal to, or greater than the specified person's name.
     */
    public int compareTo(Person p) {
        NaturalLanguageCollator comparator = new NaturalLanguageCollator();
        return comparator.compare(getName(), p.getName());
    }

    /**
     * @return String with the person's information in the format: id|phone|name
     */
    public String toString() {
        return _id + "|" + _phone + "|" + _name;
    }

    public abstract String accept(ShowUser printer);

    public abstract String showSurveyResults(String projName, String subName)
            throws InvalidDisciplineException, InvalidProjectException, NoSuchSurveyException;

    public void getNotification(String notif){
        _notifs.add(notif);
    }

    public String showNotifications(){
        String myEol = System.getProperty("line.separator");
        String s="";
        for(String notif : _notifs){
            s+=notif + myEol;
        }
        _notifs.clear();
        return s;
    }


}