import java.io.PrintWriter;
import java.io.Writer;

public class Main {
    public static void main(String[] args){
        ContactManager manager = new ContactManager();
        manager.addContact(new Contact("first", "last", "phone", "email"));
        manager.addContact(new Contact("first1", "last1", "phone1", "email1"));
        manager.addContact(new Contact("first12", "last12", "phone12", "email12"));
        manager.addContact(new Contact("first13", "last13", "phone13", "email13"));

        ContactTable contactTable = new ContactTable(manager);
        Writer writer = new PrintWriter(System.out);
        Table table = new Table(writer ,contactTable);
        try {
            table.display();
            writer.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
