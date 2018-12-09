package sth.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.app.exceptions.NoSuchPersonException;
import sth.app.person.DoLogin;
import sth.exceptions.NoSuchPersonIdException;

//FIXME import other classes if needed

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {

  Input<String> _file;
  
  /**
   * @param receiver
   */
  public DoOpen(SchoolManager receiver) {
    super(Label.OPEN, receiver);  
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws NoSuchPersonException{
    try {
      if(! _receiver.hasFile()){
        _file = _form.addStringInput(Message.openFile());
        _form.parse();
        _receiver.openFile(_file.value());
      }
       else{
         _receiver.openFile();
       }
       _display.add(_receiver.showNotifications());
       _display.display();
    } catch (FileNotFoundException fnfe) {
      _display.popup(Message.fileNotFound());
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    } catch (NoSuchPersonIdException e){
      throw new NoSuchPersonException(e.getId());
    }
  }

}
