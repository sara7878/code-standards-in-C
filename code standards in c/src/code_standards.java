import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.LineNumberReader;

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

        if (is) {
            System.out.println("inside scope");
            struct_Regex = localObject.get("struct").toString();
            union_Regex = localObject.get("union").toString();

        } else {
            System.out.println("outside scope");
            struct_Regex = globalObject.get("struct").toString();
            union_Regex = globalObject.get("union").toString();

        }

        if (ctx.structOrUnion().getText().matches("struct")) {
            if ((ctx.Identifier().getText().matches(struct_Regex))==false){
                System.out.println("Not correct identifier of struct");}
            else {System.out.println("right identifier");}
        } else {
            if ((ctx.Identifier().getText().matches(union_Regex))==false){
                System.out.println("Not correct identifier of union");}
            else {System.out.println("right identifier");}
        }
        return visitChildren(ctx);



    }


    @Override public String visitFunctionDefinition(CParser.FunctionDefinitionContext ctx) {
        String function_Regex = funcObject.get("function").toString();

        if ((ctx.declarator().directDeclarator().directDeclarator().getText().matches(function_Regex)) == false) {
            System.out.println("Not correct declarator of function");
        } else {
            System.out.println("right declarator");
        }
        return visitChildren(ctx);
    }

    @Override
    public String visitEnumSpecifier(CParser.EnumSpecifierContext ctx) {

        String enum_Regex;
        boolean is = SCOPE;


        if (is) {
            System.out.println("inside scope");
            enum_Regex = localObject.get("enum").toString();

        } else {
            System.out.println("outside scope");
            enum_Regex = globalObject.get("enum").toString();

        }

        if ((ctx.Identifier().getText().matches(enum_Regex)) == false) {
            System.out.println("Not correct identifier of enum");
        } else {
            System.out.println("right identifier");
        }

        return visitChildren(ctx);
    }


    @Override
    public String visitInitDeclarator(CParser.InitDeclaratorContext ctx) {
        String type_Regex1 ;
        boolean is = SCOPE;

        if (is) {
            System.out.println("inside scope");
            type_Regex1 = localObject.get("types").toString();

        } else {
            System.out.println("outside scope");
            type_Regex1 = globalObject.get("types").toString();

        }

        if ((ctx.declarator().directDeclarator().getText().matches(type_Regex1)) == false ) {

      /*      if ((ctx.declarator().directDeclarator().directDeclarator().getText().matches(type_Regex1)) == true) {


                System.out.println("right identifier of array"); }


            else
                {System.out.println("Not correct identifier type");}

       */
            System.out.println("Not correct identifier type");
        }


        else {
            System.out.println("right identifier");
        }
        return visitChildren(ctx);

    }


    @Override public String visitTypedefName(CParser.TypedefNameContext ctx) {
        String type_Regex;
        boolean is = SCOPE;

        if (is) {
            System.out.println("inside scope");
            type_Regex = localObject.get("types").toString();

        } else {
            System.out.println("outside scope");
            type_Regex = globalObject.get("types").toString();

        }

        if ((ctx.Identifier().getText().matches(type_Regex)) == false) {
            System.out.println("Not correct identifier type");
        } else {
            System.out.println("right identifier");
        }
        return visitChildren(ctx); }




}