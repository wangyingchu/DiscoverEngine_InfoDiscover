package com.infoDiscover.solution.common.relationship;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Property;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.database.DatabaseManager;
import com.infoDiscover.solution.arch.progress.manager.ProgressManager;
import com.infoDiscover.solution.arch.progress.manager.TaskManager;
import com.infoDiscover.solution.arch.progress.manager.UserManager;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relationable;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.orientechnologies.orient.core.command.traverse.OTraverse;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.filter.OSQLPredicate;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by sun.
 */
public class RelationshipManager {
    private final  static Logger logger = LogManager.getLogger(RelationshipManager.class);

    public boolean checkRelationship(Fact fromFact, Fact toFact) {
        List<Relationable> results = findRelationships(fromFact,toFact);
        return results.size() > 0;
    }

    public List<Relationable> findRelationships(Fact fromFact, Fact toFact) {
        logger.debug("Enter method findRelationships() from fact: " + fromFact.getId() + " to " +
                "fact: " + toFact.getId());
        String sql = "select from (TRAVERSE both() FROM " + fromFact.getId() + ") where @class=" +
                "\"" + InfoDiscoverEngineConstant.CLASSPERFIX_FACT + toFact.getType() + "\" and" +
                " " +
                "@rid=" +
                toFact.getId();

        logger.debug("findRelationships() sql: " + sql);

        List<Relationable> results = QueryExecutor.executeFactQuery(sql);

        for(Relationable r : results) {
            List<Property> ps = r.getProperties();
            for(Property p : ps) {
                logger.debug(p.getPropertyName());
                logger.debug(p.getPropertyValue());
            }
        }

        logger.debug("Exit method findRelationships()...");
        return results;
    }


//    public List<Relationable> findRelationships2(Fact fromFact, Fact toFact) {
//        logger.debug("Enter method findRelationships() from fact: " + fromFact.getId() + " to " +
//                "fact: " + toFact.getId());
////        String sql = "select $path from (TRAVERSE both() FROM " + fromFact.getId() + ") ";
//
//        String sql = "select flatten(shortestPath) from (select shortestPath(#366:1,#385:2))";
//        logger.debug("findRelationships() sql: " + sql);
//
//
//        OrientGraph graph = new OrientGraph("remote:localhost/InfoDiscover", "root", "wyc");
//
//        List<ODocument> paths = graph.getRawGraph().query(new OSQLSynchQuery<Object>(sql));
//        for(ODocument doc: paths) {
//            logger.debug("doc: " + doc.toJSON());
//        }
//
////        List<ODocument> spath = orientGraph.getRawGraph().query(new OSQLSynchQuery<Object>(
////                "select flatten(shortestPath("+v1+","+v2+",'BOTH').out)"));
//
//        for (OIdentifiable v : (Iterable<OIdentifiable>) graph.command(
//                new OCommandSQL(sql)).execute()) {
//            logger.debug("v: " + v);
//        }
//
//
//        logger.debug("Exit method findRelationships()...");
//        return null;
//    }
    public static void main(String[] args) throws InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        ProgressManager progressManager = new ProgressManager();
        TaskManager taskManager = new TaskManager();
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();
        InformationExplorer ie = ids.getInformationExplorer();
        Fact fromFact = progressManager.getProgressById(ie, "maintain001");
//        Fact toFact = taskManager.getTaskById(ie, "apply001");
        Dimension toFact = new UserManager().getUserById(ie, "zhongyanyang");
//
//        RelationshipManager manager = new RelationshipManager();
//        List<Relationable> list = manager.findRelationships2(fromFact,toFact);


        OrientGraph graph = new OrientGraph("remote:localhost/InfoDiscover", "root", "wyc");
        OrientVertex v = graph.getVertex("#366:1");
//
        for (OIdentifiable id : new OTraverse()
                //.target(new ORecordId("#6:0"), new ORecordId("#6:1"))
                .target(new ORecordId("#366:1"))
                //.fields("out", "int")
                .field("all()")
                .limit(100)
                .predicate( new OSQLPredicate("$depth <=10"))) {

            //System.out.println( "id:" + id);
        }
//
//        for (OIdentifiable id : new OSQLSynchQuery<ODocument>("traverse in, out from " +
//                "#366:1" + " while $depth <= 10") )  {
//            System.out.println(id);
//        }


//
//        Iterable<Edge>  e = v.getEdges(Direction.BOTH);
//        for (Edge edge: e) {
//            logger.debug(edge.getId());
//        }


    }
}
