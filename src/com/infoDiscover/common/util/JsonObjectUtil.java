package com.infoDiscover.common.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sun.
 */
public class JsonObjectUtil {
    public static JSONObject    string2JSONObject(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        return jsonObject;
    }

    public static String getPropertyValues(String property, String json) {
        String result = null;
        try {
            JSONObject jsonObject = JsonObjectUtil.string2JSONObject(json);
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

    public static String toJsonNameValuePairCollection(String name, String value) {
        return String.format("{\"%s\" : \"%s\"}", name, value);
    }

    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String json) {
        return (Map<String, Object>) readJson(json);
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> jsonToListOfRelationshipRepresentations(
            String json) {
        return (List<Map<String, Object>>) readJson(json);
    }

    public static Object readJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Object.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object jsonToSingleValue(String json) {
        Object jsonObject = readJson(json);
        return jsonObject instanceof Collection<?> ? jsonObject : PropertiesMap
                .assertSupportedPropertyValue(jsonObject);
    }

    public static String createJsonFromObject(Object data) {
        try {
            StringWriter writer = new StringWriter();
            JsonGenerator generator = OBJECT_MAPPER.getJsonFactory()
                    .createJsonGenerator(writer).useDefaultPrettyPrinter();
            OBJECT_MAPPER.writeValue(generator, data);
            writer.close();
            return writer.getBuffer().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String stringToJson(String s) {
        if (s == null) {
            return nullToJson();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
//			case '/':
//				sb.append("\\/");
//				break;
                default:
                    if (ch >= '\u0000' && ch <= '\u001F') {
                        String ss = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int k = 0; k < 4 - ss.length(); k++) {
                            sb.append('0');
                        }
                        sb.append(ss.toUpperCase());
                    } else {
                        sb.append(ch);
                    }
            }
        }
        return sb.toString();
    }

    public static String nullToJson() {
        return "";
    }

    public static String objectToJson(Object obj) {
        StringBuilder json = new StringBuilder();
        if (obj == null) {
            json.append("\"\"");
        } else if (obj instanceof Number) {
            json.append(numberToJson((Number) obj));
        } else if (obj instanceof Boolean) {
            json.append(booleanToJson((Boolean) obj));
        } else if (obj instanceof String) {
            json.append("\"").append(stringToJson(obj.toString())).append("\"");
        } else if (obj instanceof Object[]) {
            json.append(arrayToJson((Object[]) obj));
        } else if (obj instanceof List) {
            json.append(listToJson((List<?>) obj));
        } else if (obj instanceof Map) {
            json.append(mapToJsonStr((Map<?, ?>) obj));
        } else if (obj instanceof Set) {
            json.append(setToJson((Set<?>) obj));
        } else {
            json.append(beanToJson(obj));
        }
        return json.toString();
    }

    public static String numberToJson(Number number) {
        return number.toString();
    }

    public static String booleanToJson(Boolean bool) {
        return bool.toString();
    }

    public static String beanToJson(Object bean) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        PropertyDescriptor[] props = null;
        try {
            props = Introspector.getBeanInfo(bean.getClass(), Object.class)
                    .getPropertyDescriptors();
        } catch (IntrospectionException e) {
        }
        if (props != null) {
            for (int i = 0; i < props.length; i++) {
                try {
                    String name = objectToJson(props[i].getName());
                    String value = objectToJson(props[i].getReadMethod()
                            .invoke(bean));
                    json.append(name);
                    json.append(":");
                    json.append(value);
                    json.append(",");
                } catch (Exception e) {
                }
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    public static String listToJson(List<?> list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                json.append(objectToJson(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    public static String arrayToJson(Object[] array) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (array != null && array.length > 0) {
            for (Object obj : array) {
                json.append(objectToJson(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    public static String mapToJsonStr(Map<?, ?> map) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                json.append(objectToJson(key));
                json.append(":");
                json.append(objectToJson(map.get(key)));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    public static JsonObject mapToJsonObject(Map<?, ?> map) {
        String jsonStr = mapToJsonStr(map);
        return string2JsonObject(jsonStr);
    }

    public static String setToJson(Set<?> set) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (set != null && set.size() > 0) {
            for (Object obj : set) {
                json.append(objectToJson(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    public static JsonObject string2JsonObject(String strJson) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(strJson).getAsJsonObject();
    }



    public static JsonObject beanToJsonObject(Object bean)
            throws JsonProcessingException, IOException {
        String jsonStr = beanToJson(bean);
        return string2JsonObject(jsonStr);
    }


}
