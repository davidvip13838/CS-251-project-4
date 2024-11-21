package CommonUtils.Interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract class for our scapegoat tree.  Based on the definition of a scapegoat tree on Wikipedia.
 * @see <a href="https://en.wikipedia.org/wiki/Scapegoat_tree">Scapegoat Tree</a>
 * <p>
 * Note: you must read the instructions included in this file as not everything is the same as the website.
 *
 * @apiNote This class uses either .equals() or .compareTo() to check if two keys are equal. Any parameterized
 *   use of this class must ensure these functions are implemented in the key type for correct behavior.
 *
 * @param <K> data type for the keys the tree will store.  Must be comparable.
 * @param <V> data type for the values the tree will be storing
 */
public abstract class ScapeGoatTreeInterface<K extends Comparable<K>, V> {
    //alpha parameter
    protected static final double ALPHA_THRESHOLD = 0.57;

    /**
     * Node object for our Scapegoat tree.  Holds comparable data and its left, right, and parent nodes.
     *
     * @param <E> type for the keys to hold
     * @param <A> type for the values to hold
     */
    public static class Node<E extends Comparable<E>, A> {
        /*Note that while these variables are exposed publicly, the ScapeGoat tree never exposes
         * any of its nodes, protecting them from being changed by outside classes. */
        public E key;
        public A value;
        public Node<E, A> parent, left, right;

        //Default generated constructor
        public Node(E key, A value, Node<E, A> parent, Node<E, A> left, Node<E, A> right) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        /**
         * Default toString
         *
         * @return just the data in the object (no parent or child values)
         */
        @Override
        public String toString() {
            return "Node{" + "key=" + key + ", value=" + value + '}';
        }

