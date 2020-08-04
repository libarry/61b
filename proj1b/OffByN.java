public class OffByN extends OffByOne implements  CharacterComparator{
    private int N;
    public OffByN(int n){
        N = n;
    }
    @Override
    public boolean equalChars(char x, char y){
        return x + N == y || y + N == x;
    }
}
