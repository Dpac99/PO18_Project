package sth;


class SurveyClosedState extends SurveyState {

    private static final long serialVersionUID = 201810051538L;

    public SurveyClosedState(Survey survey){
        setSurvey(survey);
    }

    public void open(){
        getSurvey().setState(new SurveyOpenState(getSurvey()));
    }
    
    public boolean cancel(){
        getSurvey().setState(new SurveyOpenState(getSurvey()));
        return false;
    }

    public void close(){
        //Does nothing, aka overrides the superclass method so no exception is thrown
    }

    public void finish() {
        getSurvey().setState(new SurveyFinishedState(getSurvey()));
    }

    public String accept(SurveyPrinter s){
        return s.showClosedSurvey(this);
    }

}