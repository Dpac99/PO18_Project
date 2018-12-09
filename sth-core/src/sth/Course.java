package sth;

import java.util.TreeMap;
import java.util.Map;

import sth.exceptions.BadEntryException;
import sth.exceptions.DoubleProjectException;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.HasAnswersException;
import sth.exceptions.FinishedSurveyException;

import sth.exceptions.DoubleSurveyException;
import sth.exceptions.CannotOpenSurveyException;
import sth.exceptions.CannotCloseSurveyException;
import sth.exceptions.CannotFinishSurveyException;
import sth.exceptions.NoSuchSurveyException;

import java.io.Serializable;

class Course implements Serializable {
    private String _name;
    private Map<String, Subject> _subjects = new TreeMap<String, Subject>();
    private Map<Integer, Student> _students = new TreeMap<Integer, Student>();
    private Map<Integer, Professor> _professors = new TreeMap<Integer, Professor>();
    private Map<Integer, Person> _representatives = new TreeMap<Integer, Person>();

    private static final long serialVersionUID = 201810051538L;

    /**
     * Constructor for class Course.
     * 
     * @param name is the course's name.
     */
    public Course(String name) {
        _name = name;
    }

    /**
     * Adds a student to this Course.
     * 
     * @param student is the student to be added to this course's _students map.
     */
    public void addStudent(Student student) throws BadEntryException {
        if(_students.get(student.getID()) != null ){
            _students.put(student.getID(), student);
        }
    }

    /**
     * Adds a subject to this Course.
     * 
     * @param subject is the subject to be added to this course's _subjects map.
     */
    public void addSubject(Subject subject) {
        if(_subjects.get(subject.getName()) ==  null){
            _subjects.put(subject.getName(), subject);
        }
    }

    public boolean hasSubject(String subName){
        return (_subjects.get(subName)!= null);
    }
    /**
     * Adds a representative to this Course, if possible.
     * 
     * @param p is the person to be added to this course's _representatives map.
     * @throws BadEntryException
     */
    public void addRepresentative(Person p) throws BadEntryException {
        if (_representatives.size() > 7)
            throw new BadEntryException("O curso já atingiu o limite de delegados.");
        if (_representatives.get(p.getID())!= null){
            throw new BadEntryException("A pessoa com id "+ p.getID()+ " já é delegada do curso "+ getName());
        }
        _representatives.put(p.getID(), p);   
    }

    public void registerObservers(){
        for(Subject s: _subjects.values()){
            for(Person  p: _representatives.values()){
                s.registerObserver(p);
            }
        }
    }

    /**
     * Adds a professor to this Course.
     * 
     * @param prof is the professor to be added to this course's _professors map.
     */
    public void addProfessor(Professor prof) {
        if (_professors.get(prof.getID()) == null){
            _professors.put(prof.getID(), prof);
        }
    }

    /**
     * @param id is the id of the student we want to get.
     * @return Student with the specified id, in the context of the students
     *         enrolled in this course.
     */
    public Student getStudent(Integer id) {
        return _students.get(id);
    }

    public String getName() {
        return _name;
    }

    /**
     * @param name is the name of the subject we want to get.
     * @return Subject with the entered name, in the context of the subjects of this
     *         course.
     */
    public Subject getSubject(String name) {
        return _subjects.get(name);
    }

    /**
     * @param id is the id of the representative we want to get.
     * @return Person with the specified id, in the context of the representatived
     *         of this course.
     */
    public Person getRepresentative(int id) {
        return _representatives.get(id);
    }

    /**
     * @param id is the id of the professor we want to get.
     * @return Professor with the specified id, in the context of the professors
     *         teaching in this course.
     */
    public Professor getProfessor(int id) {
        return _professors.get(id);
    }

    public void createProjectSurvey(String projName, String subName)
            throws InvalidDisciplineException, InvalidProjectException, DoubleSurveyException {

        Subject sub = getSubject(subName);
        if(sub == null){
            throw new InvalidDisciplineException();
        }
        sub.createProjectSurvey(projName);
    }

    public void openProjectSurvey(String projName, String subName) throws InvalidDisciplineException,
            CannotOpenSurveyException, InvalidProjectException, NoSuchSurveyException {

        Subject sub = getSubject(subName);
        if(sub == null){
            throw new InvalidDisciplineException();
        }
        sub.openProjectSurvey(projName);
    }

    public void closeProjectSurvey(String projName, String subName) throws InvalidDisciplineException,
            CannotCloseSurveyException, InvalidProjectException, NoSuchSurveyException {

        Subject sub = getSubject(subName);
        if(sub == null){
            throw new InvalidDisciplineException();
        }
        sub.closeProjectSurvey(projName);
    }

    public void cancelProjectSurvey(String projName, String subName) throws InvalidDisciplineException,
            InvalidProjectException, NoSuchSurveyException, HasAnswersException, FinishedSurveyException {

        Subject sub = getSubject(subName);
        if(sub == null){
            throw new InvalidDisciplineException();
        }
        sub.cancelProjectSurvey(projName);
    }

    public void finishProjectSurvey(String projName, String subName) throws InvalidDisciplineException,
            CannotFinishSurveyException, InvalidProjectException, NoSuchSurveyException {

        Subject sub = getSubject(subName);
        if(sub == null){
            throw new InvalidDisciplineException();
        }
        sub.finishProjectSurvey(projName);
    }

    public boolean isRepresentative(int id){
        return _representatives.get(id) != null;
    }

    public String[] showDisciplineSurveys(String subName) throws InvalidDisciplineException{
        Subject sub = getSubject(subName);
        if (sub == null){
            throw new InvalidDisciplineException();
        }

        return sub.showDisciplineSurveys();
    }

    /**
     * @return String with the course's name.
     */
    public String toString() {
        return _name;
    }
}