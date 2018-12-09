package sth.app.person;

import pt.tecnico.po.ui.Command;
import sth.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.2.1. Show person.
 */
public class DoShowPerson extends Command<SchoolManager> {


  /**
   * @param receiver
   */
  public DoShowPerson(SchoolManager receiver) {
    super(Label.SHOW_PERSON, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    String text  = _receiver.showPerson();
    _display.add(text);
    _display.display();
  }

}
