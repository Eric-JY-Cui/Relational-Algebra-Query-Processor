import java.util.ArrayList;

public class Relation {
    private String name;
    private ArrayList<Attribute> attributes;

    /**
     * Stores the properties of a relation
     * @param name initialize with the name of the relation
     */
    public Relation(String name){
        this.name = name;
        this.attributes = new ArrayList<Attribute>();

    }

    /**
     * return the name of the relation
     * @return the variable name
     */
    public String getName() {return name;}

    /**
     * add an attribute to the relation
     * @param attribute the attribute to be added
     */
    public void addAttribute(Attribute attribute){
        attributes.add(attribute);
    }

    /**
     * add an array of new attributes
     * @param attributeName the list of names of the new attributes
     */
    public void addAttribute(String[] attributeName){
        for(String i:attributeName){
            attributes.add(new Attribute(i));
        }
    }

    /**
     * remove an attribute from the relation
     * @param attribute the attribute to be removed
     */
    public void removeAttribute(Attribute attribute){attributes.remove(attribute);}

    /**
     * add a value for every attribute in the relation
     * @param value the value added to each individual attributes
     */
    public void addAttributeValue(ArrayList<String> value){
        while(value.size()<attributes.size()){
            value.add("null");
        }
        for (int i = 0; i < attributes.size(); i++) {
            attributes.get(i).addAttributeValue(value.get(i));
        }
    }

    /**
     * Get the value of all attribute of that index
     * @param index the index value required
     * @return an arraylist containing values of all attributes of that index
     */
    public ArrayList<String> getAttributeValue(int index){
        ArrayList<String> list = new ArrayList<String>();
        for(Attribute attribute:attributes){
            list.add(attribute.getValue(index));
        }
        return list;
    }

    /**
     * remove all attributes in the relation
     */
    public void voidAttributes(){attributes = new ArrayList<Attribute>();}

    /**
     * remove the value at an index for all attributes in the relation
     * @param index the index to be removed
     */
    public void removeAttributeValueIndex(int index){
        for(Attribute attribute:attributes){
            attribute.getValues().remove(index);
        }
    }

    /**
     * get all attributes in the relation.
     * @return an arraylist of all attributes in the relation.
     */
    public ArrayList<Attribute> getAttributes(){
        return attributes;
    }

    /**
     * get the size of the attributes
     * @return the size of the value of the first attribute
     */
    public int getDepth(){
        if(attributes.size() == 0){
            return 0;
        }else{
            return attributes.get(0).getValues().size();
        }
    }

    /**
     * Check if other relation have an index where all common attribute name have exact value as the selected index
     * @param other the other relation that this value is compared with
     * @param index the target index of this relation
     * @return whether this value intersect with another relation
     */
    public boolean hasCommonValue(Relation other,int index){
        for (int i = 0; i < other.getDepth(); i++) {
            boolean hasCommon = true;
            for(Attribute thisAttribute:this.attributes){
                Attribute otherAttribute = other.findAttributeByName(thisAttribute.getName());
                if(otherAttribute != null){
                    if(!thisAttribute.getValue(index).equals(otherAttribute.getValue(i))){
                        hasCommon = false;
                        break;
                    }
                }
            }
            if(hasCommon){return true;}
        }

        return false;
    }

    /**
     * create a relation that is a selection of this relation with conditions
     * @param name the name of the new relation
     * @param condition the condition of the selection
     * @return the relation that is a selection of this relation
     */
    public Relation selection(String name,String condition){
        Relation relation = new Relation(name);
        for(Attribute attribute:this.attributes){
            relation.addAttribute(new Attribute(attribute.getName()));
        }
        String x = "";
        if(condition.contains(">")){x+=">";}
        if(condition.contains("<")){x+="<";}
        if(condition.contains("!")){x+="!";}
        if(condition.contains("=")){x+="=";}
        Attribute attribute1 = findAttributeByName(condition.split(x)[0]);
        Attribute attribute2 = findAttributeByName(condition.split(x)[1]);
        for (int i = 0; i < attribute1.getValues().size(); i++) {
            if(attribute1.compareValue(attribute2,x,i)){
                for (int j = 0; j < attributes.size(); j++) {
                    relation.getAttributes().get(j).addAttributeValue(this.getAttributes().get(j).getValue(i));
                }
            }
        }
        return relation;
    }

    /**
     * create a relation that is a projection of this relation with conditions
     * @param name the name of the new relation
     * @param condition the attributes used in the new relation
     * @return the relation that is a projection of this relation
     */
    public Relation projection(String name,String condition){
        condition.replaceAll(" ","");
        String[] context = condition.split(",");
        Relation relation = new Relation(name);
        for(String s:context){
            relation.addAttribute(findAttributeByName(s).clone());
        }
        return relation;
    }

