package simpl.interpreter;

import java.util.HashMap;

public class Mem {
    private static class Cell {
        private final Value value;
        private boolean marked = false;
        private Cell(Value value){
            this.value = value;
        }
    }
    private final HashMap<Integer,Cell> store = new HashMap<>();
    public void put(int location, Value value){
        store.put(location, new Cell(value));
    }

    public Value get(int location){
        return store.get(location).value;
    }

    public void remove(int location){
        store.remove(location);
    }

    public boolean hasMarked(int location){
        return store.get(location).marked;
    }

    public void mark(int location){
        store.get(location).marked = true;
    }

    public void unmark(int location){
        store.get(location).marked = false;
    }

    public int reclaim(){
        int oldSize = store.size();
        store.values().removeIf(cell -> !cell.marked);
        for(Cell cell: store.values())
            cell.marked = false;
        return (oldSize - store.size());
    }
}
