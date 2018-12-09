package sth;

import sth.exceptions.CannotFinishSurveyException;

import java.io.Serializable;

import sth.exceptions.CannotCloseSurveyException;
import sth.exceptions.CannotOpenSurveyException;
import sth.exceptions.FinishedSurveyException;
import sth.exceptions.HasAnswersException;

abstract class SurveyState implements Serializable {
    private Survey _survey;

    private static final long serialVersionUID = 201810051538L;

    public abstract boolean cancel() throws HasAnswersException, FinishedSurveyException;
    //Returns true when cancelation = deletion, false when cancelation = re-open

    public void open() throws CannotOpenSurveyException{
        throw new CannotOpenSurveyException();
    }

    public void close() throws CannotCloseSurveyException{
        throw new CannotCloseSurveyException();
    }

    public void finish() throws CannotFinishSurveyException{
        throw new CannotFinishSurveyException();
    }

    public boolean canSubmit() {
        return false;
    }

    public Survey getSurvey(){
        return _survey;
    }

    public void setSurvey(Survey s){
        _survey = s;
    }

    public abstract String accept(SurveyPrinter s);

    public int getAverageHours(){
        return _survey.getAverageHours();
    }

    public int getMinimumHours(){
        return _survey.getMinimumHours();
    }

    public int getMaximumHours(){
        return _survey.getMaximumHours();
    }

    public int getTotalAnswers(){
        return _survey.getTotalAnswers();
    }

}