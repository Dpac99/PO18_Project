package sth;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import sth.exceptions.DoubleSurveyException;
import sth.exceptions.FinishedSurveyException;
import sth.exceptions.HasAnswersException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.NoSuchSurveyException;

import sth.exceptions.NoSuchSurveyException;
import sth.exceptions.CannotOpenSurveyException;
import sth.exceptions.CannotCloseSurveyException;
import sth.exceptions.CannotFinishSurveyException;
import sth.exceptions.NoSuchSurveyException;

class Project implements Serializable {
    private String _name;
    private boolean _open;
    private Map<Integer, String> _submissions = new TreeMap<Integer, String>();
    private Survey _survey;

    private static final long serialVersionUID = 201810051538L;

    /**
     * Constructor for class Project
     * 
     * @param name is the project's name.
     */
    public Project(String name) {
        _name = name;
        _open = true;
    }

    /**
     * @return String containing the project's name
     */
    public String getName() {
        return _name;
    }

    public int getSubmissions(){
        return _submissions.size();
    }

    /**
    * Closes the project
    */
    public boolean closeProject() throws CannotOpenSurveyException{
        _open = false;
        if(getSurvey() != null){
            _survey.open();
            return true;
        }
        return false;
    }

    public Survey getSurvey() {
        return _survey;
    }

    public boolean hasSurvey(){
        return (getSurvey()!=null);
    }

    public boolean canSeeSurvey(int id)throws InvalidProjectException{
        if(!hasDelivered(id)){
            throw new InvalidProjectException();
        }
        return hasSurvey();
    }

    public void createSurvey() throws DoubleSurveyException, InvalidProjectException {
        if (!_open) {
            throw new InvalidProjectException();
        }
        if (getSurvey() != null) {
            throw new DoubleSurveyException();
        }
        _survey = new Survey();
    }

    public boolean cancelSurvey() throws NoSuchSurveyException, HasAnswersException, FinishedSurveyException {

        if (getSurvey() == null) {
            throw new NoSuchSurveyException();
        }
        if(getSurvey().cancel()){ //true means delete
            _survey = null;
            return false;
        }
        return true;
    }

    public void openSurvey() throws 
        CannotOpenSurveyException,
        NoSuchSurveyException{
        
        if(getSurvey() == null){
            throw new NoSuchSurveyException();
        }
        if(_open){
            throw new CannotOpenSurveyException();
        }
        _survey.open();
    }

    public void closeSurvey() throws CannotCloseSurveyException, NoSuchSurveyException {

        if (getSurvey() == null) {
            throw new NoSuchSurveyException();
        }
        _survey.close();
    }

    public void finishSurvey() throws CannotFinishSurveyException, NoSuchSurveyException {

        if (getSurvey() == null) {
            throw new NoSuchSurveyException();
        }
        _survey.finish();
    }

    public void deliverProject(String message, int id) throws InvalidProjectException{
        if(!_open){
            throw new InvalidProjectException();
        }
        _submissions.put(id, message);
    }

    public boolean hasDelivered(int id){
        return (_submissions.get(id)!= null);
    }

    public void answerSurvey(SurveyAnswer answer, int id) throws InvalidProjectException, NoSuchSurveyException{
        if (!hasDelivered(id)){
            throw new InvalidProjectException();
        }
        if (getSurvey() == null) {
            throw new NoSuchSurveyException();
        }
        _survey.submit(id, answer);
    }

    public String showSurveyResults(SurveyPrinter printer) throws NoSuchSurveyException{
        if (getSurvey() == null) {
            throw new NoSuchSurveyException();
        }
        return _survey.accept(printer);
    }

    public String toString(){
        String results = "";
        String myEol = System.getProperty("line.separator");
        for(Map.Entry<Integer, String> e : _submissions.entrySet()){
            results += "* "+e.getKey()+" - "+e.getValue() + myEol;
        }
        return results;
    }

    public String printSurvey(SurveyPrinter printer){
        if (getSurvey() != null) {
            return _survey.accept(printer);
        }
        else
            return "";
    }
}