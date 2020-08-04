import org.junit.Test;
import static org.junit.Assert.*;

public class testOffByN {
    static OffByN offBy5 = new OffByN(5);

    @Test
    public void testEqualChars(){
        offBy5.equalChars('a', 'f');  // true
        offBy5.equalChars('f', 'a');  // true
        offBy5.equalChars('f', 'h');  // false
    }
    @Test
    public void testOffByN(){
        String test1 = "eating";
        String test2 = "racehfw";
        String test3 = "fla";
        assertFalse(offBy5.isPalindrome(test1,offBy5));
        assertTrue(offBy5.isPalindrome(test2,offBy5));
        assertTrue(offBy5.isPalindrome(test3,offBy5));
    }
}
