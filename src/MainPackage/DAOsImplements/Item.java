package MainPackage.DAOsImplements;

/**
 * Created by ogs10_000 on 07/04/2017.
 */
public class Item {
    int id;
    String value;

    public Item(int id , String value){
        this.id = id;
        this.value = value;
    }
    public int getId(){
        return id;
    }

    public String getValue(){

        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof Item){

           if(getId() == ((Item)obj).getId()){

               return true;
           }
        }
        return false;
    }
}
