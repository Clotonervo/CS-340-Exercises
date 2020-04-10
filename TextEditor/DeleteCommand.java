import java.util.Scanner;

public class DeleteCommand extends Command {
    private IDocument _document;
    private String deletedText;
    private int startingIndex;


    public DeleteCommand(IDocument document){
        _document = document;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Start index: ");
        String deletionIndexInput = scanner.next();
        int deletionIndex = validateNumberInput(deletionIndexInput);
        if (deletionIndex != -1) {
            startingIndex = deletionIndex;
                    System.out.print("Number of characters to delete: ");
            String deletionDistanceInput = scanner.next();
            int deletionDistance = validateNumberInput(deletionDistanceInput);
            if (deletionDistance != -1) {
                deletedText = _document.delete(deletionIndex, deletionDistance);
                if (deletedText == null) {
                    System.out.println("Deletion unsuccessful");
                }
                success = true;
            }
        }
    }

    @Override
    public void undo() {
        if(deletedText != null){
            int insertionIndex = startingIndex;
            if (insertionIndex != -1) {
                String sequenceInput = deletedText;
                _document.insert(insertionIndex, sequenceInput);
            }
        }
    }

    @Override
    void redo() {
        int deletionIndex = startingIndex;
        int deletionDistance = deletedText.length();
            deletedText = _document.delete(deletionIndex, deletionDistance);
            if (deletedText == null) {
                System.out.println("Deletion unsuccessful");
                success = false;
            }
    }
}
