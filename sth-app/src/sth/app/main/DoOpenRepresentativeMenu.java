package sth.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Menu;
import pt.tecnico.po.ui.ValidityPredicate;
import sth.SchoolManager;
import sth.app.representative.RepresentativeMenu;

/**
 * 4.1.2. Open representative menu.
 */
public class DoOpenRepresentativeMenu extends Command<SchoolManager> {

  /**
   * @param receiver
   */
  public DoOpenRepresentativeMenu(SchoolManager receiver) {
    super(Label.OPEN_REPRESENTATIVE_MENU, receiver, new ValidityPredicate<SchoolManager>(receiver) {
      @Override
      public boolean isValid() {
        return _receiver.hasRepresentative();
      }
    });
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    Menu menu = new RepresentativeMenu(_receiver);
    menu.open();
  }

}