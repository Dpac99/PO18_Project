package sth.app.student;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Menu;
import sth.SchoolManager;

/** 4.4. Student menu. */
public class StudentMenu extends Menu {

  /**
   * @param receiver
   */
  public StudentMenu(SchoolManager receiver) {
    super(Label.TITLE, new Command<?>[] { //4.4
        new DoDeliverProject(receiver), //4.4.1
        new DoAnswerSurvey(receiver), //4.4.2
        new DoShowSurveyResults(receiver), //4.4.3
    });
  }

}
