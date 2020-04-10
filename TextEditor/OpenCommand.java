import java.util.Scanner;

public class OpenCommand extends Command {

    private IDocument _document;
    private String undoDocumentString;
    private String fileName;

    public OpenCommand(IDocument document){
        _document = document;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Name of file to open: ");
        String openFileName = scanner.next();
        fileName = openFileName;
        undoDocumentString = _document.sequence().toString();
        _document.open(openFileName);
        success = true;
    }

    @Override
    public void undo() {
        _document.clear();
        _document.insert(0,undoDocumentString);
    }

    @Override
    void redo() {
        String openFileName = fileName;
        undoDocumentString = _document.sequence().toString();
        _document.open(openFileName);
    }
}
