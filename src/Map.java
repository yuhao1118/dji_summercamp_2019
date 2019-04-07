/*
* @Description: Map object for hold a map
* @Author: Yuhao Li
* @Date: 06/04/2019
*/
public class Map {
    public char[][] map;    // map data-struct. Different integers show the different items on the map
    public int width;   // map width
    public int height;  // map height
    public Node start;  // start node
    public Node end;    // end node

    public Map(char[][] map, int width, int height, Node start, Node end){
        /*
        * @Description: Initialsing map object
        * @Param: [map, width, height, start, end]
        */

        this.map = map;
        this.width = width;
        this.height = height;
        this.start = start;
        this.end = end;
    }

}
