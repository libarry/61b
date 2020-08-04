import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testIsPalindrome(){
        String test1 = "eating";
        String test2 = "racecar";
        String test3 = "aba";
        assertFalse(palindrome.isPalindrome(test1));
        assertTrue(palindrome.isPalindrome(test2));
        assertTrue(palindrome.isPalindrome(test3));
    }
    @Test
    public void testOffByOne(){
        OffByOne offByOne = new OffByOne();
        String test1 = "eating";
        String test2 = "racedbs";
        String test3 = "flake";
        assertFalse(offByOne.isPalindrome(test1,offByOne));
        assertTrue(offByOne.isPalindrome(test2,offByOne));
        assertTrue(offByOne.isPalindrome(test3,offByOne));
    }
}
