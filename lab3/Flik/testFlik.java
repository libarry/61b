import static org.junit.Assert.*;

import org.junit.Test;

public class testFlik {
    @Test
    public void testIsSameNumber(){
        int a = 129;
        int b = 129;
        assertTrue(Flik.isSameNumber(a,b));
    }
}
