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
    public void voidAttributes(){attributes = new ArrayList<Attribute>();}
    public ArrayList getAttributes(){
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
    public Relation clone(){
        Relation relation = new Relation(this.name);
        for(Attribute attribute:attributes){
            relation.addAttribute(attribute.clone());
        }
        return relation;
    }
}
