public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        LinkedListDeque<Character> deque = new LinkedListDeque<Character>();
        for(int i = 0;i < word.length(); i++){
            deque.addLast(word.charAt(i));
        }
        return deque;
    }
    public boolean isPalindrome(String word){
        Deque<Character> deque = wordToDeque(word);
        while(!deque.isEmpty()){
            Character left = deque.removeFirst();
            Character right = deque.removeLast();
            if(right != null)
                if(left != right)
                    return false;
        }
        return true;
    }
}
