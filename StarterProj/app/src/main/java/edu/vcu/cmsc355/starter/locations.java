package edu.vcu.cmsc355.starter;

public class locations {
    private String shelf;
    private String[] size;


    locations(String shelf, String[] size)
    {
        setShelf(shelf);
        setSize(size);

    }


    public void setShelf(String shelf) {
        this.shelf = shelf;
    }



    public void setSize(String[] size) {
        this.size = size;
    }

    public int getSize() {
        return size.length;
    }

    public int getRoomLeft(){
        int count = 0;
        for(String i: size){
            if(i == null){
                count += 1;
            }
        }
        return getSize() - count;
    }

    public String getShelf() {
        return shelf;
    }
}
