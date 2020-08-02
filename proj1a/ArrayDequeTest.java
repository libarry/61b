import static org.junit.Assert.*;
import org.junit.Test;

public class ArrayDequeTest {

    @Test
    public void isEmptySizeTest() {
        System.out.println("Running add/isEmpty/Size test.");

        ArrayDeque<String> deque1 = new ArrayDeque<String>();
        assertEquals(true,deque1.isEmpty());
        deque1.addFirst("front");
        assertEquals(1,deque1.size());
        assertEquals(false,deque1.isEmpty());
        deque1.addFirst("middle");
        assertEquals(2,deque1.size());
        deque1.addFirst("back");
        assertEquals(3,deque1.size());

        System.out.println("Printing out deque: ");
        deque1.printDeque();
    }
    @Test
    public void addGetRemoveTest() {
        System.out.println("Running add/remove test.");
        ArrayDeque<Integer> deque1 = new ArrayDeque<Integer>();
        for(int i = 0;i < 20;i++) {
            deque1.addFirst(i);
        }
        for(int i = 0;i < 20;i++){
            int act = deque1.get(i);
            assertEquals(19 - i,act);
        }
        for(int i = 19;i > -1;i--){
            int act = deque1.removeFirst();
            assertEquals(i,act);
        }
        assertEquals(true,deque1.isEmpty());

    }
}
