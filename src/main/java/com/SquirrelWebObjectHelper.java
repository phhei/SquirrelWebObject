package com;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

public abstract class SquirrelWebObjectHelper {
    public static SquirrelWebObject convertToObject(byte[] bytes) {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return (SquirrelWebObject) o.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return new SquirrelWebObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
            SquirrelWebObject ret = new SquirrelWebObject();
            ret.setPendingURIs(new ArrayList<String>(Collections.singleton("ERROR while deserialazing: " + e.getMessage() + ". The currupted Bytestream is: " + bytes)));
            return ret;
        }
    }
}
