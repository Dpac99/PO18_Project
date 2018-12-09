package sth;

import sth.exceptions.FinishedSurveyException;

public class SurveyFinishedState extends SurveyState{

    private static final long serialVersionUID = 201810051538L;

    public SurveyFinishedState(Survey s){
        setSurvey(s);
    }

    public boolean cancel() throws FinishedSurveyException{
        throw new FinishedSurveyException();
    }

    public String accept(SurveyPrinter s){
        return s.showFinishedSurvey(this);
    }
}