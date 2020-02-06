import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class BaseClass {
    private String _dirName;
    private boolean _recurse;
    private Matcher _fileMatcher;

    public BaseClass(String _dirName, String _filePattern, boolean _recurse) {
        this._dirName = _dirName;
        this._recurse = _recurse;
        this._fileMatcher = Pattern.compile(_filePattern).matcher("");
    }

    abstract void doOperationOnFile(String line, int counter, File file);
    abstract void printResult();
    abstract void printFileResult(File file, int counter);

    protected void run(){
        searchDirectory(new File(_dirName));
        printResult();
    }

    private void searchDirectory(File dir) {
        if (!dir.isDirectory()) {
			nonDir(dir);
            return;
        }

        if (!dir.canRead()) {
			unreadableDir(dir);
            return;
        }

        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                if (file.canRead()) {
                    scanFile(file);
                }
                else {
					unreadableFile(file);
                }
            }
        }

        if (_recurse) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    scanFile(file);
                }
            }
        }
    }

    private void scanFile(File file) {
        String fileName = getFileName(file);
        _fileMatcher.reset(fileName);
        if (_fileMatcher.find()) {
            try {
                int counter = 0;

                InputStream data = new BufferedInputStream(new FileInputStream(file));
                try {
                    Scanner input = new Scanner(data);
                    while (input.hasNextLine()) {
                        String line = input.nextLine();
                        doOperationOnFile(line, counter, file);
                    }
                }
                finally {
                    data.close();
                    printFileResult(file, counter);
                }
            }
            catch (IOException e) {
                unreadableFile(file);
            }
        }
    }

    private String getFileName(File file) {
        try {
            return file.getCanonicalPath();
        }
        catch (IOException e) {
            return "";
        }
    }


    protected void nonDir(File dir) {
        System.out.println(dir + " is not a directory");
    }

    protected void unreadableDir(File dir) {
        System.out.println("Directory " + dir + " is unreadable");
    }

    protected void unreadableFile(File file) {
        System.out.println("File " + file + " is unreadable");
    }

    protected static void usage() {
        System.out.println("USAGE: java LineCount {-r} <dir> <file-pattern>");
    }

}
