package nicapp.nic.bihar.nicapp.Model;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;


public class UpdateModel implements KvmSerializable {
    private String fName;
    private String mName;
    private String colName;
    private String colCity;
    private String colState;
    private String branch;
    private String dob;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColCity() {
        return colCity;
    }

    public void setColCity(String colCity) {
        this.colCity = colCity;
    }

    public String getColState() {
        return colState;
    }

    public void setColState(String colState) {
        this.colState = colState;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
