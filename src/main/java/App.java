
    public class App {
        public static void main(String[] args){
            double[][] arr= {
                    {200,140,170,145,165},
                    {190,150,210,150,150},
                    {230,190,200,190,200},
                    {150,170,150,170,180}
            };
            UnivariateAnalysisOfVariance solution =
                    new UnivariateAnalysisOfVariance(arr, 0.05,true);
        }
}

