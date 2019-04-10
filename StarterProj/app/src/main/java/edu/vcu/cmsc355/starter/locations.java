package edu.vcu.cmsc355.starter;

public class locations {
    private int size;
    private char shelf;


    locations(char shelf, int size)
    {
        setShelf(shelf);
        setSize(size);

    }


    public void setShelf(char shelf) {
        this.shelf = shelf;
    }



    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }



    public char getShelf() {
        return shelf;
    }
}
