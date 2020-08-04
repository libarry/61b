public class OffByOne extends Palindrome implements  CharacterComparator{
    @Override
    public boolean equalChars(char x, char y){
        return x + 1 == y || y + 1 == x;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> deque = wordToDeque(word);
        while(!deque.isEmpty()){
            Character left = deque.removeFirst();
            Character right = deque.removeLast();
            if(right != null)
                if(!cc.equalChars(left,right))
                    return false;
        }
        return true;
    }
}
