import com.orientechnologies.orient.core.record.impl.ORecordBytes;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.*;

import java.io.*;
import java.util.Iterator;

public class OrientDBResearch {
    public static void main(String[] args){
        OrientGraph graph=null;
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/orientTest","root","wyc").setupPool(1,10);
        try {
            graph = factory.getTx();


            OrientVertex fromVertex =graph.getVertex("#29:8");
            OrientVertex toVertex =graph.getVertex("#29:1");

            Iterable<Edge> edgeIt=fromVertex.getEdges(toVertex, Direction.OUT, "VF_RELATION_ChildRel1");

            System.out.println(edgeIt);
            Iterator<Edge> itr=edgeIt.iterator();
            while(itr.hasNext()){
                Edge currentEdge=itr.next();

                System.out.println(currentEdge.getLabel());
                System.out.println(currentEdge.getId());

            }





          //  graph.addEdge(null,outA,inB,"Label");

/*
            graph.createEdgeType("edgeNae","superType");
            graph.addEdge()
            graph.dropEdgeType();
            graph.getEdge()
            graph.removeEdge();
            graph.dropEdgeType();

*/
        //    OrientEdge ed=graph.getEdge("dede");

           // OrientEdgeType oet= graph.createEdgeType("aa");

            //graph.getEdgeType()


          //  System.out.println(graph);


         //   OrientVertexType account = graph.createVertexType("Account");

         //   OrientVertexType address = graph.createVertexType("Address");
            //address.setSuperClass(account);

           // graph.addEdge(null)
/*
            Vertex vPerson = graph.addVertex("class:Account");
            vPerson.setProperty("firstName", "John1");
            vPerson.setProperty("lastName", "Smith2");

            Vertex vAddress = graph.addVertex("class:Address");
            vAddress.setProperty("street", "Van Ness Ave.21");
            vAddress.setProperty("city", "San Francisco2");
            vAddress.setProperty("state", "California USA3");

            vAddress.setProperty("sss",new File("fileeee"));
            graph.addEdge(null,vPerson,vAddress,"Address");
*/

/*
            Vertex vPerson = graph.addVertex("class:Account");
            vPerson.setProperty("firstName", "yingchu");
            vPerson.setProperty("lastName", "wang");





            File propertyFile=new File("/Users/wangychu/Desktop/DSC09764.JPG");
            vPerson.setProperty("familyPhoto",propertyFile);
*/
          //  File propertyFile=new File("/Users/wangychu/Desktop/DSC09764.JPG");

            //try {
            //    FileInputStream fis=new FileInputStream(propertyFile);
               // ORecordBytes record = new ORecordBytes().

/*
            Vertex vPerson = graph.addVertex("class:Account");
            vPerson.setProperty("firstName", "yingchu");
            vPerson.setProperty("lastName", "wang12345");


                byte[] buffer = null;
                try {
                    File file = new File("/Users/wangychu/Desktop/DSC09764.JPG");
                    FileInputStream fis = new FileInputStream(file);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
                    byte[] b = new byte[1000];
                    int n;
                    while ((n = fis.read(b)) != -1) {
                        bos.write(b, 0, n);
                    }
                    fis.close();
                    bos.close();
                    buffer = bos.toByteArray();




                    ORecordBytes record = new ORecordBytes(buffer);

                    vPerson.setProperty("binaryFile",record);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

*/





/*


            for(Vertex v: graph.getVerticesOfClass("Account")){

                //if("#12:1".equals(v.getId())){


                if(v.getProperty("binaryFile")!=null) {

                    System.out.println(v.getProperty("binaryFile").getClass());




                    ORecordBytes fileRecord=(ORecordBytes)v.getProperty("binaryFile");



                    getFile(fileRecord.getRecord().toStream(),"/Users/wangychu/Desktop/","123.jpg");


                    //File photoFile=v.getProperty("familyPhoto");

                    //System.out.println(photoFile.exists());

                    //System.out.println(photoFile.isFile());

                    //System.out.println(photoFile);
                }


               // }
                System.out.println(v.getId());

            }

*/
        } finally {
            graph.shutdown();

            factory.close();
        }
    }


    public static void getFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath+"\\"+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
