package com;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

class SquirrelWebObjectHelperTest {

    @org.junit.jupiter.api.Test
    void convertToObject() {
        SquirrelWebObject o = new SquirrelWebObject();
        List<String> pendingURIlist = new ArrayList<>(3);
        pendingURIlist.add("1. URI");
        pendingURIlist.add("https://philippheinisch.de");
        pendingURIlist.add("<http://dbPedia.org/ontology/>");
        o.setPendingURIs(pendingURIlist);

        byte[] stream = o.convertToByteStream();

        SquirrelWebObject cal = SquirrelWebObjectHelper.convertToObject(stream);

        Assertions.assertEquals(o.getCountOfPendingURIs(), cal.getCountOfPendingURIs());
    }
}