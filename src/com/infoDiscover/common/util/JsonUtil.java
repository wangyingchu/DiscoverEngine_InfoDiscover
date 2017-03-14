package com.infoDiscover.common.util;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by sun.
 */
public class JsonUtil {
    public static JSONObject string2JSONObject(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        return jsonObject;
    }

    public static String getPropertyValues(String property, String json) {
        String result = null;
        try {
            JSONObject jsonObject = JsonUtil2.string2JSONObject(json);
            result = jsonObject.getString(property);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public static String generateJsonRelationship(URI endNode,
                                                  String relationshipType, String...
                                                          jsonAttributes) {

        StringBuilder sb = new StringBuilder();
        sb.append("{\"to\":\"");
        sb.append(endNode.toString());
        sb.append("\",");

        sb.append("\"type\":\"");
        sb.append(relationshipType);
        if (jsonAttributes == null || jsonAttributes.length < 1) {
            sb.append("\"");
        } else {
            sb.append("\",\"data\":");
            for (int i = 0; i < jsonAttributes.length; i++) {
                sb.append(jsonAttributes[i]);
                if (i < jsonAttributes.length - 1) {
                    // miss off the final comma
                    sb.append(",");
                }
            }
        }

        sb.append("}");
        return sb.toString();
    }
}
