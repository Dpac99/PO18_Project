package sth;

import java.io.Serializable;
import java.util.TreeMap;
    

import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;

import sth.exceptions.BadEntryException;
import sth.exceptions.InvalidProjectException;

import sth.exceptions.DoubleSurveyException;
import sth.exceptions.FinishedSurveyException;
import sth.exceptions.HasAnswersException;
import sth.exceptions.CannotOpenSurveyException;
import sth.exceptions.CannotCloseSurveyException;
import sth.exceptions.NoSuchSurveyException;
import sth.exceptions.CannotFinishSurveyException;

class Subject implements Serializable, Comparable<Subject>, Target {
    private String _name;
    private Map<Integer, Student> _students = new TreeMap<Integer, Student>();
    private Map<Integer, Professor> _professors = new TreeMap<Integer, Professor>();
    private Course _course;
    private int _capacity = 20;
    private int _studentCount;
    private Map<String, Project> _projects = new TreeMap<String, Project>();
    private Set<Observer> _observers = new HashSet<Observer>();

    private static final long serialVersionUID = 201810051538L;

    /**
     * Constructor for class Subject.
     * 
     * @param name is the subject's name.
     */
    public Subject(Course course, String name) {
        _course = course;
        _name = name;
        _studentCount = 0;
    }

    /**
     * Adds a student to this Subject.
     * 
     * @param student is the student to be added to this subject's _students map.
     */
    public void addStudent(Student student) throws BadEntryException {
        if (_studentCount == _capacity) {
            throw new BadEntryException("Cadeira cheia.");
        }
        if (_students.get(student.getID()) != null){
            throw new BadEntryException("O alnuno com id "+student.getID() + " já existe na cadeira "+ getName());
        }
        _students.put(student.getID(), student);
        _studentCount++;
    }

    /**
     * Adds a professor to this Subject.
     * 
     * @param prof is the professor to be added to this subject's _professors map.
     */
    public void addProfessor(Professor prof) throws BadEntryException{
        if(_professors.get(prof.getID()) != null){
            throw new BadEntryException("O professor com id "+ prof.getID()+ " já existe na cadeira "+ getName());
        }
        _professors.put(prof.getID(), prof);
    }

    /**
     * Adds a project to this Subject.
     * 
     * @param proj is the project to be added to this subject's _projects map.
     */
    public void addProject(String proj) throws InvalidProjectException {
        Project project = getProject(proj);

        if (project != null) {
            throw new InvalidProjectException();
        }

        project = new Project(proj);
        _projects.put(proj, project);
    }

    public void closeProject(String projName) throws InvalidProjectException, CannotOpenSurveyException {

        Project proj = getProject(projName);

        if (proj == null)
            throw new InvalidProjectException();

        if(proj.closeProject()){
            String s  = "Pode preencher inquérito do projecto " + projName + " da disciplina "+getName();
            notifyObservers(s);
        }
    }

    /**
     * @return the subject's name (String).
     */
    public String getName() {
        return _name;
    }

    /**
     * @return the subjects's Course (Course).
     */
    public Course getCourse() {
        return _course;
    }

    /**
     * @param id , id of the Professor we want to get.
     * @return Professor with the entered id, in the context of the professors that
     *         teach this subject.
     */
    public Professor getProfessor(int id) {
        return _professors.get(id);
    }

    /**
     * @param projName , name of the Project we want to get.
     * @return Project with the entered name, in the context of the projects of this
     *         subject.
     */
    public Project getProject(String projName){
        return _projects.get(projName);
    }


    public String getCourseName() {
        return _course.getName();
    }

    /**
     * @return String with the course's name.
     */
    public String toString() {
        return _name;
    }

    /**
     * @return TreeMap<Integer, Students>, which key is each student's id,
     *         containing every student enrolled in this subject.
     */
    public String[] getDisciplineStudents() {
        String[] students = new String[_students.size()];
        ShowUser printer = new ShowPerson();
        int i = 0;
        Iterator<Student> it = _students.values().iterator();
        while (it.hasNext()) {
            students[i] = it.next().accept(printer);
            i++;
        }
        return students;
    }

