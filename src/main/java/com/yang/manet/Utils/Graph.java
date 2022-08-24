package com.yang.manet.Utils;

import java.util.*;

/**
 * @ClassName:Graph
 * @Auther: yyj
 * @Description:
 * @Date: 24/06/2022 10:31
 * @Version: v1.0
 */
public class Graph<T> {

    // We use Hashmap to store the edges in the graph
    private Map<T, List<T>> map = new HashMap<>();

    // This function adds a new vertex to the graph
    public void addVertex(T s) {
        map.put(s, new LinkedList<T>());
    }

    // This function adds the edge
    // between source to destination
    public void addEdge(T source, T destination, boolean bidirectional) {
        if (!map.containsKey(source))
            addVertex(source);
        if (!map.containsKey(destination))
            addVertex(destination);
        map.get(source).add(destination);
        if (bidirectional) {
            map.get(destination).add(source);
        }
    }

    // This function gives the count of vertices
    public void getVertexCount() {
        System.out.println("The graph has "
                + map.keySet().size()
                + " vertex");
    }

    // This function gives the count of edges
    public void getEdgesCount(boolean bidirection) {
        int count = 0;
        for (T v : map.keySet()) {
            count += map.get(v).size();
        }
        if (bidirection == true) {
            count = count / 2;
        }
        System.out.println("The graph has "
                + count
                + " edges.");
    }

    // This function gives whether
    // a vertex is present or not.
    public void hasVertex(T s) {
        if (map.containsKey(s)) {
            System.out.println("The graph contains "
                    + s + " as a vertex.");
        } else {
            System.out.println("The graph does not contain "
                    + s + " as a vertex.");
        }
    }

    // This function gives whether an edge is present or not.
    public void hasEdge(T s, T d) {
        if (map.get(s).contains(d)) {
            System.out.println("The graph has an edge between "
                    + s + " and " + d + ".");
        } else {
            System.out.println("The graph has no edge between "
                    + s + " and " + d + ".");
        }
    }
    public static List<List<String>> allPath = new ArrayList<>();
    public static List<String> currentPath = new ArrayList<>();

    public void TraversalPath(TreeNode node,List<String> list , String dest) {
        if (node.children == null || node.name.equals(dest)) {
            if (node.name.equals(dest)) {
                allPath.add(new ArrayList<>(list));
                currentPath = new ArrayList<>(list);
                return;
            } else return;
        }

        for (int i = 0; i < Objects.requireNonNull(node.children).size(); i++) {
            TreeNode tmp = node.children.get(i);
            list.add(tmp.name);
            TraversalPath(tmp, list, dest);
            list.remove(tmp.name);
        }
    }

    // Prints the adjancency list of each vertex.
    public Map<String, List<String>> PathNode = new HashMap<>();

    public class TreeNode{
        public String  name;
        public List<TreeNode> children;
        public TreeNode() {}
        public TreeNode(String name) {
            this.name = name;
        }
        public TreeNode(String name, List<TreeNode > children) {
            this.name = name;
            this.children = children;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<TreeNode> getChildren() {
            return children;
        }

        public void setChildren(List<TreeNode> children) {
            this.children = children;
        }

        public TreeNode addNode(String name, TreeNode node, List<TreeNode > children){
            if(node.children == null){
                node = new TreeNode(name,children);
            }else {
                for(TreeNode node1 : Objects.requireNonNull(node.children)){
                    if (node1.name.equals(name)){
                        node1.setChildren(children);
                        return node;
                    }
                }
                for(TreeNode node1 : Objects.requireNonNull(node.children)){
                    node.addNode(name,node1,children);
                }
            }
            return node;
        }



    }


    public TreeNode getPath(String source){
        allPath.clear();
        Set<String> seen = new HashSet<>();
        Stack<String> stack = new Stack<>();
        stack.push(source);
        seen.add(source);
        TreeNode node = new TreeNode(source);
        while (!stack.isEmpty()){
            int size = stack.size();
            for(int i =0 ;i < size; i++){
                String curr = stack.pop();
                seen.add(curr);
                List<TreeNode> children = new ArrayList<>();
                List<String> childList = PathNode.get(curr);
                for(String n : childList){
                    if(!seen.contains(n) && !stack.contains(n)){
                        TreeNode tmp = new TreeNode(n);
                        children.add(tmp);
                        if(!stack.contains(n)){
                            stack.push(n);
                        }
                    }
                }
                if(children.size()!=0){
                    node = node.addNode(curr,node,children);
                }
            }
        }
        return node;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (T v : map.keySet()) {
            List<String> Set = new LinkedList<>();
            builder.append(v.toString() + ": ");
            for (T w : map.get(v)) {
                if(!Set.contains(w.toString())){
                    Set.add(w.toString());
                    builder.append(w.toString() + " ");
                }
            }
            PathNode.put(v.toString(),Set);
            builder.append("\n");
        }
        return (builder.toString());
    }





}