package job.otherAlgrithm;

/**
 * 前缀树
 * @author LingLong.gzw
 * @create 2019-08-31
 */
public class Trie {
    private TreeNode root;

    /** Initialize your data structure here. */
    public Trie() {
        root=new TreeNode();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TreeNode node=root;
        for(int i=0; i<word.length();i++){
            char curChar=word.charAt(i);
            if(!node.containsKey(curChar)){
                node.put(curChar);
            }
            node=node.get(curChar);
        }
        node.setEnd();
    }

    /** 在字典树种找到一个字符串，并返回结尾节点*/
    public TreeNode searchPrefix(String word){
        TreeNode node=root;
        for(int i=0; i<word.length();i++){
            char curChar=word.charAt(i);
            if(node.containsKey(curChar)){
                node=node.get(curChar);
            }else{
                return null;
            }
        }
        return node;
    }
    /** Returns if the word is in the trie.是否包含word（非模糊匹配） */
    public boolean search(String word) {
        TreeNode node=searchPrefix(word);
        return node!=null && node.getEnd();
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TreeNode node=searchPrefix(prefix);
        return node!=null;
    }

    class TreeNode{
        private TreeNode[] links;
        private final int R=26;
        private boolean isEnd;

        public TreeNode(){
            links=new TreeNode[R];
        }
        public boolean containsKey(char c){
            return links[c-'a']!=null;
        }

        public void put(char c){
            links[c-'a']=new TreeNode();
        }

        public TreeNode get(char c){
            return links[c-'a'];
        }

        public void setEnd(){
            isEnd=true;
        }

        public boolean getEnd(){
            return isEnd;
        }
    }
}
