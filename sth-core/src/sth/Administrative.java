package sth;

import java.io.Serializable;

class Administrative extends Person implements Serializable{

    private static final long serialVersionUID = 201810051538L;

    /**
    * Constructor for class Administrative
    * @param name is the administrative's name
    * @param id is the administrative's id
    * @param phone is the String containing the administrative's phone number
    */
    public Administrative(int id, String phone, String name){
        super(id, phone, name);
    }

    /**
     * @return String with the administrative's information in the format:
     *          FUNCIONÁRIO|id|phone|name
     *          * Course name - Subject name 
     */
    public String accept(ShowUser printer){
        return printer.showAdmin(this);
    }

    public String showSurveyResults(String projName, String subName){
        return "";
    }
}