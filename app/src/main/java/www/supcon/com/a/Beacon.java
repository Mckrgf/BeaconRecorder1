package www.supcon.com.a;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Beacon {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String num;
    @NotNull
    private String b_id;
    @NotNull
    private String x;
    @NotNull
    private String y;
    @NotNull
    private String z;
    @NotNull
    private String floor;
    @Generated(hash = 1036996911)
    public Beacon(Long id, @NotNull String num, @NotNull String b_id,
            @NotNull String x, @NotNull String y, @NotNull String z,
            @NotNull String floor) {
        this.id = id;
        this.num = num;
        this.b_id = b_id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.floor = floor;
    }
    @Generated(hash = 1143354403)
    public Beacon() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNum() {
        return this.num;
    }
    public void setNum(String num) {
        this.num = num;
    }
    public String getB_id() {
        return this.b_id;
    }
    public void setB_id(String b_id) {
        this.b_id = b_id;
    }
    public String getX() {
        return this.x;
    }
    public void setX(String x) {
        this.x = x;
    }
    public String getY() {
        return this.y;
    }
    public void setY(String y) {
        this.y = y;
    }
    public String getZ() {
        return this.z;
    }
    public void setZ(String z) {
        this.z = z;
    }
    public String getFloor() {
        return this.floor;
    }
    public void setFloor(String floor) {
        this.floor = floor;
    }

}
