package sth.app.person;

/** Messages for menu interactions. */
@SuppressWarnings("nls")
public final class Message {

  /**
   * @return prompt for login identifier
   */
  public static String requestLoginId() {
    return "Identificador: ";
  }

  /**
   * @return prompt for identifier
   */
  public static String requestPersonId() {
    return "Identificador da pessoa: ";
  }

  /**
   * @return prompt for new phone number
   */
  public static String requestPhoneNumber() {
    return "Número de telefone: ";
  }

  /**
   * @return prompt for person name
   */
  public static String requestPersonName() {
    return "Nome a procurar: ";
  }

  /** Prevent instantiation. */
  private Message() {
    // EMPTY
  }

}
