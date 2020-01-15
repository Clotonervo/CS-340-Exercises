public class Proxy implements Array2D {

    private ArrayImplementation realSubject;
    private String fileName;
    private boolean firstCall = true;

    public Proxy(String file) {
        fileName = file;
    }

    private void firstCall() {
        System.out.println("First call to proxy!");
        realSubject = new ArrayImplementation(fileName);
        firstCall = false;
    }

    public Integer get(Integer row, Integer col) {
        if (firstCall) {
            firstCall();
        }
        return realSubject.get(row, col);
    }

    public void set(Integer row, Integer col, Integer value) {
        if (firstCall) {
            firstCall();
        }
        realSubject.set(row, col, value);
    }

    public void load(String fileName) {
        if (firstCall) {
            firstCall();
        }
        realSubject.load(fileName);
    }

    public void save(String fileName) {
        if (firstCall) {
            firstCall();
        }
        realSubject.save(fileName);
    }

    public void print(){
        if (firstCall) {
            firstCall();
        }
        realSubject.printArray();
    }
}
