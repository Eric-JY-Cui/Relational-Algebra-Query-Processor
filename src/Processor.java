import java.util.ArrayList;

public class Processor {
    private ArrayList<Relation> relations;

    public Processor(){
        relations = new ArrayList<Relation>();
    }

    public Relation executeCommand(String input){
        String command = input.substring(0,input.indexOf(' '));
        String context = input.substring(input.indexOf(' ')+1);
        switch (command.toLowerCase()){
            case "":
                return executeCommand(input.substring(1));
            case "relation":
                return this.updateRelation(context);
            case "get":
                return this.outputRelation(context);
            case "select":
                return this.relationSelection(context);
            case "project":
                return this.relationProjection(context);
            case "join":
                return this.relationJoin(context);
            case "intersect":
                return  this.relationIntersection(context);
            case "union":
                return this.relationUnion(context);
            case "minus":
                return this.relationMinus(context);
            default:
                return generateMessage("Unknown Command, type help for more info");
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


    public Relation updateRelation(String input){
        if(input.contains("=")){
            String targetName = input.substring(0,input.indexOf("=")).replaceAll(" ","");
            Relation target = findRelationByName(targetName);
            if(target == null){
                target = new Relation(targetName);
                relations.add(target);
            }
            Relation value = executeCommand(input.substring(input.indexOf('=')+1));
            for(Attribute attribute: value.getAttributes()){
                target.addAttribute(attribute.clone());
            }
            return generateMessage("Relation " + target.getName() + " updated successfully");
        }

        input = input.replaceAll(" ","");
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
        return generateMessage("Relation " + relation.getName() + " updated successfully");
    }
    public Relation outputRelation(String input){
        input = input.replaceAll(" ","");
        if(findRelationByName(input) == null){
            return generateMessage("ERROR: Relation not found");
        }else{
            return findRelationByName(input);
        }
    }

    public Relation relationSelection(String input){
        input = input.replaceAll(" ","");
        String relationName = input.substring(0,input.indexOf('('));
        String selectCondition = input.substring(input.indexOf('(')+1,input.indexOf(')'));
        Relation relation = findRelationByName(relationName);
        if(relation == null){
            return generateMessage("ERROR: Relation not found");
        }else{
            relation = relation.selection(relationName,selectCondition);
            return relation;
        }
    }

    public Relation relationProjection(String input){
        input = input.replaceAll(" ","");
        String relationName = input.substring(0,input.indexOf('('));
        String selectCondition = input.substring(input.indexOf('(')+1,input.indexOf(')'));
        Relation relation = findRelationByName(relationName);
        if(relation == null){
            return generateMessage("ERROR: Relation not found");
        }else{
            relation = relation.projection(relationName,selectCondition);
            return relation;
        }
    }

    public Relation relationJoin(String input){
        input = input.replaceAll(" ","");
        String relationName = input.substring(0,input.indexOf('('));
        Relation relation1 = findRelationByName(relationName);
        relationName = input.substring(input.indexOf(')')+1);
        Relation relation2 = findRelationByName(relationName);
        if(relation1 == null || relation2 == null){
            return generateMessage("ERROR: Relation not found");

        }

        String selectCondition = input.substring(input.indexOf('(')+1,input.indexOf(')'));
        Attribute attribute1;
        Attribute attribute2;
        try{
            String[] attributeName = selectCondition.split("=");
            attribute1 = relation1.findAttributeByName(attributeName[0]);
            attribute2 = relation2.findAttributeByName(attributeName[1]);

        }catch (IndexOutOfBoundsException e){
            return generateMessage("ERROR: Attribute Format Error");
        }


        return relation1.join(attribute1,attribute2,relation2);

    }

    public Relation relationIntersection(String input){
        input = input.replaceAll(" ","");
        String relationName1 = input.substring(0,input.indexOf('('));
        Relation relation1 = findRelationByName(relationName1);
        String relationName2 = input.substring(input.indexOf('(')+1,input.indexOf(')'));
        Relation relation2 = findRelationByName(relationName2);
        if(relation1 == null || relation2 == null){
            return generateMessage("ERROR: Relation not found");
        }
        return relation1.intersection(relation2);

    }
    public Relation relationUnion(String input){
        input = input.replaceAll(" ","");
        String relationName1 = input.substring(0,input.indexOf('('));
        Relation relation1 = findRelationByName(relationName1);
        String relationName2 = input.substring(input.indexOf('(')+1,input.indexOf(')'));
        Relation relation2 = findRelationByName(relationName2);
        if(relation1 == null || relation2 == null){
            return generateMessage("ERROR: Relation not found");
        }
        return relation1.union(relation2);

    }
    public Relation relationMinus(String input){
        input = input.replaceAll(" ","");
        String relationName1 = input.substring(0,input.indexOf('('));
        Relation relation1 = findRelationByName(relationName1);
        String relationName2 = input.substring(input.indexOf('(')+1,input.indexOf(')'));
        Relation relation2 = findRelationByName(relationName2);
        if(relation1 == null || relation2 == null){
            return generateMessage("ERROR: Relation not found");
        }
        return relation1.minus(relation2);

    }



    private Relation generateMessage(String message){
        Relation relation = new Relation("");
        relation.addAttribute(new Attribute(message));
        return relation;
    }



}
