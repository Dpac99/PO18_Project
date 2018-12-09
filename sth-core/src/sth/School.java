package sth;

import java.io.BufferedReader;
import java.io.FileReader;

import sth.exceptions.*;

import java.io.Serializable;
import java.io.IOException;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;


import java.util.Collection;
import java.util.Iterator;
import java.util.Collections;

/**
 * School implementation.
 */
public class School implements Serializable {

  private Map<Integer, Person> _persons = new TreeMap<Integer, Person>();
  private Map<Integer, Person> _students = new TreeMap<Integer, Person>();
  private Map<Integer, Person> _professors = new TreeMap<Integer, Person>();
  private Map<Integer, Person> _administratives = new TreeMap<Integer, Person>();
  private Map<String, Course> _courses = new TreeMap<String, Course>();
  private int _maxId = 100000;

  private static final long serialVersionUID = 201810051538L;

  /**
   * Factory-like function. Reads from file and slices into packages with person
   * info.
   * 
   * @param filename
   * @throws BadEntryException
   * @throws IOException
   */
  public void importFile(String filename) throws IOException, BadEntryException {
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    ArrayList<String[]> fields = new ArrayList<String[]>();
    line = reader.readLine();
    while (line != null) {
      fields.add(line.split("\\|"));
      line = reader.readLine();
      while (line != null && line.startsWith("#")) {
        fields.add(line.substring(2).split("\\|"));
        line = reader.readLine();
      }
      try {
        register(fields);
      } catch (BadEntryException e) {
        reader.close();
        throw new BadEntryException(e.getEntrySpecification());
      }
      fields.clear();
    }
    reader.close();
    registerObservers();
  }

  public void registerObservers(){
    for(Course c : _courses.values()){
      c.registerObservers();
    }
  }

  /**
   * Parses and decides what kind of person should be built
   * 
   * @param fields -> the information packages from importFile
   * @throws BadEntryException
   */
  public void register(ArrayList<String[]> fields) throws BadEntryException {
    if (fields.get(0)[0].equals("ALUNO") || fields.get(0)[0].equals("DELEGADO")) {
      registerStudent(fields);
    } else if (fields.get(0)[0].equals("DOCENTE")) {
      registerProfessor(fields);
    } else if (fields.get(0)[0].equals("FUNCIONÁRIO")) {
      registerAdministrative(fields);
    }
  }

  /**
   * Student and representative factory
   * 
   * @param fields
   * @throws BadEntryException
   */
  public void registerStudent(ArrayList<String[]> fields) throws BadEntryException {
    int id;
    String phone;
    String name;
    String course_name;
    try {
      id = Integer.parseInt(fields.get(0)[1]);
      phone = fields.get(0)[2];
      name = fields.get(0)[3];
      course_name = fields.get(1)[0];
    } catch (IndexOutOfBoundsException e) {
      throw new BadEntryException(e.getMessage());
    }

    checkId(id);
    Student student = new Student(id, phone, name);
    _persons.put(id, student);
    _students.put(id, student);

    Course course = _courses.get(course_name);
    if (course == null) {
      course = new Course(course_name);
      _courses.put(course_name, course);
    }
    course.addStudent(student);

    Subject subject = course.getSubject(fields.get(1)[1]);
    if (!course.hasSubject(fields.get(1)[1])) {
      subject = new Subject(course, fields.get(1)[1]);
    }

    course.addSubject(subject);
    student.addSubject(subject);
    subject.addStudent(student);
    subject.registerObserver(student);

    for (int i = 2; i < fields.size(); i++) {
      if (!course.toString().equals(fields.get(i)[0]))
        throw new BadEntryException("Curso errado.\n");

      subject = course.getSubject(fields.get(i)[1]);
      if (!course.hasSubject(fields.get(i)[1])) {
        subject = new Subject(course, fields.get(i)[1]);
      }

      course.addSubject(subject);
      subject.addStudent(student);
      student.addSubject(subject);
      subject.registerObserver(student);
    }

    if (fields.get(0)[0].equals("DELEGADO")) {
      course.addRepresentative(student);
    }
  }

