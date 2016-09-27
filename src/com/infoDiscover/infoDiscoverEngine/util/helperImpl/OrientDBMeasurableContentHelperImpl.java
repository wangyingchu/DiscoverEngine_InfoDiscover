package com.infoDiscover.infoDiscoverEngine.util.helperImpl;

import com.infoDiscover.infoDiscoverEngine.dataMart.Measurable;
import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.helper.MeasurableContentHelper;
import com.orientechnologies.orient.core.record.impl.ORecordBytes;
import com.infoDiscover.infoDiscoverEngine.dataMart.Property;

import java.io.*;

public class OrientDBMeasurableContentHelperImpl implements MeasurableContentHelper {
    @Override
    public void retrievePropertyBinaryContent(Property binaryProperty, String contentLocation,String contentFileName) throws InfoDiscoveryEngineRuntimeException {
        if(!binaryProperty.getPropertyType().equals(PropertyType.BINARY)){
            String exceptionMessage=binaryProperty.getPropertyName()+" isn't a binary property";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);

        }
        Object propertyBinaryContent=binaryProperty.getPropertyValue();
        ORecordBytes fileRecord=(ORecordBytes)propertyBinaryContent;
        writeByteArrayToFile(fileRecord.getRecord().toStream(),contentLocation,contentFileName);
    }

    @Override
    public void persistBinaryContentToProperty(Measurable targetMeasurable, String propertyName,String contentFullLocation) throws InfoDiscoveryEngineRuntimeException{
        try {
            byte[] buffer = null;
            File file = new File(contentFullLocation);
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
            targetMeasurable.addProperty(propertyName, buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            String exceptionMessage=contentFullLocation+" isn't a exist file";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        } catch (IOException e) {
            e.printStackTrace();
            String exceptionMessage="Common IO exception occurred";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
    }

    @Override
    public void updateBinaryContentOfProperty(Measurable targetMeasurable, String propertyName, String contentFullLocation) throws InfoDiscoveryEngineRuntimeException {
        try {
            byte[] buffer = null;
            File file = new File(contentFullLocation);
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
            targetMeasurable.updateProperty(propertyName,buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            String exceptionMessage=contentFullLocation+" isn't a exist file";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        } catch (IOException e) {
            e.printStackTrace();
            String exceptionMessage="Common IO exception occurred";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
    }

    @Override
    public byte[] retrieveBinaryPropertyContent(Property binaryProperty) throws InfoDiscoveryEngineRuntimeException {
        if(!binaryProperty.getPropertyType().equals(PropertyType.BINARY)){
            String exceptionMessage=binaryProperty.getPropertyName()+" isn't a binary property";
            throw InfoDiscoveryEngineException.getRuntimeException(exceptionMessage);
        }
        Object propertyBinaryContent=binaryProperty.getPropertyValue();
        if(propertyBinaryContent instanceof byte[]){
            return (byte[])propertyBinaryContent;
        }else{
            ORecordBytes binaryContent=(ORecordBytes)propertyBinaryContent;
            return binaryContent.toStream();
        }
    }

    private static void writeByteArrayToFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            if(filePath!=null){
                File dir = new File(filePath);
                if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                    dir.mkdirs();
                }
            }
            if(filePath!=null){
                file = new File(filePath+"/"+fileName);
            }else{
                file = new File(fileName);
            }
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
