package sample;

import java.io.Serializable;

public class Shield implements Serializable {
    private int shieldCount=0;

    public int getShieldCount() {
        return shieldCount;
    }

    public void setShieldCount(int shieldCount) {
        this.shieldCount = shieldCount;
    }
    public void  updateShildCount(int shieldCount){
        this.shieldCount += shieldCount;
    }

}
