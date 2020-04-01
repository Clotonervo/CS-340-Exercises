public class ContactTable implements TableData {
    private ContactManager manager;

    public ContactTable(ContactManager contactManager){
        manager = contactManager;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public int getRowCount() {
        return manager.getContactCount();
    }

    @Override
    public int getColumnSpacing() {
        return 2;
    }

    @Override
    public int getRowSpacing() {
        return 2;
    }

    @Override
    public char getHeaderUnderline() {
        return '_';
    }

    @Override
    public String getColumnHeader(int col) {
        if(col == 0){
            return "First Name";
        }
        else if (col == 1){
            return "Last Name";
        }
        else if (col == 2){
            return "Email";
        }
        else if (col == 3){
            return "Phone #";
        }
        return null;
    }

    @Override
    public int getColumnWidth(int col) {
        return 20;
    }

    @Override
    public Justification getColumnJustification(int col) {
        return Justification.Left;
    }

    @Override
    public String getCellValue(int row, int col) {
        Contact contact = manager.getContact(row);
        if(col == 0){
            return contact.getFirstName();
        }
        else if (col == 1){
            return contact.getLastName();
        }
        else if (col == 2){
            return contact.getEmail();
        }
        else if (col == 3){
            return contact.getPhone();
        }
        return null;
    }
}
