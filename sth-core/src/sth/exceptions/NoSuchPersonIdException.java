package sth.exceptions;

/** Exception thrown when the requested person does not exist. */
public class NoSuchPersonIdException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201809021324L;

  /** Person id. */
  private int _id;

  /**
   * @param id
   */
  public NoSuchPersonIdException(int id) {
    _id = id;
  }

  /** @return id */
  public int getId() {
    return _id;
  }

}
