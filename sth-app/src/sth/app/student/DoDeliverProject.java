package sth.app.student;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;


//FIXME import other classes if needed

/**
 * 4.4.1. Deliver project.
 */
public class DoDeliverProject extends Command<SchoolManager> {

  Input<String> _subName;
  Input<String> _projName;
  Input<String> _message;

  /**
   * @param receiver
   */
  public DoDeliverProject(SchoolManager receiver) {
    super(Label.DELIVER_PROJECT, receiver);
    _subName = _form.addStringInput(Message.requestDisciplineName());
    _projName = _form.addStringInput(Message.requestProjectName());
    _message = _form.addStringInput(Message.requestDeliveryMessage());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.deliverProject( _projName.value(), _subName.value(), _message.value());
    }catch(InvalidDisciplineException e){
      throw new NoSuchDisciplineException(_subName.value());
    }catch(InvalidProjectException e){
      throw new NoSuchProjectException(_subName.value(), _projName.value());
    }
  }

}
