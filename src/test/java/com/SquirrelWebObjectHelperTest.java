package com;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class SquirrelWebObjectHelperTest {

    @org.junit.jupiter.api.Test
    void convertToObject() {
        SquirrelWebObject o = new SquirrelWebObject();
        byte[] stream = o.convertToByteStream();

        SquirrelWebObject cal = SquirrelWebObjectHelper.convertToObject(stream);
        Assertions.assertEquals(o.getCountOfPendingURIs(), cal.getCountOfPendingURIs());
    }
}