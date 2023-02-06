package edu.bu.cs633.minimalsearchengine;

import edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.ConstraintViolationException;
import edu.bu.cs633.minimalsearchengine.utils.Utilities;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.UnknownHostException;

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

    @Test(expectedExceptions = ProtocolException.class)
    public void urlWithInvalidProtocolTest() throws IOException {

        Assert.assertFalse(Utilities.isValidURL("random://google.con"));

    }

    @Test(expectedExceptions = UnknownHostException.class)
    public void urlWithInvalidProtocolMissingForwardSlashesTest() throws IOException {

        Assert.assertFalse(Utilities.isValidURL("https:google.con"));

    }

    @Test(expectedExceptions = UnknownHostException.class)
    public void urlWithUnknownHostTest() throws IOException {

        Assert.assertFalse(Utilities.isValidURL("https://thisUrlDoesNotExitsInTheInternet.com"));

    }

}
