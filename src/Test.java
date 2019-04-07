/*
* @Description: Main file to test a sample map
* @Author: Yuhao Li
* @Date: 07/04/2019
*/
public class Test {
    public static void main(String[] args) {
        Map map = ReadMap.read("map.txt");  // get map object
        Algo algo = new Algo();     //Initialise algo object
        algo.start(map);        //start calculation
    }
}
