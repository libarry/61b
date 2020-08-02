public class ArrayDeque<T> {
    private T[] item;
    private int size;

    private int nextFirst;
    private int nextLast;

    public ArrayDeque(){
        item = (T[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
    }
    private int indexLast(int i){
        return (item.length + i - 1)% item.length;
    }
    private int indexNext(int i){
        return (i + 1) % item.length;
    }
    public void resize(int newSize){
        if(newSize < size)
            return;
        T[] temp = (T[]) new Object[newSize];
        if(nextFirst < nextLast && size != item.length)
            System.arraycopy(item, 0, temp,0,item.length);
        else {
            System.arraycopy(item, indexNext(nextFirst),temp, 0, item.length - nextFirst - 1);
            System.arraycopy(item, 0 ,temp, item.length - nextFirst - 1, nextLast);
            nextFirst = temp.length - 1;
            nextLast = size;
        }
        item = temp;
    }
    public void addFirst(T a){
        item[nextFirst] = a;
        nextFirst = indexLast(nextFirst) ;
        size += 1;
        if(size == item.length)
            resize(item.length * 2);

    }
    public void addLast(T a){
        item[nextLast] = a;
        nextLast = indexNext((nextLast));
        size += 1;
        if(size == item.length)
            resize(item.length * 2);
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        for(int i = 1;i < size + 1;i++){
            System.out.print(item[indexNext(nextFirst + i)] + " ");
        }
    }
    public T removeFirst(){
        if(isEmpty())
            return null;
        nextFirst = indexNext(nextFirst);
        T ret = item[nextFirst];
        item[nextFirst] = null;
        size -= 1;
        if(size < item.length/4 && size > 8)
            resize(item.length/2);
        return ret;
    }
    public T removeLast(){
        if(isEmpty())
            return null;
        nextLast = indexLast(nextLast);
        T ret = item[nextLast];
        item[nextLast] = null;
        size -= 1;
        if(size < item.length /4 && size > 8)
            resize(item.length/2);
        return ret;
    }
    public T get(int index){
        return item[indexNext(nextFirst + index)];
    }

}
