import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public void input(double [][]array, int size) {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                System.out.println(("\n Enter a[%d][%d]: "+ r + c));
                array[r][c] = sc.nextDouble();
            }
        }
    }

    static void output(double[][] array, int size) {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                System.out.print(array[r][c]);
                System.out.print(" ");
            }
            System.out.println();;
        }
    }

    // Получение матрицы без i-й строки и j-го столбца
    static void getMatr(double[][] arr, double[][] p, int i, int j, int m) {
        int ki, kj, di, dj;
        di = 0;
        for (ki = 0; ki < m - 1; ki++) { // проверка индекса строки
            if (ki == i) di = 1;
            dj = 0;
            for (kj = 0; kj < m - 1; kj++) { // проверка индекса столбца
                if (kj == j) dj = 1;
                p[ki][kj] = arr[ki + di][kj + dj];
            }
        }
    }

    // Рекурсивное вычисление определителя
    public static double determinant(double[][] arr, int m) {
        double d, k;
        int i, j, n;
        double [][] p = new double[m][m];
        for (i = 0; i<m; i++)
            p[i] = new double [m];
        j = 0; d = 0;
        k = 1; //(-1) в степени i
        n = m - 1;
        if (m < 1) System.out.println("Определитель вычислить невозможно!");
        if (m == 1) {
            return arr[0][0];
        }
        if (m == 2) {
            return arr[0][0] * arr[1][1] - (arr[1][0] * arr[0][1]);
        }
        if (m > 2) {
            for (i = 0; i < m; i++) {
                getMatr(arr, p, i, 0, m);
                d = d + k * arr[i][0] * determinant(p, n);
                k = -k;
            }
        }
        return d;
    }

    public static void algDop(double[][] arr, int m) {
        int i, j, d, k, n;
        double [][] p = new double[m][m];
        double [][] z = new double[m][m];
        for (i = 0; i < m; i++) {
            p[i] = new double [m];
            z[i] = new double [m];
        }
        if (m >= 2) {
            if (m == 2) {
                for (int r = 0; r < m; r++) {
                    for (int c = 0; c < m; c++) {
                        z[r][c] = Math.pow(-1, r + c) * arr[m - r - 1][m - c - 1];
                    }
                }
            }
            if (m > 2) {
                for (int r = 0; r < m; r++) {
                    for (int c = 0; c < m; c++) {
                        getMatr(arr, p, r, c, m);
                        double det = determinant(p, m - 1);
                        z[r][c] = Math.pow(-1, r + c) * det;
                        if (z[r][c] == -0) {
                            z[r][c] =0;
                        }
                    }
                }
            }
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < m; c++) {
                    arr[r][c] = z[r][c];
                }
            }
        }
    }

    public static void transpose(double[][] arr, int size) {
        double t;
        for(int i = 0; i < size; ++i) {
            for(int j = i; j < size; ++j) {
                t = arr[i][j];
                arr[i][j] = arr[j][i];
                arr[j][i] = t;
            }
        }
    }

    public static void inverceMatrix(double[][] arr, int size, double det) {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                arr[r][c] = arr[r][c] / det;
            }
        }
    }

    public static void check(double[][] arr1, double[][] arr2, int[][] answer, int size){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                answer[i][j] = 0;
                for (int k = 0; k < size; k++)
                    answer[i][j] += Math.round(arr1[i][k] * arr2[k][j]);
                System.out.print(answer[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        double [][]array = ArrayUtils.readDoubleArray2FromFile("input.txt");
        double [][] array1 = new double[array.length][array.length];
        for (int r = 0; r < array.length; r++) {
            array1[r] = new double [array.length];
        }
        for (int r = 0; r < array.length; r++) {
            for (int c = 0; c < array.length; c++){
                array1[r][c] = array[r][c];
            }
        }
        //Подсчет определителя
        double det = determinant(array, array.length);
        //Проверка определителя на 0
        if (det != 0) {
            System.out.println("det = " + det + "\n");
            if (array.length > 1) {
                algDop(array, array.length);
                transpose(array, array.length);
                inverceMatrix(array, array.length, det);
            }
            System.out.println("Обратная матрица: ");
            output(array, array.length);
            int [][] answer = new int[array.length][array.length];
            System.out.println("Результат умножения исходной матрицы на обратную: ");
            check(array1, array, answer, array.length);
        } else {
            System.out.println("det = 0, => there is no inverse matrix" + "\n");
        }
    }
}