        //default generated equals function
        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node<?, ?> node = (Node<?, ?>) o;
            return Objects.equals(key, node.key) && Objects.equals(value, node.value);
        }
    }

    /**
     * Retrieves the root of the scapegoat tree, or <code>null</code> if none exists.
     *
     * @return the root of the scapegoat tree, or <code>null</code> if none exists
     */
    public abstract Node<K, V> root();

    /**
     * Finds the first scapegoat node and returns it.  The first scapegoat node is the first node at or above
     *   the passed in node which does not satisfy the alpha-weight-balanced property.  This function is meant
     *   to be used after you insert a node in the tree.  Since this function is only supposed to be called
     *   when an insertion breaks the alpha-weight-balanced property, we know that we must find at least one
     *   node that isn't alpha-weight-balanced.
     * <p>
     * Hint: when you add a new node, the possible nodes that do not meet the alpha-weight-balanced property
     *   are on the path from the inserted node to the root.
     *
     * @implNote this function is not individually tested, it is for your convenience in implementation.
     *
     * @param node newly inserted node to start searching at
     * @return the first scapegoat node
     */
    protected abstract Node<K, V> scapeGoatNode(Node<K, V> node);

    /**
     * Rebuilds the subtree rooted at this node to be a perfectly balanced BST.
     * <p>
     * One approach is to get the subtree elements in some sorted order (for you to think about). Then,
     *   we can build a perfectly balanced BST.  The main idea is to rebuild the subtree rooted at this
     *   node into a perfectly balanced BST and then return the new root.  You could (but are not required
     *   to) use a recursive function.  The middle of a list is defined as floor(size()/2).
     *
     * @implNote this function is not individually tested, it is for your convenience in implementation.
     *
     * @param node root of subtree to rebuild
     * @return the new root of the balanced subtree
     */
    protected abstract Node<K, V> rebuild(Node<K, V> node);

    /**
     * Adds an element to the scapegoat tree. Passing key=null will not change the state of the tree.
     * Some guidance is provided below:
     * <p>
     * 1. Find the insertion point. Ensure you know the depth you are inserting at, as it is useful later.
     * 2. If that data already exists in the tree, skip inserting that data.
     * 3. Insert the new data
     * 4. Check if the tree is still alpha-weight-balanced. By the theory on the wiki page, we know we can
     *      check this by making sure the tree is still alpha-height-balanced.
     * 5. If not, rebalance. You will need to find the scapegoat node.
     * 6. The entire subtree rooted at the scapegoat node will need to be rebuilt using <code>rebuild()</code> above
     * 7. Connect new subtree back to main tree correctly
     * <p>
     * The above steps are based on the wikipedia article provided in the handout and at the top of this file.
     *
     * @param key key to insert
     * @param value value to associate with key
     */
    public abstract void add(K key, V value);

    /**
     * Removes an element from the tree. Does not change the tree if key does not exist in it.
     * Some guidance is provided below:
     * <p>
     * 1. Find the deletion point (if it exists).
     * 2. Deletion is done in the way you would delete a node from a regular BST.  The policy for this should be
     *     the same as the one in the professor's slides (there are multiple correct policies, follow the one on
     *     the slides).  The one difference is that we will use the successor node instead of the predecessor node.
     * 3. Slight modifications for scapegoat based on your implementation may be required.
     * 4. After deletion, if nodeCount <= alphaweight * MaxNodeCount, then we rebuild the entire tree around the
     *      "root" again (i.e. call rebuild and ensure the new root follows the properties of the root).
     *
     * @param key key to remove
     */
    public abstract void remove(K key);

    /**
     * Returns the data associated with the given key. Does not throw if key=null.
     *
     * @apiNote This function does not provide access to the Node carrying the information so that
     *   no external class can modify the structure of the tree.  For internal Node finding, see
     *   {@link #findNode}.
     *
     * @param key key to search for
     * @return value associated with key, or <code>null</code> if item does not exist.
     */
    public V get(K key){
        Node<K, V> temp = findNode(key);
        return temp != null ? temp.value : null;
    }

    /**
     * Returns the node associated with the given key
     *
     * @apiNote This function is protected because we don't want outside classes to have access to
     *   the internal structure of our tree, which is possible through Node's interface.  Thus, we
     *   have an internal function to find a Node and an external function which just returns the
     *   value, preventing external classes from modifying the tree.
     *
     * @param key key to search for
     * @return node associated with key, or <code>null</code> if item does not exist
     */
    protected abstract Node<K, V> findNode(K key);

    /**
     * Empties the tree, resetting all pertinent variables.
     */
    public abstract void clear();


    /* Below this are functions devoted to helping both the implementer and tester */
    /**
     * Returns a preorder traversal of the tree
     *
     * @param node root of tree to traverse
     * @return list storing the preorder traversal of the tree
     */
    public List<Node<K, V>> preorder(Node<K, V> node){
        List<Node<K, V>> nodes = new ArrayList<>();
        if(node == null) return nodes;

        nodes.add(node);
        if(node.left != null){
            nodes.addAll(preorder(node.left));
        }
        if(node.right != null){
            nodes.addAll(preorder(node.right));
        }
        return nodes;
    }

    /**
     * Returns an inorder traversal of the tree
     *
     * @param node root of tree to traverse
     * @return list storing the inorder traversal of the tree
     */
    public List<Node<K, V>> inorder(Node<K, V> node){
        List<Node<K, V>> nodes = new ArrayList<>();
        if(node == null) return nodes;

        inorderEfficient(nodes, node);

        return nodes;
    }

    /**
     * More efficient than recursing in inorder and combining lists on the way up because
     * 1. only one list, so much less copying of data -- an expensive operation
     * 2. list is passed as a parameter, so no creating new lists and "addAll" over and over (less overhead)
     * @param nodes list to add to
     * @param node current node
     */
    private void inorderEfficient(List<Node<K, V>> nodes, Node<K, V> node){
        if(node.left != null){
            inorderEfficient(nodes, node.left);
        }
        nodes.add(node);
        if(node.right != null){
            inorderEfficient(nodes, node.right);
        }
    }

    /**
     * Returns the number of nodes this tree contains.
     * @return number of nodes in the tree
     */
    public abstract int size();

    /**
     * Brute force calculating the node's number of children (inclusive of self). Can be accelerated.
     * @param node subtree to count
     * @return size of subtree rooted at node
     */
    protected int sizeOfSubtree(Node<K, V> node) {
        if (node == null)
            return 0;
        return 1 + sizeOfSubtree(node.left) + sizeOfSubtree(node.right);
    }

    /**
     * Returns the node immediately after the passed in node (the successor node), within the
     *   subtree rooted at <code>node</code> and as determined by key order.
     * @param node node to find successor for
     * @return successor of <code>node</code> within the subtree rooted at <code>node</code>, or <code>null</code>
     */
    protected Node<K, V> succNode(Node<K, V> node) {
        Node<K, V> succ = null;
        int result;
        Node<K, V> current = node;
        while(current != null){
            result = node.key.compareTo(current.key);
            if(result < 0){
                succ = current;
                current = current.left;
            }else{
                current = current.right;
            }
        }
        return succ;
    }


    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     * @param g graphics object to draw on
     */
    public abstract void draw(java.awt.Graphics g);

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     * @param g graphics object to draw on
     */
    public abstract void visualize(java.awt.Graphics g);
}
