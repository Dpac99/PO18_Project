package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import sth.SchoolManager;

import sth.exceptions.FinishedSurveyException;
import sth.exceptions.HasAnswersException;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.NoSuchSurveyException;

import sth.app.exceptions.NonEmptySurveyException;
import sth.app.exceptions.SurveyFinishedException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSurveyException;

//FIXME import other classes if needed

/**
 * 4.5.2. Cancel survey.
 */
public class DoCancelSurvey extends Command<SchoolManager> {

  Input<String> _subName;
  Input<String> _projName;

  /**
   * @param receiver
   */
  public DoCancelSurvey(SchoolManager receiver) {
    super(Label.CANCEL_SURVEY, receiver);
    _subName = _form.addStringInput(Message.requestDisciplineName());
    _projName = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.cancelSurvey(_projName.value(), _subName.value());
    } catch( HasAnswersException e){
      throw new NonEmptySurveyException(_subName.value(), _projName.value());
    } catch (FinishedSurveyException e){
      throw new SurveyFinishedException(_subName.value(), _projName.value());
    } catch(InvalidProjectException e){
      throw new NoSuchProjectException(_subName.value(), _projName.value());
    }catch(InvalidDisciplineException e){
      throw new NoSuchDisciplineException(_subName.value());
    }catch (NoSuchSurveyException e){
      throw new NoSurveyException(_subName.value(), _projName.value());
    }
  }
}
