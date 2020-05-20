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
        totalAverage=findTotalAverage(printing);
        Q1 = 4980;//TODO
        Q2 = 7270;//TODO
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

    //private findQ1(boolean printing){} TODO
    //private findQ2(boolean printing){} TODO

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