  /**
   * Professor Factory
   * 
   * @param fields
   * @throws BadEntryException
   */
  public void registerProfessor(ArrayList<String[]> fields) throws BadEntryException {
    int id = Integer.parseInt(fields.get(0)[1]);
    String phone = fields.get(0)[2];
    String name = fields.get(0)[3];

    checkId(id);
    Professor professor = new Professor(id, phone, name);
    _persons.put(id, professor);
    _professors.put(id, professor);

    for (int i = 1; i < fields.size(); i++) {
      String course_name = fields.get(i)[0];
      Course course = _courses.get(course_name);
      if (course == null) {
        course = new Course(course_name);
        _courses.put(course_name, course);
      }
      course.addProfessor(professor);

      Subject subject = course.getSubject(fields.get(i)[1]);
      if (!course.hasSubject(fields.get(i)[1])) {
        subject = new Subject(course, fields.get(i)[1]);
      }
      course.addSubject(subject);
      subject.addProfessor(professor);
      subject.registerObserver(professor);
      professor.addSubject(subject);
    }
  }

  /**
   * Administrative Factory
   * 
   * @param fields
   * @throws BadEntryException
   */
  public void registerAdministrative(ArrayList<String[]> fields) throws BadEntryException {
    int id = Integer.parseInt(fields.get(0)[1]);
    String phone = fields.get(0)[2];
    String name = fields.get(0)[3];

    checkId(id);
    Administrative admin = new Administrative(id, phone, name);
    _persons.put(id, admin);
    _administratives.put(id, admin);
  }

  /**
   * Person getter
   * 
   * @param id
   * @return Person
   */
  public Person getPerson(int id) {
    return _persons.get(id);
  }

  /**
   * Student getter
   * 
   * @param id
   * @return Person from the _students list.
   */
  public Person getStudent(int id) {
    return _students.get(id);
  }

  /**
   * Professor getter
   * 
   * @param id
   * @return Person from the _professors list.
   */
  public Person getProfessor(int id) {
    return _professors.get(id);
  }

  /**
   * Administrative getter
   * 
   * @param id
   * @return Person from the _administratives list.
   */
  public Person getAdministrative(int id) {
    return _administratives.get(id);
  }

  /**
   * Representative getter
   * 
   * @param id
   * @return Person from one of the _courses' _representatives list
   */
  public Person getRepresentative(int id) {
    Person rep;
    Iterator<Course> c = _courses.values().iterator();
    while (c.hasNext()) {
      if ((rep = c.next().getRepresentative(id)) != null) {
        return rep;
      }
    }
    return null;
  }

  /**
   * Checks if ID already exists
   * 
   * @param id
   * @throws BadEntryException
   */
  public void checkId(int id) throws BadEntryException {
    if (_persons.get(id) != null)
      throw new BadEntryException("ID já existe.\n");
    else if (id < 100000)
      throw new BadEntryException("ID inválido.\n");
    else if (id > _maxId)
      _maxId = id;
  }

  /**
   * Getter for persons as strings
   * 
   * @return String array with each person's string representation in it
   */
  public String[] getAllPersons() {
    ShowUser printer = new ShowPerson();
    String[] everyone = new String[_persons.size()];
    int i = 0;
    Iterator<Person> it = _persons.values().iterator();
    while (it.hasNext()) {
      everyone[i] = it.next().accept(printer);
      i++;
    }
    return everyone;
  }

  /**
   * Getter for all students of a subject
   * 
   * @param subName Name of the subject
   * @param profId  Id of the professor that teaches the 'subName' subject.
   * @return String array with the toString result of each of the 'subName'
   *         subject's students
   * @throws InvalidDisciplineException
   */
  public String[] getDisciplineStudents(String subName, int profId) throws InvalidDisciplineException {
    Professor p = (Professor) getProfessor(profId);
    return p.getDisciplineStudents(subName);
  }

  /**
   * @param subName  Name of the subject in which we want to create a project
   * @param profId   Id of the professor that wants to create the project
   * @param projName Name of the project to be created
   * @throws InvalidDisciplineException
   * @throws DoubleProjectException
   */
  public void createProject(String projName, String subName, int profId)
      throws InvalidDisciplineException, InvalidProjectException {
    Professor p = (Professor) getProfessor(profId);
    p.createProject(projName, subName);
  }

  /**
   * 
   * @param subName  Name of the subject in which we want to close a project
   * @param profId   Id of the professor that wants to close the project
   * @param projName Name of the project to be closed
   * @throws InvalidDisciplineException
   * @throws DoubleProjectException
   */
  public void closeProject(String projName, String subName, int profId)
      throws InvalidDisciplineException, InvalidProjectException, CannotOpenSurveyException {
    Professor p = (Professor) getProfessor(profId);
    p.closeProject(projName, subName);
  }

