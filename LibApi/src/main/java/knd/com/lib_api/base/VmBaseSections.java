package knd.com.lib_api.base;

/**
 * Created by Dell on 6/28/2017.
 */

public class VmBaseSections<T extends VmBaseSection> extends VmBases<T> {

    public VmBaseSections(int choice) {
        super(choice);
    }

    protected int getSectionType(int type){
        int typeIndex=-1;
        for (VmBaseSection s : items){
            typeIndex++;
            if (s.getSectionType() == type) {
                return typeIndex;
            }
        }
        return -1;
    }

    protected int getItemSizeInSection(int type){
        int itemCount=0;
        int typeIndex = getSectionType(type);
        for (int i=typeIndex;i<items.size();i++){
            if (type != items.get(i).getSectionType())
                break;
            itemCount++;
        }
        return itemCount-1;
    }
}
