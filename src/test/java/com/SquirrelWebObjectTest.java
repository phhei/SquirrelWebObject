package com;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SquirrelWebObjectTest {

    SquirrelWebObject o;

    @Before
    public void setUp() throws Exception {
        o = new SquirrelWebObject();
        List<String> pendingURIlist = new ArrayList<>(3);
        pendingURIlist.add("1. URI");
        pendingURIlist.add("https://philippheinisch.de");
        pendingURIlist.add("<http://dbPedia.org/ontology/>");
        o.setPendingURIs(pendingURIlist);
    }

    @Test
    public void convertToByteStream() {
        byte[] stream = o.convertToByteStream();

        SquirrelWebObject cal = SquirrelWebObjectHelper.convertToObject(stream);

        assertEquals(o.getCountOfPendingURIs(), cal.getCountOfPendingURIs());
    }

    @Test
    public void equals() {
        SquirrelWebObject o1 = new SquirrelWebObject();
        SquirrelWebObject o2 = new SquirrelWebObject();

        assertTrue("2 new SquirrelWebObjects should be equal...", o1.equals(o2));

        List<String> pendingURIS = new ArrayList<>(1);
        pendingURIS.add("https://www.philippheinisch.de");
        try {
            o2.setPendingURIs(pendingURIS);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        assertFalse("One of them got pending URIs --> not equal any more", o1.equals(o2));

        try {
            o1.setPendingURIs(pendingURIS);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        assertTrue("Now the other SquirrelWebObjects got the same list --> equal again!", o1.equals(o2));
    }
}