package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Menu;
import sth.SchoolManager;

/** 4.5. Representative menu. */
public class RepresentativeMenu extends Menu {

  /**
   * @param receiver
   */
  public RepresentativeMenu(SchoolManager receiver) {
    super(Label.TITLE, new Command<?>[] { //4.5
        new DoCreateSurvey(receiver), //4.5.1
        new DoCancelSurvey(receiver), //4.5.2
        new DoOpenSurvey(receiver), //4.5.3
        new DoCloseSurvey(receiver), //4.5.4
        new DoFinishSurvey(receiver), //4.5.5
        new DoShowDisciplineSurveys(receiver), //4.5.6
    });
  }

}