package sth.app.main;

import java.io.IOException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<SchoolManager> {

  Input<String> _file;
  /**
   * @param receiver
   */
  public DoSave(SchoolManager receiver) {
    super(Label.SAVE, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    try{
      if( ! _receiver.hasFile()){
        _file = _form.addStringInput(Message.newSaveAs());
        _form.parse();
        _receiver.newSaveAs(_file.value());
      }
       _receiver.newSaveAs();
    } catch (IOException e ){
      e.printStackTrace();
    }
  }
}
