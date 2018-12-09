package sth.app.student;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSurveyException;

import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.NoSuchSurveyException;

//FIXME import other classes if needed

/**
 * 4.4.2. Answer survey.
 */
public class DoAnswerSurvey extends Command<SchoolManager> {

  Input<String> _subName;
  Input<String> _projName;
  Input<String> _comment;
  Input<Integer> _hours;

  /**
   * @param receiver
   */
  public DoAnswerSurvey(SchoolManager receiver) {
    super(Label.ANSWER_SURVEY, receiver);
    _subName = _form.addStringInput(Message.requestDisciplineName());
    _projName = _form.addStringInput(Message.requestProjectName());
    _hours = _form.addIntegerInput(Message.requestProjectHours());
    _comment = _form.addStringInput(Message.requestComment());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.answerSurvey(_projName.value(), _subName.value(), _comment.value(), _hours.value());
    }catch(InvalidProjectException e){
      throw new NoSuchProjectException(_subName.value(), _projName.value());
    }catch(InvalidDisciplineException e){
      throw new NoSuchDisciplineException(_subName.value());
    }catch(NoSuchSurveyException e){
      throw new NoSurveyException(_subName.value(), _projName.value());
    }
  }

}
