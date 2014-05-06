package game.map.util;

public class Cell {
    
    public static int[][] genCells(int width, int height, int iterations) {
        int[][] cellBlock = new int[width][height];
        randomize(cellBlock);
        for(int i = 0; i < iterations; i++)
            iterateCells(cellBlock);
        return cellBlock;
    }
    
    private static void iterateCells(int[][] array) {
        int valid;
        int count;
        int[][] pointer;
        int[][] temp = new int[array.length][array[0].length];
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array[0].length; j++) {
                valid = 0;
                count = 0;
                pointer = getAdjacentIndices(i, j);
                for(int[] index : pointer) {
                    if((index[0] < array.length && index[0] >= 0) && (index[1] < array[0].length && index[1] >= 0)) {
                        valid++;
                        if(array[index[0]][index[1]] > array[i][j]) {
                            count++;
                        }
                    }
                }
                temp[i][j] = count < valid / 2 ? 0 : 1;
            }
        }
        
        for(int i = 0; i < array.length; i++)
            System.arraycopy(temp[i], 0, array[i], 0, array[0].length);
    }
    
    private static void randomize(int[][] array) {
        for(int[] subArray : array)
            for(int i = 0; i < subArray.length; i++)
                subArray[i] = (int)(Math.random() * 2);
    }
    
    private static int[][] getAdjacentIndices(int index1, int index2) {
        return new int[][]
        { 
            {index1+1, index2},
            {index1+1, index2-1},
            {index1, index2-1},
            {index1-1, index2-1},
            {index1-1, index2},
            {index1-1, index2+1},
            {index1, index2+1},
            {index1+1, index2+1}
        };
    }
}