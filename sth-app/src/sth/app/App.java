package sth.app;

import static pt.tecnico.po.ui.Dialog.IO;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Menu;
import sth.SchoolManager;
import sth.app.main.MainMenu;
import sth.app.person.DoLogin;
import sth.exceptions.ImportFileException;

/**
 * Main driver for the travel management application.
 */
public class App {
  /**
   * @param args
   */
  public static void main(String[] args) {
    SchoolManager school = new SchoolManager();

    String datafile = System.getProperty("import"); //$NON-NLS-1$
    if (datafile != null) {
      try {
        school.importFile(datafile);
      } catch (ImportFileException e) {
        // no behavior described: just present the problem
        e.printStackTrace();
      }
    }

    try {
      DoLogin loginCmd = new DoLogin(school);
      loginCmd.execute();
      Menu menu = new MainMenu(school);
      menu.open();
    } catch (DialogException de) {
      // DO NOTHING -- just exit
      de.printStackTrace();
    } finally {
      IO.close();
    }

  }

}
