import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ArrayImplementation implements Array2D {

    private int[][] array;

    public ArrayImplementation(Integer width, Integer height){
        int[][] newArray = new int[height][width];
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newArray[i][j] = 0;
            }
        }
        array = newArray;
    }

    public ArrayImplementation(String fileName){
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream iis = new ObjectInputStream(fis);
            array = (int[][]) iis.readObject();

        } catch (Exception e) {
            System.out.println("Something went wrong with loading the array!");
        }
    }

    public void save(String fileName){
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(array);

        } catch (Exception e) {
            System.out.println("Something went wrong with saving the array!");
        }
    }

    public void load(String fileName){
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream iis = new ObjectInputStream(fis);
            array = (int[][]) iis.readObject();

        } catch (Exception e) {
            System.out.println("Something went wrong with loading the array!");
        }
    }

    public void set(Integer row, Integer col, Integer val){
        array[row][col] = val;
    }

    public Integer get(Integer row, Integer col){
        return array[row][col];
    }

    public void printArray(){
        for(int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println(" ");
        }
    }


}
