import java.io.*;

public class KCliqueToSat {
    public int[][] matrix;
    public int k;
    public int n;
    public int nrClauses = 0;

    public void readInput(String inputFile, KCliqueToSat kCliqueToSat) {
        try {
            File file = new File(inputFile);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            line = br.readLine();
            int n = Integer.parseInt(line.split(" ")[0]);
            kCliqueToSat.n = n;
            int matrixDimension = n + 1;
            int k = Integer.parseInt(line.split(" ")[1]);
            kCliqueToSat.k = k;
            int[][] matrix = new int[matrixDimension][matrixDimension];
            for (int i = 1; i <= n-1; i++) {
                line = br.readLine();
                if(!line.equals("")) {
                    String[] numbers = line.split(" ");
                    for (int j = 0; j < numbers.length; j++) {
                        int val = Integer.parseInt(numbers[j]);
                        matrix[i][val] = 1;
                        matrix[val][i] = 1;
                    }
                }
            }
            kCliqueToSat.matrix = matrix;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeClauses(String outputFile, String clauses) {
        try(FileWriter fw = new FileWriter(outputFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(clauses);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String constructFirstClauses(KCliqueToSat kCliqueToSat) {
        StringBuilder clauses = new StringBuilder();
        int k = kCliqueToSat.k;
        int n = kCliqueToSat.n;
        for(int i = 1; i <= k; i++) {
            for(int v = 1; v <= n; v++) {
                int x = (i - 1) * n + v;
                clauses.append(x).append(" ");
            }
            clauses.append("0" + "\n");
            kCliqueToSat.nrClauses++;
        }
        return clauses.toString();
    }

    public String writeAllSecondVar(KCliqueToSat kCliqueToSat, int v, int x, int i) {
        StringBuilder clauses = new StringBuilder();
        int k = kCliqueToSat.k;
        int n = kCliqueToSat.n;
        for(int j = i + 1; j <= k; j++) {
            int y = ((j - 1) * n + v) * (-1);
            clauses.append(x).append(" ").append(y).append(" 0" + "\n");
            kCliqueToSat.nrClauses++;
        }
        return clauses.toString();
    }

    public String constructSecondClauses(KCliqueToSat kCliqueToSat) {
        StringBuilder clauses = new StringBuilder();
        int k = kCliqueToSat.k;
        int n = kCliqueToSat.n;
        for(int v = 1; v <= n; v++) {
            for(int i = 1; i <= k - 1; i++) {
                int x = ((i - 1) * n + v) * (-1);
                clauses.append(writeAllSecondVar(kCliqueToSat, v, x, i));
            }
        }
        return clauses.toString();
    }

    public String writeClauses(KCliqueToSat kCliqueToSat, int u, int v) {
        StringBuilder clauses = new StringBuilder();
        int k = kCliqueToSat.k;
        int n = kCliqueToSat.n;
        for(int i = 1; i <= k; i++) {
            int x = ((i - 1) * n + u) * (-1);
            clauses.append(writeAllSecondVar(kCliqueToSat, v, x, i));
        }
        return clauses.toString();
    }

    public String constructThirdClauses(KCliqueToSat kCliqueToSat) {
        StringBuilder clauses = new StringBuilder();
        int k = kCliqueToSat.k;
        int n = kCliqueToSat.n;
        int[][] matrix = kCliqueToSat.matrix;
        for(int v = 1; v <= n; v++) {
            for(int u = 1; u <= n; u++) {
                if(matrix[v][u] == 0 && u != v) {
                    clauses.append(writeClauses(kCliqueToSat, u, v));
                }
            }
        }
        return clauses.toString();
    }

    public static void main(String[] args) {
        KCliqueToSat kCliqueToSat = new KCliqueToSat();
        String inputFile = args[0];
        String outputFile = args[1];

        kCliqueToSat.readInput(inputFile, kCliqueToSat);

        int noOfVar = kCliqueToSat.n * kCliqueToSat.k;

        String clauses = kCliqueToSat.constructFirstClauses(kCliqueToSat);
        clauses += kCliqueToSat.constructSecondClauses(kCliqueToSat);
        clauses += kCliqueToSat.constructThirdClauses(kCliqueToSat);

        String finalString = "p cnf " + noOfVar + " " + kCliqueToSat.nrClauses + "\n" + clauses;
        finalString = finalString.substring(0, finalString.length() - 1);

        kCliqueToSat.writeClauses(outputFile, finalString);
    }

}
