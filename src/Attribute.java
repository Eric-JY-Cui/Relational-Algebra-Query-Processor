import java.util.ArrayList;

public class Attribute {
    private String attributeName;
    private ArrayList<String> attributeValue;

    /**
     * the individual attributes each containing an arraylist of values
     * @param attributeName
     */
    public Attribute(String attributeName){
        this.attributeName = attributeName;
        attributeValue = new ArrayList<>();
    }

    /**
     * Add an attribute with the give value
     * @param value the value of the new attribute value
     */
    public void addAttributeValue(String value){attributeValue.add(value);}
    public int getSize(){return attributeValue.size();}
    public String getName(){return attributeName;}
    public ArrayList<String> getValues() {return attributeValue;}

    /**
     * return the value of the index of the attribute, return the string null if index out of bound.
     * @param index the index
     * @return the value of the index or the String null.
     */
    public String getValue(int index){
        try{
            return attributeValue.get(index).toString();
        }catch (IndexOutOfBoundsException e){}
        return "null";
    }

    /**
     * compare an index value of this attribute and another attribute, return true if the comparison match the operation
     * @param other the other attribute
     * @param operation the operation for the comparison
     * @param index the index of the value used for comparison
     * @return whether the comparison match the operation
     */
    public boolean compareValue(Attribute other,String operation,int index){
        if(this.getValue(index).equals("null")){
            return false;
        }

        switch (operation){
            case "=":
                return this.getValue(index).equals(other.getValue(index));

            case "!=":
                return !this.getValue(index).equals(other.getValue(index));

            case "<":
                try{
                    return Integer.parseInt(this.getValue(index)) < Integer.parseInt(other.getValue(index));
                }catch(NumberFormatException e){}
                return false;

            case ">":
                try{
                    return Integer.parseInt(this.getValue(index)) > Integer.parseInt(other.getValue(index));
                }catch(NumberFormatException e){}
                return false;

            case ">=":
                try{
                    return Integer.parseInt(this.getValue(index)) >= Integer.parseInt(other.getValue(index));
                }catch(NumberFormatException e){}
                return false;

            case "<=":
                try{
                    return Integer.parseInt(this.getValue(index)) <= Integer.parseInt(other.getValue(index));
                }catch(NumberFormatException e){}
                return false;

            default:
                System.out.println("ERROR: Unknown operands");
                return false;
        }
    }

    /**
     * Create an exact copy of this attribute
     * @return the exact copy of the attribute
     */
    public Attribute clone(){
        Attribute attribute = new Attribute(this.attributeName);
        for (int i = 0; i < attributeValue.size(); i++) {
            attribute.addAttributeValue(this.attributeValue.get(i));
        }
        return attribute;
    }
}
