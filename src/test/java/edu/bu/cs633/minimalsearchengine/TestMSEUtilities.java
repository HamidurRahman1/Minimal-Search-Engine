package edu.bu.cs633.minimalsearchengine;

import edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.ConstraintViolationException;
import edu.bu.cs633.minimalsearchengine.utils.Utilities;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestMSEUtilities {

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void invalidQueryWhenNullTest() {

        Utilities.cleanWordsFromQuery(null);

    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void invalidQueryWhenEmptyTest() {

        Utilities.cleanWordsFromQuery("  ");

    }

    @Test
    public void cleanWordsTest() {

        Assert.assertEquals(Utilities.cleanWordsFromQuery("remove I from this").size(), 2);

    }

    @Test
    public void noCleanWordsTest() {

        Assert.assertEquals(Utilities.cleanWordsFromQuery("some other").size(), 0);

    }

}
