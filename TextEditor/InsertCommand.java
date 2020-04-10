import java.util.Scanner;

public class InsertCommand extends Command {

    private IDocument _document;
    private int startIndex;
    private String insertedString;

    public InsertCommand(IDocument document){
        _document = document;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Start index: ");
        String insertionIndexInput = scanner.next();
        int insertionIndex = validateNumberInput(insertionIndexInput);
        if (insertionIndex != -1) {
            startIndex = insertionIndex;
            System.out.print("Sequence to insert: ");
            String sequenceInput = scanner.next();
            insertedString = sequenceInput;
            _document.insert(insertionIndex, sequenceInput);
            success = true;
        }
    }

    @Override
    public void undo() {
        int deletionIndex = startIndex;
        int deletionDistance = insertedString.length();
        if (_document.delete(deletionIndex, deletionDistance) == null)
            System.out.println("Undo unsuccessful");

    }

    @Override
    void redo() {
        int insertionIndex = startIndex;
        String sequenceInput = insertedString;
        _document.insert(insertionIndex, sequenceInput);
    }
}
