package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple Main.
 */
public class Main
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Main(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( Main.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testMain()
    {
        assertTrue( true );
    }
}
