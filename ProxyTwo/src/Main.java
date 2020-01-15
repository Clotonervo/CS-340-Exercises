public class Main {

    static public void main(String[] args){
        ArrayImplementation arrayImplementation = new ArrayImplementation(5, 5);
        arrayImplementation.set(1, 1, 1);
        arrayImplementation.set(2, 2, 2);
        arrayImplementation.set(3, 3, 3);
        arrayImplementation.set(4, 4, 4);
        arrayImplementation.save("test.dat");   //Create an array

        arrayImplementation.printArray();   //Array Proxy should have
        System.out.println();

        arrayImplementation.set(1, 1, 0);
        arrayImplementation.set(2, 2, 0);
        arrayImplementation.set(3, 3, 0);
        arrayImplementation.set(4, 4, 0);
        arrayImplementation.printArray(); //Prints changed array to test that Proxy loads one from the saved file

        System.out.println();

        Proxy proxy = new Proxy("test.dat");

        proxy.print(); //Compare arrays
        proxy.get(1,1);
        proxy.set(0,4,5);
        proxy.set(4,0,5);
        proxy.save("test.dat"); // Alter array and save it

        System.out.println();

        ArrayImplementation newArray = new ArrayImplementation("test.dat");

        newArray.printArray(); //See if save worked from proxy


    }
}
