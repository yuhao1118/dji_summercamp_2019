import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 * @Description: Algorithm object, for solving the question
 * @Author: Yuhao Li
 * @Date: 06/04/2019
 */
public class Algo {
    /*Define map representings*/
    public final static int BAR = '1';
    public final static int PATH = '2';

    /*Define E cost price*/
    public final static int DIRECT = 10; // move directly
    public final static int DIAGONAL = 14;  // move diagonally

    /*Define open/close lists*/
    Queue<Node> open_list = new PriorityQueue<>(); // Queue the Nodes in ascending order (use Comparable interface)
    List<Node> close_list = new ArrayList<>();  //Simply holding the Nodes, no order required

    public void start(Map map){
        if(map != null){
            open_list.clear();
            close_list.clear();
            open_list.add(map.start);

            while (!open_list.isEmpty()){
                if (in_close(map.end.point.x, map.end.point.y)){
                    System.out.println("Solution found! ");
                    System.out.println("Minimum E+T: " + (map.end.e_cost+map.end.t_cost)/10);
                    map.map = draw_path(map, map.end);
                    print_map(map);
                    break;
                }

                // poll the first node (smallest E+T) from the open_list
                Node current = open_list.poll();
                close_list.add(current);

                // add surrounding nodes to the open_list
                add_all_open(map,current);
            }
        }

    }

    public void add_all_open(Map map, Node current){
        /*
        * @Description: Add the surrounding 8 nodes to open_list
        * @Param: [map, current]
        * @return: null
        */

        int x = current.point.x;
        int y = current.point.y;

        // direct four nodes
        add_one_open(map, current, x+1, y, DIRECT);
        add_one_open(map, current, x-1, y, DIRECT);
        add_one_open(map, current, x, y+1, DIRECT);
        add_one_open(map, current, x, y-1, DIRECT);

        // diagonal four nodes
        add_one_open(map, current, x+1, y+1, DIAGONAL);
        add_one_open(map, current, x+1, y-1, DIAGONAL);
        add_one_open(map, current, x-1, y-1, DIAGONAL);
        add_one_open(map, current, x-1, y+1, DIAGONAL);
    }

    public void add_one_open(Map map, Node current, int x, int y, int e_value) {
        /*
         * @Description: add one node to open_list
         * @Param: [map, current, x, y, e_cost]
         * @return: null
         */

        // Node should at least addable
        if (node_addable(map, x, y)) {
            Node end = map.end;
            Point current_point = new Point(x, y);
            Node child = find_in_open(current_point);
            int e_cost = current.e_cost + e_value;

            // if the child is not in the open_list
            // it should be a new node
            if (child == null) {
                int t_cost = cal_t_cost(end.point, current_point);

                if (is_end_point(end.point, current_point)) {
                    child = end;
                    child.parent = current;
                    child.e_cost = e_cost;
                    child.t_cost = t_cost;
                    close_list.add(child);
                } else {
                    child = new Node(current_point, current, e_cost, t_cost);

                    // add to open_list
                    open_list.add(child);
                }

            }
            // if the child is already in the list
            // the new e_cost is less than the previous e_cost
            // need to be refresh some value
            else if (child.e_cost > e_cost) {
                child.e_cost = e_cost; // no need to refresh t_cost, will not change
                child.parent = current;

                // add to open_list
                open_list.add(child);
            }
        }
    }

    public boolean node_addable(Map map, int x, int y) {
        /*
         * @Description: return is the current node is availiale to be added to the open_lsit
         * @Param: [map, x, y]
         * @return:  true -- ok to add; false -- not ok to add
         */

        // In the map ?
        if (x < 0 || x >= map.width || y < 0 || y >= map.height)
            return false;

        // Accessible ?
        if (map.map[y][x] == BAR)
            return false;

        // In the close_list ?
        if (in_close(x, y))
            return false;

        return true;
    }

    public boolean in_close(int x, int y) {
        /*
         * @Description: return is node with given coordinate is in the close_list
         * @Param: [x, y]
         * @return:  true -- in the list; false -- not in the list
         */

        // If close_list is empty
        if (close_list.isEmpty()) return false;

        // go through the list
        for (Node node : close_list) {
            if(node.point.x == x && node.point.y == y)
                return true;
        }

        return false;
    }

    public Node find_in_open(Point current) {
        /*
         * @Description: find a node in open_list
         * @Param: [x, y]
         * @return:  node
         */

        // if the open_list is empty
        if (open_list.isEmpty()) return null;

        for (Node node : open_list) {
            if (node.point.equals(current))
                return node;
        }

        return null;
    }

    public Node find_in_close(Point current){
        /*
        * @Description: find a node in close_list
        * @Param: [current]
        * @return:  node
        */

        // if the close_list is empty
        if (close_list.isEmpty()) return null;

        for (Node node : close_list) {
            if (node.point.x == current.x && node.point.y == current.y)
                return node;
        }

        return null;
    }

    public int cal_t_cost(Point end, Point current) {
        /*
         * @Description: Use Manhattan method to predict the t_cost
         * @Param: [end, current]
         * @return:  t_cost
         */

        return Math.abs(end.x - current.x) + Math.abs(end.y - current.y);
    }

    public boolean is_end_point(Point end, Point current) {
        /*
        * @Description: return if the current point is the end point
        * @Param: [end, current]
        * @return:  true -- is end point; false -- not end point
        */
        return end.x == current.x && end.y == current.y;
    }

    public char[][] draw_path(Map map, Node end){
        /*
        * @Description: Once the solution is found, drawing the
        * path by tracing the parent node, starting from the
        * end node
        * @Param: [map, end]
        * @return: solution_map[][]
        */

        char[][] solution_map = map.map;

        while (end != null){
            Point current_point = end.point;
            if (!current_point.equals(map.start.point) && !current_point.equals(map.end.point))
                solution_map[current_point.y][current_point.x] = PATH;
            end = end.parent;
        }

        return solution_map;
    }

    public void print_map(Map map){
        for(int i=0; i<map.height; i++){
            for(int j=0; j<map.width; j++){
                System.out.print(map.map[i][j]+" ");
            }
            System.out.println();
        }
    }
}
