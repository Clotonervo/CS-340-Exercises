import java.io.*;
import java.util.*;
import java.util.regex.*;

public class LineCount extends BaseClass{
	private int _totalLineCount;

	public LineCount(String directory, String pattern, boolean recurse) {
		super(directory, pattern, recurse);
		_totalLineCount = 0;
	}

	public static void main(String[] args) {
		String directory = "";
		String pattern = "";
		boolean recurse = false;

		if (args.length == 2) {
			recurse = false;
			directory = args[0];
			pattern = args[1];
		}
		else if (args.length == 3 && args[0].equals("-r")) {
			recurse = true;
			directory = args[1];
			pattern = args[2];
		}
		else {
			usage();
			return;
		}

		LineCount lineCounter = new LineCount(directory, pattern, recurse);
		lineCounter.run();
	}

	@Override
	void printFileResult(File file, int counter) {
		System.out.println(counter + "  " + file);
	}

	@Override
	void printResult() {
		System.out.println("TOTAL: " + _totalLineCount);
	}

	@Override
	void doOperationOnFile(String line, int curLineCount, File file) {
		++curLineCount;
		++_totalLineCount;
	}


}
