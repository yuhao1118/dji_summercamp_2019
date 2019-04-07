import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*
 * @Description: Read and get a map object from a file
 * @Author: Yuhao Li
 * @Date: 07/04/2019
 */
public final class ReadMap {

    /*Define map representings*/
    public final static char WORKABLE = '0';
    public final static char BAR = '1';
    public final static char PATH = '2';
    public final static char START = 'S';
    public final static char END = 'E';

    public static Map read(String filename) {
        /*
         * @Description: read map file
         * @Param: [filename]
         * @return:  map
         */

        // map[y][x] match the coordinate (x,y)
        char[][] map;

        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }

        Scanner scanner = null;

        try {
            File file = new File(filename);

            if (!file.exists()) {
                throw new IllegalArgumentException("File doesn't exist");
            }

            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), StandardCharsets.UTF_8);

            //read first line (height, width)
            String rc_line = scanner.nextLine();
            String[] rc = rc_line.trim().split("\\s+");

            int row = Integer.parseInt(rc[0]);
            int col = Integer.parseInt(rc[1]);

            // initialising start/end node
            Node start = null;
            Node end = null;

            map = new char[row][col];

            // read following rows
            for (int i = 0; i < row; i++) {
                String line = scanner.nextLine();

                if (line.length() != col) {
                    throw new IllegalArgumentException("Error in " + row + "th row");
                }

                for (int j = 0; j < col; j++) {
                    map[i][j] = line.charAt(j);
                    if(map[i][j] == START) start = new Node(j, i);
                    if(map[i][j] == END) end = new Node(j, i);
                }
            }
            return new Map(map, col, row, start, end);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return null;
    }
}
