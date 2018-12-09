package sth;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import sth.exceptions.CannotCloseSurveyException;
import sth.exceptions.CannotFinishSurveyException;
import sth.exceptions.CannotOpenSurveyException;
import sth.exceptions.DoubleSurveyException;
import sth.exceptions.FinishedSurveyException;
import sth.exceptions.HasAnswersException;
import sth.exceptions.NoSuchSurveyException;

class Survey implements Serializable {
    private SurveyState _state = new SurveyCreatedState(this);
    private Set<Integer> _students = new HashSet<Integer>();
    private List<SurveyAnswer> _answers = new ArrayList<SurveyAnswer>();

    private static final long serialVersionUID = 201810051538L;


    public boolean cancel() throws HasAnswersException, FinishedSurveyException{
        return _state.cancel();
    }

    public void submit(int id, SurveyAnswer s) throws NoSuchSurveyException{
        if(!_state.canSubmit()){
            throw new NoSuchSurveyException();
        }
        if(!_students.contains(id)){
            _students.add(id);
            _answers.add(s);
        }
    }

    public void open() throws CannotOpenSurveyException{
        _state.open();

    }

    public void close() throws CannotCloseSurveyException{
        _state.close();
    }

    public void finish() throws CannotFinishSurveyException{
        _state.finish();
    }

    public void setState(SurveyState state){
        _state = state;
    }

    public SurveyState getState(){
        return _state;
    }

    public boolean hasAnswers(){
        return _answers.size() > 0;
    }

    public String accept(SurveyPrinter s){
        return _state.accept(s);
    }

    public int getAverageHours(){
        int total = 0;
        if(_answers.size() == 0){
            return total;
        }
        for(SurveyAnswer s : _answers){
            total += s.getHours();
        }
        return total/_students.size();
    }

    public int getMinimumHours(){
        if(_answers.size() == 0){
            return 0;
        }
        int min = _answers.get(0).getHours();
        for(SurveyAnswer s : _answers){
            if(s.getHours() < min){
                min  = s.getHours();
            }
        }
        return min;
    }

    public int getMaximumHours(){
        if(_answers.size() == 0){
            return 0;
        }
        int max = _answers.get(0).getHours();
        for(SurveyAnswer s : _answers){
            if(s.getHours() > max){
                max  = s.getHours();
            }
        }
        return max;
    }

    public int getTotalAnswers(){
        return _answers.size();
    }

}