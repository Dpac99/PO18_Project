package sth.app.exceptions;

/** Messages for menu interactions. */
@SuppressWarnings("nls")
public final class Message {

  /**
   * @param id
   * @return message
   */
  public static String noSuchPerson(int id) {
    return "A pessoa com identificador " + id + " não existe.";
  }
  
  /**
   * @param name
   * @return error message
   */
  public static String noSuchDiscipline(String name) {
    return "A disciplina '" + name + "' não existe ou não é válida para o utilizador em causa.";
  }

  /**
   * @param discipline
   * @param project
   * @return error message
   */
  public static String noSuchProject(String discipline, String project) {
    return "O projecto '" + project + "' não existe na disciplina '" + discipline + "'.";
  }

  /**
   * @param discipline
   * @param project
   * @return error message
   */
  public static String duplicateProject(String discipline, String project) {
    return "O projecto '" + project + "' já existe na disciplina '" + discipline + "'.";
  }

  /**
   * @param discipline
   * @param project
   * @return error message
   */
  public static String noSurvey(String discipline, String project) {
    return "O projecto '" + project + "' da disciplina '" + discipline + "' não tem inquérito associado.";
  }

  /**
   * @param discipline
   * @param project
   * @return error message
   */
  public static String duplicateSurvey(String discipline, String project) {
    return "O projecto '" + project + "' da disciplina '" + discipline + "' já tem um inquérito associado.";
  }

  /**
   * @param discipline
   * @param project
   * @return error messages
   */
  public static String nonEmptySurvey(String discipline, String project) {
    return "O inquérito do projecto '" + project + "' da disciplina '" + discipline + "' já tem respostas.";
  }

  /**
   * @param discipline
   * @param project
   * @return error message
   */
  public static String surveyFinished(String discipline, String project) {
    return "O inquérito do projecto '" + project + "' da disciplina '" + discipline + "' já está finalizado.";
  }

  /**
   * @param discipline
   * @param project
   * @return error message
   */
  public static String closingSurvey(String discipline, String project) {
    return "Problema ao fechar o inquérito do projecto '" + project + "' da disciplina '" + discipline + "'.";
  }

  /**
   * @param discipline
   * @param project
   * @return error message
   */
  public static String openingSurvey(String discipline, String project) {
    return "Problema ao abrir o inquérito do projecto '" + project + "' da disciplina '" + discipline + "'.";
  }

  /**
   * @param discipline
   * @param project
   * @return error message
   */
  public static String finishingSurvey(String discipline, String project) {
    return "Problema ao finalizar o inquérito do projecto '" + project + "' da disciplina '" + discipline + "'.";
  }

  /** Prevent instantiation. */
  private Message() {
    // EMPTY
  }

}
