package sth.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Menu;
import pt.tecnico.po.ui.ValidityPredicate;
import sth.SchoolManager;
import sth.app.teaching.TeachingMenu;

/**
 * 4.1.2. Open teaching menu.
 */
public class DoOpenTeachingMenu extends Command<SchoolManager> {

  /**
   * @param receiver
   */
  public DoOpenTeachingMenu(SchoolManager receiver) {
    super(Label.OPEN_TEACHING_MENU, receiver, new ValidityPredicate<SchoolManager>(receiver) {
      @Override
      public boolean isValid() {
        return _receiver.hasProfessor();
      }
    });
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    Menu menu = new TeachingMenu(_receiver);
    menu.open();
  }

}