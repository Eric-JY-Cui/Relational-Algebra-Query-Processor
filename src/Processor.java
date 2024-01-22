import java.util.ArrayList;

public class Processor {
    private ArrayList<Relation> relations;



    public Processor(){
        relations = new ArrayList<Relation>();

    }

    public void executeCommand(String command, String context){
        switch (command.toLowerCase()){
            case "create":
                this.updateRelation(context);
                break;
            case "get":
                this.outputRelation(context);
                break;
            case "select":
                break;
            case "project":
                break;
            case "join":
                break;
            case "set":
                break;
            default:
                System.out.println("Unknown command, type help for command list");
        }
    }

    /**
     * Return the relation of the same name, return null if no relation have such name
     * @param name the name of the searching relation
     * @return Relation
     */
    public Relation findRelationByName(String name){
        for(Relation relation: relations){
            if(relation.getName().equals(name)){
                return relation;
            }
        }
        return null;
    }


    public void updateRelation(String input){
        input = input.replaceAll(" ","");
        input = input.replaceAll("\n","");
        String relationName = input.substring(0,input.indexOf('('));
        String attributeNameList = input.substring(input.indexOf('(')+1,input.indexOf(')'));
        String[] attributeValueList = input.substring(input.indexOf('{')+1,input.indexOf('}')).split(",");
        Relation relation = findRelationByName(relationName);
        if(relation == null){
            relation = new Relation(relationName);
            relations.add(relation);
        }
        relation.voidAttributes();
        relation.addAttribute(attributeNameList.split(","));
        for (int i = 0; i < attributeValueList.length;) {
            for(Object a:relation.getAttributes()){
                Attribute x = (Attribute)a;
                if(i<attributeValueList.length){
                    x.addAttributeValue(attributeValueList[i]);
                    i++;
                }else{
                    x.addAttributeValue();
                }
            }
        }
    }
    public void outputRelation(String input){
        if(findRelationByName(input) == null){
            System.out.println("no such relation");
        }else{
            System.out.println(findRelationByName(input).toString());
        }
    }

    public Relation relationSelection(String input){
        String relationName = input.substring(0,input.indexOf('('));
        String selectCondition = input.substring(input.indexOf('(')+1,input.indexOf(')'));
        Relation relation = findRelationByName(relationName);
        if(relation == null){
            System.out.println("ERROR: Relation not found");
            return null;
        }else{
            String x = "";
            if(input.contains(">")){x+=">";}
            if(input.contains("<")){x+="<";}
            if(input.contains("!")){x+="!";}
            if(input.contains("=")){x+="=";}
            switch (x){
                case "=":
                    break;
                case "!=":
                    break;
                case "<":
                    break;
                case ">":
                    break;
                case ">=":
                    break;
                case "<=":
                    break;
                case "><":
                    break;
                default:
                    System.out.println("ERROR: Unknown commands");
            }
        }

        return null;
    }




}
