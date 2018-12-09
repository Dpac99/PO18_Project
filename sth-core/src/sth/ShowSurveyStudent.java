package sth;

public class ShowSurveyStudent extends SurveyPrinter{

    public String showFinishedSurvey(SurveyFinishedState s){
        String myEol = System.getProperty("line.separator"); 
        int hours = s.getAverageHours();
        int answers = s.getTotalAnswers();
        String str = getSubject() + " - " + getProjectName() + myEol;
        str += " * Número de respostas: " + answers+ myEol;
        str+= " * Tempo médio (horas): " + hours + myEol;
        return str;
    }
}