    /**
     * create a relation that is a join of two relation
     * @param thisAttribute the attribute in this relation that is used for the join
     * @param otherAttribute the attribute in the other relation that is used for the join
     * @param other the other relation used for the join
     * @return the relation that is a join of two relation
     */
    public Relation join(Attribute thisAttribute,Attribute otherAttribute,Relation other){
        Relation relation = new Relation("");
        for(Attribute attribute:this.attributes){
            relation.addAttribute(new Attribute(attribute.getName()));
        }
        for(Attribute attribute:other.attributes){
            relation.addAttribute(new Attribute(attribute.getName()));
        }
        for (int i=0;i<thisAttribute.getSize();i++) {
            for (int j = 0; j < otherAttribute.getSize(); j++) {
                if(thisAttribute.getValue(i).equals(otherAttribute.getValue(j))){
                    ArrayList<String> value = this.getAttributeValue(i);
                    value.addAll(other.getAttributeValue(j));
                    relation.addAttributeValue(value);
                }
            }
        }
        return relation;
    }

    /**
     * Create a relation that is the intersection result of two relations
     * @param other the other relation used in the intersection
     * @return the relation that is the intersection result of two relations
     */
    public Relation intersection(Relation other){
        Relation relation = new Relation("");
        for(Attribute attribute:attributes){
            if(other.findAttributeByName(attribute.getName(),false) != null){
                relation.addAttribute(new Attribute(attribute.getName()));
            }
        }
        for (int i = 0; i < this.getDepth(); i++) {
            if(hasCommonValue(other,i)){
                relation.addAttributeValue(this.getAttributeValue(i));
            }
        }
        return relation;
    }

    /**
     * Create a relation that is the union result of two relations
     * @param other the other relation used in the intersection
     * @return the relation that is the union result of two relations
     */
    public Relation union(Relation other){
        Relation relation = this.clone();
        for(Attribute attribute:attributes){
            if(other.findAttributeByName(attribute.getName(),false) == null){
                relation.removeAttribute(attribute);
            }
        }
        for (int i = 0; i < other.getDepth(); i++) {
            if(!other.hasCommonValue(this,i)){
                relation.addAttributeValue(other.getAttributeValue(i));
            }
        }
        return relation;
    }

    /**
     * Create a relation that is this relation minus the other relation
     * @param other the other relation used in the intersection
     * @return the relation that is this relation minus the other relation
     */
    public Relation minus(Relation other){
        Relation relation = this.clone();
        for(Attribute attribute:attributes){
            if(this.findAttributeByName(attribute.getName(),false) == null){
                relation.removeAttribute(attribute);
            }
        }
        for (int i = this.getDepth()-1; i >= 0; i--) {
            System.out.println("***");
            if(this.hasCommonValue(other,i)){
                relation.removeAttributeValueIndex(i);
            }
        }
        return relation;
    }

    /**
     * Find the attribute in the relation by name. if no relation found, then create a new relation with values consist of this name
     * @param name the name of the attribute to be found
     * @return the attribute found
     */
    public Attribute findAttributeByName(String name){
        return findAttributeByName(name,true);
    }

    /**
     * Find the attribute in the relation by name. if no relation found and useFiller is true, then create a new relation with values consist of this name.
     * @param name
     * @param useFiller
     * @return
     */
    public Attribute findAttributeByName(String name,boolean useFiller){
        for(Attribute attribute: attributes){
            if(attribute.getName().equals(name)){
                return attribute;
            }
        }
        if(useFiller){
            Attribute a = new Attribute(name);
            for (int i = 0; i < attributes.getFirst().getValues().size(); i++) {
                a.addAttributeValue(name);
            }
            return a;
        }else{
            return null;
        }

    }

    /**
     * create a string that print out to be a table illustrating the structure of the relation
     * @return the string that when used in System.out.println() shows the relation in a table
     */
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Attribute attribute:attributes) {
            stringBuilder.append(attribute.getName()+"\t");
        }
        stringBuilder.append("\n");
        for (int i = 0; i < getDepth(); i++) {
            for (Attribute attribute:attributes) {
                stringBuilder.append(attribute.getValue(i)+"\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    /**
     * Create an exact copy of this relation
     * @return the exact copy of the relation
     */
    public Relation clone(){
        Relation relation = new Relation(this.name);
        for(Attribute attribute:attributes){
            relation.addAttribute(attribute.clone());
        }
        return relation;
    }
}
