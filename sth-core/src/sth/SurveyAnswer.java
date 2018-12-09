package sth;

import java.io.Serializable;

class SurveyAnswer implements Serializable {
    private int _hours;
    private String _comment;

    private static final long serialVersionUID = 201810051538L;

    public SurveyAnswer(int hours, String comment){
        _hours= hours;
        _comment = comment;
    }

    public int getHours(){
        return _hours;
    }

    public String getComment(){
        return _comment;
    }
}