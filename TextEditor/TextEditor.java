import java.util.Scanner;

class TextEditor {

    private IDocument _document;
    private UndoRedoManager manager;

    TextEditor(IDocument document) {
        _document = document;
        manager = new UndoRedoManager();
    }

    void run() {
        while (true) {
            printOptions();

            Scanner scanner = new Scanner(System.in);
            String optionInput = scanner.next();
            int option = validateNumberInput(optionInput);

            if (option != -1) {
                switch (option) {
                    case 1:
                        Command insert = new InsertCommand(_document);
                        manager.execute(insert);
                        break;
                    case 2:
                        Command delete = new DeleteCommand(_document);
                        manager.execute(delete);
                        break;
                    case 3:
                        Command replace = new ReplaceCommand(_document);
                        manager.execute(replace);
                        break;
                    case 4:
                        _document.display();
                        break;
                    case 5:
                        save();
                        break;
                    case 6:
                        Command open = new OpenCommand(_document);
                        manager.execute(open);
                        break;
                    case 7:
                        Command start = new StartCommand(_document);
                        manager.execute(start);
                        break;
                    case 8:
                        manager.undo();
                        break;
                    case 9:
                        manager.redo();
                        break;
                    case 10:
                        return;
                }
            }

            System.out.println();
        }
    }

    private void printOptions() {
        System.out.println("SELECT AN OPTION (1 - 10):");
        System.out.println("1. Insert a string at a specified index in the document");
        System.out.println("2. Delete a sequence of characters at a specified index");
        System.out.println("3. Replace a sequence of characters at a specified index with a new string");
        System.out.println("4. Display the current contents of the document");
        System.out.println("5. Save the document to a file");
        System.out.println("6. Open a document from a file");
        System.out.println("7. Start a new, empty document");
        System.out.println("8. Undo");
        System.out.println("9. Redo");
        System.out.println("10. Quit");

        System.out.println();
        System.out.print("Your selection: ");
    }


    private void save() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Name of file: ");
        String saveFileName = scanner.next();
        _document.save(saveFileName);
    }

    private int validateNumberInput(String input) {
        int selection = -1;
        try {
            selection = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }

        return selection;
    }
}
