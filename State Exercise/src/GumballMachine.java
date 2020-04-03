public class GumballMachine {
    private State state;
    private int gumballs;
    private double totalMoney;

    public GumballMachine(){
        gumballs = 0;
        totalMoney = 0;
        state = new NoQuarterNoGumball();
    }

    public void addGumballs(int count){
        gumballs += count;
    }

    public void addQuarter(){
        state.addQuarter(this);
    }

    public void removeQuarter(){
        state.removeQuarter(this);
    }

    public void turnHandle(){
        state.handleTurned(this);
    }

    public void updateState(State state){
        this.state = state;
    }

    public void swallowQuarter(){
        totalMoney += .25;
        gumballs -= 1;
    }

    public int getGumballs(){
        return gumballs;
    }

    public double getTotalMoney(){
        return totalMoney;
    }

}
