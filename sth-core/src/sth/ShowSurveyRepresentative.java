package sth;

public class ShowSurveyRepresentative extends SurveyPrinter{

    public String showFinishedSurvey(SurveyFinishedState s){
        String myEol = System.getProperty("line.separator"); 
        int hours = s.getAverageHours();
        int answers = s.getTotalAnswers();
        return getSubject()+" - "+getProjectName()+" - "+answers+" respostas - "+hours+" horas" + myEol;
    }
}