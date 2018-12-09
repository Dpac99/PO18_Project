package sth;

import java.util.TreeMap;

import sth.exceptions.BadEntryException;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.NoSuchSurveyException;

import java.text.Collator;
import java.util.Locale;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

class Student extends Person implements Serializable {
    private TreeMap<String, Subject> _subjects = new TreeMap<String, Subject>(new NaturalLanguageCollator());

    private static final long serialVersionUID = 201810051538L;

    /**
     * Constructor for class Student
     * 
     * @param name  is the student's name
     * @param id    is the student's id
     * @param phone is the String containing the student's phone number
     */
    public Student(int id, String phone, String name) {
        super(id, phone, name);
    }

    /**
     * Adds a subject to this student's _subjects map
     * 
     * @param subject is the subject to be added to the _subjects map
     */
    public void addSubject(Subject subject) throws BadEntryException {
        if (_subjects.size() > 6)
            throw new BadEntryException("Aluno já tem 6 cadeiras");
        if (_subjects.get(subject.getName()) != null)
            throw new BadEntryException("O aluno já está na cadeira");
        _subjects.put(subject.getName(), subject);

    }

    /**
     * @param n is the name of the subject we want to get.
     * @return Subject whose name is the n parameter, in the context of the subjects
     *         attended by the student.
     */
    public Subject getSubject(String subject){
        return _subjects.get(subject);
    }

    /**
     * @return the Course the student is enrolled in.
     */
    public Course getCourse() {
        return _subjects.firstEntry().getValue().getCourse();
        //Alternative to keeping the Course on the Student class as an attribute.
    }

    public String getSubjects() {
        String course = getCourse().toString();
        String s = "";
        String myEol = System.getProperty("line.separator");
        Iterator<Subject> it = _subjects.values().iterator();
        while (it.hasNext()) {
            s += myEol + "* " + course + " - " + it.next().toString();
        }
        return s + myEol;
    }

    public String accept(ShowUser printer) {
        return printer.showStudent(this);
    }

    public void deliverProject(String projName, String subName, String message)
            throws InvalidDisciplineException, InvalidProjectException {

        Subject sub = getSubject(subName);
        if (sub == null) {
            throw new InvalidDisciplineException();
        }
        sub.deliverProject(projName, message, getID());
    }

    public boolean isRepresentative() {
        return getCourse().isRepresentative(getID());
    }

    public void answerSurvey(String projName, String subName, SurveyAnswer answer, int id)
            throws InvalidDisciplineException, InvalidProjectException, NoSuchSurveyException {

        Subject sub = getSubject(subName);
        if (sub == null) {
            throw new InvalidDisciplineException();
        }

        sub.answerSurvey(projName, answer, id);
    }

    public boolean canSeeSurvey(String projName, String subName)
            throws InvalidDisciplineException, InvalidProjectException {

        Subject sub = _subjects.get(subName);
        if (sub == null) {
            throw new InvalidDisciplineException();
        }
        return sub.canSeeSurvey(projName, getID());     
    }

    public String showSurveyResults(String projName, String subName)
            throws InvalidDisciplineException, InvalidProjectException, NoSuchSurveyException {
        
        //Checks if everything exists (the subject, the project and its survey) and if the student submitted a project.
        if(!canSeeSurvey(projName, subName)){ 
            throw new NoSuchSurveyException();
        }

        SurveyPrinter printer = new ShowSurveyStudent();
        Subject sub = _subjects.get(subName);
        if(sub == null){
            throw new InvalidDisciplineException();
        }
        return sub.showSurveyResults(projName , printer);
    }
}