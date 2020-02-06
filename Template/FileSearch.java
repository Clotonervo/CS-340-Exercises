import com.sun.xml.internal.rngom.parse.host.Base;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class FileSearch extends BaseClass {

	private Matcher _searchMatcher;
	private int _totalMatches;
	
	public FileSearch(String dirName, String filePattern, String searchPattern, boolean recurse) {
		super(dirName, filePattern, recurse);
		_searchMatcher = Pattern.compile(searchPattern).matcher("");
		_totalMatches = 0;
	}

	public static void main(String[] args) {

		String dirName = "";
		String filePattern = "";
		String searchPattern = "";
		boolean recurse = false;

		if (args.length == 3) {
			recurse = false;
			dirName = args[0];
			filePattern = args[1];
			searchPattern = args[2];
		}
		else if (args.length == 4 && args[0].equals("-r")) {
			recurse = true;
			dirName = args[1];
			filePattern = args[2];
			searchPattern = args[3];
		}
		else {
			usage();
			return;
		}

		FileSearch fileSearch = new FileSearch(dirName, filePattern, searchPattern, recurse);
		fileSearch.run();
	}

	@Override
	void printFileResult(File file, int counter) {
		if (counter > 0) {
			System.out.println("MATCHES: " + counter);
		}
	}

	@Override
	void printResult() {
		System.out.println("");
		System.out.println("TOTAL MATCHES: " + _totalMatches);
	}

	@Override
	void doOperationOnFile(String line, int counter, File file){

		_searchMatcher.reset(line);
		if (_searchMatcher.find()) {
			if (++counter == 1) {
				System.out.println("");
				System.out.println("FILE: " + file);
			}

			System.out.println(line);
			++_totalMatches;
		}

	}



}
