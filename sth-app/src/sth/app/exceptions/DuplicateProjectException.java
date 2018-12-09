/**
 * 
 */
package sth.app.exceptions;

import pt.tecnico.po.ui.DialogException;

/**
 *
 */
public class DuplicateProjectException extends DialogException {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201810051538L;

  /** Discipline name. */
  private String _discipline;

  /** Project name. */
  private String _project;
  
  /**
   * @param discipline 
   * @param project 
   */
  public DuplicateProjectException(String discipline, String project) {
    _discipline = discipline;
    _project = project;
  }

  /** @see pt.tecnico.po.ui.DialogException#getMessage() */
  @Override
  public String getMessage() {
    return Message.duplicateProject(_discipline, _project);
  }

}
