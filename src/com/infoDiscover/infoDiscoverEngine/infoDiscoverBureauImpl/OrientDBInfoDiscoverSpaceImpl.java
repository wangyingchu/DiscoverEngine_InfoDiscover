package com.infoDiscover.infoDiscoverEngine.infoDiscoverBureauImpl;

import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.dataMartImpl.*;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouseImpl.OrientDBInformationExplorerImpl;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineDataOperationUtil;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.orientechnologies.orient.core.exception.OValidationException;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.*;

import java.util.*;

public class OrientDBInfoDiscoverSpaceImpl implements InfoDiscoverSpace {

    private OrientGraph graph;
    private String spaceName;
    private OrientGraphFactory factory;

    public OrientDBInfoDiscoverSpaceImpl(String spaceName, OrientGraph graph,OrientGraphFactory factory) {
        this.spaceName = spaceName;
        this.graph = graph;
        this.factory = factory;
    }

    @Override
    public Fact addFact(Fact fact) throws InfoDiscoveryEngineRuntimeException {
        String factType = fact.getType();
        if (!hasFactType(factType)) {
            String exceptionMessage = factType + " isn't a Fact Type";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
        String orientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_FACT + factType;
        String factInstanceClassName = InfoDiscoverEngineConstant.CLASSINSTANCEPERFIX +
                orientDBClassName;
        try {
            OrientVertex resultVertex = null;
            OrientDBMeasurableImpl currentMeasurable = (OrientDBMeasurableImpl) fact;
            Map<String, Object> initProperties = currentMeasurable.getInitProperties();
            if (initProperties != null) {
                resultVertex = this.graph.addVertex(factInstanceClassName, initProperties);
            } else {
                resultVertex = this.graph.addVertex(factInstanceClassName);
            }
            if (resultVertex != null) {
                this.graph.commit();
                if (fact instanceof OrientDBFactImpl) {
                    OrientVertex instance = resultVertex.getVertexInstance();
                    OrientDBFactImpl implFact = (OrientDBFactImpl) fact;
                    implFact.setFactVertex(instance);
                }
                return fact;
            }
        } catch (OValidationException e) {
            String exceptionMessage = "Init Fact data not match Fact Type " + factType + "'s " +
                    "validation requirement";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
        return null;
    }

    @Override
    public boolean removeFact(String factId) throws InfoDiscoveryEngineRuntimeException {
        OrientVertex ov = this.graph.getVertex(factId);
        if (ov == null) {
            String exceptionMessage = "Measurable of id " + factId + " doesn't exist";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);

        } else {
            String factType = ov.getType().getName();
            if (factType.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_FACT)) {
                this.graph.removeVertex(ov);
                this.graph.commit();
                return true;
            } else {
                String exceptionMessage = "Measurable of id " + factId + " isn't a Fact";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
        }
    }

    @Override
    public Fact getFactById(String factId) throws InfoDiscoveryEngineRuntimeException {
        OrientVertex ov = this.graph.getVertex(factId);
        if (ov == null) {
            return null;
        } else {
            String factType = ov.getType().getName();
            if (factType.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_FACT)) {
                String factBusinessName = factType.replaceFirst(InfoDiscoverEngineConstant
                        .CLASSPERFIX_FACT, "");
                OrientDBFactImpl targetFact = new OrientDBFactImpl(factBusinessName);
                targetFact.setFactVertex(ov);
                return targetFact;
            } else {
                String exceptionMessage = "Measurable of id " + factId + " isn't a Fact";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
        }
    }

    @Override
    public Dimension addDimension(Dimension dimension) throws InfoDiscoveryEngineRuntimeException {
        String dimensionType = dimension.getType();
        if (!hasDimensionType(dimensionType)) {
            String exceptionMessage = dimensionType + " isn't a Dimension Type";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
        String orientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION + dimensionType;
        String dimensionInstanceClassName = InfoDiscoverEngineConstant.CLASSINSTANCEPERFIX +
                orientDBClassName;
        try {
            OrientVertex resultVertex = null;
            OrientDBMeasurableImpl currentMeasurable = (OrientDBMeasurableImpl) dimension;
            Map<String, Object> initProperties = currentMeasurable.getInitProperties();
            if (initProperties != null) {
                resultVertex = this.graph.addVertex(dimensionInstanceClassName, initProperties);
            } else {
                resultVertex = this.graph.addVertex(dimensionInstanceClassName);
            }
            if (resultVertex != null) {
                this.graph.commit();
                if (dimension instanceof OrientDBDimensionImpl) {
                    OrientVertex instance = resultVertex.getVertexInstance();
                    OrientDBDimensionImpl implDimension = (OrientDBDimensionImpl) dimension;
                    implDimension.setDimensionVertex(instance);
                }

                return dimension;
            }
        } catch (OValidationException e) {
            String exceptionMessage = "Init Dimension data not match Dimension Type " +
                    dimensionType + "'s validation requirement";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
        return null;
    }

    @Override
    public boolean removeDimension(String dimensionId) throws InfoDiscoveryEngineRuntimeException {
        OrientVertex ov = this.graph.getVertex(dimensionId);
        if (ov == null) {
            String exceptionMessage = "Measurable of id " + dimensionId + " doesn't exist";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        } else {
            String dimensionType = ov.getType().getName();
            if (dimensionType.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION)) {
                this.graph.removeVertex(ov);
                this.graph.commit();
                return true;
            } else {
                String exceptionMessage = "Measurable of id " + dimensionId + " isn't a Dimension";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
        }
    }

    @Override
    public Dimension getDimensionById(String dimensionId) throws
            InfoDiscoveryEngineRuntimeException {
        OrientVertex ov = this.graph.getVertex(dimensionId);
        if (ov == null) {
            return null;
        } else {
            String dimensionType = ov.getType().getName();
            if (dimensionType.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION)) {
                String dimensionBusinessName = dimensionType.replaceFirst
                        (InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION, "");
                OrientDBDimensionImpl targetDimension = new OrientDBDimensionImpl
                        (dimensionBusinessName);
                targetDimension.setDimensionVertex(ov);
                return targetDimension;
            } else {
                String exceptionMessage = "Measurable of id " + dimensionId + " isn't a Dimension";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
        }
    }

    @Override
    public boolean hasFactType(String factType) {
        String orientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_FACT + factType;
        OrientVertexType ovt = this.graph.getVertexType(orientDBClassName);
        if (ovt != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public FactType getFactType(String typeName) {
        String orientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_FACT + typeName;
        OrientVertexType ovt = this.graph.getVertexType(orientDBClassName);
        if (ovt != null) {
            FactType currentFactType = new OrientDBFactTypeImpl(ovt, this.graph);
            return currentFactType;
        } else {
            return null;
        }
    }

    @Override
    public FactType addFactType(String factType) throws InfoDiscoveryEngineDataMartException {
        if (hasFactType(factType)) {
            String exceptionMessage = "Fact Type " + factType + " already exists";
            throw InfoDiscoveryEngineException.getDataMartException(exceptionMessage);
        } else {
            //All fact type should be the child class of class "VF_FACT"
            OrientVertexType rootFactType = this.graph.getVertexType(InfoDiscoverEngineConstant
                    .FACT_ROOTCLASSNAME);
            if (rootFactType == null) {
                this.graph.createVertexType(InfoDiscoverEngineConstant.FACT_ROOTCLASSNAME);
            }
            String orientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_FACT + factType;
            OrientVertexType ovt = this.graph.createVertexType(orientDBClassName,
                    InfoDiscoverEngineConstant.FACT_ROOTCLASSNAME);
            if (ovt != null) {
                FactType currentFactType = new OrientDBFactTypeImpl(ovt, this.graph);
                return currentFactType;
            } else {
                return null;
            }
        }
    }

    @Override
    public boolean removeFactType(String factType) throws InfoDiscoveryEngineDataMartException {
        if (!hasFactType(factType)) {
            String exceptionMessage = "Fact Type " + factType + " not exist";
            throw InfoDiscoveryEngineException.getDataMartException(exceptionMessage);
        } else {
            String orientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_FACT + factType;
            if (this.graph.getVertexType(orientDBClassName).count(true) > 0) {
                //can't delete factType which contains fact
                return false;
            } else {
                this.graph.dropVertexType(orientDBClassName);
                return true;
            }
        }
    }

    @Override
    public List<String> getFactTypesList() {
        List<String> factTypeList = new ArrayList<String>();
        OrientVertexType rootFactType = this.graph.getVertexType(InfoDiscoverEngineConstant
                .FACT_ROOTCLASSNAME);
        if (rootFactType == null) {
            return factTypeList;
        } else {
            Collection<OClass> factClassCollection = rootFactType.getSubclasses();
            Iterator<OClass> itr = factClassCollection.iterator();
            while (itr.hasNext()) {
                OClass currentClass = itr.next();
                String className = currentClass.getName();
                String factType = className.replaceFirst(InfoDiscoverEngineConstant
                        .CLASSPERFIX_FACT, "");
                factTypeList.add(factType);
            }
            return factTypeList;
        }
    }

    @Override
    public DimensionType addChildDimensionType(String childTypeName, String parentTypeName)
            throws InfoDiscoveryEngineDataMartException {
        String parentOrientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION +
                parentTypeName;
        OrientVertexType parentOvt = this.graph.getVertexType(parentOrientDBClassName);
        if (parentOvt == null) {
            String exceptionMessage = "Parent Dimension Type " + parentTypeName + " not exist";
            throw InfoDiscoveryEngineException.getDataMartException(exceptionMessage);
        }
        String childOrientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION +
                childTypeName;
        OrientVertexType childOvt = this.graph.getVertexType(childOrientDBClassName);
        if (childOvt != null) {
            String exceptionMessage = "Child Dimension Type " + childTypeName + " already exists";
            throw InfoDiscoveryEngineException.getDataMartException(exceptionMessage);
        }
        OrientVertexType newOvt = this.graph.createVertexType(childOrientDBClassName,
                parentOrientDBClassName);
        if (newOvt != null) {
            DimensionType newDimensionType = new OrientDBDimensionTypeImpl(newOvt, this.graph);
            return newDimensionType;
        } else {
            return null;
        }
    }

    @Override
    public List<String> getRootDimensionTypesList() {
        List<String> dimensionTypeList = new ArrayList<String>();
        OrientVertexType rootDimensionType = this.graph.getVertexType(InfoDiscoverEngineConstant
                .DIMENSION_ROOTCLASSNAME);
        if (rootDimensionType == null) {
            return dimensionTypeList;
        } else {
            Collection<OClass> dimensionClassCollection = rootDimensionType.getSubclasses();
            Iterator<OClass> itr = dimensionClassCollection.iterator();
            while (itr.hasNext()) {
                OClass currentClass = itr.next();
                String className = currentClass.getName();
                String dimensionType = className.replaceFirst(InfoDiscoverEngineConstant
                        .CLASSPERFIX_DIMENSION, "");
                dimensionTypeList.add(dimensionType);
            }
            return dimensionTypeList;
        }
    }

    @Override
    public boolean hasRelationType(String relationType) {
        String orientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + relationType;
        OrientEdgeType ovt = this.graph.getEdgeType(orientDBClassName);
        if (ovt != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public RelationType addRelationType(String relationType) throws
            InfoDiscoveryEngineDataMartException {
        if (hasRelationType(relationType)) {
            String exceptionMessage = "Relation Type " + relationType + " already exists";
            throw InfoDiscoveryEngineException.getDataMartException(exceptionMessage);
        } else {
            //All relation type should be the child class of class "VF_RELATION"
            OrientEdgeType rootRelationType = this.graph.getEdgeType(InfoDiscoverEngineConstant
                    .RELATION_ROOTCLASSNAME);
            if (rootRelationType == null) {
                this.graph.createEdgeType(InfoDiscoverEngineConstant.RELATION_ROOTCLASSNAME);
            }
            String orientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION +
                    relationType;
            OrientEdgeType oet = this.graph.createEdgeType(orientDBClassName,
                    InfoDiscoverEngineConstant.RELATION_ROOTCLASSNAME);
            if (oet != null) {
                RelationType newRelationType = new OrientDBRelationTypeImpl(oet, this.graph);
                return newRelationType;
            } else {
                return null;
            }
        }
    }

    @Override
    public RelationType getRelationType(String typeName) {
        String orientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + typeName;
        OrientEdgeType oet = this.graph.getEdgeType(orientDBClassName);
        if (oet != null) {
            RelationType newRelationType = new OrientDBRelationTypeImpl(oet, this.graph);
            return newRelationType;
        } else {
            return null;
        }
    }

    @Override
    public boolean removeRelationType(String typeName) throws InfoDiscoveryEngineDataMartException {
        if (!hasRelationType(typeName)) {
            String exceptionMessage = "Relation Type " + typeName + " not exists";
            throw InfoDiscoveryEngineException.getDataMartException(exceptionMessage);
        } else {
            String orientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + typeName;
            OrientEdgeType oet = this.graph.getEdgeType(orientDBClassName);
            if (oet.getSubclasses().size() > 0) {
                String exceptionMessage = "Relation Type " + typeName + " has child relation";
                throw InfoDiscoveryEngineException.getDataMartException(exceptionMessage);
            }
            if (this.graph.getEdgeType(orientDBClassName).count(true) > 0) {
                //can't delete relationType which contains relation
                return false;
            } else {
                this.graph.dropEdgeType(orientDBClassName);
                return true;
            }
        }
    }

    @Override
    public RelationType addChildRelationType(String childTypeName, String parentTypeName) throws
            InfoDiscoveryEngineDataMartException {
        String parentOrientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION +
                parentTypeName;
        OrientEdgeType parentOet = this.graph.getEdgeType(parentOrientDBClassName);
        if (parentOet == null) {
            String exceptionMessage = "Parent Relation Type " + parentTypeName + " not exist";
            throw InfoDiscoveryEngineException.getDataMartException(exceptionMessage);
        }
        String childOrientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION +
                childTypeName;
        OrientEdgeType childOet = this.graph.getEdgeType(childOrientDBClassName);
        if (childOet != null) {
            String exceptionMessage = "Child Relation Type " + childTypeName + " already exists";
            throw InfoDiscoveryEngineException.getDataMartException(exceptionMessage);
        }
        OrientEdgeType newOet = this.graph.createEdgeType(childOrientDBClassName,
                parentOrientDBClassName);
        if (newOet != null) {
            RelationType newRelationType = new OrientDBRelationTypeImpl(newOet, this.graph);
            return newRelationType;
        } else {
            return null;
        }
    }

    @Override
    public List<String> getRootRelationTypesList() {
        List<String> relationTypeList = new ArrayList<String>();
        OrientEdgeType rootRelationType = this.graph.getEdgeType(InfoDiscoverEngineConstant
                .RELATION_ROOTCLASSNAME);
        if (rootRelationType == null) {
            return relationTypeList;
        } else {
            Collection<OClass> relationClassCollection = rootRelationType.getSubclasses();
            Iterator<OClass> itr = relationClassCollection.iterator();
            while (itr.hasNext()) {
                OClass currentClass = itr.next();
                String className = currentClass.getName();
                String relationType = className.replaceFirst(InfoDiscoverEngineConstant
                        .CLASSPERFIX_RELATION, "");
                relationTypeList.add(relationType);
            }
            return relationTypeList;
        }
    }

    @Override
    public Relation addDirectionalFactRelation(Fact fromFact, Fact toFact, String relationType,
                                               boolean repeatable) throws
            InfoDiscoveryEngineRuntimeException {
        if (!hasRelationType(relationType)) {
            String exceptionMessage = "Relation Type " + relationType + " not exist";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        } else {
            String relationTypeClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION +
                    relationType;
            OrientVertex fromVertex = ((OrientDBFactImpl) fromFact).getFactVertex();
            OrientVertex toVertex = ((OrientDBFactImpl) toFact).getFactVertex();
            if (fromVertex.getId().equals(toVertex.getId())) {
                String exceptionMessage = "From and to Facts can't be the same one";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
            if (!repeatable) {
                Iterable<Edge> edgeIterator = fromVertex.getEdges(toVertex, Direction.OUT,
                        relationTypeClassName);
                if (edgeIterator.iterator().hasNext()) {
                    String exceptionMessage = "Relation " + relationType + " From Fact " +
                            fromFact.getId() + " to " + toFact.getId() + " already exists";
                    throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
                }
            }
            OrientEdge resultEdge = this.graph.addEdge(null, fromVertex, toVertex,
                    relationTypeClassName);
            this.graph.commit();
            OrientDBRelationImpl newRelation = new OrientDBRelationImpl(relationType);
            newRelation.setRelationEdge(resultEdge);
            return newRelation;
        }
    }

    @Override
    public Relation addDirectionalFactRelation(Fact fromFact, Fact toFact, String relationType,
                                               boolean repeatable, Map<String, Object>
                                                       initRelationProperties) throws
            InfoDiscoveryEngineRuntimeException {
        if (!hasRelationType(relationType)) {
            String exceptionMessage = "Relation Type " + relationType + " not exist";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        } else {
            String relationTypeClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION +
                    relationType;
            OrientVertex fromVertex = ((OrientDBFactImpl) fromFact).getFactVertex();
            OrientVertex toVertex = ((OrientDBFactImpl) toFact).getFactVertex();
            if (fromVertex.getId().equals(toVertex.getId())) {
                String exceptionMessage = "From and to Facts can't be the same one";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
            if (!repeatable) {
                Iterable<Edge> edgeIterator = fromVertex.getEdges(toVertex, Direction.OUT,
                        relationTypeClassName);
                if (edgeIterator.iterator().hasNext()) {
                    String exceptionMessage = "Relation " + relationType + " From Fact " +
                            fromFact.getId() + " to " + toFact.getId() + " already exists";
                    throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
                }
            }
            OrientEdge resultEdge = fromVertex.getGraph().addEdge(null, fromVertex, toVertex,
                    relationTypeClassName);

            if (initRelationProperties != null) {
                Set<String> propertyNameSet = initRelationProperties.keySet();
                Iterator<String> iterator = propertyNameSet.iterator();
                while (iterator.hasNext()) {
                    String propertyName = iterator.next();
                    if (InfoDiscoverEngineDataOperationUtil.checkNotReservedProperty
                            (propertyName)) {
                        Object propertyValue = initRelationProperties.get(propertyName);
                        InfoDiscoverEngineDataOperationUtil.saveOrientPropertyWithoutCommit
                                (resultEdge, propertyName, propertyValue);
                    }
                }
            }

            this.graph.commit();
            OrientDBRelationImpl newRelation = new OrientDBRelationImpl(relationType);
            newRelation.setRelationEdge(resultEdge);
            return newRelation;
        }
    }

    @Override
    public Relation getRelationById(String relationId) {
        OrientEdge targetEdge = this.graph.getEdge(relationId);
        if (targetEdge != null) {
            String edgeOrientClassName = targetEdge.getType().getName();
            String relationType = edgeOrientClassName.replaceFirst(InfoDiscoverEngineConstant
                    .CLASSPERFIX_RELATION, "");
            OrientDBRelationImpl targetRelation = new OrientDBRelationImpl(relationType);
            targetRelation.setRelationEdge(targetEdge);
            return targetRelation;
        } else {
            return null;
        }
    }

    @Override
    public boolean removeRelation(String relationId) throws InfoDiscoveryEngineRuntimeException {
        OrientEdge targetEdge = this.graph.getEdge(relationId);
        if (targetEdge == null) {
            String exceptionMessage = "Relation of id" + relationId + " not exist";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        } else {
            this.graph.removeEdge(targetEdge);
            this.graph.commit();
            return true;
        }
    }

    @Override
    public Relation addDirectionalDimensionRelation(Dimension fromDimension, Dimension
            toDimension, String relationType, boolean repeatable) throws
            InfoDiscoveryEngineRuntimeException {
        if (!hasRelationType(relationType)) {
            String exceptionMessage = "Relation Type " + relationType + " not exist";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        } else {
            String relationTypeClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION +
                    relationType;
            OrientVertex fromVertex = ((OrientDBDimensionImpl) fromDimension).getDimensionVertex();
            OrientVertex toVertex = ((OrientDBDimensionImpl) toDimension).getDimensionVertex();
            if (fromVertex.getId().equals(toVertex.getId())) {
                String exceptionMessage = "From and to Dimensions can't be the same one";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
            if (!repeatable) {
                Iterable<Edge> edgeIterator = fromVertex.getEdges(toVertex, Direction.OUT,
                        relationTypeClassName);
                if (edgeIterator.iterator().hasNext()) {
                    String exceptionMessage = "Relation " + relationType + " From Dimension " +
                            "" + fromDimension.getId() + " to " + toDimension.getId() + " already" +
                            " exists";
                    throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
                }
            }
            OrientEdge resultEdge = this.graph.addEdge(null, fromVertex, toVertex,
                    relationTypeClassName);
            this.graph.commit();
            OrientDBRelationImpl newRelation = new OrientDBRelationImpl(relationType);
            newRelation.setRelationEdge(resultEdge);
            return newRelation;
        }
    }

    @Override
    public Relation addDirectionalDimensionRelation(Dimension fromDimension, Dimension toDimension, String relationType) throws InfoDiscoveryEngineRuntimeException {
        if (!hasRelationType(relationType)) {
            String exceptionMessage = "Relation Type " + relationType + " not exist";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        } else {
            String relationTypeClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION +
                    relationType;
            OrientVertex fromVertex = ((OrientDBDimensionImpl) fromDimension).getDimensionVertex();
            OrientVertex toVertex = ((OrientDBDimensionImpl) toDimension).getDimensionVertex();
            if (fromVertex.getId().equals(toVertex.getId())) {
                String exceptionMessage = "From and to Dimensions can't be the same one";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }

            Iterable<Edge> edgeIterator = fromVertex.getEdges(toVertex, Direction.OUT,
                    relationTypeClassName);
            if (edgeIterator.iterator().hasNext()) {
                //already attached just return this old one
                Edge existEdge = edgeIterator.iterator().next();
                OrientDBRelationImpl newRelation = new OrientDBRelationImpl(relationType);
                newRelation.setRelationEdge((OrientEdge) existEdge);
                return newRelation;
            } else {
                OrientEdge resultEdge = this.graph.addEdge(null, fromVertex, toVertex,
                        relationTypeClassName);
                this.graph.commit();
                OrientDBRelationImpl newRelation = new OrientDBRelationImpl(relationType);
                newRelation.setRelationEdge(resultEdge);
                return newRelation;
            }
        }
    }

    @Override
    public boolean hasDimensionType(String typeName) {
        String orientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION + typeName;
        OrientVertexType ovt = this.graph.getVertexType(orientDBClassName);
        if (ovt != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DimensionType getDimensionType(String typeName) {
        String orientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION + typeName;
        OrientVertexType ovt = this.graph.getVertexType(orientDBClassName);
        if (ovt != null) {
            DimensionType newDimensionType = new OrientDBDimensionTypeImpl(ovt, this.graph);
            return newDimensionType;
        } else {
            return null;
        }
    }

    @Override
    public DimensionType addDimensionType(String typeName) throws
            InfoDiscoveryEngineDataMartException {
        if (hasDimensionType(typeName)) {
            String exceptionMessage = "Dimension Type " + typeName + " already exists";
            throw InfoDiscoveryEngineException.getDataMartException(exceptionMessage);
        } else {
            //All dimension type should be the child class of class "VF_DIMENSION"
            OrientVertexType rootDimensionType = this.graph.getVertexType
                    (InfoDiscoverEngineConstant.DIMENSION_ROOTCLASSNAME);
            if (rootDimensionType == null) {
                this.graph.createVertexType(InfoDiscoverEngineConstant.DIMENSION_ROOTCLASSNAME);
            }
            String orientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION + typeName;
            OrientVertexType ovt = this.graph.createVertexType(orientDBClassName,
                    InfoDiscoverEngineConstant.DIMENSION_ROOTCLASSNAME);
            if (ovt != null) {
                DimensionType newDimensionType = new OrientDBDimensionTypeImpl(ovt, this.graph);
                return newDimensionType;
            } else {
                return null;
            }
        }
    }

    @Override
    public boolean removeDimensionType(String typeName) throws
            InfoDiscoveryEngineDataMartException {
        if (!hasDimensionType(typeName)) {
            String exceptionMessage = "Dimension Type " + typeName + " not exist";
            throw InfoDiscoveryEngineException.getDataMartException(exceptionMessage);
        } else {
            String orientDBClassName = InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION + typeName;
            OrientVertexType ovt = this.graph.getVertexType(orientDBClassName);
            if (ovt.getSubclasses().size() > 0) {
                String exceptionMessage = "Dimension Type " + typeName + " has child dimension " +
                        "type";
                throw InfoDiscoveryEngineException.getDataMartException(exceptionMessage);
            }
            if (this.graph.getVertexType(orientDBClassName).count(true) > 0) {
                //can't delete dimensionType which contains dimension
                return false;
            } else {
                this.graph.dropVertexType(orientDBClassName);
                return true;
            }
        }
    }

    @Override
    public Relation attachFactToDimension(String factId, String dimensionId, String relationType)
            throws InfoDiscoveryEngineRuntimeException {
        //create Fact to Dimension Link, if already exist, ignore this method and return old
        // relation
        if (!hasRelationType(relationType)) {
            String exceptionMessage = "Relation Type " + relationType + " not exist";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        } else {
            Fact fact = getFactById(factId);
            if (fact == null) {
                String exceptionMessage = "Fact of id " + factId + " not exist";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
            Dimension dimension = getDimensionById(dimensionId);
            if (dimension == null) {
                String exceptionMessage = "Dimension of id " + dimensionId + " not exist";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
            String relationTypeClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION +
                    relationType;

            OrientVertex fromVertex = ((OrientDBFactImpl) fact).getFactVertex();
            OrientVertex toVertex = ((OrientDBDimensionImpl) dimension).getDimensionVertex();

            Iterable<Edge> edgeIterator = fromVertex.getEdges(toVertex, Direction.OUT,
                    relationTypeClassName);
            if (edgeIterator.iterator().hasNext()) {
                //already attached just return this old one
                Edge existEdge = edgeIterator.iterator().next();
                OrientDBRelationImpl newRelation = new OrientDBRelationImpl(relationType);
                newRelation.setRelationEdge((OrientEdge) existEdge);
                return newRelation;
            } else {
                OrientEdge resultEdge = this.graph.addEdge(null, fromVertex, toVertex,
                        relationTypeClassName);
                this.graph.commit();
                OrientDBRelationImpl newRelation = new OrientDBRelationImpl(relationType);
                newRelation.setRelationEdge(resultEdge);
                return newRelation;
            }
        }
    }

    @Override
    public Relation attachFactToDimension(String factId, String dimensionId, String relationType,
                                          Map<String, Object> initRelationProperties) throws
            InfoDiscoveryEngineRuntimeException {
        //create Fact to Dimension Link, if already exist, ignore this method and return old
        // relation
        if (!hasRelationType(relationType)) {
            String exceptionMessage = "Relation Type " + relationType + " not exist";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        } else {
            Fact fact = getFactById(factId);
            if (fact == null) {
                String exceptionMessage = "Fact of id " + factId + " not exist";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
            Dimension dimension = getDimensionById(dimensionId);
            if (dimension == null) {
                String exceptionMessage = "Dimension of id " + dimensionId + " not exist";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
            String relationTypeClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION +
                    relationType;

            OrientVertex fromVertex = ((OrientDBFactImpl) fact).getFactVertex();
            OrientVertex toVertex = ((OrientDBDimensionImpl) dimension).getDimensionVertex();

            Iterable<Edge> edgeIterator = fromVertex.getEdges(toVertex, Direction.OUT,
                    relationTypeClassName);
            if (edgeIterator.iterator().hasNext()) {
                //already attached just return this old one
                Edge existEdge = edgeIterator.iterator().next();
                OrientDBRelationImpl newRelation = new OrientDBRelationImpl(relationType);
                newRelation.setRelationEdge((OrientEdge) existEdge);
                return newRelation;
            } else {
                OrientEdge resultEdge = this.graph.addEdge(null, fromVertex, toVertex,
                        relationTypeClassName);
                if (initRelationProperties != null) {
                    Set<String> propertyNameSet = initRelationProperties.keySet();
                    Iterator<String> iterator = propertyNameSet.iterator();
                    while (iterator.hasNext()) {
                        String propertyName = iterator.next();
                        if (InfoDiscoverEngineDataOperationUtil.checkNotReservedProperty
                                (propertyName)) {
                            Object propertyValue = initRelationProperties.get(propertyName);
                            InfoDiscoverEngineDataOperationUtil.saveOrientPropertyWithoutCommit
                                    (resultEdge, propertyName, propertyValue);
                        }
                    }
                }
                this.graph.commit();
                OrientDBRelationImpl newRelation = new OrientDBRelationImpl(relationType);
                newRelation.setRelationEdge(resultEdge);
                return newRelation;
            }
        }
    }

    @Override
    public Relation connectDimensionWithFact(String dimensionId, String factId, String
            relationType) throws InfoDiscoveryEngineRuntimeException {
        //create Fact to Dimension Link, if already exist, ignore this method and return old
        // relation
        if (!hasRelationType(relationType)) {
            String exceptionMessage = "Relation Type " + relationType + " not exist";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        } else {
            Fact fact = getFactById(factId);
            if (fact == null) {
                String exceptionMessage = "Fact of id " + factId + " not exist";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
            Dimension dimension = getDimensionById(dimensionId);
            if (dimension == null) {
                String exceptionMessage = "Dimension of id " + dimensionId + " not exist";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
            String relationTypeClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION +
                    relationType;

            OrientVertex fromVertex = ((OrientDBDimensionImpl) dimension).getDimensionVertex();
            OrientVertex toVertex = ((OrientDBFactImpl) fact).getFactVertex();

            Iterable<Edge> edgeIterator = fromVertex.getEdges(toVertex, Direction.OUT,
                    relationTypeClassName);
            if (edgeIterator.iterator().hasNext()) {
                //already attached just return this old one
                Edge existEdge = edgeIterator.iterator().next();
                OrientDBRelationImpl newRelation = new OrientDBRelationImpl(relationType);
                newRelation.setRelationEdge((OrientEdge) existEdge);
                return newRelation;
            } else {
                OrientEdge resultEdge = this.graph.addEdge(null, fromVertex, toVertex,
                        relationTypeClassName);
                this.graph.commit();
                OrientDBRelationImpl newRelation = new OrientDBRelationImpl(relationType);
                newRelation.setRelationEdge(resultEdge);
                return newRelation;
            }
        }
    }

    @Override
    public Relation connectDimensionWithFact(String dimensionId, String factId, String
            relationType, Map<String, Object> initRelationProperties) throws
            InfoDiscoveryEngineRuntimeException {
        //create Fact to Dimension Link, if already exist, ignore this method and return old
        // relation
        if (!hasRelationType(relationType)) {
            String exceptionMessage = "Relation Type " + relationType + " not exist";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        } else {
            Fact fact = getFactById(factId);
            if (fact == null) {
                String exceptionMessage = "Fact of id " + factId + " not exist";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
            Dimension dimension = getDimensionById(dimensionId);
            if (dimension == null) {
                String exceptionMessage = "Dimension of id " + dimensionId + " not exist";
                throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
            }
            String relationTypeClassName = InfoDiscoverEngineConstant.CLASSPERFIX_RELATION +
                    relationType;

            OrientVertex fromVertex = ((OrientDBDimensionImpl) dimension).getDimensionVertex();
            OrientVertex toVertex = ((OrientDBFactImpl) fact).getFactVertex();

            Iterable<Edge> edgeIterator = fromVertex.getEdges(toVertex, Direction.OUT,
                    relationTypeClassName);
            if (edgeIterator.iterator().hasNext()) {
                //already attached just return this old one
                Edge existEdge = edgeIterator.iterator().next();
                OrientDBRelationImpl newRelation = new OrientDBRelationImpl(relationType);
                newRelation.setRelationEdge((OrientEdge) existEdge);
                return newRelation;
            } else {
                OrientEdge resultEdge = this.graph.addEdge(null, fromVertex, toVertex,
                        relationTypeClassName);
                if (initRelationProperties != null) {
                    Set<String> propertyNameSet = initRelationProperties.keySet();
                    Iterator<String> iterator = propertyNameSet.iterator();
                    while (iterator.hasNext()) {
                        String propertyName = iterator.next();
                        if (InfoDiscoverEngineDataOperationUtil.checkNotReservedProperty
                                (propertyName)) {
                            Object propertyValue = initRelationProperties.get(propertyName);
                            InfoDiscoverEngineDataOperationUtil.saveOrientPropertyWithoutCommit
                                    (resultEdge, propertyName, propertyValue);
                        }
                    }
                }
                this.graph.commit();
                OrientDBRelationImpl newRelation = new OrientDBRelationImpl(relationType);
                newRelation.setRelationEdge(resultEdge);
                return newRelation;
            }
        }
    }

    @Override
    public String getSpaceName() {
        return this.spaceName;
    }

    @Override
    public void closeSpace() {
        this.graph.shutdown();
        this.factory.close();
    }

    @Override
    public InformationExplorer getInformationExplorer() {
        InformationExplorer informationExplore = new OrientDBInformationExplorerImpl(this.graph);
        return informationExplore;
    }

    @Override
    public Measurable getMeasurableById(String measurableId) {
        OrientElement oElement = this.graph.getElement(measurableId);
        if (oElement == null) {
            return null;
        }
        if (oElement.getElementType().equals("Vertex")) {
            OrientVertex ov = (OrientVertex) oElement;
            String measurableTypeStr = ov.getType().getName();
            if (measurableTypeStr.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION)) {
                String dimensionBusinessName = measurableTypeStr.replaceFirst
                        (InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION, "");
                OrientDBDimensionImpl targetDimension = new OrientDBDimensionImpl
                        (dimensionBusinessName);
                targetDimension.setDimensionVertex(ov);
                return targetDimension;
            } else if (measurableTypeStr.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_FACT)) {
                String factBusinessName = measurableTypeStr.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_FACT, "");
                OrientDBFactImpl targetFact = new OrientDBFactImpl(factBusinessName);
                targetFact.setFactVertex(ov);
                return targetFact;
            }
        }
        if (oElement.getElementType().equals("Edge")) {
            OrientEdge oe = (OrientEdge) oElement;
            String measurableTypeStr = oe.getType().getName();
            if (measurableTypeStr.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION)) {
                String relationType = measurableTypeStr.replaceFirst(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION, "");
                OrientDBRelationImpl targetRelation = new OrientDBRelationImpl(relationType);
                targetRelation.setRelationEdge(oe);
                return targetRelation;
            }
        }
        return null;
    }
}
