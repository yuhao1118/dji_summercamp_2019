import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

/*
 * @Description: Get a map from a file
 * @Author: Yuhao Li
 * @Date: 07/04/2019
 */
public final class ReadMap {

    public static Map read(String filename) {
        /*
         * @Description: read map file
         * @Param: [filename]
         * @return:  map[][]
         */

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
            scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");

            //read first line (height, width)
            String rcLine = scanner.nextLine();
            String[] rc = rcLine.trim().split("\\s+");

            int row = Integer.parseInt(rc[0]);
            int col = Integer.parseInt(rc[1]);

            // initialising start/end node
            Node start = null;
            Node end = null;

            map = new char[row][col];
            //读取后续的row行
            for (int i = 0; i < row; i++) {
                String line = scanner.nextLine();

                if (line.length() != col) {
                    throw new IllegalArgumentException("Error in " + row + "th row");
                }

                for (int j = 0; j < col; j++) {
                    map[i][j] = line.charAt(j);
                    if(map[i][j] == 'S') start = new Node(j, i);
                    if(map[i][j] == 'E') end = new Node(j, i);
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
