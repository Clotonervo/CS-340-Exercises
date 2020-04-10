import java.util.Scanner;

public class ReplaceCommand extends Command {

    private IDocument _document;
    private String undoString;
    private String redoString;
    private int startIndex;
    private int endIndex;


    public ReplaceCommand(IDocument document){
        _document = document;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Start index: ");
        String replaceIndexInput = scanner.next();
        int replaceIndex = validateNumberInput(replaceIndexInput);
        if (replaceIndex != -1) {
            startIndex = replaceIndex;
            System.out.print("Number of characters to replace: ");
            String replaceDistanceInput = scanner.next();
            int replaceDistance = validateNumberInput(replaceDistanceInput);
            if (replaceDistance != -1) {
                endIndex = replaceDistance;
                System.out.print("Replacement string: ");
                String replacementString = scanner.next();
                undoString = _document.sequence().toString();
                _document.delete(replaceIndex, replaceDistance);
                _document.insert(replaceIndex, replacementString);
                success = true;
            }
        }
    }

    @Override
    public void undo() {
        redoString = _document.sequence().toString();
        _document.clear();
        _document.insert(0, undoString);
    }

    @Override
    void redo() {
        String replacementString = redoString;
        undoString = _document.sequence().toString();
        _document.clear();
        _document.insert(0, replacementString);

    }
}
