package knd.com.lib_core.base;

/**
 * Created by Dell on 6/28/2017.
 */

public abstract class VmBaseSection<T> extends VmBase<T> {

    private int sectionType;
    private String name;

    public VmBaseSection(T t) {
        super(t);
    }

    public VmBaseSection() {

    }

    public void setSectionType(int sectionType) {
        this.sectionType = sectionType;
    }

    public int getSectionType() {
        return sectionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
