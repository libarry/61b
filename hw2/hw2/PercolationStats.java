package hw2;

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] thresholds ;
    private int N;
    public PercolationStats(int N, int T, PercolationFactory pf)   // perform T independent experiments on an N-by-N grid
    {
        if(N < 0 || T < 0)
            throw new IndexOutOfBoundsException();
        thresholds = new double[T];
        this.N = N;
        for(int i = 0;i < T;i++){
            Percolation p = pf.make(N);
            while(!p.percolates()){
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                p.open(row,col);
            }
            thresholds[i] = ((double)p.numberOfOpenSites())/(N*N);
        }
    }
    public double mean()                                           // sample mean of percolation threshold
    {
        if(thresholds == null){
            throw new RuntimeException();
        }
        double sum = 0.0;
        for(double i : thresholds)
            sum += i;
        return sum/(thresholds.length);
    }
    public double stddev()                                         // sample standard deviation of percolation threshold
    {
        double u = mean();
        double sum = 0.0;
        for(double i : thresholds)
            sum += (i - u)*(i - u);
        return sum/(thresholds.length - 1);
    }
    public double confidenceLow()                                  // low endpoint of 95% confidence interval
    {
        double u = mean();
        double r = stddev();
        return u - 1.96 * r/Math.sqrt(thresholds.length);
    }
    public double confidenceHigh()                                 // high endpoint of 95% confidence interval
    {
        double u = mean();
        double r = stddev();
        return u + 1.96 * r/Math.sqrt(thresholds.length);
    }
    public static void main(String[] args){
        PercolationFactory pf = new PercolationFactory();
        PercolationStats stats = new PercolationStats(100,1000,pf);
        double mean = stats.mean();
        double std = stats.stddev();
        double up = stats.confidenceHigh();
        double low = stats.confidenceLow();
        System.out.printf("mean:%.5f\nstddev:%.5f\nconfidence:%.5f-%.5f",mean,std,low,up);
    }
}
