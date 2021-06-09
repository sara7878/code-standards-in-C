import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class code_standards extends CBaseVisitor <String> {

    public JSONObject funcObject = new JSONObject();
    public JSONObject globalObject = new JSONObject();
    public JSONObject localObject = new JSONObject();

    code_standards(JSONObject globalObject, JSONObject localObject, JSONObject funcObject){
        this.globalObject = globalObject;
        this.localObject = localObject;
        this.funcObject = funcObject;

    }

    static boolean SCOPE=false;
    @Override public String visitCompoundStatement(CParser.CompoundStatementContext ctx)
    {
        SCOPE=true;
        return visitChildren(ctx);
    }

    @Override
    public String visitStructOrUnionSpecifier(CParser.StructOrUnionSpecifierContext ctx) {

        boolean is=SCOPE;
        String struct_Regex;
        String union_Regex;
        String scope;

        if (is) {
            //System.out.println("inside scope");
            struct_Regex = localObject.get("struct").toString();
            union_Regex = localObject.get("union").toString();
            scope="l_";
        } else {
            //System.out.println("outside scope");
            struct_Regex = globalObject.get("struct").toString();
            union_Regex = globalObject.get("union").toString();
            scope="g_";

        }

        if (ctx.structOrUnion().getText().matches("struct")) {
            if ((ctx.Identifier().getText().matches(struct_Regex))==false){
                System.out.println("Line :" + ctx.start.getLine() +" Incorrect struct identifier \"it should begin with ("+ scope +") and end with (_s)\"");
            }
            //else {System.out.println("right identifier");}
        } else {
            if ((ctx.Identifier().getText().matches(union_Regex))==false){
                System.out.println("Line :" + ctx.start.getLine() +" Incorrect union identifier \"it should begin with ("+ scope +") and end with (_u)\"");
            }
            //else {System.out.println("right identifier");}
        }
        return visitChildren(ctx);



    }


    @Override public String visitFunctionDefinition(CParser.FunctionDefinitionContext ctx) {
        String function_Regex = funcObject.get("function").toString();

        if ((ctx.declarator().directDeclarator().directDeclarator().getText().matches(function_Regex)) == false) {
            System.out.println("Line :" + ctx.start.getLine() +" Not correct declarator of function \"it should begin with (func_)\"");
        }
        /*else {
            System.out.println("right declarator");
        }*/
        return visitChildren(ctx);
    }

    @Override
    public String visitEnumSpecifier(CParser.EnumSpecifierContext ctx) {

        String enum_Regex;
        boolean is = SCOPE;
        String scope;

        if (is) {
            //System.out.println("inside scope");
            enum_Regex = localObject.get("enum").toString();
            scope="l_";

        } else {
            //System.out.println("outside scope");
            enum_Regex = globalObject.get("enum").toString();
            scope="g_";

        }

        if ((ctx.Identifier().getText().matches(enum_Regex)) == false) {
            System.out.println("Line :" + ctx.start.getLine() +" Incorrect enum identifier \"it should begin with ("+
                    scope +") and end with (_e)\"");
        } else {
            //System.out.println("right identifier");
        }

        return visitChildren(ctx);
    }


    @Override
    public String visitInitDeclarator(CParser.InitDeclaratorContext ctx) {
        String scope;
        String type_Regex1 ;
        boolean is = SCOPE;

        if (is) {
            //System.out.println("inside scope");
            type_Regex1 = localObject.get("types").toString();
            scope="l_";

        } else {
            // System.out.println("outside scope");
            type_Regex1 = globalObject.get("types").toString();
            scope="g_";

        }

        if ((ctx.declarator().directDeclarator().getText().matches(type_Regex1)) == false ) {

      /*      if ((ctx.declarator().directDeclarator().directDeclarator().getText().matches(type_Regex1)) == true) {


                System.out.println("right identifier of array"); }


            else
                {System.out.println("Not correct identifier type");}

       */
            System.out.println("Line :" + ctx.start.getLine() +" Incorrect identifier type \"it should begin with ("+ scope +") and end with (_t)\"");
        }


        else {
            //System.out.println("right identifier");
        }
        return visitChildren(ctx);

    }


    @Override public String visitTypedefName(CParser.TypedefNameContext ctx) {
        String type_Regex;
        boolean is = SCOPE;
        String scope;

        if (is) {
            //System.out.println("inside scope");
            type_Regex = localObject.get("types").toString();
            scope="l_";

        } else {
            //System.out.println("outside scope");
            type_Regex = globalObject.get("types").toString();
            scope="g_";

        }

        if ((ctx.Identifier().getText().matches(type_Regex)) == false) {
            System.out.println("Line :" + ctx.start.getLine() +" Incorrect identifier type \"it should begin with ("+ scope +") and end with (_t)\"");

        } else {
            // System.out.println("right identifier");
        }
        return visitChildren(ctx); }
}