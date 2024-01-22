import java.util.ArrayList;

public class Attribute {
    private String attributeName;
    public enum AttributeType{INT,STRING,BOOLEAN}
    private AttributeType attributeType;
    private ArrayList<String> attributeValue;
    public Attribute(String attributeName){
        this.attributeName = attributeName;
        attributeValue = new ArrayList<>();
    }
    public void addAttributeValue(){
        attributeValue.add(null);
    }
    public void addAttributeValue(int value){
        attributeValue.add(value+"");
    }
    public void addAttributeValue(String value){
        attributeValue.add(value);
    }
    public void addAttributeValue(Boolean value){
        attributeValue.add(value+"");
    }
    public void addAttributeValue(String[] value){
        for(String i:value){attributeValue.add(i);}
    }
    public String getName(){return attributeName;}
    public ArrayList getValues() {return attributeValue;}
    public String getValue(int index){
        try{
            return attributeValue.get(index).toString();
        }catch (IndexOutOfBoundsException e){

        }
        return "Null";
    }
    public Attribute clone(){
        Attribute attribute = new Attribute(this.attributeName);
        for (int i = 0; i < attributeValue.size(); i++) {
            attribute.addAttributeValue(this.attributeValue.get(i));
        }
        return attribute;
    }
}