  /**
   * 
   * @param pattern "Name"/input inserted by the logged in user
   * @return String array with the toString of every person that matches the
   *         pattern
   */
  public String[] searchPerson(String pattern) {
    ArrayList<Person> matches = new ArrayList<Person>();
    Iterator<Person> person_it = _persons.values().iterator();
    ShowUser printer = new ShowPerson();
    while (person_it.hasNext()) {
      Person p = person_it.next();
      if (p.getName().contains(pattern)) {
        matches.add(p);
      }
    }
    Collections.sort(matches);
    String[] toStrings = new String[matches.size()];
    person_it = matches.iterator();
    int i = 0;
    while (person_it.hasNext()) {
      toStrings[i++] = person_it.next().accept(printer);
    }
    return toStrings;
  }

  /**
   * 
   * @param id    Id of the person that wants to change its phone number
   * @param phone New phone number
   */
  public void changePhoneNumber(int id, String phone) {
    Person p = getPerson(id);
    p.setPhoneNumber(phone);
  }

  /**
   * 
   * @param id Id of the person to be shown
   * @return the toString of the person
   */
  public String showPerson(int id) {
    ShowUser printer = new ShowPerson();
    Person p = getPerson(id);
    return p.accept(printer);
  }

  public void createProjectSurvey(String projName, String subName, int id)
      throws InvalidDisciplineException, InvalidProjectException, DoubleSurveyException {

    Student rep = (Student) getRepresentative(id);
    Course course = rep.getCourse();
    course.createProjectSurvey(projName, subName);
  }

  public void openProjectSurvey(String projName, String subName, int id)
      throws InvalidDisciplineException, InvalidProjectException, CannotOpenSurveyException, NoSuchSurveyException {

    Student rep = (Student) getRepresentative(id);
    Course course = rep.getCourse();
    course.openProjectSurvey(projName, subName);
  }

  public void closeProjectSurvey(String projName, String subName, int id)
      throws InvalidDisciplineException, InvalidProjectException, CannotCloseSurveyException, NoSuchSurveyException {

    Student rep = (Student) getRepresentative(id);
    Course course = rep.getCourse();

    course.closeProjectSurvey(projName, subName);
  }

  public void cancelProjectSurvey(String projName, String subName, int id) throws InvalidDisciplineException,
      InvalidProjectException, NoSuchSurveyException, HasAnswersException, FinishedSurveyException {

    Student rep = (Student) getRepresentative(id);
    Course course = rep.getCourse();
    course.cancelProjectSurvey(projName, subName);
  }

  public void finishProjectSurvey(String projName, String subName, int id)
      throws InvalidDisciplineException, InvalidProjectException, CannotFinishSurveyException, NoSuchSurveyException {

    Student rep = (Student) getRepresentative(id);
    Course course = rep.getCourse();

    course.finishProjectSurvey(projName, subName);
  }

  public void deliverProject(String projName, String subName, String message, int id)
      throws InvalidDisciplineException, InvalidProjectException {

    Student s = (Student) getStudent(id);
    s.deliverProject(projName, subName, message);
  }

  public void answerSurvey(String projName, String subName, SurveyAnswer answer, int id)
      throws InvalidDisciplineException, InvalidProjectException, NoSuchSurveyException {

    Student stu = (Student) getStudent(id);
    stu.answerSurvey(projName, subName, answer, id);
  }

  public String showSurveyResults(String projName, String subName, int id)
      throws InvalidDisciplineException, InvalidProjectException, NoSuchSurveyException {
    Person p = getPerson(id);
    return p.showSurveyResults(projName, subName);
  }

  public String showNotifications(int id){
    Person p = _persons.get(id);
    return p.showNotifications();
  }

  public String showProjectSubmissions(String projName, String subName, int id)throws InvalidProjectException, InvalidDisciplineException{
    Professor p = (Professor)getProfessor(id);
    return p.showProjectSubmissions(projName, subName);
  }

  public String[] showDisciplineSurveys(String subName, int repId) throws InvalidDisciplineException{
    Student rep = (Student)getRepresentative(repId);
    Course course = rep.getCourse();
    return course.showDisciplineSurveys(subName);
  }
}
