package sth.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Menu;
import sth.SchoolManager;

/** 4.1. Main menu. */
public class MainMenu extends Menu {

  /**
   * @param receiver
   */
  public MainMenu(SchoolManager receiver) {
    super(Label.TITLE, new Command<?>[] { //4.1
        new DoOpen(receiver), //4.1.1
        new DoSave(receiver), //4.1.1
        new DoOpenPersonnelMenu(receiver), //4.1.2
        new DoOpenTeachingMenu(receiver), //4.1.2
        new DoOpenStudentMenu(receiver), //4.1.2
        new DoOpenRepresentativeMenu(receiver), //4.1.2
    });
  }

}
