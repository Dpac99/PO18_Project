package sth;

import sth.exceptions.HasAnswersException;

class SurveyOpenState extends SurveyState {

    private static final long serialVersionUID = 201810051538L;

    public SurveyOpenState(Survey survey){
        setSurvey(survey);
    }
    
    public boolean cancel() throws HasAnswersException{
        if(getSurvey().hasAnswers()){
            throw new HasAnswersException();
        }
        return true;
    }

    public void close() {
        getSurvey().setState(new SurveyClosedState(getSurvey()));
    }

    public boolean canSubmit(){
        return true;
    }

    public String accept(SurveyPrinter s){
        return s.showOpenSurvey(this);
    }
}