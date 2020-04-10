import java.util.Stack;

public class UndoRedoManager {

    //Stack of commands
    //Stack of undone commands
    private Stack<Command> done;
    private Stack<Command> undone;

    public UndoRedoManager(){
        done = new Stack<>();
        undone = new Stack<>();
    }

    public void execute(Command command){
        command.execute();
        done.push(command);
        undone.clear();
    }

    public void undo(){
        if (canUndo()){
            Command command = done.pop();
            undone.push(command);
            command.undo();
        }
        else {
            System.out.println("Nothing to undo!");
        }
    }

    public void redo(){
        if (canRedo()){
            Command command = undone.pop();
            done.push(command);
            command.redo();
        }
        else {
            System.out.println("Nothing to redo!");
        }
    }

    public boolean canUndo(){
        return !done.isEmpty();
    }

    public boolean canRedo(){
        return !undone.isEmpty();
    }
}
