import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.XChartSeriesMarkers;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;

/**
 *
 * @author Rowan Holop
 * @version March 29 2020
 */
class RunningTimeRecorder
{
    /**
     * Takes in an int, produces a random list of numbers of n size and performs various sorting algorithms.
     * @param int n
     * @return Double[] of runningTimes: order - insertionSort, selectionSort, mergeSort, bubbleSort
     */
    public static Double[] empiricalRunningTime(int n) 
    {
        Integer[] arr = RandomIntegerGenerator.generateRandomNumbers(n);

        // copy 1
        Integer[] copiedArray1 = new Integer[n];
        System.arraycopy(arr, 0, copiedArray1, 0, n);
        // copy 2
        Integer[] copiedArray2 = new Integer[n];
        System.arraycopy(arr, 0, copiedArray2, 0, n);
        // copy 3
        Integer[] copiedArray3 = new Integer[n];
        System.arraycopy(arr, 0, copiedArray3, 0, n);
        // copy 4
        Integer[] copiedArray4 = new Integer[n];
        System.arraycopy(arr, 0, copiedArray4, 0, n);
        // array of arrays
        Integer[][] copiedArrays = {copiedArray1, copiedArray2, copiedArray3, copiedArray4};

        Double[] runTimes = new Double[4]; // holder array of running times to return

        int c = 0; // counter
        for (Integer[] copiedArray : copiedArrays) 
        {
            long startTime;
            long stopTime;
            // Following loop allows us to iterate through each array and perform the different sorting algorithms
            if (c == 0) {startTime = System.nanoTime(); SortingAlgorithms.insertionSort(copiedArray); stopTime = System.nanoTime();}
            else if (c == 1) {startTime = System.nanoTime(); SortingAlgorithms.selectionSort(copiedArray); stopTime = System.nanoTime();}
            else if (c == 2) {startTime = System.nanoTime(); SortingAlgorithms.mergeSort(copiedArray); stopTime = System.nanoTime();}
            else {startTime = System.nanoTime(); SortingAlgorithms.bubbleSort(copiedArray); stopTime = System.nanoTime();}
            c++;
            Double runningTime = (double)(stopTime - startTime)/1000000000;
            runTimes[c-1] = runningTime;
        }

        return runTimes;
    }    
}

/**
 * This wrapper class records results into a .csv file.
 */
class FileRecord {

    FileWriter csv;

    FileRecord(String filename, String[] titles) 
    /**
     * @param String filename
     * @param String[] titles (for columns of chart)
     */
    {
        try 
        {
            this.csv = new FileWriter(filename);
            write(String.join(",", titles));
            write("\n");
        }
        catch (IOException e) 
        {
            System.out.println("Unable to create file.");
            e.printStackTrace();
        }
    }

    public void write(String text)
    /**
     * @param String text - text to write to file
     */
    {
        try 
        {
            csv.append(text);
        }
        catch (IOException e) 
        {
            System.out.println("Unable to write to file.");
            e.printStackTrace();
        }
    }

    public void writeRow(Double x, Double[] dataList)
    /**
     * Shortcut method for writing a whole row at once.
     * @param Double x - row title
     * @param Double[] dataList - data to be stored in said row
     */
    {
        String[] data = ConvertArray.doubletoString(dataList);
        List<String> row = Arrays.asList(data);
        write(Double.toString(x) + ",");
        write(String.join(",", row));
        write("\n");
    }

    public void end()
    /**
     * Closes the .csv file
     */
    {
        try {
            csv.flush();
            csv.close();
            System.out.println("Written to file");
        }
        catch (IOException e) {
            System.out.println("An error occurred when closing file.");
            e.printStackTrace();
        }
    }
}

/**
 * Simple class to convert certain types of arrays into other arrays.
 */
class ConvertArray 
{
    public static String[] doubletoString(Double[] arr)
    /**
     * @param Double[]
     * @return String[]
     */
    {
        String[] temp = new String[arr.length];
        int i = 0;
        for (Double d : arr) {
            String t = d.toString();
            temp[i] = t;
            i++;
        }
        return temp;
    }

