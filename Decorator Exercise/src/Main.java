import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        ArrayList<String> currentSource = new ArrayList<>();
        currentSource.add("The");
        currentSource.add("quick");
        currentSource.add("brown");
        currentSource.add("jumped");
        currentSource.add("over");
        currentSource.add("the");
        currentSource.add("lazy");
        currentSource.add("dog");
        int test = currentSource.size();

        Capitalize capitalize = new Capitalize(currentSource);
        Hangman hangman = new Hangman(capitalize);
        for(int i = 0; i < test; i++){
            System.out.println(hangman.next());
        }
    }
}

interface StringSource {
    String next();
}

class SentanceMaker implements StringSource {
    List<String> stringList;

    public SentanceMaker(List<String> newList){
        this.stringList = newList;
    }

    public String next(){
        if (stringList.size() == 0){
            return null;
        }
        if (stringList.size() == 1){
            return stringList.remove(0) + ".";
        }
        else {
            return stringList.remove(0);
        }
    }
}

class Capitalize implements StringSource {
    List<String> stringList;

    public Capitalize(List<String> newList){
        this.stringList = newList;
    }

    public String next(){
        if (stringList.size() == 0){
            return null;
        }
        else {
            String str = stringList.remove(0);
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }
}

class Excitement implements StringSource {
    StringSource source;

    public Excitement(StringSource source){
        this.source = source;
    }

    public String next(){
        String str = source.next();
        if (str == null){
            return null;
        }
        else {
            return str + "!";
        }
    }
}

class Hangman implements StringSource {
    StringSource source;

    public Hangman(StringSource source){
        this.source = source;
    }

    public String next(){
        String str = source.next();
        if (str == null){
            return null;
        }
        else {
            char[] returnString = str.toCharArray();
            for (int i = 0; i < returnString.length; i++){
                returnString[i] = '-';
            }
            return new String(returnString);
        }
    }
}

class KissesAndHugs implements StringSource {
    StringSource source;
    int counter = 0;

    public KissesAndHugs(StringSource source){
        this.source = source;
    }

    public String next(){
        String str = source.next();
        if (str == null){
            return null;
        }
        else {
            char[] returnString = str.toCharArray();
            for (int i = 0; i < returnString.length; i++){
                int selection = counter % 4;
                if (selection == 0){
                    returnString[i] = 'X';
                }
                else if (selection == 1){
                    returnString[i] = 'O';
                }
                else if (selection == 2){
                    returnString[i] = 'x';
                }
                else {
                    returnString[i] = 'o';
                }
                counter++;
            }
            return new String(returnString);
        }
    }
}