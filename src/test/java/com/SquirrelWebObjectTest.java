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
}