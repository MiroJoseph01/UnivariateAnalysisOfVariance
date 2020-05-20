import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UnivariateAnalysisOfVariance {
    private double [] [] X;
    private int col;
    private int row;
    private List<Double> groupAverages;
    private double totalAverage;
    private double Q1; //TODO
    private double Q2; //TODO
    private double estimationOfIntergroupVariance;
    private double estimationOfIntragroupVariance;
    private double Kobs;
    private double Kcr;
    private double alpha;

    public UnivariateAnalysisOfVariance(double[][] arr, double alpha, boolean printing){
        X=arr;
        this.alpha=alpha;
        row = X.length;
        col = X[0].length;
        groupAverages = findGroupAverages(printing);
        totalAverage = findTotalAverage(printing);

        //Q1 = 4980;
        //Q2 = 7270;
        Q1 = findQ1(true);
        Q2= findQ2(true);
        estimationOfIntergroupVariance=findEstimationOfIntergroupVariance(true);
        estimationOfIntragroupVariance=findEstimationOfIntragroupVariance(true);
        Kobs=observation(true);
        Kcr = critical();
        decision();
    }

    private List<Double> findGroupAverages(boolean printing){
        StringBuilder res = new StringBuilder();
        List<Double> resList=new ArrayList<Double>();
        for (int i = 0; i<row;i++ ) {
            double sum = 0;
            res.append("x").append(i+1).append(" = 1/").append(col).append("(");
            for(int j = 0; j<col; j++){
                sum+=X[i][j];
                res.append(X[i][j]).append(" + ");
            }
            res.append(") = ");
            res.append(sum/col).append("\n");

            resList.add(sum/col);
        }
        if(printing){
        System.out.print(res.toString());
        }
        return resList;
    }

    private double findTotalAverage(boolean printing)
    {
        double totalAverage;
        double sum=0;
        StringBuilder res = new StringBuilder();
        res.append("x** = 1/").append(col-1).append("(");

        for (double i: groupAverages) {
            res.append(i).append(" + ");
            sum+=i;
        }
        res.append(") = ").append(sum/(col-1)).append("\n");
        if(printing){
            System.out.print(res.toString());
        }
        return sum/(col-1);
    }


    private double findQ1(boolean printing){
        double temp = 0;
        if (printing) System.out.printf("\nQ1 = %d(", col);

        for (int i = 0; i < groupAverages.size(); i++) {
            temp = Math.pow(groupAverages.get(i) - totalAverage, 2);
            Q1 += temp;
            if (printing && i + 1 != groupAverages.size())
                System.out.printf("%.2f + ", temp);
            else
                System.out.printf("%.2f)", temp);
        }
        Q1 *= col;
        System.out.printf(" = %.2f\n", Q1);
        return Q1;
    }

    private double findQ2(boolean printing){
        double[] rowAverage = new double[row];
        double temp = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                rowAverage[i] += X[i][j];
            }
            rowAverage[i] /= col;
        }

        if (printing) System.out.print("Q2 = ");
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X[0].length; j++)
            {
                temp = Math.pow(X[i][j] - rowAverage[i],2);
                Q2 += temp;
                if (printing && !(i + 1 == X.length && j + 1 == X[0].length)){
                    System.out.printf("%.2f + ", temp);
                } else
                {
                    System.out.printf("%.2f ", temp);
                }

            }
            System.out.println();
        }
        System.out.printf(" = %.2f\n\n", Q2);
        return Q2;
    }

    private double findEstimationOfIntergroupVariance(boolean printing){
        if(printing){
        System.out.printf("S1 = %.3f/(%d-1) = %.3f\n", Q1, row-1, Q1/(row-1));}
        return Q1/(row-1);
    }

    private double findEstimationOfIntragroupVariance(boolean printing){
        if(printing){
        System.out.printf("S2 = %.3f/(%d-%d) = %.3f\n", Q2, col*row, row, Q2/(col*row-row));}
        return Q2/(col*row-row);
    }

    private double observation(boolean printing){
        if(printing){
            System.out.printf("Kobs = %.3f/%.3f = %.3f\n",
                    estimationOfIntergroupVariance,
                    estimationOfIntragroupVariance,
                    estimationOfIntergroupVariance/estimationOfIntragroupVariance);
        }
        return  estimationOfIntergroupVariance/estimationOfIntragroupVariance;
    }

    private double critical(){
        Scanner scanner = new Scanner(System.in);
        System.out.printf("F: %.3f; %d:%d\n" +
                "Separator ',' \n", alpha, row-1, col*row - row );
        return scanner.nextDouble();
    }

    private boolean decision(){
        if(Kobs>Kcr){
            System.out.println("Differences exists");
            return true;
        }
        else{
            System.out.println("Differences dont exist");
            return false;
        }
    }

}
