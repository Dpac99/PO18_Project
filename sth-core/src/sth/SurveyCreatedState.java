package sth;

class SurveyCreatedState extends SurveyState {

    private static final long serialVersionUID = 201810051538L;

    public SurveyCreatedState(Survey survey){
        setSurvey(survey);
    }
    
    public boolean cancel(){
        return true;
    }

    public void open(){
        getSurvey().setState(new SurveyOpenState(getSurvey()));
    }

    public String accept(SurveyPrinter s){
        return s.showCreatedSurvey(this);
    }

}