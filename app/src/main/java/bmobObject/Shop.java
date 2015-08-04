package bmobObject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by Albert on 2015/7/15.
 */
public class Shop extends BmobUser {

    private String shopname; // ��������
    private String susername; // �̼��û���
    private String suserpwd; // �̼�����
    private String scat; // ���̷���
    private String business; // ��Ӫҵ��
    private String shopadd; // ���̵�ַ
    private String shoptel;// ������ϵ��ʽ
    private Boolean operatesta; // Ӫҵ״̬
    private String starttime;
    private String endtime;// Ӫҵʱ��
    private Integer totalgood; // ������
    private Integer totalbad; //�ܲ���
    private BmobGeoPoint slonla; // ���̾�γ��
    private Double sendprice; // ���ͼ�
    private Double distributeprice; // ���ͼ�
    private String spicture; // �̼�ͷ��ͼƬ
    private Integer checkstate; // ����ע�����״̬

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Integer getCheckstate() {
        return checkstate;
    }

    public void setCheckstate(Integer checkstate) {
        this.checkstate = checkstate;
    }

    public Double getDistributeprice() {
        return distributeprice;
    }

    public void setDistributeprice(Double distributeprice) {
        this.distributeprice = distributeprice;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Boolean getOperatesta() {
        return operatesta;
    }

    public void setOperatesta(Boolean operatesta) {
        this.operatesta = operatesta;
    }

    public String getScat() {
        return scat;
    }

    public void setScat(String scat) {
        this.scat = scat;
    }

    public Double getSendprice() {
        return sendprice;
    }

    public void setSendprice(Double sendprice) {
        this.sendprice = sendprice;
    }

    public String getShopadd() {
        return shopadd;
    }

    public void setShopadd(String shopadd) {
        this.shopadd = shopadd;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShoptel() {
        return shoptel;
    }

    public void setShoptel(String shoptel) {
        this.shoptel = shoptel;
    }

    public BmobGeoPoint getSlonla() {
        return slonla;
    }

    public void setSlonla(BmobGeoPoint slonla) {
        this.slonla = slonla;
    }

    public String getSpicture() {
        return spicture;
    }

    public void setSpicture(String spicture) {
        this.spicture = spicture;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getSusername() {
        return susername;
    }

    public void setSusername(String susername) {
        this.susername = susername;
    }

    public String getSuserpwd() {
        return suserpwd;
    }

    public void setSuserpwd(String suserpwd) {
        this.suserpwd = suserpwd;
    }

    public Integer getTotalbad() {
        return totalbad;
    }

    public void setTotalbad(Integer totalbad) {
        this.totalbad = totalbad;
    }

    public Integer getTotalgood() {
        return totalgood;
    }

    public void setTotalgood(Integer totalgood) {
        this.totalgood = totalgood;
    }
}
