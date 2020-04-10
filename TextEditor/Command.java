public abstract class Command {

    protected boolean success = false;
    //State of the document

    abstract void execute();
    abstract void undo();
    abstract void redo();

    protected int validateNumberInput(String input) {
        int selection = -1;
        try {
            selection = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }

        return selection;
    }

    protected boolean isSuccess(){
        return success;
    }
}
