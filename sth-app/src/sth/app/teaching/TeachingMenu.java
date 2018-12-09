package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Menu;
import sth.SchoolManager;

/** 4.3. Teaching menu. */
public class TeachingMenu extends Menu {

  /**
   * @param receiver
   */
  public TeachingMenu(SchoolManager receiver) {
    super(Label.TITLE, new Command<?>[] { //4.3
        new DoCreateProject(receiver), //4.3.1
        new DoCloseProject(receiver), //4.3.2
        new DoShowProjectSubmissions(receiver), //4.3.3
        new DoShowDisciplineStudents(receiver), //4.3.4
        new DoShowSurveyResults(receiver), //4.3.5
    });
  }

}