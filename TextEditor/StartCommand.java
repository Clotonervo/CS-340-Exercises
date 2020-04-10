public class StartCommand extends Command {

    private IDocument _document;
    private String documentString;

    public StartCommand(IDocument document){
        _document = document;
    }

    @Override
    public void execute() {
        documentString = _document.sequence().toString();
        _document.clear();
        success = true;
    }

    @Override
    public void undo() {
        _document.insert(0,documentString);
    }

    @Override
    void redo() {
        documentString = _document.sequence().toString();
        _document.clear();
    }
}
