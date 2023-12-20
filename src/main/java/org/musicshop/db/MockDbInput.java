package org.musicshop.db;

public final class MockDbInput {

    public static final String inputJson =
        // Nice try with the invalid/inconsistent JSON ;)
        "{" +
            "'sales':[" +
                "{'PRODUCT': 1,'USER': 1}," +
                "{'PRODUCT': 2,'USER': 1}," +
                "{'PRODUCT': 1,'USER': 2}," +
                "{'PRODUCT': 3,'USER': 1}," +
                "{'PRODUCT': 1,'USER': 1}," +
                "{'PRODUCT': 1,'USER': 3}," +
                "{'PRODUCT': 4,'USER': 3}" +
            "]" +
        "}";

}
