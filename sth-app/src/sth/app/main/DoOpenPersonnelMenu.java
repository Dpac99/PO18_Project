package sth.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Menu;
import sth.SchoolManager;
import sth.app.person.PersonnelMenu;

/**
 * 4.1.2. Open personnel menu.
 */
public class DoOpenPersonnelMenu extends Command<SchoolManager> {

  /**
   * @param receiver
   */
  public DoOpenPersonnelMenu(SchoolManager receiver) {
    super(Label.OPEN_PERSONEL_MENU, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    Menu menu = new PersonnelMenu(_receiver);
    menu.open();
  }

}
