public class Point {
    /*Coordinate of point*/
    public int x;
    public int y;

    public Point(int x, int y){
        /*
        * @Description: Constructor of Point
        * @Param: [x, y]
        * @return: null
        */
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj){
        /*
        * @Description: equals function for coordinate comparation
        * @Param: [obj]
        * @return:  true -- equal; false -- not equal
        */

        // if obj is null, not equal
        if(obj == null) return false;

        // if obj is an instance of Point class
        if(obj instanceof Point){
            Point temp = (Point)obj;
            return temp.x == this.x && temp.y == this.y;
        }

        // Other situation, not equal.
        return false;
    }
}
