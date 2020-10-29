
public class RandomIntegerGenerator{
    /**
     * generateRandomNumbers(int n) method generates an array
     * of n random Integers. 
     * @param n
     * @return an array of n Integers
     */

    public static Integer[] generateRandomNumbers(int n)
    {
        if (n < 0)
            n = 10; //default value

        Integer[] data = new Integer[n];

        for(int i = 0; i < n; i++)
            data[i] = (int) (Math.random() * 1000.0);
        return data;
    }
}

