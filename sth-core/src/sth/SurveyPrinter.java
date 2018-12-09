package sth;

public abstract class SurveyPrinter{
    private String _subName;
    private Project _proj;

     
    public void setSubName(String subName){
        _subName = subName;
    }

    public void setProject(Project proj){
        _proj = proj;
    }

    public String showOpenSurvey(SurveyOpenState s){
        String myEol = System.getProperty("line.separator"); 
        return _subName + " - " + _proj.getName() + " (aberto)" + myEol;
    }
    public String showClosedSurvey(SurveyClosedState s){
        String myEol = System.getProperty("line.separator"); 
        return _subName + " - " + _proj.getName() + " (fechado)" + myEol;
    }

    public String showCreatedSurvey(SurveyCreatedState s){
        String myEol = System.getProperty("line.separator"); 
        return _subName + " - " + _proj.getName() + " (por abrir)" + myEol;
    }
    public abstract String showFinishedSurvey(SurveyFinishedState s);

    public String getSubject(){
        return _subName;
    }

    public String getProjectName(){
        return _proj.getName();
    }

    public int getProjectSubmissions(){
        return _proj.getSubmissions();
    }
}