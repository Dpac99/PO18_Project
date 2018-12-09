package sth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import sth.exceptions.CannotCloseSurveyException;
import sth.exceptions.CannotOpenSurveyException;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.NoSuchSurveyException;

import java.util.TreeSet;

class Professor extends Person implements Serializable {
    private Map<String, TreeSet<Subject>> _subjects = new HashMap<String, TreeSet<Subject>>();
    /* A TreeSet is used in this situation because:
     *  - Subjects are compared taking it's course into consideration, no two subjects here are equal
     * because this situation is checked beforehand
     *   - If it ever happens to be necessary to search for a specific subject that exists in multiple courses, this 
     * implementation supports logarithmic searches.
     */


    private static final long serialVersionUID = 201810051538L;

    /**
     * Constructor for class Professor
     * 
     * @param name  is the professor's name
     * @param id    is the professor's id
     * @param phone is the String containing the professor's phone number
     */
    public Professor(int id, String phone, String name) {
        super(id, phone, name);
    }

    public boolean hasSubject(Subject s) {
        return _subjects.containsKey(s.getName());
    }

    /**
     * @param s is the subject to be added to this professor's _subjects map.
     */
    public void addSubject(Subject s) {
        if (!hasSubject(s)) {
            _subjects.put(s.getName(), new TreeSet<Subject>());
        }
        _subjects.get(s.getName()).add(s);
    }

    /**
     * @param n is the name of the subject we want to get.
     * @return Subject whose name is the n parameter, in the context of the subjects
     *         given by the professor.
     */
    public Subject getSubject(String n){
        return (_subjects.get(n)!=null?_subjects.get(n).first(): null);
    }

    /**
     * @return String with each pair "Course - Subject" given by the professor
     */
    public String orderSubjects() { //
        ArrayList<String> s = new ArrayList<String>();
        String myEol = System.getProperty("line.separator");
        for (Map.Entry<String, TreeSet<Subject>> it_1 : _subjects.entrySet()) {
            for (Subject subject : it_1.getValue()) {
                String temp = myEol + "* " + subject.getCourse().toString() + " - " + subject.toString();
                s.add(temp);
            }
        }
        Collections.sort(s, new NaturalLanguageCollator());
        String result = "";
        for (String str : s) {
            result += str;
        }
        result += myEol;
        return result;
    }

    /**
     * @return String with the professor's information in the format:
     *         DOCENTE|id|phone|name * Course name - Subject name
     */
    public String accept(ShowUser printer) {
        return printer.showProfessor(this);
    }

    public String[] getDisciplineStudents(String subName) throws InvalidDisciplineException {

        Subject subject = getSubject(subName);
        if(subject == null){
            throw new InvalidDisciplineException();
        }
        return subject.getDisciplineStudents();
    }

    public void createProject(String projName, String subName)
            throws InvalidDisciplineException, InvalidProjectException {

        Subject subject = getSubject(subName);
        if(subject == null){
            throw new InvalidDisciplineException();
        }
        subject.addProject(projName);
    }

    public void closeProject(String projName, String subName)
            throws InvalidDisciplineException, InvalidProjectException, CannotOpenSurveyException {

        Subject subject = getSubject(subName);
        if(subject == null){
            throw new InvalidDisciplineException();
        }
        subject.closeProject(projName);
    }


    public String showSurveyResults(String projName, String subName)
            throws InvalidDisciplineException, InvalidProjectException, NoSuchSurveyException {

        Subject subject = getSubject(subName);
        if(subject == null){
            throw new InvalidDisciplineException();
        }
        SurveyPrinter printer = new ShowSurveyProfessor();
        return subject.showSurveyResults(projName, printer);
    }

    public String showProjectSubmissions(String projName, String subName) throws InvalidProjectException, InvalidDisciplineException{
        Subject s = getSubject(subName);
        if(s == null){
            throw new InvalidDisciplineException();
        }
        return s.showProjectSubmissions(projName);
      }
}