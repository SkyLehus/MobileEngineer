package a3.mobile.engineer;

public class Filter {

    public final static String ATTRIBUTE_ID = "ID";
    public final static String ATTRIBUTE_NAME = "Name";
    public final static String ATTRIBUTE_QUAL = "Qual";

    private String ID;
    private String Name;
    private String Qual;

    public Filter() {

    }

    public Filter(String ID, String Name) {
        this.ID = ID;
        this.Name = Name;
    }


    public void setID(String ID) {
        this.ID = ID;
    }
    public String getID() {
        return this.ID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    public String getName() {
        return this.Name;
    }

    public void setQual(String Qual) {
        this.Qual = Qual;
    }
    public String getQual() {
        return this.Qual;
    }
}
