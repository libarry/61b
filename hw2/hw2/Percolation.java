package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {
    private boolean[] fullStatus;
    private WeightedQuickUnionUF set;
    private int N;
    private int openCount;
    public Percolation(int N)                // create N-by-N grid, with all sites initially blocked
    {
        if(N < 0){
            throw new IndexOutOfBoundsException();
        }
        this.N = N;
        set = new WeightedQuickUnionUF(N * N + 2);
        fullStatus = new boolean[N * N + 2];
        for(int i = 1;i < N * N + 1;i++){
            fullStatus[i] = true;
        }
        fullStatus[0] = false;
        fullStatus[N*N+1] = false;
        openCount = 0;
    }
    private int getIndex(int row,int col){
        return row * N + col + 1;
    }
    public void open(int row, int col)       // open the site (row, col) if it is not open already
    {
        if(isFull(row,col)) {
            openCount += 1;
            int n = getIndex(row, col);
            fullStatus[n] = false;
            int left = col - 1;
            int right = col + 1;
            int upper = row - 1;
            int down = row + 1;
            if (left >= 0 && isOpen(row,left)) {
                int temp = getIndex(row, left);
                set.union(temp, n);
            }
            if (right <= N - 1 && isOpen(row,right)) {
                int temp = getIndex(row, right);
                set.union(temp, n);
            }
            if (upper >= 0 && isOpen(upper,col)) {
                int temp = getIndex(upper,col);
                set.union(temp, n);
            }
            if (down <= N - 1 && isOpen(down,col)) {
                int temp = getIndex(down,col);
                set.union(temp, n);
            }
            if(row == 0){
                set.union(0,n);
            }
            if(row == N - 1){
                set.union(N * N + 1,n);
            }
        }

    }

    public boolean isOpen(int row, int col)  // is the site (row, col) open?
    {
        int index = getIndex(row,col);
        return !fullStatus[index];
    }
    public boolean isFull(int row, int col)  // is the site (row, col) full?
    {
        int index = getIndex(row,col);
        return fullStatus[index];
    }
    public int numberOfOpenSites()           // number of open sites
    {
        return openCount;
    }

    public boolean percolates()// does the system percolate?
    {
        return set.connected(0,N*N+1);
    }
    public static void main(String[] args)   // use for unit testing (not required)
    {
        Percolation p = new Percolation(10);
        for(int i = 0;i < 10;i++){
            p.open(i,0);
        }
        if(p.percolates()){
            System.out.println("percolated!");
        }else{
            System.out.println("not percolated!");
        }
    }
}
