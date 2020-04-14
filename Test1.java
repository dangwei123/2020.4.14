设计和构建一个“最近最少使用”缓存，该缓存会删除最近最少使用的项目。缓存应该从键映射到值(允许你插入和检索特定键对应的值)，并在初始化时指定最大容量。当缓存被填满时，它应该删除最近最少使用的项目。

它应该支持以下操作： 获取数据 get 和 写入数据 put 。

获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
写入数据 put(key, value) - 如果密钥不存在，则写入其数据值。当缓存容量达到上限时，它应该在写入新数据之前删除最近最少使用的数据值，从而为新的数据值留出空间。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/lru-cache-lcci
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class LRUCache {
    private static class Node{
        private int key;
        private int val;
        public Node(int key,int val){
            this.key=key;
            this.val=val;
        }
    }

    Map<Integer,Node> map=new HashMap<>();
    LinkedList<Node> cached=new LinkedList<>();
    private int capacity;
    private int size;
    public LRUCache(int capacity) {
        this.capacity=capacity;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }
        Node node=map.get(key);
        cached.remove(node);
        Node cur=new Node(key,node.val);
        cached.addFirst(cur);
        map.put(key,cur);
        return node.val;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){           
            Node node=map.get(key);
            cached.remove(node);
            Node cur=new Node(key,value);
            cached.addFirst(cur);
            map.put(key,cur);
        }else{
            if(size==capacity){
                Node node=cached.removeLast();
                map.remove(node.key);
                size--;
            }
            Node node=new Node(key,value);
            map.put(key,node);
            cached.addFirst(node);
            size++;
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
 
 请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。它应该支持以下操作：get 和 put。

get(key) - 如果键存在于缓存中，则获取键的值（总是正数），否则返回 -1。
put(key, value) - 如果键已存在，则变更其值；如果键不存在，请插入键值对。当缓存达到其容量时，则应该在插入新项之前，使最不经常使用的项无效。在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除 最近 最少使用的键。
「项的使用次数」就是自插入该项以来对其调用 get 和 put 函数的次数之和。使用次数会在对应项被移除后置为 0 。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/lfu-cache
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class LFUCache {
    private static class Node{
        private int key;
        private int val;
        private int count=1;
        public Node(int key,int val){
            this.key=key;
            this.val=val;
        }
    }

    private Map<Integer,Node> map;
    private Map<Integer,LinkedList<Node>> freMap;
    private int capacity;
    private int min=1;
    public LFUCache(int capacity) {
        map=new HashMap<>();
        freMap=new HashMap<>();
        this.capacity=capacity;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }
        Node node=map.get(key);
        inc(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if(capacity==0) return;
        if(map.containsKey(key)){
            Node node=map.get(key);
            node.val=value;
            inc(node);
        }else{
            if(map.size()==capacity){
                LinkedList<Node> list=freMap.get(min);
                Node node=list.removeLast();
                map.remove(node.key);
            }
            LinkedList<Node> tmp=freMap.get(1);
            if(tmp==null){
                tmp=new LinkedList<>();
                freMap.put(1,tmp);
            }
            Node cur=new Node(key,value);
            tmp.addFirst(cur);
            map.put(key,cur);
            min=1;
        }
    }

    private void inc(Node node){
        int fre=node.count;
        LinkedList<Node> list=freMap.get(fre);
        list.remove(node);
        node.count++;
        if(fre==min&&list.size()==0){
            min=fre+1;
        }
        LinkedList<Node> tmp=freMap.get(fre+1);
        if(tmp==null){
            tmp=new LinkedList<>();
            freMap.put(fre+1,tmp);
        }
        tmp.addFirst(node);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
 
 给出一个完全二叉树，求出该树的节点个数。

说明：

完全二叉树的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为第 h 层，则该层包含 1~ 2h 个节点。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/count-complete-tree-nodes
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int countNodes(TreeNode root) {
        if(root==null) return 0;
        TreeNode left=root;
        int i=0;
        while(left!=null){
            left=left.left;
            i++;
        }

        TreeNode right=root;
        int j=0;
        while(right!=null){
            right=right.right;
            j++;
        }
        if(i==j){
            return (int)Math.pow(2,i)-1;
        }
        return 1+countNodes(root.left)+countNodes(root.right);
    }
}