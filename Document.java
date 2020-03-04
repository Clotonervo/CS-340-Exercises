/**
 * The Document class is used to store and modify the contents of a 
 * text document.
 * 
 * Operations are provided for reading, inserting, and deleting characters
 * in the document.
 * For convenience, cursor-relative operations are provided to make it easier
 * to edit a document in the style supported by a typical text editor.
 *
 * Domain:
 *      Content: Sequence<char>, Document content
 *      Length: Integer, Number of characters in the document (initially zero)
 *      Cursor: Integer, Position in the character sequence where some
 *              operations on the document occur (initially zero)
 *
 * @invariant length() >= 0
 * @invariant 0 <= cursor() <= length()
 */
class Document {

	/**
	 * Initializes empty document
	 * 
	 * @pre None
	 * 
	 * @post new length() = 0
	 * @post new cursor() = 0
	 */
	Document(){}

    
	/**
	 * Returns the number of characters in the document
	 * 
	 * @pre None
	 * 
	 * @post Return value contains the number of characters in the document
	 */
	int length() { return 0; }

    
	/**
	 * Returns the specified number of characters, starting at the specified
	 * position
	 * 
	 * @param pos Position of the characters to be returned
	 * @param count Number of characters to be returned
	 * 
	 * 
	 * 
	 * 
	 * @pre a position that is within the document greater than -1 less than length, and a count that is greater
	 * 		than 0 and count + pos is less than length
	 *
	 * @post return value contains the number of characters starting at the specified position
	 * 		in the document
	 * 
	 * 
	 */
	String read(int pos, int count) { return null; }
	
    
	/**
	 * Inserts a character sequence into the document
	 * 
	 * @param pos Position at which the character sequence should be inserted
	 * @param s Character sequence to be inserted
	 * 
	 * 
	 * 
	 * 
	 * @pre a position that is within the document greater than -1 and less than length +1,
	 *		a String that is not null
	 * 	
	 * @post new length is old.length + s.length(), The content is a CharSequence that contains the String
	 *		s starting at pos 
	 * 
	 * 
	 * 
	 */
	void insert(int pos, String s) {}

	
	/**
	 * Deletes a character sequence from the document.
	 * 
	 * @param pos Starting position of the sequence that is being deleted 
	 * @param count Number of characters to be deleted
	 * 
	 * 
	 * 
	 * 
	 * @pre a position that is within the document greater than -1 less than length, and a count that is greater
	 * 		than 0 and count + pos is less than length
	 * 
	 * @post new length is old.length - count, the substring that started at pos and went count characters
	 * 		is gone
	 * 
	 * 
	 */
	void delete(int pos, int count) {}

    
	/**
	 * Returns the current cursor position
	 * 
	 * @pre None
	 * 
	 * @post Return value contains the current cursor position
	 */
	int cursor() { return 0; }
	
    
	/**
	 * Sets the current cursor position
	 * 
	 * @param pos New cursor position
	 * 
	 * @pre 0 <= pos <= length()
	 * 
	 * @post new cursor() = pos
	 */
	void setCursor(int pos) {}

    
	/**
	 * Inserts a single character into the document at the current cursor
     * position, and advances the cursor forward one position
	 * 
	 * @param c Character to be inserted
	 * 
	 * 
	 * @pre a valid character, and that the cursor is in a position that is 0 <= cursor() <= length()
	 * 
	 * @post cursor = old.cursor + 1, character at old.cursor is c, length = old.length + 1
	 * 
	 * 
	 * 
	 * 
	 */
	void insert(char c) {}
	
    
	/**
	 * Deletes the character at the current cursor position
	 * 
	 * 
	 * @pre the cursor is at a valid position 0 <= cursor() <= length()
	 * 
	 * @post character at old.cursor is gone, length = old.length - 1
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	void delete() {}
	
    
	/**
	 * Moves the cursor backward one position, and deletes the character at the new cursor position
	 *
	 * 
	 * 
	 * 
	 * @pre the cursor is at a valid position 0 < cursor() <= length()
	 * 
	 * @post character at old.cursor - 1 is gone, length = old.length - 1, cursor = old.cursor - 1
	 * 
	 * 
	 * 
	 */
	void deletePrevious() {}

    
	/**
	 * Deletes the entire contents of the document and resets it to an empty state
	 *
	 * 
	 * 
	 * @pre None
	 * 
	 * @post content = empty string, cursor = 0, length = 0
	 * 
	 * 
	 * 
	 * 
	 */
	void clear() {}
}
