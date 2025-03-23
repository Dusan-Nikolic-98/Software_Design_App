package commands;

import gui.swing.view.MainFrame;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private List<AbstractCommand> commands = new ArrayList<>();
    private int currC = 0;
    private boolean undoFlag = false; private boolean redoFlag = false;

    //i sada add/do/undo
    public void addCommand(AbstractCommand command){
        while(currC < commands.size())
            commands.remove(currC);
        commands.add(command);
        doCommand();
    }


    public void doCommand(){
        if(currC < commands.size()){
            commands.get(currC++).doCommand();
            MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(true);
            undoFlag = true;
        }
        if(currC==commands.size()){
            MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(false);
            redoFlag = false;
        }
    }

    public void undoCommand(){
        if(currC > 0){
            MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(true);
            commands.get(--currC).undoCommand();
            redoFlag = true;
        }
        if(currC==0){
            MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(false);
            undoFlag = false;
        }
    }

    public void setFlags(){
        MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(undoFlag);
        MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(redoFlag);
    }



}
