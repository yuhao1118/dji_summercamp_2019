public class Test {
    public static void main(String[] args) {
        Map map = ReadMap.read("map.txt");
        Algo algo = new Algo();
        algo.start(map);
    }
}
