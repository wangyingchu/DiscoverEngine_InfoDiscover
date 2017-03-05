package com.infoDiscover.app.base.relationship;

import com.infoDiscover.app.base.executor.QueryExecutor;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relation;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;

import java.util.List;

/**
 * Created by sun.
 */
public class RelationshipManager {

    public boolean checkRelationship(Fact fromFact, Fact toFact) {
        String sql = "select from (TRAVERSE both() FROM " + fromFact.getId() + ") where @class=" +
                "\"" + InfoDiscoverEngineConstant.CLASSPERFIX_FACT + toFact.getType() + "\" and" +
                " " +
                "@rid=" +
                toFact.getId();

        System.out.println("checkRelationship() sql: " + sql);

        List<Relationable> results = QueryExecutor.executeQuery(sql);
        return results.size() > 0;
    }
}
