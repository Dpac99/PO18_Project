package sth.app.student;

/** Messages for menu interactions. */
@SuppressWarnings("nls")
public final class Message {

  /**
   * @return prompt for discipline name
   */
  public static String requestDisciplineName() {
    return "Nome da disciplina: ";
  }

  /**
   * @return prompt for project name
   */
  public static String requestProjectName() {
    return "Nome do projecto: ";
  }

  /**
   * @return prompt for project delivery message
   */
  public static String requestDeliveryMessage() {
    return "Mensagem de entrega: ";
  }

  /**
   * @return prompt for survey hours
   */
  public static String requestProjectHours() {
    return "Número de horas de trabalho: ";
  }

  /**
   * @return prompt for survey comments
   */
  public static String requestComment() {
    return "Comentário ao projecto: ";
  }

  /** Prevent instantiation. */
  private Message() {
    // EMPTY
  }

}
