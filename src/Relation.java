import java.util.ArrayList;

public class Relation {
    private String name;
    private ArrayList<Attribute> attributes;
    public Relation(String name){
        this.name = name;
        this.attributes = new ArrayList<Attribute>();

    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

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
    public void removeAttribute(Attribute attribute){
        attributes.remove(attribute);
    }
    public void voidAttributes(){attributes = new ArrayList<Attribute>();}
    public ArrayList<Attribute> getAttributes(){
        return attributes;
    }
    public void printAttribute(){
        for(Attribute attribute:attributes){
            System.out.println(attribute.getName());
            for(Object o:attribute.getValues()){
                System.out.println(o);
            }
            System.out.println();
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
    public int getDepth(){
        if(attributes.size() == 0){
            return 0;
        }else{
            return attributes.get(0).getValues().size();
        }
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

    public Attribute findAttributeByName(String name){
        for(Attribute attribute: attributes){
            if(attribute.getName().equals(name)){
                return attribute;
            }
        }
        Attribute a = new Attribute(name);
        for (int i = 0; i < attributes.getFirst().getValues().size(); i++) {
            a.addAttributeValue(name);
        }

        return a;
    }

    public Relation clone(){
        Relation relation = new Relation(this.name);
        for(Attribute attribute:attributes){
            relation.addAttribute(attribute.clone());
        }
        return relation;
    }
}
