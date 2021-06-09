import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Test {

    public static void main (String[] args) throws Exception{

        JSONParser jsonParser = new JSONParser();
        JSONObject declare = new JSONObject();
        JSONObject globalObject = new JSONObject();
        JSONObject localObject = new JSONObject();
        JSONObject functionobject = new JSONObject();

        try (FileReader reader = new FileReader("regex.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            declare = (JSONObject) obj;
            globalObject = (JSONObject) declare.get("Global Declarations");
            localObject = (JSONObject) declare.get("Local Declarations");
            functionobject = (JSONObject) declare.get("Function Declarations");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String inputFile = "c.expr";
        FileInputStream is = new FileInputStream(inputFile);
        ANTLRInputStream input = new ANTLRInputStream(is);
        CLexer lexer = new CLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokens);

        CParser.CompilationUnitContext tree = parser.compilationUnit();
        System.out.println(tree.toStringTree(parser));


        code_standards code_standards = new code_standards(globalObject,localObject,functionobject);
        code_standards.visit(tree);
    }
}
