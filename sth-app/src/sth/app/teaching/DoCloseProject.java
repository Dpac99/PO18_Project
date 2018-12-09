package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

import sth.exceptions.InvalidProjectException;
import sth.exceptions.CannotOpenSurveyException;
import sth.exceptions.InvalidDisciplineException; 
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.OpeningSurveyException;

/**
 * 4.3.2. Close project.
 */
public class DoCloseProject extends Command<SchoolManager> {

  Input<String> _subName;
  Input<String> _projName;

  /**
   * @param receiver
   */
  public DoCloseProject(SchoolManager receiver) {
    super(Label.CLOSE_PROJECT, receiver);
    _subName = _form.addStringInput(Message.requestDisciplineName());
    _projName = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.closeProject(_projName.value(), _subName.value());
    }catch(InvalidProjectException e){
      throw new NoSuchProjectException(_subName.value(), _projName.value());
    }catch(InvalidDisciplineException e){
      throw new NoSuchDisciplineException(_subName.value());
    }catch(CannotOpenSurveyException e){
      throw new OpeningSurveyException(_subName.value(), _projName.value());
    }
    
  }

}