    public void createProjectSurvey(String projName) throws DoubleSurveyException, InvalidProjectException {
        Project proj = getProject(projName);
        if(proj == null){
            throw new InvalidProjectException();
        }
        proj.createSurvey();
    }

    public void cancelProjectSurvey(String projName)
            throws NoSuchSurveyException, InvalidProjectException, HasAnswersException, FinishedSurveyException {

        String s  = "Pode preencher inquérito do projecto " + projName + " da disciplina "+getName();
        Project proj = getProject(projName);
        if(proj == null){
            throw new InvalidProjectException();
        }
        if(proj.cancelSurvey()){ //true means survey has been reopened
            notifyObservers(s);
        }
    }

    public int compareTo(Subject s) {
        return getCourseName().compareTo(s.getCourseName());
    }

    public void openProjectSurvey(String projName)
            throws InvalidProjectException, CannotOpenSurveyException, NoSuchSurveyException {

        Project proj = getProject(projName);
        if(proj == null){
            throw new InvalidProjectException();
        }
        proj.openSurvey();
        String s  = "Pode preencher inquérito do projecto " + projName + " da disciplina "+getName();
        notifyObservers(s);
    }

    public void closeProjectSurvey(String projName)
            throws InvalidProjectException, CannotCloseSurveyException, NoSuchSurveyException {

        Project proj = getProject(projName);
        if(proj == null){
            throw new InvalidProjectException();
        }
        proj.closeSurvey();
    }

    public void finishProjectSurvey(String projName)
            throws InvalidProjectException, CannotFinishSurveyException, NoSuchSurveyException {

        Project proj = getProject(projName);
        if(proj == null){
            throw new InvalidProjectException();
        }
        proj.finishSurvey();
        String s  = "Resultados do inquérito do projecto " + projName + " da disciplina "+getName();
        notifyObservers(s);
    }

    public void deliverProject(String projName, String message, int id) throws InvalidProjectException {

        Project proj = getProject(projName);
        if(proj == null){
            throw new InvalidProjectException();
        }
        proj.deliverProject(message, id);
    }

    public void answerSurvey(String projName, SurveyAnswer answer, int id)
            throws InvalidProjectException, NoSuchSurveyException {

        Project proj = getProject(projName);
        if(proj == null){
            throw new InvalidProjectException();
        }
        proj.answerSurvey(answer, id);
    }

    public boolean canSeeSurvey(String projName, int id) throws InvalidProjectException {
        Project proj = getProject(projName);
        if(proj == null){
            throw new InvalidProjectException();
        }
        return proj.canSeeSurvey(id);  
    }

    public String showSurveyResults(String projName, SurveyPrinter printer) throws InvalidProjectException, NoSuchSurveyException{
        Project proj = getProject(projName);
        if(proj == null){
            throw new InvalidProjectException();
        }
        printer.setSubName(getName());
        printer.setProject(proj);
        return proj.showSurveyResults(printer);
    }

    public void notifyObservers(String s){
        for(Observer o : _observers){
            o.getNotification(s);
        }
    }

    public void registerObserver(Observer o){
        //if(!_observers.contains(o))
            _observers.add(o);
    }

    public void removeObserver(Observer o){
        _observers.remove(o);
    }

    public String showProjectSubmissions(String projName) throws InvalidProjectException{
        String myEol = System.getProperty("line.separator");
        if(getProject(projName) == null){
            throw new InvalidProjectException();
        }
        String s = getName() + " - " + projName + myEol;
        return s + getProject(projName).toString();
      }

    public String[] showDisciplineSurveys(){
        String[] surveys = new String[_projects.size()];
        SurveyPrinter printer = new ShowSurveyRepresentative();
        int i = 0;
        for(Map.Entry<String,Project> c: _projects.entrySet()){
            printer.setSubName(getName());
            printer.setProject(c.getValue());
            surveys[i] = c.getValue().printSurvey(printer);
            i++;
        }
        return surveys;
    }
}