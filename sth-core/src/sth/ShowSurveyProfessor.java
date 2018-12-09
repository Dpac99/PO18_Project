package sth;

public class ShowSurveyProfessor extends SurveyPrinter{


    public String showFinishedSurvey(SurveyFinishedState s){
        String myEol = System.getProperty("line.separator"); 
        int avHours = s.getAverageHours();
        int minHours = s.getMinimumHours();
        int maxHours = s.getMaximumHours();
        int answers = s.getSurvey().getTotalAnswers();
        String str = getSubject() + " - " + getProjectName() + myEol;
        str+=" * Número de submissões: "+getProjectSubmissions() + myEol;
        str += " * Número de respostas: " + answers+ myEol;
        str+= " * Tempos de resolução (horas) (mínimo, médio, máximo): " + minHours + ", "+avHours+", "+maxHours + myEol;
        return str;
    }
}