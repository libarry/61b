public class LinkedListDeque<T> {
    private class Node{
        public Node prior;
        public Node next;
        public T item;
    }
    private int size;
    private Node s;

    public LinkedListDeque(){
        s = new Node();
        s.item = null;
        s.prior = s;
        s.next = s;
    }

    //Add item to the first of the list.
    public void addFirst(T item){
        Node node = new Node();
        node.item = item;
        node.next = s.next;
        node.prior = s;

        s.next.prior = node;
        s.next = node;
        size += 1;
    }

    //Add item to the last of the list.
    public void addLast(T item){
        Node node = new Node();
        node.item = item;
        node.prior = s.prior;
        node.next = s;

        s.prior.next = node;
        s.prior = node;
        size += 1;
    }

    //Check if the list is empty.
    public boolean isEmpty(){
        return s.next == s;
    }

    public int size(){return size;}

    //Print the deque's items from first to last.
    public void printDeque(){
        Node t = s.next;
        while(t != s){
            System.out.print(t.item + " ");
            t = t.next;
        }
    }

    public T removeFirst(){
        if(isEmpty()){
            return null;
        }
        Node first = s.next;
        first.prior.next = first.next;
        first.next.prior = first.prior;
        return first.item;
    }
    public T removeLast(){
        if(isEmpty()){
            return null;
        }
        Node last = s.prior;
        last.prior.next = last.next;
        last.next.prior = last.prior;
        return last.item;
    }

    public T get(int index){
        if(index >= size || index < 0){
            return null;
        }
        Node temp = s.next;
        for(int i = 0;i < index;i++){
            temp = temp.next;
        }
        return temp.item;
    }

    //Get item at index with a recursive way.
    public T getRecursive(int index){
        if(index >= size || index < 0){
            return null;
        }
        return getRec(s.next,index);
    }
    private T getRec(Node node,int index){
        if(index == 0)
            return node.item;
        return getRec(node.next,index - 1);
    }
}
