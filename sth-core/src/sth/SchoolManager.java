package sth;

import sth.exceptions.*;

import java.io.ObjectInputStream;
import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.io.FileOutputStream;

import java.io.IOException;

/**
 * The façade class.
 */
public class SchoolManager {

  private int _id;
  private School _school = new School();
  private String _file = null;

  /**
   * @param datafile
   * @throws ImportFileException
   * @throws InvalidCourseSelectionException
   */
  public void importFile(String datafile) throws ImportFileException {
    try {
      _school.importFile(datafile);
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(e);
    }
  }

  /**
   * @param id
   * @throws NoSuchPersonIdException
   */
  public void login(int id) throws NoSuchPersonIdException {
    if (_school.getPerson(id) == null) {
      throw new NoSuchPersonIdException(id);
    } else {
      _id = id;
    }
  }

  /**
   * @return true when the currently logged in person is an administrative
   */
  public boolean hasAdministrative() {
    return _school.getAdministrative(_id) != null;
  }

  /**
   * @return true when the currently logged in person is a professor
   */
  public boolean hasProfessor() {
    return _school.getProfessor(_id) != null;
  }

  /**
   * @return true when the currently logged in person is a student
   */
  public boolean hasStudent() {
    return _school.getStudent(_id) != null;
  }

  /**
   * @return true when the currently logged in person is a representative
   */
  public boolean hasRepresentative() {
    return _school.getRepresentative(_id) != null;
  }

  /**
   * @return true if file is defined
   */
  public boolean hasFile() {
    return _file != null;
  }

  /**
   * Set file
   * 
   * @param file
   */
  public void setFile(String file) {
    _file = file;
  }

  /**
   * @return String array with each person's toString in it
   */
  public String[] showAllPersons() {
    return _school.getAllPersons();
  }

  /**
   * @param phoneNumber
   */
  public void changePhoneNumber(String phoneNumber) {
    _school.changePhoneNumber(_id, phoneNumber);
  }

  /**
   * @return the current logged in person's toString
   */
  public String showPerson() {
    return _school.showPerson(_id);
  }

  /**
   * @throws FileNotFoundException
   * @throws ClassNotFoundException
   * @throws IOException
   */

  public void openFile() throws FileNotFoundException, ClassNotFoundException, IOException, NoSuchPersonIdException {
    School s = _school;
    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(_file));
    _school = (School) ois.readObject();
    ois.close();
    try {
      login(_id);
    } catch (NoSuchPersonIdException e) {
      _school = s;
      setFile(null);
      throw new NoSuchPersonIdException(_id);
    }
  }

  public void openFile(String file)throws FileNotFoundException, ClassNotFoundException, IOException, NoSuchPersonIdException{
    setFile(file);
    openFile();
  }

  /**
   * @throws IOException
   */
  public void newSaveAs() throws IOException {
    ObjectOutputStream oos;
    if (_file != null) {
      oos = new ObjectOutputStream(new FileOutputStream(_file));
      oos.writeObject(_school);
      oos.close();
    }
  }

  public void newSaveAs(String file) throws IOException{
    setFile(file);
    newSaveAs();
  }

  /**
   * @return String with the current file's name
   */
  public String getFile() {
    return _file;
  }

  /**
   * @param subName
   * @return String array with the toString result of each of the 'subName'
   *         subject's students
   * @throws InvalidDisciplineException
   */
  public String[] showDisciplineStudents(String subName) throws InvalidDisciplineException {
    return _school.getDisciplineStudents(subName, _id);
  }

  /**
   * @param subName
   * @param projName
   * @throws InvalidDisciplineException
   * @throws InvalidProjectException
   */
  public void createProject(String projName, String subName)
      throws InvalidDisciplineException, InvalidProjectException {
    _school.createProject(projName, subName, _id);
  }

  /**
   * @param subName
   * @param projName
   * @throws InvalidDisciplineException
   * @throws InvalidProjectException
   */
  public void closeProject(String projName, String subName)
      throws InvalidDisciplineException, InvalidProjectException, CannotOpenSurveyException {
    _school.closeProject(projName, subName, _id);
  }

  /**
   * @param pattern
   * @return String array with the toString of every person that matches the
   *         pattern
   */
  public String[] searchPerson(String pattern) {
    return _school.searchPerson(pattern);
  }

  public void createSurvey(String projName, String subName)
      throws InvalidDisciplineException, InvalidProjectException, DoubleSurveyException {

    _school.createProjectSurvey(projName, subName, _id);
  }

  public void cancelSurvey(String projName, String subName) throws InvalidDisciplineException,
      InvalidProjectException, NoSuchSurveyException, HasAnswersException, FinishedSurveyException {

    _school.cancelProjectSurvey(projName, subName, _id);
  }

  public void openSurvey(String projName, String subName)
      throws InvalidDisciplineException, InvalidProjectException, CannotOpenSurveyException, NoSuchSurveyException {

    _school.openProjectSurvey(projName, subName, _id);
  }

  public void closeSurvey(String projName, String subName)
      throws InvalidDisciplineException, InvalidProjectException, CannotCloseSurveyException, NoSuchSurveyException {

    _school.closeProjectSurvey(projName, subName, _id);
  }

  public void finishSurvey(String projName, String subName)
      throws InvalidDisciplineException, InvalidProjectException, CannotFinishSurveyException, NoSuchSurveyException {

    _school.finishProjectSurvey(projName, subName, _id);
  }

  public void deliverProject(String projName, String subName, String message)
      throws InvalidDisciplineException, InvalidProjectException {

    _school.deliverProject(projName, subName, message, _id);
  }

  public void answerSurvey(String projName, String subName, String comment, int hours)
      throws InvalidDisciplineException, InvalidProjectException, NoSuchSurveyException {

    SurveyAnswer answer = new SurveyAnswer(hours, comment);
    _school.answerSurvey(projName, subName, answer, _id);
  }

  public String showSurveyResults(String projName, String subName)
      throws InvalidDisciplineException, InvalidProjectException, NoSuchSurveyException {
    return _school.showSurveyResults(projName, subName, _id);
  }

  public String showNotifications(){
    return _school.showNotifications(_id);
  }

  public String showProjectSubmissions(String projName, String subName) throws InvalidProjectException, InvalidDisciplineException{
    return _school.showProjectSubmissions(projName, subName, _id);
  }

  public String[] showDisciplineSurveys(String subName) throws InvalidDisciplineException{
    return _school.showDisciplineSurveys(subName, _id);
  }
}
