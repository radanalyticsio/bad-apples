package io.radanalytics.limitfilter;

/**
 * main class for running the application
 */
public class App
{
    public static void main(String[] args)
    {
        LimitFilter lf = new LimitFilter();
        lf.filter(10000);
    }
}