    public static double[] bigDtoLittleD(Double[] arr)
    /**
     * @param Double[]
     * @return double[]
     */
    {
        double[] temp = new double[arr.length];
        int i = 0;
        for (Double d : arr) {
            double t = d;
            temp[i] = t;
            i++;
        }
        return temp;
    }
}

class RunningTimeChart
/**
 * Wraper class for creating the runtimes chart.
 */ 
{
    XYChart chart;
    double[] xValues;
    double[] yInsertionSort;
    double[] ySelectionSort;
    double[] yMergeSort;
    double[] yBubbleSort;

    RunningTimeChart(String title, String xAxis, String yAxis, double[] xValues)
    /**
     * Constructor
     * @param String title
     * @param String xAxis
     * @param String yAxis
     * @param double[] xValues
     */
    {
        // Build the chart
        chart = new XYChartBuilder().width(1400).height(1000).title(title).xAxisTitle(xAxis).yAxisTitle(yAxis).build();
        // Some stylistic choices
        chart.getStyler().setYAxisMin((double)0.00000);
        chart.getStyler().setYAxisMax((double).18000);
        chart.getStyler().setXAxisMin((double)5);
        chart.getStyler().setXAxisMax((double)10000.0);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chart.getStyler().setXAxisLogarithmic(true);
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        // Initialize member variables
        this.xValues = xValues;
        yInsertionSort = new double[xValues.length];
        ySelectionSort = new double[xValues.length];
        yMergeSort = new double[xValues.length];
        yBubbleSort = new double[xValues.length];
    }

    public void update(int i, Double[] values)
    /**
     * Updates the internal double[]'s for each sorting method.
     * @param int i - index to insert value
     * @param Double[] values - runtime values to distribute amongst the internal sorting method's arrays.
     */
    {
        double[] v = ConvertArray.bigDtoLittleD(values);
        yInsertionSort[i] = v[0];
        ySelectionSort[i] = v[1];
        yMergeSort[i] = v[2];
        yBubbleSort[i] = v[3];
    }

    public void makeChart() 
    /**
     * Call this method after all updates are complete to create the chart's line values.
     */
    {
        chart.addSeries("Insertion Sort", xValues, yInsertionSort);
        chart.addSeries("Selection Sort", xValues, ySelectionSort);
        chart.addSeries("Merge Sort", xValues, yMergeSort);
        chart.addSeries("Bubble Sort", xValues, yBubbleSort);
    }

    public void saveChart(String filename)
    /**
     * Saves the chart to a new file name - saves as PNG
     * @param String filename ex: "file.PNG"
     */
    {
        try {
            BitmapEncoder.saveBitmapWithDPI(chart, filename, BitmapEncoder.BitmapFormat.PNG, 300);
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void showChart() 
    /**
     * Displays the chart.
     */
    {
        new SwingWrapper(chart).displayChart();
    }

}

public class Assignment02 
{
    public static void main(String[] args){

        // Numbers for test data
        double[] numbers = {5, 10, 50, 100, 200, 500, 1000, 2000, 5000, 10000};
        // Column titles
        String[] titles = {"Number of Elements", "Insertion Sort Time", "Selection Sort Time", 
                "Merge Sort Time", "Bubble Sort Time"};

        String file = "assignment02RunningHolop.csv"; // File title
        FileRecord f = new FileRecord(file, titles); // Create new FileRecord Object to write to

        // Create chart
        RunningTimeChart c = new RunningTimeChart("Running Times", "Number of Elements", "System Time (ms)", numbers);

        // Iterate through sample numbers
        for (int i = 0; i < numbers.length; i++) {
            // Run method to get array of runtimes
            Double[] doubleRunTimes = RunningTimeRecorder.empiricalRunningTime((int)numbers[i]);
            // Record runtimes in .csv file
            f.writeRow(numbers[i], doubleRunTimes);
            // Update chart with runtimes
            c.update(i, doubleRunTimes);
        }

        f.end(); // Close the file
        c.makeChart(); // Create the chart
        c.showChart(); // Display the chart
        c.saveChart("graph.PNG"); // Save the chart
    }
}
