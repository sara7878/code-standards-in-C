import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class WriteJSONExample
{
    @SuppressWarnings("unchecked")
	public static void main( String[] args )
    {
    	JSONObject globalregexdetails = new JSONObject();
		JSONObject localregexdetails = new JSONObject();
		JSONObject funcregexdetails = new JSONObject();

		globalregexdetails.put("struct", "g_([A-Za-z]([A-Za-z]|[0-9])*_)s");
		globalregexdetails.put("union", "g_([A-Za-z]([A-Za-z]|[0-9])*_)u");
		globalregexdetails.put("enum", "g_([A-Za-z]([A-Za-z]|[0-9])*_)e");
		globalregexdetails.put("types", "g_([A-Za-z]([A-Za-z]|[0-9])*_)t");

		localregexdetails.put("struct", "l_([A-Za-z]([A-Za-z]|[0-9])*_)s");
		localregexdetails.put("union", "l_([A-Za-z]([A-Za-z]|[0-9])*_)u");
		localregexdetails.put("enum", "l_([A-Za-z]([A-Za-z]|[0-9])*_)e");
		localregexdetails.put("types", "l_([A-Za-z]([A-Za-z]|[0-9])*_)t");

		funcregexdetails.put("function", "func(_[A-Za-z]([A-Za-z]|[0-9])*)");


		JSONObject regexdetails1 = new JSONObject();

		regexdetails1.put("Global Declarations", globalregexdetails);
		regexdetails1.put("Local Declarations", localregexdetails);
		regexdetails1.put("Function Declarations", funcregexdetails);



    	//Write JSON file
    	try (FileWriter file = new FileWriter("regex.json")) {

            file.write(regexdetails1.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
