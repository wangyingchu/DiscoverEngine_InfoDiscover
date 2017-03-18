package com.infoDiscover.solution.common.relationship;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun.
 */
public class Short2 {

    public static void main(String[] args) {
        OrientGraph orient = new OrientGraph("remote:localhost/DemoArch", "root", "wyc");


//        List<ODocument> spath = graph.getRawGraph().query( new OSQLSynchQuery<Object>(sql));

        String start = "#105:3";
        String end = "#107:2";

        List<ODocument> b = orient.getRawGraph().query(new OSQLSynchQuery<Object>("select expand" +
                "(shortestPath(" + start + "," +
                end + ", 'BOTH'))"));
//
//                .command("sql","select expand(shortestPath(" + start + "," +
//                end + ", 'BOTH'))");
        List<ODocument> list = new ArrayList<>();
        for (int i = 0; i < b.size() - 1; i++) {
            String rid1 = b.get(i).getIdentity().toString();
            String rid2 = b.get(i + 1).getIdentity().toString();
            String query = "select from e where in = " + rid1 + " and out = " + rid2 + " limit" +
                    " 1";
            List<ODocument> edge = b = orient.getRawGraph().query(new OSQLSynchQuery<Object>
                    (query));

//
//                    g.command("sql",query);
            list.add(b.get(i));
            list.add(edge.get(0));
            if (i == b.size() - 2) {
                list.add(b.get(i + 1));
            }
        }

        System.out.println("list: " + list.size());
    }

}
