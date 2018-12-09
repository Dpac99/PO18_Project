package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

import sth.app.exceptions.DuplicateProjectException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.InvalidDisciplineException;


/**
 * 4.3.1. Create project.
 */
public class DoCreateProject extends Command<SchoolManager> {

  Input<String> _subName;
  Input<String> _projName;

  /**
   * @param receiver
   */
  public DoCreateProject(SchoolManager receiver) {
    super(Label.CREATE_PROJECT, receiver);
    _subName = _form.addStringInput(Message.requestDisciplineName());
    _projName = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.createProject(_projName.value(), _subName.value());
    }catch(InvalidDisciplineException e){
      throw new NoSuchDisciplineException(_subName.value());
    }catch(InvalidProjectException e){
      throw new DuplicateProjectException(_subName.value(), _projName.value());
    }
    
  }

}
