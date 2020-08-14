package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        assertTrue(arb.isEmpty());
        assertFalse(arb.isFull());
        for(int i = 0;i < 10;i++){
            arb.enqueue(i);
        }
        assertTrue(arb.isFull());
        for(int i = 0;i < 10;i++){
            int exp = i;
            int act = arb.peek();
            assertEquals(exp,act);
            act = arb.dequeue();
            assertEquals(exp,act);
        }
        assertTrue(arb.isEmpty());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
