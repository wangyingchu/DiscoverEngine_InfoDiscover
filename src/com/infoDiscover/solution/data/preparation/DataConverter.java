package com.infoDiscover.solution.data.preparation;

import com.infoDiscover.common.util.JsonUtil;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by sun.
 */
public class DataConverter {

    private void jsonConvert() throws JSONException {
        String file = "/Users/sun/InfoDiscovery/Demodata/MaintainProject/Converter1.json";

        JsonNode jsonNode = JsonUtil.loadJsonFile(file);

        JSONArray array = new JSONArray();


        JSONObject json = new JSONObject();
        Iterator<String> it = jsonNode.getFieldNames();
        JSONArray properties = new JSONArray();
        while (it.hasNext()) {
            String propertyName = it.next();
            String propertyValue = jsonNode.get(propertyName).asText();
            System.out.println("propertyName: " + propertyName);
            System.out.println("propertyValue: " + propertyValue);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("propertyType", "String");
            jsonObject.put("propertyName", propertyName);
            jsonObject.put("propertyValue", propertyValue);
            properties.put(jsonObject);
        }
        System.out.println(properties);
    }

}
