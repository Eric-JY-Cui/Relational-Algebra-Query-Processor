import java.util.ArrayList;

public class Relation {
    private String name;
    private ArrayList<Attribute> attributes;

    public Relation(String name){
        this.name = name;
        this.attributes = new ArrayList<Attribute>();

    }

    public String getName() {return name;}


    public void addAttribute(Attribute attribute){
        attributes.add(attribute);
    }
    public void addAttribute(String[] attributeName){
        for(String i:attributeName){
            attributes.add(new Attribute(i));
        }
    }
    public void addAttribute(ArrayList<Attribute> attributes){
        for(Attribute attribute:attributes){
            this.addAttribute(attribute);
        }
    }
    public void removeAttribute(Attribute attribute){attributes.remove(attribute);}
    public void addAttributeValue(ArrayList<String> value){
        while(value.size()<attributes.size()){
            value.add("null");
        }
        for (int i = 0; i < attributes.size(); i++) {
            attributes.get(i).addAttributeValue(value.get(i));
        }
    }
    public ArrayList<String> getAttributeValue(int index){
        ArrayList<String> list = new ArrayList<String>();
        for(Attribute attribute:attributes){
            list.add(attribute.getValue(index));
        }
        return list;
    }
    public void voidAttributes(){attributes = new ArrayList<Attribute>();}
    public void removeAttributeValueIndex(int index){
        for(Attribute attribute:attributes){
            attribute.getValues().remove(index);
        }
    }
    public ArrayList<Attribute> getAttributes(){
        return attributes;
    }
    public ArrayList<String> getIndexValue(int index){
        ArrayList<String> list = new ArrayList<String>();
        for(Attribute attribute:attributes){
            list.add(attribute.getValue(index));
        }
        return list;
    }
    public void setAttributes(ArrayList<Attribute> attributes){this.attributes = attributes;}
    public void printAttribute(){
        for(Attribute attribute:attributes){
            System.out.println(attribute.getName());
            for(Object o:attribute.getValues()){
                System.out.println(o);
            }
            System.out.println();
        }
    }

    public int getDepth(){
        if(attributes.size() == 0){
            return 0;
        }else{
            return attributes.get(0).getValues().size();
        }
    }
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

    public Relation projection(String name,String condition){
        condition.replaceAll(" ","");
        String[] context = condition.split(",");
        Relation relation = new Relation(name);
        for(String s:context){
            relation.addAttribute(findAttributeByName(s).clone());
        }
        return relation;
    }

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
                    ArrayList<String> value = this.getIndexValue(i);
                    value.addAll(other.getIndexValue(j));
                    relation.addAttributeValue(value);
                }
            }
        }
        return relation;
    }

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

    public Attribute findAttributeByName(String name){
        return findAttributeByName(name,true);
    }
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



    public Relation clone(){
        Relation relation = new Relation(this.name);
        for(Attribute attribute:attributes){
            relation.addAttribute(attribute.clone());
        }
        return relation;
    }
}
