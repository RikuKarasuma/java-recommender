package org.musicshop.db;

import org.musicshop.pojo.Relationship;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public final class FakeStaticDb {

    private static final List<Relationship> RELATIONSHIPS = new ArrayList<>();
    public static List<Relationship> getRelationships() {
        return RELATIONSHIPS;
    }

    public static void addRelationships(final List<Relationship> relationships) {
        if (CollectionUtils.isEmpty(relationships))
            return;

        RELATIONSHIPS.addAll(relationships);
    }
}
