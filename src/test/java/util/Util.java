package util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Util {
    private static final Logger log = LogManager.getLogger(Util.class);
    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param){
        try{
            JSONParser jp = new JSONParser();
            FileReader reader = new FileReader("element.json");
            Object obj = jp.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            this.param = String.valueOf((jsonObject.get(param)));

        }catch (Exception e){
            log.error(e.getMessage());
            Assert.assertFalse(String.valueOf(e),true);
        }
    }
}
