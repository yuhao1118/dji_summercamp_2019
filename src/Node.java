/*
 * @Description: Node object. Implements with Comparable
 * interface to be added to a priority queue.
 * @Author: Yuhao Li
 * @Date: 06/04/2019
 */
public class Node implements Comparable<Node>{
    public Point point;
    public Node parent;     // parent node
    public int e_cost;      // controlling cost for taking to this node
    public int t_cost;      // time cost to get to the end node(point)

    public Node(int x, int y){
        /*
        * @Description: Initialising Node through /*
        * a point. Use for constructing the first Node
        * @Param: [x, y]
        * @return: null
        */

        this.point = new Point(x, y); // One node with one point object
    }

    public Node(Point point, Node parent, int e_cost, int t_cost){
         /*
         * @Description: Initialising Node through /*
         * parent node.
         * @Param: [point, parent, g, h]
         * @return: null
         */

         this.point = point;
         this.parent = parent;
         this.e_cost = e_cost;
         this.t_cost = t_cost;
    }

    @Override
    public int compareTo(Node o) {
        /*
        * @Description: compareTo interface
        * 0 -- equal; 1 -- greater than; -1 -- less than
        * @Param: [o]
        * @return: null
        */
        if (o == null) return -1;
        if(this.e_cost + this.t_cost > o.e_cost + o.t_cost)
            return 1;
        else if (this.e_cost + this.t_cost < o.e_cost + o.t_cost)
            return -1;
        return 0;
    }
}
